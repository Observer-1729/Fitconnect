package com.example.fitconnect

//import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AccountDetailsScreen(viewModel: UserInputViewModel) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    var user by remember { mutableStateOf<UserEntity?>(null) }
    var profileImageUri =viewModel.imageUri.value

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        profileImageUri = uri
    }

    val context = LocalContext.current
    val border = Color(ContextCompat.getColor(context, R.color.heads))
    val head = Color(ContextCompat.getColor(context, R.color.purple_200))

    LaunchedEffect(userId) {
        userId?.let {
            viewModel.getUserFromRoom(it) { fetchedUser ->
                user = fetchedUser
            }
        }
    }
    LaunchedEffect(userId) {
        userId?.let {
            viewModel.loadProfile(it) {
                // profile loaded and imageUri already updated inside viewModel
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "${user?.username ?: "Loading..."}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        AsyncImage(
            model = profileImageUri ?: R.drawable.ic_account,
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(140.dp)  // Increased from 100dp to 140dp
                .clip(CircleShape)
//                .clickable { imagePickerLauncher.launch("image/*") }
                .border(width = 2.dp, color = border, shape = CircleShape)
        )

        Text(
            text = "Tap to change profile picture",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E1E1E) // Dark background for the table
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "ACCOUNT DETAILS",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = head,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Table header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background( Color(0xFFFFAC07), shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                ) {
                    Text(
                        text = "DETAIL",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Black
                    )
                    Text(
                        text = "VALUE",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Black
                    )
                }

                // Table rows
                DetailTableRow("Age", user?.age?.toString() ?: "Loading...")
                DetailTableRow("Gender", user?.gender ?: "Loading...")
                DetailTableRow("Height", "${user?.height ?: "Loading..."} cm")
                DetailTableRow("Weight", "${user?.weight ?: "Loading..."} kg")
                DetailTableRow("BMI", user?.bmi?.toFloat().toString() ?: "Loading...")
                DetailTableRow("Goal", user?.goal?.toString() ?: "Loading...")
            }
        }
    }
}

@Composable
fun DetailTableRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray
        )
    }

    Divider(color = Color.DarkGray, thickness = 0.5.dp)
}