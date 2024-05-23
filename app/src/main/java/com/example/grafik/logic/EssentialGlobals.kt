package com.example.grafik.logic

import androidx.compose.ui.unit.dp
import com.example.grafik.Realm.DataRealm

//data for each category in list, which is forming any time application started
//forming happens in MainActivityLogic.search
//global for accessing from any place
//CraftingNew screen can save data here only if this variable is global
var dataList : List<Category> = emptyList()

//gives the possibility to get category (used in GraphLogic for chooseCategory function)
var categoryChoice: Category? = null

var rusBorderSize = 0
var engBorderSize = 0

//the biggest length of name of category
const val nameSize = 25


//length of number we can input, includes point
//length = 8, biggest number then is 99999.99
val length = 8

//size of boxes, in which we can modify inputted in graph numbers
val size = 40

var languageChoice: String = ""
val russian : String = "Русский"
val english : String = "English"


