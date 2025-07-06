package com.example.fitconnect

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch


@Composable
fun ExerciseScreen(
    exerciseViewModel: ExerciseViewModel = viewModel(),
    exerciseState: State<ExerciseViewModel.ExerciseState>,
    onNavigateToDetail: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        exerciseViewModel.loadExercisesFromDao()
    }

    when {
        exerciseState.value.loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        exerciseState.value.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${exerciseState.value.error}", color = Color.Red)
            }
        }

        else -> {
            Log.d("ExerciseScreen", "Loaded exercise list size: ${exerciseState.value.list.size}")
            InsideExerciseScreen(
                exercise = exerciseState.value.list,
                onNavigateToDetail = onNavigateToDetail
            )
        }
    }
}

@Composable
fun InsideExerciseScreen(
    exercise: List<ExerciseItem>,
    onNavigateToDetail: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(exercise) { exerciseItem ->
            ExerciseCard(exerciseFromAPI = exerciseItem,
                onNavigateToDetail
            )
        }
    }
}

@Composable
fun ExerciseCard(exerciseFromAPI: ExerciseItem, onNavigateToDetail: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF383838)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clickable { onNavigateToDetail(exerciseFromAPI.id) }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = exerciseFromAPI.exercise,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = exerciseFromAPI.repsTime,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

