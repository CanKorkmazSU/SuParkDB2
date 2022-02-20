package com.example.suparkdb2.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.suparkdb2.screens.admin_screens.AddUserScreen
import com.example.suparkdb2.screens.user_screens.*
import com.example.suparkdb2.viewmodels.AdminViewModel
import com.example.suparkdb2.viewmodels.MainViewmodel


@Composable
fun Navigation(navController: NavHostController, mainViewmodel: MainViewmodel, adminViewModel: AdminViewModel){
    NavHost(navController = navController , startDestination = Screen.LoginScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                {},
                {navController.navigate(Screen.ViewParkingsScreen.route)},
                {navController.navigate(Screen.DeclareEntranceScreen.route)},
                {navController.navigate(Screen.DeclareleavingScreen.route)},
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
                {navController.navigate(Screen.ViewParkingsScreen.route)},
                mainViewmodel
            )
        }
        composable(Screen.DeclareleavingScreen.route){
            DeclareLeavingScreen(
                navigateToHomeScreen = {
                    navController.navigate(Screen.HomeScreen.route){
                    popUpTo(Screen.HomeScreen.route){
                        inclusive = true
                    }
                }},
                navigateToViewParkingsScreen = { navController.navigate(Screen.ViewParkingsScreen.route)},
                mainViewmodel = mainViewmodel
            )
        }
        composable(Screen.ViewParkingsScreen.route){
            ViewParkingsScreen(
                navigateToHomeScreen = { navController.navigate(Screen.HomeScreen.route){
                    popUpTo(Screen.HomeScreen.route){
                        inclusive = true
                    }
                } },
                navigateToParkedCarScreen = { navController.navigate(Screen.ParkedCarScreen.route) },
                viewmodel = mainViewmodel
            )
        }
        composable(
            Screen.ParkedCarScreen.route + "/{parkedById}",
            listOf(navArgument("parkedById" ){type = NavType.IntType})
        ){
            ParkedCarScreen(
                navigateToViewParkingsScreen = { navController.navigate(Screen.ViewParkingsScreen.route)},
                carId = it.arguments?.getInt("parkedById") ?: 13,
                viewModel = mainViewmodel
            )   
        }
    }
}