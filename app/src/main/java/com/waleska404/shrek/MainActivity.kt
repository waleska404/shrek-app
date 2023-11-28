package com.waleska404.shrek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.waleska404.shrek.navigation.SetupNavGraph
import com.waleska404.shrek.ui.theme.ShrekTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShrekTheme {
                navController = rememberNavController()
                SetupNavGraph(navController)
            }
        }
    }
}