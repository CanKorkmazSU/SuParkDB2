package com.example.suparkdb2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.suparkdb2.data.Cars
import com.example.suparkdb2.data.SuParkRepository
import com.example.suparkdb2.data.Users
import com.example.suparkdb2.viewmodels.MainViewmodel
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.suparkdb2.viewmodels.AdminViewModel

import dagger.hilt.android.qualifiers.ApplicationContext


@Composable
fun AddUserScreen(navController: NavController, viewmodel: MainViewmodel, adminViewmodel: AdminViewModel){

    var name by remember {mutableStateOf("")}
    var surname by remember {mutableStateOf("")}
    var driversLicense by remember {mutableStateOf("")}

    var isStudent by remember { mutableStateOf(true)}
    var isOwner by remember { mutableStateOf(true)}
    var isPersonnel by remember { mutableStateOf(false)}
    var isFacultyMember by remember { mutableStateOf(false)}
    var isAdmin by remember { mutableStateOf(false)}

    var age by remember {mutableStateOf(-1)}
    var suid by remember {mutableStateOf(-1)}

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(value = name,
            placeholder = { Text(text = "Name") },
            label = { Text(text = "Your Name: ") },
            onValueChange ={name = it}
        )
        OutlinedTextField(value = surname,
            placeholder = { Text(text = "Surname") },
            label = { Text(text = "Your Surname: ") },
            onValueChange ={surname = it}
        )
        OutlinedTextField(value = driversLicense,
            placeholder = { Text(text = "driver's license") },
            label = { Text(text = "Your driver's license: ") },
            onValueChange ={driversLicense = it}
        )
        OutlinedTextField(value = "$suid",
            placeholder = { Text(text = "SU-ID") },
            label = { Text(text = "Your Sabancı ID: ") },
            onValueChange ={suid = it.toInt()}
        )
        OutlinedTextField(value = "$age",
            placeholder = { Text(text = "Age") },
            label = { Text(text = "Your Age: ") },
            onValueChange ={age =it.toInt()}
        )

        Button(
            onClick = {
                adminViewmodel.
                onAddUser(
                    user = Users(
                        name,
                        surname,
                        driversLicense,
                        age,
                        suid,
                        true,
                        true,
                        false,
                        false,
                        false
                    )
                )
            }
        ){
            Text(text = "Add User")
        }
    }
}

