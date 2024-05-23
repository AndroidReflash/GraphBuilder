package com.example.grafik.ui.theme.screenElements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.grafik.R

@Composable
fun Background(painter: Painter){
    Image(
        painter = painter,
        contentDescription = "",
        Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}