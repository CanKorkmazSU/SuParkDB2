package com.example.suparkdb2

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.example.suparkdb2.Navigation.Navigation
import com.example.suparkdb2.screens.HomeScreen
import com.example.suparkdb2.ui.theme.SuParkDBTheme
import com.example.suparkdb2.viewmodels.AdminViewModel
import com.example.suparkdb2.viewmodels.MainViewmodel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.NullPointerException
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val mainViewModel: MainViewmodel by viewModels()
    private val adminViewModel: AdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            SuParkDBTheme {
                Navigation(
                    navController = navController,
                    mainViewmodel = mainViewModel,
                    adminViewModel = adminViewModel
                )
            }
        }
    }
}
