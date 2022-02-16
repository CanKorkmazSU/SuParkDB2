package com.example.suparkdb2.viewmodels

import android.os.Bundle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.bundleOf
import androidx.lifecycle.*
import com.example.suparkdb2.data.Cars
import com.example.suparkdb2.data.ParkedBy
import com.example.suparkdb2.data.SuParkRepository
import com.example.suparkdb2.data.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/* this viewmodel should
* only be used for user functionalites
* , logic for admin shouldn't be here, except log-in
*
* // todo: use savedstatehandle to persist currentLoginUser, also implement mechanism for checkign whether the login is recent
* */

@HiltViewModel
class MainViewmodel @Inject constructor(private val suParkRepository: SuParkRepository, private val state: SavedStateHandle): ViewModel() {


   var currentLoginSession: MutableLiveData<Users> = state.getLiveData("current_login_session")
        set(value){
            field = value
            state["current_login_session"] = value
        }


    init {

    }

    fun onLogin(){

    }

     fun onDeclareEntrance(){

     }
}

