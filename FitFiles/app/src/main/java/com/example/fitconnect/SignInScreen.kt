package com.example.fitconnect

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(onNavigateToLogin:()-> Unit){

    var email by remember { mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var firstName by remember{ mutableStateOf("")}
    var lastName by remember { mutableStateOf("")}
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    val firestore = FirebaseFirestore.getInstance()
    val keyboardController = LocalSoftwareKeyboardController.current

    val violet = Color(ContextCompat.getColor(context, R.color.violet))

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)
        .pointerInput(Unit) {
            detectTapGestures {
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
                "Sign Up Page",
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
                text = "First Name", color = Color.White,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
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
                text = "Last Name", color = Color.White,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
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

            Button(
                onClick = {
                    if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Get the created user's ID
                                    val userId = auth.currentUser?.uid

                                    // Create a map to store user details
                                    val userData = hashMapOf(
                                        "firstName" to firstName,
                                        "lastName" to lastName,
                                        "email" to email
                                    )

                                    // Add user data to Firestore under the user's ID
                                    userId?.let {
                                        firestore.collection("users").document(it)
                                            .set(userData)
                                            .addOnSuccessListener {
                                                Toast.makeText(
                                                    context,
                                                    "Account created and data saved!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                onNavigateToLogin()
                                            }
                                            .addOnFailureListener { e ->
                                                Toast.makeText(
                                                    context,
                                                    "Data saving failed: ${e.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    }
                                } else {
                                    // Display error if sign-up failed
                                    Toast.makeText(
                                        context,
                                        "Sign up failed: ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT)
                            .show()
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
                Text("Sign Up")
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                "Already have an account? Log In.",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { onNavigateToLogin() },
                color = violet
            )
        }
    }
}