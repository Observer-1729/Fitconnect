package com.example.fitconnect

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonDefaults
//
//import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
fun AgeSelectionScreen(viewModel: UserInputViewModel,onNavigateToGender:() ->Unit , onNavigateToHeight:()->Unit) {
    var selectedAge by remember { mutableIntStateOf(viewModel.age.value) } // Default selected age
    val ageList = (10..101).toList() // List of ages
    val listState = rememberLazyListState() // LazyColumn's scroll state
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val violet = Color(ContextCompat.getColor(context, R.color.violet))

    // Calculate the index of the selected age and scroll to it when the UI is first launched
    LaunchedEffect(Unit) {
        val initialIndex = ageList.indexOf(selectedAge)
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
            text = "How Old are You?",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 200.dp,bottom = 10.dp)
        )


        LazyColumn(
            state = listState,
            modifier = Modifier
                .height(150.dp)
        ) {
            itemsIndexed(ageList) { index, age ->
                val isSelected = age == selectedAge
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clickable {
                            selectedAge = age
                            viewModel.age.value = age
                            // Handle age selection on click
                            coroutineScope.launch {
                                listState.animateScrollToItem(index)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = age.toString(),
                        color = if (isSelected) Color.White else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = if (isSelected) 28.sp else 22.sp,
                        modifier = Modifier.padding(vertical = 10.dp)
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
                        selectedAge = ageList[centerIndex]
                        viewModel.age.value = ageList[centerIndex]

                    }
                }
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {onNavigateToGender()},
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                colors = ButtonColors(
                    containerColor = violet,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )
            ){
                Text(text = "Prev")

            }
//                Spacer(modifier = Modifier.padding(16.dp))
            Button(onClick = {onNavigateToHeight()},
                modifier = Modifier.padding(16.dp).wrapContentSize(),
                colors = ButtonColors(
                    containerColor = violet,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )
            ){
                Text(text = "Next")

            }

        }




    }
}

