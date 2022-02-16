package com.example.suparkdb2.Navigation

sealed class Screen(val route: String){
    object HomeScreen: Screen("home_screen")
    object AddNewUserScreen: Screen("add_new_user_screen")
    object DeclareEntranceScreen: Screen("declare_entrance_screen")
    object ViewParkingsScreen: Screen("view_parkings_screen")

}
