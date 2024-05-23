package com.example.grafik.ui.theme.screenElements.MainScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavController
import com.example.grafik.logic.Category
import com.example.grafik.logic.Constants
import com.example.grafik.logic.Screen
import com.example.grafik.logic.dataList
import com.example.grafik.ui.theme.screenElements.ImageUri


@Composable
fun CategoryItem(
    constants: Constants,
    category: Category,
    navController: NavController,
    onValueChange: (List<Category>) -> Unit
){
    //category box
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(top = 10.dp, bottom = 10.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .border(3.dp, color = Color.White)
            .clickable {
                //navigation to graph screen
                navController.navigate(Screen.ScreenWithGraphs.withArgs(category.categoryName))
                //navigation to graph screen
            }, contentAlignment = Alignment.CenterEnd
    ) {
        //content inside category box
        Row(modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            val imageUri = category.categoryData.picture
            //miniature of chosen picture
            if(imageUri.toString() != constants.empty){
                Box(
                    modifier = Modifier
                        .width(94.dp)
                        .height(70.dp)
                        .padding(start = 10.dp)
                    , contentAlignment = Alignment.Center
                ) {
                    ImageUri(source = imageUri!!)
                }
            }
            //miniature of chosen picture

            //name of category
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category.categoryName,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
            //name of category

            //button to delete category
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp), contentAlignment = Alignment.CenterEnd
            ) {
                DeleteButton(category, constants) {
                    dataList = it
                    onValueChange(dataList)
                }
            }
            //button to delete category
        }
        //content inside category box
    }
    //category box
}

