package com.example.grafik.logic

import android.net.Uri


//providing data for each category, unit of Category class
//same as CategoryDataRealm

data class CategoryData(
    var listOfWeight: List<Double> = emptyList(),
    var listOfRepeats: List<Int> = emptyList(),
    var listOfCalories: List<Int> = emptyList(),
    var listOfDate: List<String> = emptyList(),
    var weightGraph: Boolean = false,
    var repeatsGraph: Boolean = false,
    var caloricGraph: Boolean = false,
    var picture: Uri? = null
)
