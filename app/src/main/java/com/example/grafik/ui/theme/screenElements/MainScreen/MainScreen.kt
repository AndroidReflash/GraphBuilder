package com.example.grafik.ui.theme.screenElements.MainScreen

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.grafik.R
import com.example.grafik.logic.Category
import com.example.grafik.logic.Constants
import com.example.grafik.logic.Screen
import com.example.grafik.logic.english
import com.example.grafik.logic.languageChoice
import com.example.grafik.logic.russian
import com.example.grafik.ui.theme.screenElements.Background
import com.example.grafik.ui.theme.screenElements.LanguageChange


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    edit: SharedPreferences.Editor,
    data:List<Category>,
    constants: Constants,
    navController: NavController,
    onValueChange:(String) -> Unit
) {
    //happens if dataList is empty, which is only at the start of application
    val mutData = remember {
        mutableStateOf(data)
    }
    val engBorder = remember {
        mutableStateOf(constants.eng)
    }
    val rusBorder = remember {
        mutableStateOf(constants.rus)
    }
    Scaffold(
        //button in the bottom of the screen for adding categories
        floatingActionButton = {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween

                ){
                LanguageChange(rusBorder.value, painterResource = painterResource(id = R.drawable.russia)) {
                    languageChoice = russian
                    onValueChange(languageChoice)
                    rusBorder.value = 3
                    engBorder.value = 0
                    edit.apply {
                        putString("choice", languageChoice)
                        apply()
                    }
                }
                FloatingActionButton(
                    content = {
                        Icon(Icons.Filled.Add, contentDescription = constants.add)
                    },
                    onClick = {
                        navController.navigate(Screen.RegisterScreen.route)
                    }
                )
                LanguageChange(engBorder.value, painterResource = painterResource(id = R.drawable.usa)) {
                    languageChoice = english
                    onValueChange(languageChoice)
                    engBorder.value = 3
                    rusBorder.value = 0
                    edit.apply {
                        putString("choice", languageChoice)
                        apply()
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,

    ) {
        //the rest part of the screen
        Background(painter = painterResource(id = R.drawable.main_screen))
        ScreenContent(
            constants = constants,
            navController = navController,
            onValueChange = {
                mutData.value = it
            }
        )
    }
}







