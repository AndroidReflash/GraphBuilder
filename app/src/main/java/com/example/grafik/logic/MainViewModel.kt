package com.example.grafik.logic

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grafik.Realm.CategoryDataRealm
import com.example.grafik.Realm.DataRealm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    //link to the Realm
    private val realm = MyApp.realm

    //realm data for graphs
    val dataRealm = DataRealm()


    //needed to take data from the Realm
    private suspend fun formDataRealmList(): Flow<List<DataRealm>> {
        Log.d("MyLogLaunchRealm", "Realm")
        return withContext(Dispatchers.IO){
            realm.query<DataRealm>().asFlow().map {
                it.list.toList()
            }
        }
    }


    //extracts data from the Realm into RAM of the device
    suspend fun load(reload:(List<Category>) -> Unit) = coroutineScope{
        val list = formDataRealmList()
        list.collect{
            //forming dataList
            for (category in it.reversed()) {
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
            reload(dataList)
        }
    }

    //deleting data from database
    fun deleteSelectedData(name: String) {
        viewModelScope.launch {
            realm.write {
                //looking for data by using name of category
                val dataRealm =
                    realm.query<DataRealm>("categoryName==$0", name).find().firstOrNull()
                //looking for data by using name of category

                //if previous succeeded, deletes founded
                if (dataRealm != null) {
                    findLatest(dataRealm)
                        ?.also { delete(it) }
                }
                //if previous succeeded, deletes founded
            }
        }
    }
    //this function helps to refresh database or save
    fun modifyData(
        name: String,
        listOfWeightStart: List<Double>,
        listOfRepeatsStart: List<Int>,
        listOfCaloriesStart: List<Int>,
        listOfDateStart: List<String>,
        weightBoolean: Boolean,
        repeatsBoolean: Boolean,
        caloricBoolean: Boolean,
        pic: String
    ) {
        //starting in coroutine, because Realm works only in it
        viewModelScope.launch {
            realm.write {
                //forming data for the Realm
                val data = dataRealm.apply {
                    categoryName = name
                }
                val categoryData = CategoryDataRealm().apply {
                    listOfWeight = listOfWeightStart.toRealmList()
                    listOfRepeats = listOfRepeatsStart.toRealmList()
                    listOfCalories = listOfCaloriesStart.toRealmList()
                    listOfDate = listOfDateStart.toRealmList()
                    weightGraph = weightBoolean
                    repeatsGraph = repeatsBoolean
                    caloricGraph = caloricBoolean
                    picture = pic
                }
                data.categoryData = categoryData
                //forming data for the Realm

                //adding data
                copyToRealm(
                    data, UpdatePolicy.ALL
                )
                //if we trying not to add new one but to modify existing
                //it is needed to use deleteSelectedData function first for deleting old version
            }
        }
    }
}