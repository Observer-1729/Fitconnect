package com.example.fitconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.fitconnect.ui.theme.FitConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setDailyTaskClearAlarm(this)
        setContent {
            FitConnectTheme {
                val navController = rememberNavController()
                val viewModel:MainViewModel = viewModel()
                val pd = PaddingValues()
                AppNavigation(navController = navController, viewModel = viewModel,pd =pd)

            }
        }
        NotificationUtils.createNotificationChannel(this)

    }
}
