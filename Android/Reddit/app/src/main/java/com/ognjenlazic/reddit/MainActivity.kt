package com.ognjenlazic.reddit

import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.ognjenlazic.reddit.navigation.SetupNavGraph
import com.ognjenlazic.reddit.ui.theme.RedditTheme

class MainActivity : ComponentActivity() {

    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RedditTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }



    }
}