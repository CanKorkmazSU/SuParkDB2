package com.example.suparkdb2.viewmodels

import android.os.Bundle
import android.os.LocaleList
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.bundleOf
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suparkdb2.data.Cars
import com.example.suparkdb2.data.ParkedBy
import com.example.suparkdb2.data.SuParkRepository
import com.example.suparkdb2.data.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
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
class MainViewmodel @Inject constructor(private val suParkRepository: SuParkRepository, private val state: SavedStateHandle): ViewModel() {

    private var allUsers: MutableStateFlow<List<Users>> = MutableStateFlow(emptyList())
    private var allCars: MutableStateFlow<List<Cars>> = MutableStateFlow(emptyList())

    var currentLoginSession: MutableLiveData<Users> = state.getLiveData("current_login_session")
        set(value){
            field = value
            state["current_login_session"] = value
        }
   ;
    val allParkedCars: MutableState<List<Cars>> = mutableStateOf(emptyList())
    val allParkings: MutableState<List<ParkedBy>> = mutableStateOf(emptyList())
    val userLoggedIn: MutableState<Boolean> = mutableStateOf(false)

    init {
        viewModelScope.launch {
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
    }

    fun userLoginValidate(user: Users) = user in allUsers.value // probably won't need this, validation is done in LoginScreen


    fun findAllParkings(){
        viewModelScope.launch(Dispatchers.IO) {
            suParkRepository.getAllParkedCarsBySuId(currentLoginSession.value!!.suID).collect {
                allParkings.value = it
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
    // then query cars with the cid from ParkedBy

    fun fetchAllActiveParkings(){ // this will fetch all active parkings(parked cars) of cars that that can be used by the current user
        var activeParkings: List<ParkedBy> = emptyList()
        val parkedCars: MutableList<Cars> = mutableListOf()
        viewModelScope.launch(Dispatchers.IO) {
            suParkRepository.getAllParkedCarsBySuId(currentLoginSession.value!!.suID).collect{
                activeParkings = it
            }
        }
        // implement .join() between these two, it can be problematic
        for(parking in activeParkings){
            viewModelScope.launch(Dispatchers.IO) {
                suParkRepository.getCarsByCarId(parking.cid).collect{
                    parkedCars.add(it)
                }
            }
        }
        allParkedCars.value = parkedCars
    }
}

