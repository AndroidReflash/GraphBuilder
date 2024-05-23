package com.example.grafik.ui.theme.screenElements.ScreenWithGraphs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.grafik.R
import com.example.grafik.logic.Category
import com.example.grafik.logic.Constants
import com.example.grafik.logic.FormingGraphs
import com.example.grafik.logic.ScreenWithGraphsLogic
import com.example.grafik.logic.categoryChoice
import com.example.grafik.ui.theme.GraphTheme
import com.example.grafik.ui.theme.screenElements.Background
import com.example.grafik.ui.theme.screenElements.ImageUri


@Composable

fun ScreenWithGraphs(name: String, constants: Constants, onValueChange:(List<Category>)-> Unit) {

    //essential values
    val formingGraphs = FormingGraphs()
    val screenWithGraphsLogic = ScreenWithGraphsLogic()
    screenWithGraphsLogic.chooseCategory(name) //gives data to global categoryChoice
    val category = categoryChoice

    val dataOfScreen = category!!.categoryData
    val listOfRepeats = dataOfScreen.listOfRepeats
    val listOfDate = dataOfScreen.listOfDate
    val listOfCalories = dataOfScreen.listOfCalories
    val listOfWeight = dataOfScreen.listOfWeight

    val weightGraph = dataOfScreen.weightGraph      //boolean to define existence of weightGraph
    val repeatsGraph = dataOfScreen.repeatsGraph    //boolean to define existence of repeatsGraph
    val caloricGraph = dataOfScreen.caloricGraph    //boolean to define existence of caloricGraph

    val newListOfRepeats = remember {
        mutableStateOf(listOfRepeats)
    }
    val newListOfWeight = remember {
        mutableStateOf(listOfWeight)
    }
    val newListOfDate = remember {
        mutableStateOf(listOfDate)
    }
    val newListOfCalories = remember {
        mutableStateOf(listOfCalories)
    }

    //forming weightGraph
    val newChartWeight = formingGraphs.createLineChartDataFloat(
        newListOfDate.value, newListOfWeight.value, constants.kg)

    //forming repeatsGraph
    val newChartRepeats = formingGraphs.createLineChartDataInt(
        newListOfDate.value, newListOfRepeats.value, constants.reps)

    //forming caloricGraph
    val newChartCalories = formingGraphs.createLineChartDataInt(
        newListOfDate.value, newListOfCalories.value, constants.kcal)

    //screen content
    Background(painter = painterResource(id = R.drawable.screen_with_graphs))
    GraphTheme {
        val scroll = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 5.dp, end = 5.dp)
                .verticalScroll(scroll)
        ) {

            //title
            BoxForContent(text = name, height = 100, fontSize = 30)

            //box with picture
            val picture = dataOfScreen.picture
            if(picture.toString() != constants.empty){
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
                    .height(300.dp), contentAlignment = Alignment.Center){
                    ImageUri(source = picture!!)
                }
            }

            //graphs
            if (weightGraph){           //weight
                TheGraph(
                    constants = constants,
                    text = constants.weightGraph,
                    newChart = newChartWeight,
                    def =  constants.kg,
                    list = listOfWeight
                )
            }
            if (repeatsGraph){          //repeats
                TheGraph(
                    constants = constants,
                    text = constants.repeatsGraph,
                    newChart = newChartRepeats,
                    def =  constants.reps,
                    list = listOfRepeats.map { it.toDouble() }
                )
            }
            if (caloricGraph){          //calories
                TheGraph(
                    constants = constants,
                    text = constants.caloriesGraph,
                    newChart = newChartCalories,
                    def =  constants.kcal,
                    list = listOfCalories.map { it.toDouble() }
                )
            }

            //fields for manipulating graphs
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                DataForTheGraph(
                    name,
                    listOfWeight,
                    listOfRepeats,
                    listOfCalories,
                    listOfDate,
                    weightGraph,
                    repeatsGraph,
                    caloricGraph,
                    onValueChangeDate = {
                        newListOfDate.value = it },
                    onValueChangeWeight = {
                        newListOfWeight.value = it },
                    onValueChangeRepeats = {
                        newListOfRepeats.value = it },
                    onValueChangeCalories = {
                        newListOfCalories.value = it},
                    onValueChange = {
                        onValueChange(it)
                    },
                    picture = picture,
                    constants = constants
                )
            }
        }
    }
}

