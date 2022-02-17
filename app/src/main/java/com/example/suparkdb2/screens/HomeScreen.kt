package com.example.suparkdb2.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.suparkdb2.data.SuParkRepository
import com.example.suparkdb2.data.Users
import com.example.suparkdb2.viewmodels.MainViewmodel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//Home Screen for non admin users
@Composable
fun HomeScreen(declareEntranceNavigation: ()->Unit, viewmodel: MainViewmodel){

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // this for User
        Button(onClick = { /*TODO*/ }) {
            Text(text = "View your cars")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "View Your parked cars") // this
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Declare Entrance") //
        }
        Button(onClick = { /*TODO*/ }){
            Text(text = "Declare Leaving")
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen(declareEntranceNavigation = {}, viewmodel = viewModel())
}