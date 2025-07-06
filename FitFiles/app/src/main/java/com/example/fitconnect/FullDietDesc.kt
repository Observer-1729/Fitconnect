package com.example.fitconnect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun DietDescription( foodItem: DietItem) {

    val viewModel: DietViewModel = viewModel()
    val dietState by viewModel.dietState
    val context = LocalContext.current
    val blue = Color(ContextCompat.getColor(context, R.color.bulu))
    val card = Color(ContextCompat.getColor(context, R.color.subcard))
    val heads = Color(ContextCompat.getColor(context, R.color.heads))
    val subhead = Color(ContextCompat.getColor(context, R.color.subhead))
    val items = Color(ContextCompat.getColor(context, R.color.purple_200))




    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberAsyncImagePainter(foodItem.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f).border(width = 4.dp, color = blue)
            )
        }

        Text(
            text = foodItem.name,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = items
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Recipes",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = subhead
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn() {
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = card),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                        .border(width = 1.dp, color = blue, RoundedCornerShape(12.dp))
                ) {
                    Text(
                        "1. ${foodItem.recipe1}",
                        fontWeight = FontWeight.Bold,
                        color = heads,
                        modifier = Modifier.padding(top = 12.dp, start = 12.dp,end =12.dp)
                    )
                    Text(
                        foodItem.recipe1Description,
                        textAlign = TextAlign.Justify,
                        color = subhead,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = card),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                        .border(width = 1.dp, color = blue, RoundedCornerShape(12.dp))

                ) {
                    Text(
                        "2. ${foodItem.recipe2}",
                        fontWeight = FontWeight.Bold,
                        color = heads,
                        modifier = Modifier.padding(top = 12.dp, start = 12.dp,end =12.dp)
                    )
                    Text(
                        foodItem.recipe2Description,
                        textAlign = TextAlign.Justify,
                        color = subhead,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = card),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                        .border(width = 1.dp, color = blue, RoundedCornerShape(12.dp))

                ) {
                    Text(
                        "3. ${foodItem.recipe3}",
                        fontWeight = FontWeight.Bold,
                        color = heads,
                        modifier = Modifier.padding(top = 12.dp, start = 12.dp,end =12.dp)
                    )
                    Text(
                        foodItem.recipe3Description,
                        textAlign = TextAlign.Justify,
                        color = subhead,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
