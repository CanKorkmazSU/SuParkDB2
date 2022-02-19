package com.example.suparkdb2.screens.user_screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.suparkdb2.data.Cars
import com.example.suparkdb2.data.ParkedBy
import com.example.suparkdb2.data.ParkingAreas
import com.example.suparkdb2.viewmodels.MainViewmodel

// For normal users only , for now, list all parkings for the user,
//
// users have two ways of declaring_leaving, first from the declare leaving screen directly,
// other is from View_parkings_screen(this screen)-> click on one of the parked cars -> parked_car_screen -> declare_leaving_button
//
// create a parked_car_screen so user can press
@Composable
fun ViewParkingsScreen( // lazy list of ViewParkingItems here,
    navigateToHomeScreen: () -> Unit,
    navigateToParkedCarScreen: () -> Unit,
    viewmodel: MainViewmodel
){
    val allParkedBys: MutableState<List<ParkedBy>> = remember{ mutableStateOf(emptyList())} // parkedBys for current user
    val allParkedCars: MutableState<List<Cars>> = remember { mutableStateOf(emptyList()) } // Cars from , first get cars of a user, then

}



@Composable // card for showing a single car with information of where it is parked.
fun ViewParkingItem(

){

}