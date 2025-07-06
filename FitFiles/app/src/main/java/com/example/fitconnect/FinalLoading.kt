package com.example.fitconnect

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
@Composable
fun LoadingScreen(
    userId: String,
    onLoadingFinished: () -> Unit,
    mainViewModel: MainViewModel,
    dietViewModel: DietViewModel,
    exerciseViewModel: ExerciseViewModel
) {
    val isFetching by mainViewModel.isFetching.collectAsState()
    var user by remember { mutableStateOf<UserEntity?>(null) }

    val context = LocalContext.current
    val violet = Color(ContextCompat.getColor(context, R.color.heads))

    val userInputViewModel: UserInputViewModel = viewModel()

    // Fetch user once
    LaunchedEffect(Unit) {
        Log.d("LoadingScreen", "Step 1: Polling for user with ID $userId")

        var fetchedUser: UserEntity? = null

        while (fetchedUser == null) {
            fetchedUser = userInputViewModel.userDao.getUser(userId)
            if (fetchedUser == null) {
                Log.d("LoadingScreen", "User not yet in Room... waiting...")
                delay(500) // wait and try again
            }
        }

        Log.d("LoadingScreen", "Step 2: User found! ID = $userId")

        mainViewModel.runFitnessPlanLogic(
            user = fetchedUser,
            dietViewModel = dietViewModel,
            exerciseViewModel = exerciseViewModel
        )

        // Step 3: Wait for fetching to finish
        while (mainViewModel.isFetching.value) {
            delay(100)
        }

        delay(300) // tiny delay for smoother transition
        Log.d("LoadingScreen", "Step 3: Navigation triggered")
        onLoadingFinished()
    }


    // UI
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Syncing your FitConnect data...", style = MaterialTheme.typography.titleMedium, color = Color.White)
            Spacer(modifier = Modifier.height(24.dp))
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = violet)
        }
    }
}
