package com.example.suparkdb2.screens.admin_screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.suparkdb2.data.Cars
import com.example.suparkdb2.data.Users
import com.example.suparkdb2.viewmodels.AdminViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdminViewAllCarsScreen(adminViewModel: AdminViewModel){
    val allCars by adminViewModel.allCars.collectAsState()
    val allParkedCars = adminViewModel.allParkedCars.collectAsState()

    val carParkedMap: MutableState<Map<Cars, Boolean>> = remember { mutableStateOf(mapOf()) }

    LaunchedEffect(key1 = allCars){
        carParkedMap.value = adminViewModel.getAllParkedCarCombination()
    }

    val listState = rememberLazyListState()

    LazyColumn(Modifier
        .fillMaxSize()
        .background(Color.White),
        state = listState
    ){
        carParkedMap.value.forEach{ (car, isParked) ->
             item(car.cid) {
                SingleCarListCard(car, isParked)
             }
        }
    }

}

@Preview
@Composable
fun SingleCarListCard(
    car: Cars = Cars(22,"Volkswagen","Golf","35 hjlks 8999",13),
    isParked: Boolean = false
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { },
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
                            append("Owner: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) {
                            append("car owner here")
                        }
                    },
                    Modifier
                        .weight(4.5f)
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
                        withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                        ) {
                            append("Is Car Parked: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) {
                            append("$isParked")
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