package com.example.fitconnect

import android.graphics.Insets.add
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest


@Composable
fun ExerciseDescription(exerciseItem: ExerciseItem) {
    val context = LocalContext.current
    val blue = Color(ContextCompat.getColor(context, R.color.bulu))
    val card = Color(ContextCompat.getColor(context, R.color.subcard))
    val heads = Color(ContextCompat.getColor(context, R.color.heads))
    val subhead = Color(ContextCompat.getColor(context, R.color.subhead))
    val items = Color(ContextCompat.getColor(context, R.color.purple_200))


    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()


    val request = ImageRequest.Builder(context)
        .data(exerciseItem.gif) // must be a full URL like "https://..."
        .crossfade(true)
        .decoderFactory(if (Build.VERSION.SDK_INT >= 28) {
            ImageDecoderDecoder.Factory()
        } else {
            GifDecoder.Factory()
        })
        .build()


    val viewModel: ExerciseViewModel = viewModel()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = request,
            contentDescription = exerciseItem.exercise,
            imageLoader = imageLoader,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(width = 4.dp, color = blue),
            contentScale = ContentScale.Crop
        )

        // Exercise Name
        Text(
            text = exerciseItem.exercise,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(top = 16.dp),
            color = items
        )

        //Description Section
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.background(card, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .border(width = 1.dp, color = blue, RoundedCornerShape(12.dp))

                ) {
                    Column(modifier = Modifier.padding(4.dp)) {
                        Text(
                            text = "Description",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp),
                            color = heads
                        )
                        Text(
                            text = exerciseItem.description,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(12.dp),
                            color = subhead
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.background(card, RoundedCornerShape(12.dp))
//                .width(250.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(width = 1.dp, color = blue, RoundedCornerShape(12.dp))
                ) {
                    Column(modifier = Modifier.padding(4.dp)) {
                        Text(
                            "Effects", fontSize = 20.sp,
                            modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp),
                            color = heads
                        )
                        Text(
                            text = exerciseItem.effects,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(12.dp),
                            color = subhead
                        )
                    }
                }
            }
        }
    }
}
