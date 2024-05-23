package com.example.grafik.ui.theme.screenElements


import android.net.Uri
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageUri(source: Uri){
    //Glide API for showing pics and gifs
    GlideImage(model = source, contentDescription = "",
        Modifier
            .height(225.dp)
            .width(300.dp)){
        it.load(source)
    }
}



