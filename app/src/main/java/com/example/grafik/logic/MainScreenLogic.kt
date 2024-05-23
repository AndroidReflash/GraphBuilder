package com.example.grafik.logic

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class MainScreenLogic {
    //works with dataList
    fun search(
        request: String,        //what you type in search bar
        list: List<Category>    //where you looking for
    ): List<Category>{
        val foundedList = mutableListOf<Category>()     //future list of founded results
        for(category in list){                          //checking all categories for concurrence
            if(request in category.categoryName){
                foundedList+= category                  //forming list of results
            }
        }
        return foundedList
    }

    //works with elements of dataList
    fun deleteCategory(category: Category){
        dataList -= category
        val uri = category.categoryData.picture
        deleteFileByUri(uri as Uri)
    }

    private fun deleteFileByUri(uri: Uri): Boolean { //deleting from internal storage

        // Getting path to file from Uri
        val filePath: String = uri.path
            ?: // Wrong URI (when there is no picture, for example)
            return false

        // Creating file object from the path
        val file = File(filePath)

        // Delete file
        val deleted = file.delete()

        if (deleted) {
            // File successfully deleted
            return true
        } else {
            // Something went wrong
            return false
        }
    }
}