package com.example.fitconnect

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitconnect.DietItem // make sure this import matches your package

@Composable
fun DietScreen(
    dietViewModel: DietViewModel,
    dietState: State<DietViewModel.DietState>,
    onNavigateToRecipe: (String) -> Unit

) {
    LaunchedEffect(Unit) {
        dietViewModel.loadDietFromDao()
    }
    when {
        dietState.value.loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        dietState.value.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${dietState.value.error}", color = Color.Red)
            }
        }

        else -> {
            Log.d("DietScreen", "Loaded diet list size: ${dietState.value.list.size}")
            InsideDietScreen(food = dietState.value.list, onNavigateToRecipe)

        }
    }
}

@Composable
fun InsideDietScreen(food: List<DietItem>, onNavigateToRecipe: (String) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(food) { dietItem ->
            DietCard(dietItem, onNavigateToRecipe)
        }
    }
}

@Composable
fun DietCard(foodItem: DietItem, onNavigateToRecipe: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF383838)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clickable { onNavigateToRecipe(foodItem.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(foodItem.image),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(foodItem.name, fontSize = 20.sp, color = Color.White)
                Text(foodItem.amount, fontSize = 16.sp, color = Color.White)
            }
        }
    }
}
