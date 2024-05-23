package com.example.grafik.ui.theme.screenNavigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.grafik.Realm.DataRealm
import com.example.grafik.logic.Category
import com.example.grafik.logic.Constants
import com.example.grafik.logic.MainViewModel
import com.example.grafik.logic.Screen
import com.example.grafik.logic.dataList
import com.example.grafik.logic.englishConstants
import com.example.grafik.logic.languageChoice
import com.example.grafik.logic.russian
import com.example.grafik.logic.russianConstants
import com.example.grafik.ui.theme.screenElements.CraftingNew.CraftingNew
import com.example.grafik.ui.theme.screenElements.MainScreen.MainScreen
import com.example.grafik.ui.theme.screenElements.ScreenWithGraphs.ScreenWithGraphs
import kotlinx.coroutines.launch


@Composable
fun Navigation(edit: SharedPreferences.Editor, constants: Constants) {
    //essential values
    val navController = rememberNavController()
    val mutData = remember {
        mutableStateOf(dataList)
    }
    val mutConstants = remember {
        mutableStateOf(constants)
    }
    //essential values
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        //main screen (list of categories, search bar, addCategory button
        composable(
            route = Screen.MainScreen.route,
        ) {entry->
            MainScreen(
                edit,
                data = mutData.value,
                navController = navController,
                constants = mutConstants.value,
                onValueChange = {
                    languageChoice = it
                    mutConstants.value= if(languageChoice == russian){
                        russianConstants
                    }else{
                        englishConstants
                    }
                }
            )
        }
        //main screen (list of categories, search bar, addCategory button

        //category screen
        composable(
            route = Screen.ScreenWithGraphs.route + "/{name}",
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { entry ->
            val name = entry.arguments?.getString("name").toString()
            ScreenWithGraphs(name = name, constants = mutConstants.value){
                mutData.value = it
            }
        }
        //category screen

        //screen for adding category
        composable(
            route = Screen.RegisterScreen.route,
        ) {
            CraftingNew(navController, mutConstants.value){
                mutData.value = it //refresh displayed list of categories
            }
        }
        //screen for adding category
    }
}




