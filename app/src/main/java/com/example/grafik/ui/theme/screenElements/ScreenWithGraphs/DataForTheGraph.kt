package com.example.grafik.ui.theme.screenElements.ScreenWithGraphs

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.grafik.logic.Category
import com.example.grafik.logic.CategoryData
import com.example.grafik.logic.Constants
import com.example.grafik.logic.MainViewModel
import com.example.grafik.logic.ScreenWithGraphsLogic
import com.example.grafik.logic.categoryChoice
import com.example.grafik.logic.dataList
import com.example.grafik.logic.englishConstants
import com.example.grafik.logic.length
import com.example.grafik.logic.russian
import com.example.grafik.logic.russianConstants
import com.example.grafik.logic.size
import com.example.grafik.ui.theme.screenElements.ButtonBox
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DataForTheGraph( //component of screen with graphs
    name: String,
    listOfWeight: List<Double>,
    listOfRepeats: List<Int>,
    listOfCalories: List<Int>,
    listOfDate: List<String>,
    weightGraph: Boolean,
    repeatsGraph: Boolean,
    caloricGraph: Boolean,
    onValueChangeDate: (List<String>) -> Unit,
    onValueChangeWeight: (List<Double>) -> Unit,
    onValueChangeRepeats: (List<Int>) -> Unit,
    onValueChangeCalories: (List<Int>) -> Unit,
    onValueChange: (List<Category>) -> Unit,
    picture: Uri?,
    constants: Constants
) {
    //essential values
    val openDialog = remember {
        mutableStateOf(false)
    }
    val timeStamp: String = SimpleDateFormat(
        "dd.MM.yyyy", Locale.getDefault()).format(Date())
    val scope = rememberCoroutineScope()
    val viewModel = MainViewModel()
    val screenWithGraphsLogic = ScreenWithGraphsLogic()

    //recomposable for weightGraph
    val newListOfWeight = remember { mutableStateOf(listOfWeight) }

    //recomposable for caloricGraph
    val newListOfCalories = remember { mutableStateOf(listOfCalories) }

    //recomposable for dateGraph
    val newListOfDate = remember { mutableStateOf(listOfDate) }

    //recomposable for repeatsGraph
    val newListOfRepeats = remember { mutableStateOf(listOfRepeats) }

    //recomposables for field that inputs numbers
    val numberWeight = remember {
        mutableStateOf("0")
    }
    val numberRepeats = remember {
        mutableStateOf("0")
    }
    val numberCalories = remember {
        mutableStateOf("0")
    }

    val messageData = remember { mutableStateOf(timeStamp) }           //data textField
    val messageWeight = remember { mutableStateOf("0") }        //weight number roll value
    val messageRepeats = remember { mutableStateOf("0") }       //repeats number roll value
    val messageCalories = remember { mutableStateOf("0") }      //caloric number roll value
    val context: Context = LocalContext.current                       //for toasts

    //defining existence of weight number field
    val weightGraphBoolean = if(weightGraph){
        messageWeight.value.isNotEmpty()
    } else {
        true
    }

    //defining existence of repeats number field
    val repeatsGraphBoolean = if(repeatsGraph){
        messageRepeats.value.isNotEmpty()
    } else {
        true
    }

    //defining existence of caloric number field
    val caloriesGraphBoolean = if(caloricGraph){
        messageCalories.value.isNotEmpty()
    }else{
        true
    }

    //content
    Column(
        modifier = Modifier
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //input values for x axis (horizontally)
        Text(text = constants.day)
        TextField(
            modifier = Modifier
                .width(200.dp)
                .padding(10.dp),
            value = messageData.value,
            textStyle = TextStyle(fontSize = 20.sp),
            onValueChange = { newText -> messageData.value = newText }
        )

        //input values for y axis (vertically) (values for weight graph) (with check for existing)
        if (weightGraph) {
            Text(text = constants.weightGraph + constants.numbers)
            NumberAddition(
                constants,
                constants.weightGraph,
                numberWeight.value,
                size,
                length
            ){
                messageWeight.value = it
                numberWeight.value = it
            }
        }

        //input values for y axis (vertically) (values for repeats graph) (with check for existing)
        if (repeatsGraph) {
            Text(text = constants.repeatsGraph + constants.numbers)
            NumberAddition(
                constants = constants,
                constants.repeatsGraph,
                numberRepeats.value,
                size,
                length
            ){
                messageRepeats.value = it
                numberRepeats.value = it
            }
        }

        //input values for y axis (vertically) (values for caloric graph) (with check for existing)
        if (caloricGraph) {
            Text(text = constants.caloriesGraph + constants.numbers)
            NumberAddition(
                constants = constants,
                constants.caloriesGraph,
                numberCalories.value,
                size,
                length
            ){
                messageCalories.value = it
                numberCalories.value = it
            }
        }

        //apply input data button (adding)
        ButtonBox(text = constants.accept, height = 50, fontSize = 20){
            if (
                messageData.value.isNotEmpty() &&
                weightGraphBoolean &&
                repeatsGraphBoolean &&
                caloriesGraphBoolean
            ) {
                //forming input data
                //x axis (horizontally)
                newListOfDate.value += messageData.value
                messageData.value = timeStamp

                //y axis (vertically) weight, repeats and caloric
                if (weightGraph){
                    newListOfWeight.value += messageWeight.value.toDouble()
                    messageWeight.value = ""
                    numberWeight.value = "0" //filling field with adding numbers with zeros
                }
                if (repeatsGraph) {
                    newListOfRepeats.value += messageRepeats.value.toDouble().toInt()
                    messageRepeats.value = ""
                    numberRepeats.value = "0" //filling field with adding numbers with zeros
                }
                if(caloricGraph){
                    newListOfCalories.value += messageCalories.value.toDouble().toInt()
                    messageCalories.value = ""
                    numberCalories.value = "0" //filling field with adding numbers with zeros
                }

                //forming input data

                //helps to understand which category is needed to be refreshed
                //gives data to global categoryChoice
                screenWithGraphsLogic.chooseCategory(name)
                val oldCategory = categoryChoice as Category

                //forming category to add in dataList
                val categoryData = CategoryData(
                    newListOfWeight.value,
                    newListOfRepeats.value,
                    newListOfCalories.value,
                    newListOfDate.value,
                    weightGraph,
                    repeatsGraph,
                    caloricGraph,
                    picture
                )
                val category = Category(
                    name,
                    categoryData
                )

                //updating dateList
                dataList = screenWithGraphsLogic.addToDataList(oldCategory, category)

                onValueChangeDate(newListOfDate.value) //updating date graph

                onValueChangeWeight(newListOfWeight.value) //updating weight graph

                onValueChangeRepeats(newListOfRepeats.value) //updating repeats graph

                onValueChangeCalories(newListOfCalories.value) //updating caloric graph

                onValueChange(dataList)
                //saving changes to the Realm
                scope.launch {
                    viewModel.modifyData(
                        name = name,
                        listOfWeightStart = newListOfWeight.value.toRealmList(),
                        listOfCaloriesStart = newListOfCalories.value.toRealmList(),
                        listOfRepeatsStart = newListOfRepeats.value.toRealmList(),
                        listOfDateStart = newListOfDate.value.toRealmList(),
                        weightBoolean = weightGraph,
                        repeatsBoolean = repeatsGraph,
                        caloricBoolean = caloricGraph,
                        pic = picture.toString()
                    )
                    //deleting previous version (Realm cannot do it by itself)
                    viewModel.deleteSelectedData(name)
                }
            } else {
                //message box appears if something is not filled
                Toast.makeText(
                    context,
                    constants.fillAll,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        //delete button
        ButtonBox(text = constants.delete, height = 50, fontSize = 20){
            openDialog.value = !openDialog.value
        }

        //dialog window for delete button
        if(openDialog.value){
            ConfirmDialog(
                constants = constants,
                typeOfObject = constants.graphElement,
                accept = {
                    //checking if there is more than one point on x axis
                    if(newListOfDate.value.size > 1){

                        //forming input data
                        //same as in previous button, except here it removing data
                        newListOfDate.value -= newListOfDate.value[newListOfDate.value.size-1]
                        messageData.value = timeStamp

                        if (weightGraph){
                            newListOfWeight.value =
                                newListOfWeight.value.subList(0, newListOfWeight.value.size-1)
                            messageWeight.value = ""
                        }
                        if (repeatsGraph) {
                            newListOfRepeats.value =
                                newListOfRepeats.value.subList(0, newListOfRepeats.value.size-1)
                            messageRepeats.value = ""
                        }
                        if(caloricGraph){
                            newListOfCalories.value =
                                newListOfCalories.value.subList(0, newListOfCalories.value.size-1)
                            messageCalories.value = ""
                        }

                        //saving to the Realm
                        scope.launch {
                            viewModel.modifyData(
                                name = name,
                                listOfWeightStart = newListOfWeight.value.toRealmList(),
                                listOfCaloriesStart = newListOfCalories.value.toRealmList(),
                                listOfRepeatsStart = newListOfRepeats.value.toRealmList(),
                                listOfDateStart = newListOfDate.value.toRealmList(),
                                weightBoolean = weightGraph,
                                repeatsBoolean = repeatsGraph,
                                caloricBoolean = caloricGraph,
                                pic = picture.toString()
                            )
                            //deleting previous version (Realm cannot do it by itself)
                            viewModel.deleteSelectedData(name)
                        }

                        //gives data to global categoryChoice
                        screenWithGraphsLogic.chooseCategory(name)
                        val oldCategory = categoryChoice as Category

                        //forming data for dataList
                        //forming actual category
                        val categoryData = CategoryData(
                            newListOfWeight.value,
                            newListOfRepeats.value,
                            newListOfCalories.value,
                            newListOfDate.value,
                            weightGraph,
                            repeatsGraph,
                            caloricGraph,
                            picture
                        )
                        val category = Category(
                            name,
                            categoryData
                        )

                        //updating dateList, also updating categories list on main screen
                        dataList = screenWithGraphsLogic.addToDataList(oldCategory, category)

                        onValueChangeDate(newListOfDate.value) //updating date graph

                        onValueChangeWeight(newListOfWeight.value) //updating weight graph

                        onValueChangeRepeats(newListOfRepeats.value) //updating repeats graph

                        onValueChangeCalories(newListOfCalories.value) //updating caloric graph

                        onValueChange(dataList)

                        openDialog.value = !openDialog.value //closing dialog
                    }else{
                        //message appears if there is no values to delete
                        openDialog.value = !openDialog.value //closing dialog
                        Toast.makeText(context,
                            constants.nothingToDelete,
                            Toast.LENGTH_LONG).show()
                    }
                },
                decline = { openDialog.value = !openDialog.value }

            )
        }

        //empty box for raising up content (when navigation buttons of phone is on screen)
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp))
    }
}