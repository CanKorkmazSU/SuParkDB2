package com.example.suparkdb2.screens

import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suparkdb2.Navigation.Screen
import com.example.suparkdb2.data.Users
import com.example.suparkdb2.viewmodels.MainViewmodel

// view parking screen is screen that allows user to see where their cars parked at
@Composable
fun DeclareEntranceScreen(navigateToHomePage: ()->Unit, navigateToViewParkingsScreen: ()->Unit, mainViewmodel: MainViewmodel){

    val currentLoginSession: MutableState<Users> = remember{ mutableStateOf( mainViewmodel.currentLoginSession.value!!)}
    var plateNo by remember {mutableStateOf("")}

    OutlinedTextField(value = plateNo,
        placeholder = { Text(text = "Enter Plate No") },
        label = { Text(text = "Plate No: ") },
        onValueChange ={plateNo = it}
    )
    Button(onClick = { /*TODO*/ }) {
        Text(text = "Declare Entrance")
    }

    // get cars used by the user that aren't currently parked in any parking areas,
    //      ->select from
}