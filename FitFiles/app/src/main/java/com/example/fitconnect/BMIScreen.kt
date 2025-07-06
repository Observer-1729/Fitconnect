package com.example.fitconnect

import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Composable
fun GoalSelectionScreen(viewModel: UserInputViewModel, onGPSelect: () -> Unit) {
    var selectedGoal by remember { mutableStateOf(viewModel.goal.value) }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val context = LocalContext.current
    val dietViewModel: DietViewModel = viewModel(factory = DietViewModelFactory(context.applicationContext as Application))
    val exerciseViewModel: ExerciseViewModel = viewModel(factory = ExerciseViewModelFactory(context.applicationContext as Application))

    val violet = Color(ContextCompat.getColor(context, R.color.violet))

    LaunchedEffect(Unit) { viewModel.calculateBMI() }

    LaunchedEffect(viewModel.goal.value) { selectedGoal = viewModel.goal.value }



    val bmiValue = viewModel.bmi.value
    val bmiCategory = when {
        bmiValue < 18.5 -> "Underweight"
        bmiValue in 18.5..24.9 -> "Normal"
        bmiValue in 25.0..29.9 -> "Overweight"
        else -> "Obese"
    }

    val bmiColor = when (bmiCategory) {
        "Underweight" -> Color.Yellow
        "Normal" -> Color.Green
        "Overweight" -> Color.Red
        "Obese" -> Color(0xFF000000)
        else -> Color.Gray
    }

    val goalOptions = listOf(
        "Gain Weight", "Lose Weight", "Get Fitter",
        "Gain More Flexibility", "Learn the Basics"
    )

    val filteredGoals = goalOptions.filterNot {
        (bmiCategory in listOf("Overweight", "Obese") && it == "Gain Weight") ||
                (bmiCategory == "Underweight" && it == "Lose Weight")
    }

    LaunchedEffect(selectedGoal) {
        val index = filteredGoals.indexOf(selectedGoal)
        if (index >= 0) {
            coroutineScope.launch { listState.scrollToItem(index) }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp))
        ) {
            Column(
                modifier = Modifier.background(Color.Gray).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Your BMI is: $bmiValue"
                    , color = bmiColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("UnderWeight", color = Color(0xFFFFEB3B), fontSize = 14.sp)
                    Text("Normal", color = Color.Green, fontSize = 14.sp)
                    Text("OverWeight", color = Color.Red, fontSize = 14.sp)
                    Text("Obese", color = Color.Black, fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))
        Text("What's Your Fitness Goal?", color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 10.dp))

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth().weight(1f).padding(8.dp)
        ) {
            items(filteredGoals) { goal ->
                val isSelected = goal == selectedGoal
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            selectedGoal = goal
                            viewModel.goal.value = goal
                        }
                        .background(if (isSelected) Color(0x926C27B0) else Color.Transparent, RoundedCornerShape(12.dp))

                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = goal,
                        color = Color.White,
                        fontSize = if (isSelected) 20.sp else 16.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    // Launch a coroutine to handle asynchronous tasks
                    coroutineScope.launch {
                        viewModel.goal.value = selectedGoal
                        Log.d("Firebase", "Button clicked, attempting to save user data...")

                        // First, save user data to Firestore
                        val isSaved = suspendCancellableCoroutine<Boolean> { continuation ->
                            viewModel.saveUserData { isSuccess ->
                                if (isSuccess) {
                                    Log.d("Firebase", "User data successfully saved.")
                                    continuation.resume(true)  // Successfully saved, resume the continuation
                                } else {
                                    Log.e("Firebase", "Failed to save user data.")
                                    continuation.resume(false)  // Failed to save, resume with failure
                                }
                            }
                        }

                        // After saving user data, fetch user data only if save was successful
                        if (isSaved) {
                            Log.d("Firebase", "Now fetching user data...")

                            viewModel.getUserData()

                            // 🔁 Wait until the user actually exists in Room before navigating
                            val userId = FirebaseAuth.getInstance().currentUser?.uid
                            if (userId != null) {
                                var userInRoom: UserEntity? = null
                                while (userInRoom == null) {
                                    userInRoom = viewModel.userDao.getUser(userId)
                                    Log.d("GoalSelection", "Waiting for user to appear in Room...")
                                    delay(200)
                                }
                            }
//                            val selectedId = listOf("1","2","3")


                            Log.d("Firebase", "User is now available in Room, navigating...")
                            onGPSelect()
                        }

                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                colors = ButtonDefaults.buttonColors(containerColor = violet, contentColor = Color.White)
            ) {
                Text("Generate Your Plan")
            }

        }
    }
}
