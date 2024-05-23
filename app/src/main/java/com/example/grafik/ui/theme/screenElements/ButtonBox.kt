package com.example.grafik.ui.theme.screenElements

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
//custom version of default button
fun ButtonBox(text: String, height: Int, fontSize: Int, start:()->Unit){
    Box(modifier = Modifier
        .fillMaxWidth(0.99f)
        .height(height.dp)
        .padding(5.dp).clickable {
            start()
        }
        .clip(shape = RoundedCornerShape(10.dp))
        .border(3.dp, color = Color.Black),
        contentAlignment = Alignment.Center){
        Text(
            text = text,
            fontSize = fontSize.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 5.dp, end = 5.dp)
        )
    }
}