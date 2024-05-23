package com.example.grafik.ui.theme.screenElements.CraftingNew

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun RadioButton(check: Boolean, name: String, onValueChange:(mutableCheck: Boolean)->Unit ){
    var newCheck = check
    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
        androidx.compose.material3.RadioButton(selected = newCheck, onClick ={
            when{
                !check ->{newCheck = true}
                check ->{newCheck = false}
            }
            onValueChange(newCheck)
        })
        Text(text = name, fontSize = 20.sp)
    }
}
