package com.example.grafik.ui.theme.screenElements.MainScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.grafik.R
import com.example.grafik.logic.Category
import com.example.grafik.logic.Constants
import com.example.grafik.logic.MainScreenLogic
import com.example.grafik.logic.dataList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    constants: Constants,
    onValueChange: (List<Category>) -> Unit
){
    val findObject = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val mainScreen = MainScreenLogic()
    var newList: List<Category>
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(top = 10.dp, bottom = 20.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .border(3.dp, color = Color.Black)
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp), horizontalArrangement = Arrangement.SpaceAround) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.85f),
                value = findObject.value,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.LightGray,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedLabelColor = Color.LightGray
                ),
                onValueChange = {
                        newText -> findObject.value = newText
                },
                placeholder = { Text(text = constants.search) }
            )
            Box(modifier =
            Modifier
                .height(60.dp)
                .fillMaxWidth()
                .background(color = Color.LightGray)
            ){
                Image(painter = painterResource(id = R.drawable.search),
                    "search",
                    //logic for searching
                    Modifier
                        .fillMaxSize()
                        .clickable {
                            //when search bar is not empty
                            //it will find all categories which is contain
                            //chars which was typed in search bar
                            if (findObject.value != "") {
                                newList =
                                    mainScreen.search(findObject.value, dataList)
                                if (newList.isNotEmpty()) {
                                    onValueChange(newList)
                                }
                                Toast
                                    .makeText(
                                        context,
                                        constants.succeededSearch,
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                                findObject.value = ""
                            } else {
                                //when search bar is empty
                                //it will bring back the whole categories
                                //if it was founded something before
                                Toast
                                    .makeText(
                                        context,
                                        constants.emptySearch,
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                                newList = dataList
                                onValueChange(newList)
                            }
                        })
            }
        }
    }
}