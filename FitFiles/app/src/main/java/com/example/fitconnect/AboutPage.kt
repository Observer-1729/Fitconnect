package com.example.fitconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

@Composable
fun AboutScreen() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            val violet = Color(ContextCompat.getColor(context, R.color.violet))
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "FitConnect is your ultimate fitness companion! " +
                        "Designed to help you achieve your fitness goals," +
                        " it provides tailored diet and exercise plans based on your body" +
                        " metrics and desired physique.",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
                    .align(Alignment.Start)
            )

            Text(
                text = "Key Features:",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            FeatureItem("✔ Personalized Diet and Exercise Plans")
            FeatureItem("✔ BMI Calculation and Analysis")
            FeatureItem("✔ Daily Schedule & Reminders")
            FeatureItem("✔ Firebase Authentication")
            FeatureItem("✔ User Profile Editing")
            FeatureItem("✔ API Integration for Exercises & Diet Plans")

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Stay Fit, Stay Connected!",
                color = violet,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(150.dp))
            Text(text = "Made by: SahilKumar Singh" , modifier = Modifier.padding(bottom = 40.dp),
                color = Color.White)
        }


}

@Composable
fun FeatureItem(feature: String) {
    Text(
        text = feature,
        color = Color.White,
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 6.dp),
        textAlign = TextAlign.Left
    )
}
