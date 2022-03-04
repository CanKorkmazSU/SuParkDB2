package com.example.suparkdb2.screens.user_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suparkdb2.viewmodels.MainViewmodel

//Home Screen for non admin users
@Composable
fun HomeScreen(
    navigateToLoginScren:()->Unit,
    navigateToViewCarsScreen:()->Unit,
    navigateToViewParkedCarsScreen:()->Unit,
    navigateToDeclareEntranceScreen: ()->Unit,
    navigateToDeclareLeavingScreen:()->Unit,
    viewmodel: MainViewmodel
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color.White)
    ){

        GenericTopAppBar("Login Screen") {
            viewmodel.clearCurrentLogin()
            navigateToLoginScren()
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // this for User
            Button(onClick = { navigateToViewCarsScreen()}) {
                Text(text = "View your cars")
            }
            Button(onClick = { navigateToViewParkedCarsScreen() }) {
                Text(text = "View Your parked cars") // this
            }
            Button(onClick = { navigateToDeclareEntranceScreen()}) {
                Text(text = "Declare Entrance") //
            }
            Button(onClick = { navigateToDeclareLeavingScreen() }){
                Text(text = "Declare Leaving")
            }
        }
    }

}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen( {},{},{},{},{}, viewmodel = viewModel())
}


@Composable
fun GenericTopAppBar(
    title: String,
    onArrowPressed: ()-> Unit // takes a navigation lambda
){
    TopAppBar(
        title = { Text(title) },
        actions ={
            BackArrowAction(onArrowPressed)
        }
    )
}
@Preview
@Composable
fun HomeTopBarPreview(){
    GenericTopAppBar("Login Screen") {}
}

@Composable
fun BackArrowAction(
    onArrowPressed:()->Unit
){
    IconButton(
        onClick = { onArrowPressed() }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack, // change this to logout icon
            contentDescription = "Logout Icon"
        )
    }
}

