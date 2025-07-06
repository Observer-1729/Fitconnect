package com.example.fitconnect

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material.ButtonDefaults
//
//import androidx.compose.material.Button

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.fitconnect.R
import com.example.fitconnect.UserInputViewModel


@Composable
fun GenderSelector(viewModel: UserInputViewModel,onNavigateToProfile:()->Unit,onNavigateToAge:()->Unit) {
    var selectedGender by remember { mutableStateOf<String?>(viewModel.gender.value) }

    val context = LocalContext.current
    val violet = Color(ContextCompat.getColor(context, R.color.violet))


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title Section
        Column(
            modifier = Modifier.padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select your Gender",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )

        }

        // Gender Selection Options
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Male Option
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(if (selectedGender == "male") violet else Color.Transparent
                        ,RoundedCornerShape(8.dp))
                    .clickable {
                        selectedGender = "male"
                        viewModel.gender.value ="male"
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.male),
                        contentDescription = "male",
                        modifier = Modifier
                            .fillMaxHeight(0.6f)
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    Text(
                        text = "Male",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Female Option
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selectedGender == "female") violet else Color.Transparent
                        ,RoundedCornerShape(8.dp))
                    .clickable
                    {
                        selectedGender = "female"
                        viewModel.gender.value ="female"
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.female),
                        contentDescription = "female",
                        modifier = Modifier
                            .fillMaxHeight(0.6f)
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    Text(
                        text = "Female",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onNavigateToProfile() },
                modifier = Modifier.padding(16.dp).wrapContentSize(),
                colors = ButtonColors(
                    containerColor = violet,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )

            ) {
                Text(text = "Prev")
            }

            Button(
                onClick = { onNavigateToAge() },
                modifier = Modifier.padding(16.dp).wrapContentSize(),
                colors = ButtonColors(
                    containerColor = violet,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )
            ) {
                Text(text = "Next")
            }
        }
    }
}

