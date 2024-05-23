package com.example.grafik.logic

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CraftingNewLogic {
    val constants = if(languageChoice== russian){
        russianConstants
    }else{
        englishConstants
    }
    //helps in selection of category
    fun checkName(name: String): Boolean{
        for(category in dataList){
            if(category.categoryName == name){
                return false
            }
        }
        return true
    }
    fun addToDataList(category: Category){
        val list = emptyList<Category>().toMutableList()
        list += category
        list += dataList
        dataList = list
    }

    //function to save pictures/gifs
    fun saveFileToInternalStorage(context: Context, uri: Uri): Uri? {
        val contentResolver: ContentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.use { input ->
            // Creating unique name depending on time
            val timeStamp: String = SimpleDateFormat(
                constants.dataFormat, Locale.getDefault()).format(Date())
            val filename = "file_$timeStamp.txt"

            // Creating file in internal storage of app
            val outputFile = File(context.filesDir, filename)
            // Copying data from stream to new file
            FileOutputStream(outputFile).use { output ->
                val buffer = ByteArray(4 * 1024) // 4 KB buffer size
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                }
            }
            return Uri.fromFile(outputFile)
        }
        return null
    }

}