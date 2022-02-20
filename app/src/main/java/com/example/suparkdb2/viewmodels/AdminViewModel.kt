package com.example.suparkdb2.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suparkdb2.data.Cars
import com.example.suparkdb2.data.ParkingAreas
import com.example.suparkdb2.data.SuParkRepository
import com.example.suparkdb2.data.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(private val suParkRepository: SuParkRepository, private val state: SavedStateHandle): ViewModel(){

    private var allUsers: MutableStateFlow<List<Users>> = MutableStateFlow(emptyList())
    var allCars: MutableStateFlow<List<Cars>> = MutableStateFlow(emptyList())
    var allParkedCars: MutableStateFlow<List<Cars>> = MutableStateFlow(emptyList())


    private var carLocation: MutableState<ParkingAreas?> = mutableStateOf(null) // car location of what, remove this or chnage

    var currentAdminLoginSession: MutableLiveData<Users> = state.getLiveData("current_admin_login_session")
        set(value){
            field = value
            state["current_login_session"] = value
        }

    init {
        Log.d("main","Admin View Model created")

        viewModelScope.launch {
            suParkRepository.getAllParkedCars().collect {
                allParkedCars.value = it
            }
            suParkRepository.getAllUsers().collect {
                allUsers.value = it
            }
            suParkRepository.getAllCars().collect{
                allCars.value = it
            }
        }
    }


    fun onAdminLogin(adminUser: Users){

    }

    fun onAddUser(user: Users){
        suParkRepository.addUser(user)
    }

    fun onAddCar(car: Cars){
        suParkRepository.addCar(car)
    }

    suspend fun findCarByPlate(plateNO: String){ //first find carId from cars, then find parkId from ParkedBy, then find parkName from parkId
        var cid: Int? = null
        var parkId: Int? = null
        suParkRepository.getCarbyPlate(plateNO).collect {
            cid = it.cid
        }
         suParkRepository.getLocationByCarId(cid!!).collect {
            parkId = it
        }
        suParkRepository.getParkingAreaByPid(parkId!!).collect {
            carLocation.value = it
        }
    }

    fun getAllParkedCarCombination(): MutableMap<Cars, Boolean> {
        val isCarParkedMap: MutableMap<Cars, Boolean> = mutableMapOf()
        viewModelScope.launch {
            for(car in allCars.value){
                isCarParkedMap[car] = car in allParkedCars.value
            }
        }
        return isCarParkedMap
    }

}