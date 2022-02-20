package com.example.suparkdb2.screens.user_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    navigateToParkedCarScreen: (Int) -> Unit,
    viewmodel: MainViewmodel
){
    val allParkedBys by viewmodel.allParkings // parkedBys for current user
    val allParkedCars by viewmodel.allParkedCars// Cars from , first get cars of a user, then
    val parkedCarParkingAreaMap: MutableState<Map<Cars, ParkingAreas>> = remember{ mutableStateOf(mapOf()) }

    LaunchedEffect(key1 = allParkedCars){
        parkedCarParkingAreaMap.value = viewmodel.getAllCarParkingAreaComb()
    }

    val listState = rememberLazyListState()

    LazyColumn(state = listState){
        parkedCarParkingAreaMap.value.forEach{ (car, pa) ->
            item {
                ViewParkedItem(
                    car = car,
                    location = pa,
                    onItemClicked = {
                        navigateToParkedCarScreen(car.cid)
                    }
                )
            }
        }
    }
}


@Preview
@Composable // card for showing a single car with information of where it is parked.
fun ViewParkedItem(
    car: Cars = Cars(22,"Volkswagen","Golf","35 hjlks 8999",13),
    location: ParkingAreas = ParkingAreas("p13", 40, 111),
    onItemClicked: (Int)->Unit ={}
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { onItemClicked(car.cid)},
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Row(
                Modifier
                    .height(24.dp)

            ){
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.W900,
                            color = Color(0xFF4552B8))
                        ) {
                            append("Car Brand: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) {
                            append(car.brand)
                        }
                    },
                    Modifier.weight(4.5f)
                )
                Box(
                    Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f))
                        .height(4.dp)
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.W900,
                            color = Color(0xFF4552B8))
                        ) {
                            append("Car Model: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) {
                            append(car.carModel)
                        }
                    },
                    Modifier
                        .weight(5f)
                        .padding(start = 6.dp)
                )
            }
            Row(
                Modifier
                    .height(24.dp)

            ){
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.W900,
                            color = Color(0xFF4552B8))
                        ) {
                            append("Plate No: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) {
                            append(car.plateNo)
                        }
                    },
                    Modifier.weight(4.5f))
                Box(
                    Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f))
                        .height(4.dp)
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.W900,
                            color = Color(0xFF4552B8))
                        ) {
                            append("Parking Area: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) {
                            append(location.parkName)
                        }
                    },
                    Modifier
                        .weight(5f)
                        .padding(start = 6.dp)
                )
            }
        }
    }
}


@Composable
fun ViewParkingsScreenTopAppBar(){

}

