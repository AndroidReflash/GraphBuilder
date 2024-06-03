package com.example.grafik.ui.theme.screenElements.MainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.grafik.R
import com.example.grafik.logic.Category
import com.example.grafik.logic.Constants
import com.example.grafik.logic.MainScreenLogic
import com.example.grafik.logic.MainViewModel
import com.example.grafik.logic.dataList
import com.example.grafik.ui.theme.screenElements.Background
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ScreenContent(
    constants: Constants,
    navController: NavController,
    onValueChange:(List<Category>) -> Unit
) {
    //essential values
    val mutData = remember {
        mutableStateOf(dataList)
    }
    val emptyRealm = remember {
        mutableStateOf(true)
    }
    val viewModel = MainViewModel()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
    ) {
        Column(Modifier.padding(top = 20.dp, start = 5.dp, end = 5.dp)) {
            SearchBar(constants){       //search bar
                mutData.value = it
            }
            val empty = emptyList<Category>()
            if(dataList == empty && emptyRealm.value){
                //loading data from the Realm
                Loading()
                LaunchedEffect(Unit) {
                    //empty is needed to prevent rebuild after each recomposition
                    delay(2000)
                    viewModel.load{
                        mutData.value = it
                        emptyRealm.value = false
                    }
                }
            }else{
                LazyColumn(         //category list
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(mutData.value){_, eachDataObject->
                        CategoryItem(
                            constants = constants,
                            category = eachDataObject,
                            navController = navController,
                            onValueChange = {
                                mutData.value = it
                                onValueChange(it)
                            }
                        )
                    }
                }
            }
        }
    }
}