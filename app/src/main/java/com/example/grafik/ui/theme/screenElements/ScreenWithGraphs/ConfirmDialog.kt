package com.example.grafik.ui.theme.screenElements.ScreenWithGraphs

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.grafik.logic.Constants

@Composable
fun ConfirmDialog( //dialog for deleting
    constants: Constants,
    typeOfObject: String,
    accept: () -> Unit,
    decline: () -> Unit
){
    Dialog(onDismissRequest = { }) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .border(
                    1.dp, color = Color.Green,
                    shape = RoundedCornerShape(15.dp)
                )
                .fillMaxWidth(0.95f)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth()
                    , contentAlignment = Alignment.Center
                ){
                    Text(
                        text = constants.areYouSure + " " + typeOfObject,
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth().padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { //accept button
                        accept()
                    }) {
                        Text(text = constants.yes)
                    }
                    Button(onClick = { //decline button
                        decline()
                    }) {
                        Text(text = constants.no)
                    }
                }
            }
        }
    }
}