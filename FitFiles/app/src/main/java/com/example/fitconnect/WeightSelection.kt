package com.example.fitconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch

@Composable
fun WeightSelectionScreen(viewModel: UserInputViewModel,onNavigateToHeight:()->Unit,onBMISelect:()->Unit) {
    var selectedWeight by remember { mutableIntStateOf(viewModel.weight.value) }  // Default selected weight in kg
    val weightList = (30..200).toList() // Weight range in kg
    val listState = rememberLazyListState() // State for scrolling
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val violet = Color(ContextCompat.getColor(context, R.color.violet))

    LaunchedEffect(Unit) {
        val initialIndex = weightList.indexOf(selectedWeight)
        listState.scrollToItem(initialIndex)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "What's your Weight?",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 200.dp, bottom = 10.dp)
        )

        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(start = 50.dp, end = 50.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            itemsIndexed(weightList) { index, weight ->
                val isSelected = weight == selectedWeight
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .clickable {
                            selectedWeight = weight
                            viewModel.weight.value = weight //Might cause error
                            coroutineScope.launch {
                                listState.animateScrollToItem(index)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$weight kg",
                        color = if (isSelected) Color.White else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = if (isSelected) 28.sp else 22.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }

        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo }
                .collect { visibleItems ->
                    // Find the item closest to the center
                    val centerIndex = visibleItems
                        .minByOrNull { Math.abs(it.offset + it.size / 2 - listState.layoutInfo.viewportEndOffset / 2) }
                        ?.index
                    if (centerIndex != null) {
                        selectedWeight = weightList[centerIndex]
                        viewModel.weight.value = weightList[centerIndex]

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
                onClick = { onNavigateToHeight() },
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
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
                onClick = { onBMISelect() },
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                colors = ButtonColors(
                    containerColor = violet,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )
            ) {
                Text(text = "BMI")
            }
        }
    }
}
