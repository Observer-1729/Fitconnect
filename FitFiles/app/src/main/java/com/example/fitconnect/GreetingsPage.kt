package com.example.fitconnect

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//@Composable
//fun GreetingScreen(viewModel:UserInputViewModel, onContinue: () -> Unit) {
//    val firstName by viewModel.firstName
//    val lastName by viewModel.lastName
//    var isLoading by remember { mutableStateOf(true) }
//
//    // Fetch user data from Firestore
//    LaunchedEffect(Unit) {
//        viewModel.getUserData { fetchedFirstName, fetchedLastName ->
//            viewModel.firstName.value = fetchedFirstName ?: "User"
//            viewModel.lastName.value = fetchedLastName ?: ""
//            isLoading = false
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black),
//        contentAlignment = Alignment.Center
//    ) {
//        if (isLoading) {
//            CircularProgressIndicator(color = Color.White)
//        } else {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    text = "Welcome $firstName $lastName!",
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White
//                )
//
//                Spacer(modifier = Modifier.height(20.dp))
//
//                Button(
//                    onClick = onContinue,
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0))
//                ) {
//                    Text("Continue", color = Color.White)
//                }
//            }
//        }
//    }
//}
