package com.example.suparkdb2.screens.user_screens

import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.example.suparkdb2.data.Users
import com.example.suparkdb2.viewmodels.MainViewmodel
import kotlinx.coroutines.flow.MutableStateFlow

// for normal users only, either navigate back to user home screen or to view your parkings screen
@Composable
fun DeclareLeavingScreen(
    navigateToHomeScreen:()->Unit,
    navigateToViewParkingsScreen: ()->Unit,
    mainViewmodel: MainViewmodel
){
    val currentLoginSession by mainViewmodel.currentLoginSession

    var plateNo by remember {mutableStateOf("")}

    OutlinedTextField(value = plateNo,
        placeholder = { Text(text = "Enter Plate No") },
        label = { Text(text = "Plate No: ") },
        onValueChange ={plateNo = it}
    )
    Button(onClick = { /*TODO*/ }) {
        Text(text = "Declare Leaving")
    }
}