package com.example.suparkdb2.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.suparkdb2.screens.AddUserScreen
import com.example.suparkdb2.screens.DeclareEntranceScreen
import com.example.suparkdb2.screens.HomeScreen
import com.example.suparkdb2.viewmodels.AdminViewModel
import com.example.suparkdb2.viewmodels.MainViewmodel


@Composable
fun Navigation(navController: NavHostController, mainViewmodel: MainViewmodel, adminViewModel: AdminViewModel){
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreen({navController.navigate(Screen.DeclareEntranceScreen.route)},
                mainViewmodel
            )
        }
        composable(Screen.AddNewUserScreen.route){
            AddUserScreen(
                navController = navController,
                mainViewmodel,
                adminViewModel
            )
        }
        composable(Screen.DeclareEntranceScreen.route){ // to be filled, arguments =
            DeclareEntranceScreen(
                {navController.navigate(Screen.HomeScreen.route)},
                {navController.navigate(Screen.ViewParkingsScreen.route)}
            )
        }
    }
}