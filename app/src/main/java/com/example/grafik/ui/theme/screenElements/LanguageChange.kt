package com.example.grafik.ui.theme.screenElements

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LanguageChange(border: Int, painterResource: Painter, change: () -> Unit){
    Box(
        modifier = Modifier
            .width(60.dp)
            .height(40.dp)
            .clickable {
                change()
            }.border(border.dp, color = Color.Green)
    ) {
        Image(
            painter = painterResource,
            contentDescription = "language",
            modifier = Modifier.fillMaxSize()
        )
    }
}