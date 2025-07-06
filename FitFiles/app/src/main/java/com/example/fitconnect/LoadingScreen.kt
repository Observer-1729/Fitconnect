package com.example.fitconnect

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val violet = Color(ContextCompat.getColor(context, R.color.violet))

    // Use ViewModels with factory to access DAOs
    val dietViewModel: DietViewModel = viewModel(
        modelClass = DietViewModel::class.java,
        factory = DietViewModelFactory(context.applicationContext as Application)
    )
    val exerciseViewModel: ExerciseViewModel = viewModel(
        modelClass = ExerciseViewModel::class.java,
        factory = ExerciseViewModelFactory(context.applicationContext as Application)
    )
    LaunchedEffect(Unit) {



        delay(1000) // simulate splash loading

        val hasDietData = dietViewModel.hasDietData()
        val hasExerciseData = exerciseViewModel.hasExerciseData()

        if (hasDietData && hasExerciseData) {
            navController.navigate("main view") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.fitconnect_logo_nobackground),
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp)
            )

            LinearProgressIndicator(color = violet)
        }
    }
}