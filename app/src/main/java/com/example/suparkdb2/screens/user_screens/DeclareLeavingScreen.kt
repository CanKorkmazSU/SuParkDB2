package com.example.suparkdb2.screens.user_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    Column(
        Modifier.fillMaxSize().background(Color.White),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = plateNo,
            placeholder = { Text(text = "Enter Plate No") },
            label = { Text(text = "Plate No: ") },
            onValueChange ={plateNo = it}
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Declare Leaving")
        }
    }

}