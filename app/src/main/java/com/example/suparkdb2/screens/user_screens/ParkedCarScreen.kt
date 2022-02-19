package com.example.suparkdb2.screens.user_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.suparkdb2.viewmodels.MainViewmodel

// detailed information of where the car is parked at, when it's parked and who parked it.
// also has a button for declaring leaving

// WHo parked car, is s/he the owner, when it's been parked, Car Model-Age, For how long it's been parked, where it is
@Composable
fun ParkedCarScreen(
    navigateToViewParkingsScreen: ()->Unit,
    parkedById: Int,
    viewModel: MainViewmodel
){
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(12.dp)
    ) {
        CarInfoCard()
    }
}


@Composable
fun CarInfoCard(
    carBrand: String ="vw",
    carModel: String = "golf",
    carAge: Int = 1920
){
    Column(
        Modifier
            .background(Color.White)
            .padding(8.dp)
    ) {
        Surface(
            Modifier
                .padding(bottom = 8.dp)
                .border(1.dp, color = Color.LightGray)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 6.dp, bottomEnd = 6.dp)
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style= SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ){
                        append("Car Brand: ")
                    }
                    append(carBrand)
                }
            )
        }
        Surface(
            Modifier
                .padding(bottom = 8.dp)
                .background(Color.White)
                .border(1.dp, color = Color.LightGray)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 4.dp, bottomEnd = 4.dp)
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style= SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ){
                        append("Car Model: ")
                    }
                    append(carModel)
                }
            )
        }
        Surface(
            Modifier
                .border(1.dp, color = Color.LightGray)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 4.dp, bottomEnd = 4.dp)
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style= SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ){
                        append("Car Age: ")
                    }
                    append("$carAge")
                }
            )
        }
    }
}

@Preview
@Composable
fun CarInfoCardPreview(){
    CarInfoCard()
}

@Composable
fun WhenAndWhereParkedCard(
    parkingAreaName: String,
    parkedAtDateTime: String
){

}

@Composable
fun WhoParkedCard(
    name: String,
    surname: String,
    isOwner: Boolean
){

}

