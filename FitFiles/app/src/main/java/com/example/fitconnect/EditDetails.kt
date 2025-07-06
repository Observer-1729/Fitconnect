package com.example.fitconnect

import androidx.core.content.ContextCompat

//
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import kotlinx.coroutines.launch
//
//@Composable
//fun EditDetailsScreen(viewModel: UserInputViewModel) {
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//val violet = Color(ContextCompat.getColor(context, R.color.violet))
//
//    var selectedAge by remember { mutableIntStateOf(viewModel.age.value) }
//    var selectedHeight by remember { mutableIntStateOf(viewModel.height.value) }
//    var selectedWeight by remember { mutableIntStateOf(viewModel.weight.value) }
//    var selectedGoal by remember { mutableStateOf(viewModel.goal.value ?: "Get Fitter") }
//
//    val ageList = (viewModel.age.value..100).toList()
//    val heightList = (viewModel.height.value..220).toList()
//    val weightList = (30..200).toList()
//    val goalOptions = listOf("Gain Weight", "Lose Weight", "Get Fitter", "Gain More Flexibility",
//        "Learn the Basics")
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black)
//            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        DetailsSelector("Age", ageList, selectedAge) { selectedAge = it }
//        DetailsSelector("Height (cm)", heightList, selectedHeight) { selectedHeight = it }
//        DetailsSelector("Weight (kg)", weightList, selectedWeight) { selectedWeight = it }
//
//        Spacer(modifier = Modifier.height(20.dp))
//        Text("Goal", color = Color.White, fontSize = 18.sp)
//        GoalDropdown(selectedGoal, goalOptions) { selectedGoal = it }
//
//        Spacer(modifier = Modifier.height(40.dp))
//        Button(
//            onClick = {
//                val bmi = selectedWeight / ((selectedHeight / 100.0) * (selectedHeight / 100.0))
//                val isUnderweight = bmi < 18.5
//                val isOverweight = bmi in 25.0..29.9
//                val isObese = bmi >= 30.0
//
//                if ((isUnderweight && selectedGoal == "Lose Weight") ||
//                    ((isOverweight || isObese) && selectedGoal == "Gain Weight")) {
//                    Toast.makeText(
//                        context,
//                        "You must not ${if (isUnderweight) "lose" else "gain"} weight." +
//                                " Select a different goal.",
//                        Toast.LENGTH_LONG
//                    ).show()
//                } else {
//                    coroutineScope.launch {
//                        viewModel.age.value = selectedAge
//                        viewModel.height.value = selectedHeight
//                        viewModel.weight.value = selectedWeight
//                        viewModel.goal.value = selectedGoal
//                        viewModel.calculateBMI()
//                        viewModel.saveUserData { success -> if (success)
//                            Toast.makeText(context,"Changes saved successfully", Toast.LENGTH_SHORT).show()}
//                    }
//                }
//            },
//            modifier = Modifier.padding(16.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = violet)
//        ) {
//            Text("Save Changes", color = Color.White)
//        }
//    }
//}
//
//@Composable
//fun <T> DetailsSelector(label: String, items: List<T>,
//                        selectedItem: T, onItemSelected: (T) -> Unit) {
//    Spacer(modifier = Modifier.height(20.dp))
//    Text(label, color = Color.White, fontSize = 18.sp)
//    Spacer(modifier = Modifier.height(10.dp))
//    Box(modifier = Modifier.width(120.dp)) {
//        CenteredLazyRow(items, selectedItem, onItemSelected)
//    }
//}
//
//@Composable
//fun <T> CenteredLazyRow(items: List<T>, selectedItem: T, onItemSelected: (T) -> Unit) {
//    val listState = rememberLazyListState()
//    val coroutineScope = rememberCoroutineScope()
//
//    LaunchedEffect(Unit) {
//        val initialIndex = items.indexOf(selectedItem)
//        if (initialIndex != -1) listState.scrollToItem(initialIndex)
//    }
//
//    LazyRow(
//        state = listState,
//        horizontalArrangement = Arrangement.Center,
//        modifier = Modifier.fillMaxWidth().height(60.dp)
//    ) {
//        itemsIndexed(items) { index, item ->
//            val isSelected = index == listState.firstVisibleItemIndex + 1
//            Box(
//                modifier = Modifier
//                    .padding(horizontal = 10.dp)
//                    .clickable {
//                        onItemSelected(item)
//                        coroutineScope.launch { listState.animateScrollToItem(index) }
//                    },
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    "$item", color = if (isSelected) Color.White else Color.Gray,
//                    fontSize = if (isSelected) 22.sp else 18.sp,
//                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
//                )
//            }
//        }
//    }
//
//    LaunchedEffect(listState) {
//        snapshotFlow { listState.firstVisibleItemIndex }
//            .collect { index ->
//                if (index in items.indices) {
//                    onItemSelected(items[index + 1])
//                }
//            }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun GoalDropdown(selectedGoal: String, options: List<String>, onGoalSelected: (String) -> Unit) {
//    var expanded by remember { mutableStateOf(false) }
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = { expanded = it }
//    ) {
//        TextField(
//            value = selectedGoal,
//            onValueChange = {},
//            readOnly = true,
//            modifier = Modifier.menuAnchor().fillMaxWidth().padding(8.dp),
//            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Black,
//                cursorColor = Color.White,
//                focusedTextColor = Color.White,
//                errorPlaceholderColor = Color.Red,
//                unfocusedTextColor = Color.White
//                )
//        )
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier.background(Color.Gray)
//        ) {
//            options.forEach { goal ->
//                DropdownMenuItem(
//                    text = { Text(goal, modifier = Modifier, color = Color.White) },
//                    onClick = {
//                        onGoalSelected(goal)
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
//}
