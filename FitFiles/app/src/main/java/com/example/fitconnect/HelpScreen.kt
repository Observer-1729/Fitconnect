package com.example.fitconnect

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.room.util.copy

@Composable
fun HelpScreen(){

    val context = LocalContext.current
    val head = Color(ContextCompat.getColor(context, R.color.heads))
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val email = "sahilkumarsingh24.4.4@gmail.com"



    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .padding(24.dp)) {
        LazyColumn(modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Text(text = "App Description\n", color = head, style = MaterialTheme.typography.headlineMedium)
                Text(text = "FitConnect is your all-in-one fitness companion, designed to help you stay on track with your health and wellness goals. Whether you're aiming to lose weight, build muscle, or simply maintain a healthy lifestyle, FitConnect provides the tools you need—all in one place.\n" +
                        "\n",color = Color.White )
                Text("What FitConnect Offers:\n", color = head, style = MaterialTheme.typography.headlineMedium )
                Text(
                        "1. Personalized Fitness Plans\n" +
                        "Get started by entering your personal details such as age, gender, height, weight, and fitness goal. FitConnect then calculates your Body Mass Index (BMI) and generates a customized 30-day exercise and diet plan tailored specifically to your body type and objectives.\n" +
                        "\n" +
                        "2. Smart Diet Guidance\n" +
                        "Your diet plan includes a curated list of food items, recipes, and descriptions to ensure you're eating the right way to support your fitness goals. Whether you're trying to bulk, cut, or maintain, your plan is designed to match your selected goal.\n" +
                        "\n" +
                        "3. Guided Exercise Routines\n" +
                        "Receive a structured exercise schedule based on your fitness level and goal. These routines include bodyweight exercises that target different muscle groups effectively and safely—perfect for at-home or gym workouts.\n" +
                        "\n" +
                        "4. Daily Task Planner\n" +
                        "Stay productive and organized with the built-in daily planner. You can schedule custom tasks for your day—whether fitness-related or general—and FitConnect will automatically send you a notification 1 minute before each task begins.\n" +
                        "\n" +
                        "5. Automatic Daily Reset\n" +
                        "To keep your planning clean and simple, all tasks reset automatically at the end of each day, helping you start fresh every morning.\n" +
                        "\n" +
                        "6. Seamless Experience\n" +
                        "Your data is securely stored, and the app ensures smooth performance and persistence—even when closed—so you never lose progress.\n" +
                        "\n"
                        +
                        "Stay consistent. Stay motivated.\n" +
                        "FitConnect – Your journey starts here.\n\n", color = Color.White)
                Text(text = "For issues or updating your account, contact us on:\n", color = head)
                Text(text =email ,color = Color.Blue,modifier = Modifier.clickable { clipboardManager.setText(AnnotatedString(email));Toast.makeText(context,"Copied",Toast.LENGTH_SHORT).show()
                })
            }
        }
    }
}