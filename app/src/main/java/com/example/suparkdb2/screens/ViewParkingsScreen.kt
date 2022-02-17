package com.example.suparkdb2.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.suparkdb2.data.Cars
import com.example.suparkdb2.data.ParkingAreas

// For normal users only , for now, list all parkings for the user
@Composable
fun ViewParkingsScreen(){

    val allParkedCars: MutableState<Map<Cars, ParkingAreas>>
}