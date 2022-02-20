package com.example.suparkdb2.viewmodels

import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.bundleOf
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suparkdb2.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.time.Duration

/* this viewmodel should
* only be used for user functionalites
* , logic for admin shouldn't be here
*
* todo: use savedstatehandle to persist currentLoginUser, also implement mechanism for checkign whether the login is recent
*
* todo: implement automatic parking slot finding.
* */

@HiltViewModel
class MainViewmodel @Inject constructor(
    private val suParkRepository: SuParkRepository,
    private val state: SavedStateHandle
): ViewModel() {

    private var allUsers: MutableStateFlow<List<Users>> = MutableStateFlow(emptyList())// all users in the database
    private var allCars: MutableStateFlow<List<Cars>> = MutableStateFlow(emptyList())// all cars in the database

    /*var currentLoginSession: MutableLiveData<Users> = state.getLiveData("current_login_session")
        set(value){
            field = value
            state["current_login_session"] = value
        }
   ;*/

    private var currentUserSuId: Int? = state["current_login_suid"]
        set(value){
            field = value
            state["current_login_suid"] = value
        }
    ;

    var currentLoginSession: MutableState<Users?> = mutableStateOf(null)
    val userLoggedIn: MutableState<Boolean> = mutableStateOf(false)

    val allParkedCars: MutableState<List<Cars>> = mutableStateOf(emptyList()) // all parked cars of the user
    val allParkings: MutableState<List<ParkedBy>> = mutableStateOf(emptyList()) // all parkings of the user
    val allAvailableCars: MutableState<List<Cars>> = mutableStateOf(emptyList())

    val selectedCar: MutableState<Cars?> = mutableStateOf(null) // all parkings of the user

    //var allParkedCarParkingComb: Map<Cars, ParkingAreas>

    init {
        Log.d("main","Main View Model created")
        currentUserSuId?.let { getCurrentUser(it) }

        userLoggedIn.value = currentLoginSession.value != null
        var job1: Job? = null
        if(userLoggedIn.value){
            job1 =viewModelScope.launch {
                val job2 =viewModelScope.launch {
                    findAllParkings()

                }
                job2.join()
                fetchAllActiveParkings() // actually fetchALlActiveParkings does redundant fetching, can be simplified by using allParkings from findAllPaekings()
            }
        }

        viewModelScope.launch {
            job1?.join()
            suParkRepository.getAllUsers().collect {
                allUsers.value = it
            }
        }
        viewModelScope.launch {
            suParkRepository.getAllCars().collect{
                allCars.value = it
            }

        }
    }

    fun isUserLoggedIn(){ // this may not even be needed
        userLoggedIn.value = currentLoginSession.value != null
    }

    private fun getCurrentUser(suId: Int){
        viewModelScope.launch {
            suParkRepository.getUserbySUid(suId).collect {
                currentLoginSession.value = it
            }
        }
    }

    fun findUserBySuId(suId: Int): Users?{
        var user: Users? = null
        viewModelScope.launch {
            suParkRepository.getUserbySUid(suId).collect {
                user = it
            }
        }
        return user
    }

    fun onLogin(user: Users){
        /*if(userLoginValidate(user)){
            currentLoginSession.value = user
        }*/
        currentLoginSession.value = user
        currentUserSuId = user.suID
    }

    fun userLoginValidate(user: Users) = user in allUsers.value // probably won't need this, validation is done in LoginScreen

    fun findAllParkings(){ // all active parkings of the current user
        viewModelScope.launch(Dispatchers.IO) {
            suParkRepository.getAllParkedCarsBySuId(currentLoginSession.value!!.suID).collect {
                allParkings.value = it
            }
        }
    }
    fun findCarByCarId(cid: Int){
        viewModelScope.launch{
            suParkRepository.getCarsByCarId(cid).collect {
                selectedCar.value= it
            }
        }

    }

    fun onDeclareEntranceWithPlateNo(plateNo: String, parkId: Int){
        var carId: Int? = null
        viewModelScope.launch(Dispatchers.IO) {
            suParkRepository.getCarbyPlate(plateNo).collect {
                carId = it.cid
            }
        }
        val entrance = ParkedBy(
            currentLoginSession.value!!.suID,
            carId!!,
            parkId,
            LocalDateTime.now().toString(),
            null,
            0
        )
        viewModelScope.launch(Dispatchers.IO) {
            suParkRepository.addParkedBy(entrance)
        }
    }


    // first fetch all active parkings from ParkedBy with suId,
    // then query cars with the cid from ParkedBy,

    //!!! this is for finding parked CARS!!!, not parkedBYs, use findAllParkings() if you want to fetch all active parkedBys for the current user
    private fun fetchAllActiveParkings(){ // this will fetch all active parkings(parked cars) of cars that that can be used by the current user
        //var activeParkings: List<ParkedBy> = emptyList()
        val parkedCars: MutableList<Cars> = mutableListOf()
       /* viewModelScope.launch(Dispatchers.IO) {
            suParkRepository.getAllParkedCarsBySuId(currentLoginSession.value!!.suID).collect{
                activeParkings = it
            }
        }*/
        // implement .join() between these two, it can be problematic
        for(parking in allParkings.value){
            viewModelScope.launch(Dispatchers.IO) {
                suParkRepository.getCarsByCarId(parking.cid).collect{
                    parkedCars.add(it)
                }
            }
        }
        allParkedCars.value = parkedCars
    }

    fun fetchAllAvailableCars(){
        val availableCars = allCars.value.subtract(allParkedCars.value.toSet()).toList()
        allAvailableCars.value = availableCars
    }
    fun getAllCarParkingAreaComb(): MutableMap<Cars, ParkingAreas>{ // users's cars(cid), user's parkedBys(cid, suid, pid)
        val parkedCarParkingArea: MutableMap<Cars, ParkingAreas> = mutableMapOf()
        viewModelScope.launch {
            for(car in allParkedCars.value){
                 suParkRepository.getActiveParkingAreaForCar(car.cid).collect {
                     parkedCarParkingArea[car] = it
                 }
            }
        }
        return parkedCarParkingArea
    }
}

