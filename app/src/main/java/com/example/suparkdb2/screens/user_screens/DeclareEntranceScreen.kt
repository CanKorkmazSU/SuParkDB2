package com.example.suparkdb2.screens.user_screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suparkdb2.Navigation.Screen
import com.example.suparkdb2.data.Cars
import com.example.suparkdb2.data.Users
import com.example.suparkdb2.viewmodels.MainViewmodel

// view parking screen is screen that allows user to see where their cars parked at
@Composable
fun DeclareEntranceScreen(
    navigateToHomePage: () -> Unit,
    navigateToViewParkingsScreen: () -> Unit,
    mainViewmodel: MainViewmodel,
){

    val currentLoginSession: MutableState<Users> = remember{ mutableStateOf( mainViewmodel.currentLoginSession.value!!)}
    var plateNo by remember {mutableStateOf("")}

    val allAvaiableCars: List<Cars> by mainViewmodel.allAvailableCars
    val selectedCar: Cars? by remember { mutableStateOf(null) }

    // choose available cars
    LaunchedEffect(key1 = allAvaiableCars){
        mainViewmodel.fetchAllAvailableCars()
    }
    Column(
        Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SelectAvailabeCarDropDown(
            onCarSelected = {},
            carsToList = allAvaiableCars
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Declare Entrance")
        }
    }
    // get cars used by the user that aren't currently parked in any parking areas,
    //      ->select from
}

@Composable
fun SelectAvailabeCarDropDown(
    onCarSelected: (Cars)->Unit,
    carsToList: List<Cars>
){
    var expandedState by remember{mutableStateOf(false)}

    Row(
        Modifier
            .width(240.dp)
            .height(32.dp)
            .clickable { // also check if there's available car, else display toast
                expandedState = !expandedState
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Select car to declare entrance",
            color = Color.Gray
        )
        Canvas(
            modifier = Modifier
                .size(12.dp)
                .weight(weight = 1f)
                .align(Alignment.CenterVertically)
        ) {
            drawCircle(color = Color.Cyan)
        }
        DropdownMenu(expanded = expandedState, onDismissRequest = { expandedState = false }) {
            carsToList.forEach{
                DropdownMenuItem(onClick = { onCarSelected(it)}) {
                    SelectCarCard(car =it )
                }
            }
        }
    }
}

@Composable
fun SelectCarCard(car: Cars){
    Card() {

    }
}