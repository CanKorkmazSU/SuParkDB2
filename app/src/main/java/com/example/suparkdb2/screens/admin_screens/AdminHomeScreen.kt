package com.example.suparkdb2.screens.admin_screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AdminHomeScreen(
    navigateToAddUserScreen: ()->Unit,
    navigateToDeleteUserScreen: ()->Unit,
    navigateToAddCarScreen:()->Unit,
    navigateToAddParkingArea:()->Unit,
    //adminViewModel: AdminViewModel
){

    TopAppBar(
        title = {
            Row(){
                Text(text = "Admin Home Screen")
            }
        }
    )
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // this for User
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Add User")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Delete User")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Add Car")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Add Parking Area")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Declare Entrance")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Declare Leaving")
        }
    }
}


@Preview
@Composable
fun AdminHomeScreenPreview(){
    AdminHomeScreen(
        navigateToAddUserScreen = { /*TODO*/ },
        navigateToDeleteUserScreen = { /*TODO*/ },
        navigateToAddCarScreen = { /*TODO*/ }) {
        
    }
}