package com.example.suparkdb2.Navigation

sealed class Screen(val route: String){
    object HomeScreen: Screen("home_screen")
    object AddNewUserScreen: Screen("add_new_user_screen")
    object DeclareEntranceScreen: Screen("declare_entrance_screen")
    object ViewParkingsScreen: Screen("view_parkings_screen") // normal users only, for now view parkings of all cars used or owned by you
    object AdminViewAllParkingsScreen: Screen("admin_view_all_parkings_screen")
    object AdminHomeScreen: Screen("admin_home_screen")
    object DeclareleavingScreen: Screen("declare_leaving_screen")
    object LoginScreen: Screen("login_screen")
    object UserViewAllCarsScreen: Screen("user_view_all_cars_screen")
    object ParkedCarScreen: Screen("parked_car_screen")
    object FindCarScreen: Screen("find_car_screen")

}
