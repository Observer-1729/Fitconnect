package com.example.fitconnect

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onNavigateToSignUp: () -> Unit, onNavigateToProfile: () -> Unit, onNavigateToReset:()-> Unit,
                onNavigateToLoading:()-> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val violet = Color(ContextCompat.getColor(context, R.color.violet))

    val coroutineScope = rememberCoroutineScope()
    val viewModel: UserInputViewModel = viewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .pointerInput(Unit) {
                detectTapGestures {
                    // Hide the keyboard when tapping outside the fields
                    keyboardController?.hide()
                }
            }
    ) {


        Column(
            modifier = Modifier
                .padding()
                .fillMaxSize()
                .background(color = Color.Black),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "Log In Page",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                color = Color.White,
                fontSize = 24.sp,  // Increase the font size
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Email", color = Color.White,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,  // Border when focused
                    unfocusedBorderColor = Color.Gray,  // Border when not focused
                    cursorColor = Color.White,  // Cursor color
//                    textColor = Color.White,  // Text inside the box
//                    placeholderColor = Color.LightGray // Placeholder text
                    focusedTextColor = Color.White,
                    errorPlaceholderColor = Color.Red,


                    ),
                singleLine = true
            )
            Text(
                "Password", color = Color.White,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,  // Border when focused
                    unfocusedBorderColor = Color.Gray,  // Border when not focused
                    cursorColor = Color.White,  // Cursor color
//                    textColor = Color.White,  // Text inside the box
//                    placeholderColor = Color.LightGray // Placeholder text
                    focusedTextColor = Color.White,
                    errorPlaceholderColor = Color.Red,
                    ),
                singleLine = true
            )
            Text(text = "Forgot Password?",
                modifier = Modifier.clickable { onNavigateToReset()}
                    .align(Alignment.Start)
                    .padding(start = 16.dp),
                color = violet

            )

            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val userId = auth.currentUser?.uid
                                    if (userId != null) {
                                        Toast.makeText(
                                            context,
                                            "Login successful!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        coroutineScope.launch {
                                            try {
                                                val document = Firebase.firestore.collection("users")
                                                    .document(userId)
                                                    .get()
                                                    .await()

                                                val goal = document.getString("goal")

                                                if (!goal.isNullOrEmpty()) {
                                                    Log.d("LoginScreen", "User data with goal exists in Firestore.")

                                                    // Safely extract other values
                                                    val age = document.getLong("age")?.toInt() ?: 0
                                                    val height = document.getLong("height")?.toInt() ?: 0
                                                    val weight = document.getLong("weight")?.toInt() ?: 0
                                                    val bmi = document.getDouble("bmi") ?: 0.0
                                                    val gender = document.getString("gender") ?: "Not specified"
                                                    val username = document.getString("username") ?: "User"

                                                    val userEntity = UserEntity(
                                                        id = userId,
                                                        age = age,
                                                        height = height,
                                                        weight = weight,
                                                        bmi = bmi,
                                                        gender = gender,
                                                        username = username,
                                                        goal = goal
                                                    )

                                                    viewModel.userDao.insertUser(userEntity)
                                                    Log.d("LoginScreen", "User inserted into Room.")

                                                    onNavigateToLoading()

                                                } else {
                                                    Log.d("LoginScreen", "No goal data found. Navigating to profile setup.")
                                                    onNavigateToProfile()
                                                }

                                            } catch (e: Exception) {
                                                Log.e("LoginScreen", "Error checking user document", e)
                                                Toast.makeText(
                                                    context,
                                                    "Something went wrong. Try again.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Login failed: ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }

                },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonColors(
                    containerColor = violet,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )
            ) {
                Text("Log In")
            }


            Spacer(modifier = Modifier.height(30.dp))
            Text(
                "Don't have an account? Sign Up.",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { onNavigateToSignUp() },
                color = violet
            )
        }
    }
}