package com.example.grafik.logic

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.example.grafik.ui.theme.screenNavigation.Navigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val prefLang = getSharedPreferences("language", MODE_PRIVATE)
        val edit = prefLang.edit()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            languageChoice = prefLang.getString("choice", "") as String
            val constants = if(languageChoice == russian){
                russianConstants
            }else{
                englishConstants
            }
            Navigation(edit, constants) //shows content of main screen

        }
    }
}
