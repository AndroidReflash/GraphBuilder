package com.example.grafik.ui.theme.screenElements.CraftingNew

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.grafik.R
import com.example.grafik.logic.Category
import com.example.grafik.logic.CategoryData
import com.example.grafik.logic.Constants
import com.example.grafik.logic.CraftingNewLogic
import com.example.grafik.logic.MainViewModel
import com.example.grafik.logic.Screen
import com.example.grafik.logic.dataList
import com.example.grafik.logic.nameSize
import com.example.grafik.ui.theme.screenElements.Background
import com.example.grafik.ui.theme.screenElements.ButtonBox
import com.example.grafik.ui.theme.screenElements.ImageUri
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.launch


@Composable
fun CraftingNew(
    navController: NavController,
    constants: Constants,       //for text
    onValueChange: (List<Category>) -> Unit
) {
    //essential values
    val craftingNewLogic = CraftingNewLogic()
    val viewModel = MainViewModel()     //for saving to the Realm
    var imageUri by remember {          //address of picture
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val listOfWeight = listOf(0.0)     //initial list of weight
    val listOfRepeats = listOf(0)       //initial list of repeats
    val listOfCalories = listOf(0)      //initial list of calories
    val listOfDate = listOf("0")        //initial list of date

    val checkWeight = remember {        //recomposable for radiobutton of weight graph
        mutableStateOf(false)
    }
    val checkRepeats = remember {       //recomposable for radiobutton of repeats graph
        mutableStateOf(false)
    }
    val checkImage = remember {         //recomposable for radiobutton of image
        mutableStateOf(false)
    }
    val checkCalories = remember {      //recomposable for radiobutton of calories
        mutableStateOf(false)
    }

    //this is needed to choosing picture
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) {uri: Uri? ->
        imageUri = uri
        checkImage.value = imageUri != null
    }

    val nameOfCategory = remember { mutableStateOf("") } //for textField of categoryName

    //content
    Background(painter = painterResource(id = R.drawable.crafting_new))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        val scroll = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(scroll),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        ) {
            //textField for typing name of category
            Text(text = constants.typeTheName)
            TextField(
                modifier = Modifier
                    .width(200.dp)
                    .padding(10.dp),
                value = nameOfCategory.value,
                textStyle = TextStyle(fontSize = 20.sp),
                onValueChange = {newText -> nameOfCategory.value = newText}
            )

            //buttons for applying graphs
            Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {
                //button for the graph of weight
                RadioButton(
                    checkWeight.value,
                    constants.weightGraph,
                    onValueChange = { checkWeight.value = it }
                )

                //button for the graph of repeats
                RadioButton(
                    checkRepeats.value,
                    constants.repeatsGraph,
                    onValueChange = { checkRepeats.value = it }
                )

                //button for the graph of calories
                RadioButton(
                    checkCalories.value,
                    constants.caloriesGraph,
                    onValueChange = { checkCalories.value = it }
                )

                //button for accepting of using image
                RadioButton(
                    checkImage.value,
                    constants.image,
                    onValueChange = {
                        checkImage.value = it
                        launcher.launch("image/*")
                    }
                )
            }

            //box with a picture/gif
            if(imageUri != null){
                Column {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp), contentAlignment = Alignment.Center){
                        ImageUri(source = imageUri!!)
                    }
                }
            }

            //apply button
            ButtonBox(text = constants.accept, height = 50, fontSize = 20){
                //name of category cannot be empty
                if(
                    nameOfCategory.value.isNotEmpty()
                    && craftingNewLogic.checkName(nameOfCategory.value)
                    && (nameOfCategory.value.length <= nameSize)
                ){

                    if(
                    //it is needed to be chosen at least one of the graphs
                    //there is can be no picture
                        !checkWeight.value && !checkRepeats.value && !checkCalories.value
                    ) {
                        //message appears if it is not enough of chosen things
                        Toast.makeText(context, constants.chooseSomething, Toast.LENGTH_LONG)
                            .show()
                    }else{
                        //recreates Uri from place it was found to place it was saved
                        //Uri of copy of chosen pic/gif in internal storage
                        if(imageUri.toString() != constants.empty){
                            imageUri = craftingNewLogic.saveFileToInternalStorage(context, imageUri as Uri)
                        }

                        //coroutine for saving, saving is not working outside of coroutine
                        scope.launch {
                            //saving initial data of that category to the Realm
                            viewModel.modifyData(
                                name = nameOfCategory.value,
                                listOfWeightStart = listOfWeight.toRealmList(),
                                listOfCaloriesStart = listOfCalories.toRealmList(),
                                listOfRepeatsStart = listOfRepeats.toRealmList(),
                                listOfDateStart = listOfDate.toRealmList(),
                                weightBoolean = checkWeight.value,
                                repeatsBoolean = checkRepeats.value,
                                caloricBoolean = checkCalories.value,
                                pic = imageUri.toString()
                            )
                        }

                        //forming initial data of category for adding this to dataList
                        val categoryData = CategoryData(
                            listOfWeight,
                            listOfRepeats,
                            listOfCalories,
                            listOfDate,
                            checkWeight.value,
                            checkRepeats.value,
                            checkCalories.value,
                            imageUri
                        )
                        val category = Category(
                            nameOfCategory.value,
                            categoryData
                        )

                        //adding category to the dataList
                        craftingNewLogic.addToDataList(category)

                        //recomposing list of categories on the main screen
                        onValueChange(dataList)

                        //navigating to the main screen
                        navController.navigate(Screen.MainScreen.route)
                    }
                }else if(nameOfCategory.value.isEmpty()){
                    //if categoryName is empty
                    Toast.makeText(context, constants.fillTheNameOfCategory, Toast.LENGTH_LONG)
                        .show()
                }else if(!craftingNewLogic.checkName(nameOfCategory.value)){
                    //you cannot create two categories with the same names
                    Toast.makeText(context, constants.existingNameOfCategory, Toast.LENGTH_LONG)
                        .show()
                }else if(nameOfCategory.value.length > nameSize){
                    //categoryName is too long
                    Toast.makeText(context, constants.tooBig, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}

