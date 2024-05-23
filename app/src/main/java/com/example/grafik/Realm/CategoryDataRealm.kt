package com.example.grafik.Realm

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList

//providing anything needed for category, smallest unit of dataList
//same as CategoryData

class CategoryDataRealm: EmbeddedRealmObject {
    var listOfWeight: RealmList<Double> = realmListOf()
    var listOfRepeats: RealmList<Int> = realmListOf()
    var listOfCalories: RealmList<Int> = realmListOf()
    var listOfDate: RealmList<String> =  realmListOf()
    var weightGraph: Boolean = false
    var repeatsGraph: Boolean = false
    var caloricGraph: Boolean = false
    var picture: String = ""
}
