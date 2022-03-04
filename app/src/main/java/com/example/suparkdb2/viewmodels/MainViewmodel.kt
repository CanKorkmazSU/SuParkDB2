package com.example.suparkdb2.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.suparkdb2.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import java.time.LocalDateTime
import javax.inject.Inject

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
    private val state: SavedStateHandle,
    private val storeRepository: DataStoreRepository
): ViewModel() {

    private var allUsers: MutableStateFlow<List<Users>> = MutableStateFlow(emptyList())// all users in the database
    private var allCars: MutableStateFlow<List<Cars>> = MutableStateFlow(emptyList())// all cars in the database
    var allCarsOfCurrentUser: MutableState<List<Cars>> = mutableStateOf(emptyList())


    private var currentUserSuId: MutableStateFlow<Int> = MutableStateFlow(0)
    var currentLoginSession: MutableState<Users?> = mutableStateOf(null)
    val userLoggedIn: MutableState<Boolean> = mutableStateOf(false)

    val allParkedCars: MutableState<List<Cars>> = mutableStateOf(emptyList()) // all parked cars of the user
    val allParkings: MutableState<List<ParkedBy>> = mutableStateOf(emptyList()) // all parkings of the user
    val allAvailableCars: MutableState<List<Cars>> = mutableStateOf(emptyList())

    val selectedCar: MutableState<Cars?> = mutableStateOf(null) // all parkings of the user

    //var allParkedCarParkingComb: Map<Cars, ParkingAreas>

    init {
        Log.d("main","Main View Model created")

        viewModelScope.launch(Dispatchers.IO){
            Log.d("main","inside viewmodel coroutine scope 1")
            val job1 =viewModelScope.launch(Dispatchers.IO){
                readSuId()
            }
            Log.d("main","inside viewmodel coroutine scope 2: --> ${currentUserSuId.value}}")
            viewModelScope.launch(Dispatchers.IO) {
                delay(500)
                if(currentUserSuId.value != 0){
                    getCurrentUser(currentUserSuId.value)
                }
            }


        }
        isUserLoggedIn()
        Log.d("main", "  user logged in : ${userLoggedIn.value}")


        if(userLoggedIn.value){
            viewModelScope.launch(Dispatchers.IO) {
                val job2 =viewModelScope.launch {
                    findAllParkings()
                }
                //job2.join()
                fetchAllActiveParkings() // actually fetchALlActiveParkings does redundant fetching, can be simplified by using allParkings from findAllPaerings()
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            suParkRepository.getAllUsers().collect {
                allUsers.value = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            suParkRepository.getAllCars().collect{
                allCars.value = it
            }
        }
    }

    fun clearCurrentLogin(){
        currentUserSuId.value = 0
        userLoggedIn.value = false
        currentLoginSession.value= null
        viewModelScope.launch(Dispatchers.IO) {
            storeRepository.clearAllData()
        }
    }

    private suspend fun readSuId(){
        storeRepository.suIdKeyFlow.collect {
            currentUserSuId.value = it
            Log.d("main", "current user su id flow: ${currentUserSuId.value}")
        }
    }

    fun isUserLoggedIn(){ // this may not even be needed
        userLoggedIn.value = currentLoginSession.value != null
    }

    private fun getCurrentUser(suId: Int){
        currentLoginSession.value =suParkRepository.getUserbySUid(suId)
    }

    fun findUserBySuId(suId: Int): Users? {

        return suParkRepository.getUserbySUid(suId)
    }

    fun onLogin(user: Users){
        /*if(userLoginValidate(user)){
            currentLoginSession.value = user
        }*/
        currentLoginSession.value = user
        currentUserSuId.value = user.suID
        viewModelScope.launch {
            storeRepository.persistSuId(currentUserSuId.value)
            isUserLoggedIn()
        }
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
        viewModelScope.launch(Dispatchers.IO){
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
        viewModelScope.launch(Dispatchers.IO) {
            for(car in allParkedCars.value){
                 suParkRepository.getActiveParkingAreaForCar(car.cid).collect {
                     parkedCarParkingArea[car] = it
                 }
            }
        }
        return parkedCarParkingArea
    }

    // this should get all cars that can be used or owned by the current user
    fun getAllCarsOfAUser(){
        viewModelScope.launch(Dispatchers.IO) {
            suParkRepository.getCarsOfASingleUser(currentUserSuId.value).collect {
                allCarsOfCurrentUser.value = it
            }
        }
    }

}

