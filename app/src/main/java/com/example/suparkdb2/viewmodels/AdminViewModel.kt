package com.example.suparkdb2.viewmodels

import androidx.compose.runtime.MutableState
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
    private var allCars: MutableStateFlow<List<Cars>> = MutableStateFlow(emptyList())

    private var carLocation: MutableState<ParkingAreas>? = null

    var currentAdminLoginSession: MutableLiveData<Users> = state.getLiveData("current_admin_login_session")
        set(value){
            field = value
            state["current_login_session"] = value
        }

    init {
        viewModelScope.launch {
            suParkRepository.getAllUsers().collect {
                allUsers.value = it
            }
        }
    }



    fun onAddUser(user: Users){
        suParkRepository.addUser(user)
    }

    fun onAddCar(car: Cars){
        suParkRepository.addCar(car)
    }

    suspend fun findCar(plateNO: String){
         suParkRepository.getCarbyPlate(plateNO).collect {
            carLocation?.value = it
        }

    }
}