package com.example.suparkdb2.screens

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.example.suparkdb2.viewmodels.MainViewmodel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun DeclareLeavingScreen(viewmodel: MainViewmodel){
    var plateNo by remember {
        mutableStateOf(-1)
    }
    OutlinedTextField(value = "$plateNo",
        placeholder = { Text(text = "Enter Plate No") },
        label = { Text(text = "Plate No: ") },
        onValueChange ={plateNo = it.toInt()}
    )
}