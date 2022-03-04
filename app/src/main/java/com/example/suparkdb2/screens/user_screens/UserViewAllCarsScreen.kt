package com.example.suparkdb2.screens.user_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.suparkdb2.viewmodels.MainViewmodel

import  androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.example.suparkdb2.data.Cars


// all cars that can be used or owned by the current user are listed here
// no need to make cars clickable, all the functionalities are already implemented in other pages
@Composable
fun UserViewAllCarsScreen(
    navigateToHomeScreen: ()->Unit,
    mainViewmodel: MainViewmodel
){

    val allCars by mainViewmodel.allCarsOfCurrentUser

    LaunchedEffect(key1 = allCars ){
        mainViewmodel.getAllCarsOfAUser()
    }
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            GenericTopAppBar(title = "View Your Cars") { navigateToHomeScreen() }
        }
    ) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = 4.dp)
        ){ // CarInfoCard already has 8.dp padding on all sides
            items(allCars){ car->
                CarInfoCard(car)
            }
        }
    }
}


