package com.example.suparkdb2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.suparkdb2.data.Users
import com.example.suparkdb2.viewmodels.AdminViewModel
import com.example.suparkdb2.viewmodels.MainViewmodel

@Composable
fun LoginScreen(
    navigateToHomeScreen: ()->Unit,
    navigateToAdminHomeScreen:()->Unit,
    viewModel: MainViewmodel,
    adminViewModel: AdminViewModel
){

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var driversLicense by remember { mutableStateOf("")}

    var isStudent by remember { mutableStateOf(true) } // implement a drop-down menu for choosing one of these
    var isPersonnel by remember { mutableStateOf(false) }
    var isFacultyMember by remember { mutableStateOf(false) }
    var isAdmin by remember { mutableStateOf(false) }

    var suid by remember { mutableStateOf(-1) }


    val isLoggedIn by viewModel.userLoggedIn

    LaunchedEffect(key1 = isLoggedIn ){ // this should be changed to check whether the user is admin or user, also implement timed login session,
        viewModel.isUserLoggedIn()
        if(isLoggedIn){
            navigateToHomeScreen()
        }
    }

    val onMenuItemClick: (String)->Unit = {
        when(it){
            "Student"->{
                isStudent = true
                isPersonnel = false
                isFacultyMember = false
                isAdmin = false
            }
            "Faculty Member"->{
                isStudent = false
                isPersonnel = false
                isFacultyMember = true
                isAdmin = false
            }
            "Personnel"->{
                isStudent = false
                isPersonnel = true
                isFacultyMember = false
                isAdmin = false
            }
            else->{
                isStudent = false
                isPersonnel = false
                isFacultyMember = false
                isAdmin = true
            }
        }
    }

    Column(Modifier
        .fillMaxSize()
        .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = name,
            placeholder = { Text(text = "Name") },
            label = { Text(text = "Your Name: ") },
            onValueChange = { name = it }
        )
        OutlinedTextField(value = surname,
            placeholder = { Text(text = "Surname") },
            label = { Text(text = "Your Surname: ") },
            onValueChange = { surname = it }
        )
        OutlinedTextField(value = driversLicense,
            placeholder = { Text(text = "driver's license") },
            label = { Text(text = "Your driver's license: ") },
            onValueChange = { driversLicense = it }
        )
        OutlinedTextField(value = "$suid",
            placeholder = { Text(text = "SU-ID") },
            label = { Text(text = "Your Sabancı ID: ") },
            onValueChange = { suid = it.toInt() }
        )
        RowWithDropDownMenu(menuItemOnClick = onMenuItemClick)
        // put here some functionality for letting admin's to choose between admin login and user login, since they can't declare entrance at admin
        Button(
            onClick = {
                val user: Users? = viewModel.findUserBySuId(suid)
                if (user != null && user.driversLicense == driversLicense) {
                    when (user.isAdmin) {
                        true -> {
                            adminViewModel.onAdminLogin(user)
                            navigateToAdminHomeScreen()
                            //maybe ask it here if they wan't to do user login here instead
                        }
                        else -> {
                            viewModel.onLogin(user)
                            navigateToHomeScreen()
                        }
                    }
                }
            }
        ) {
            Text(text = "Login")
        }
    }
}