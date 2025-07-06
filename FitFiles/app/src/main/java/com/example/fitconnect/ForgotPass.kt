package com.example.fitconnect

import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.material.Button
//import androidx.compose.material.OutlinedTextField
//import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.google.firebase.auth.FirebaseAuth
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterEmail(onNavigateToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val violet = Color(ContextCompat.getColor(context, R.color.violet))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(color = Color.Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter your Email:",
                color = Color.White
            )

            OutlinedTextField(
                email, { email = it }, Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("Email") }, // Label is automatically styled
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,  // Border when focused
                    unfocusedBorderColor = Color.Gray,  // Border when not focused
                    cursorColor = Color.White,  // Cursor color
//                    textColor = Color.White,  // Text inside the box
//                    placeholderColor = Color.LightGray // Placeholder text
                    focusedTextColor = Color.White,
                    errorPlaceholderColor = Color.Red,


                )
            )

            Button(
                onClick = {
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                    }
                    auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Password reset email sent!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Failed to send password reset email!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    onNavigateToLogin()
                },
                colors = ButtonColors(
                    containerColor = violet,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )
            ) {
                Text("Reset Password")
            }
        }
    }
}
