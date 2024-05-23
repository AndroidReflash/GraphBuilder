package com.example.grafik.logic

import android.util.Log
import androidx.core.net.toUri
import com.example.grafik.Realm.DataRealm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityLogic {
    //needed to take data from the Realm
    private suspend fun formDataRealmList(): Flow<List<DataRealm>> {
        val realm = MyApp.realm
        Log.d("MyLogLaunch", "Realm")
        return withContext(Dispatchers.Default) {
            realm.query<DataRealm>().asFlow().map {
                it.list.toList()
            }
        }
    }

    //extracts data from the Realm into RAM of the device
    suspend fun load() = coroutineScope{
        launch { // срабатывает несколько раз. Надо исправить
            Log.d("MyLogLaunch2", "Data is loading")
            val listDB = formDataRealmList()
            listDB.collect{dataDB->
                for (category in dataDB.reversed()) {
                    //reverse raises last changed categories up
                    val dataListCategoryData = CategoryData(
                        category.categoryData!!.listOfWeight,
                        category.categoryData!!.listOfRepeats,
                        category.categoryData!!.listOfCalories,
                        category.categoryData!!.listOfDate,
                        category.categoryData!!.weightGraph,
                        category.categoryData!!.repeatsGraph,
                        category.categoryData!!.caloricGraph,
                        category.categoryData!!.picture.toUri()
                    )
                    val dataListCategory = Category(
                        category.categoryName,
                        dataListCategoryData
                    )
                    dataList += dataListCategory
                }
                Log.d("MyLogLaunch1", "Data is loaded")
            }

        }
    }
}