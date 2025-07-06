package com.example.fitconnect

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitconnect.Screen.BottomScreen.Diet.title
import com.google.firebase.auth.FirebaseAuth
@Composable
fun LogoutDialog(navController: NavController, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val head = Color(ContextCompat.getColor(context, R.color.heads))
    val subhead = Color(ContextCompat.getColor(context, R.color.purple_200))
    val violet = Color(ContextCompat.getColor(context, R.color.subcard))
    val border = Color(ContextCompat.getColor(context, R.color.violet))

    val taskViewModel: TaskViewModel = viewModel()
    val dietViewModel:DietViewModel = viewModel()
    val exerciseViewModel:ExerciseViewModel = viewModel()
    val userViewModel:UserInputViewModel = viewModel()
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Logout", fontWeight = FontWeight.Bold, fontSize = 24.sp , color = head)  },
        text = { Text(text = "Are you sure you want to logout?", fontSize = 14.sp , color = subhead) },
        confirmButton = {
            TextButton(onClick = {
                FirebaseAuth.getInstance().signOut()
                taskViewModel.clearTasks()
                dietViewModel.clearDiet()
                exerciseViewModel.clearExercise()
                userViewModel.clearProfile()

                val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
                intent?.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context.startActivity(intent)
                Runtime.getRuntime().exit(0)

                onDismiss()
            }) {
                Text(text = "Yes", color = Color.Blue, fontSize = 20.sp)
            }
        }
            ,
        dismissButton = {
            TextButton(onClick = {
                navController.popBackStack()
                onDismiss()
            }) {
                Text(text = "Cancel", color = Color.Red, fontSize = 20.sp)
            }
        },
        shape = RoundedCornerShape(10.dp),
        backgroundColor = violet,
        modifier = Modifier.border(width = 1.dp, color = border ,RoundedCornerShape(10.dp)),

    )
}
