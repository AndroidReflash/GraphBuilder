package com.example.grafik.Realm

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

//providing data for each category, unit of list in the Realm
//same as Category

class DataRealm: RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var categoryName: String = ""
    var categoryData: CategoryDataRealm? = null
}