package com.example.grafik.ui.theme.screenElements.MainScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.grafik.R
import com.example.grafik.logic.Category
import com.example.grafik.logic.Constants
import com.example.grafik.logic.MainScreenLogic
import com.example.grafik.logic.MainViewModel
import com.example.grafik.logic.dataList
import com.example.grafik.ui.theme.screenElements.ScreenWithGraphs.ConfirmDialog


@Composable
fun DeleteButton(category: Category, constants: Constants, onValueChange: (List<Category>) -> Unit){
    val mainScreen = MainScreenLogic()
    val openDialog = remember {
        mutableStateOf(false)
    }
    val viewModel = MainViewModel()
    Column {
        Image(painter = painterResource(id = R.drawable.delete),
            "delete",
            Modifier
                .fillMaxSize()
                .clickable {
                    //dialog for deleting opens
                    openDialog.value = !openDialog.value
                }
        )
        if(openDialog.value){
            ConfirmDialog(
                constants,
                typeOfObject = constants.category,
                accept = {
                    //delete category from the Realm
                    viewModel.deleteSelectedData(category.categoryName)
                    //delete category from dataList
                    mainScreen.deleteCategory(category)
                    //boolean to close dialog of deleting
                    openDialog.value = !openDialog.value
                    //function to recompose
                    onValueChange(dataList)
                },
                decline = {
                    //boolean to close dialog of deleting
                    openDialog.value = !openDialog.value
                }
            )
        }
    }
}