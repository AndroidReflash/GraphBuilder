package com.example.grafik.logic

import android.app.Application
import com.example.grafik.Realm.CategoryDataRealm
import com.example.grafik.Realm.DataRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class MyApp: Application() {

    companion object{
        lateinit var realm: Realm
    }

    //provides access to the Realm
    override fun onCreate() {
        super.onCreate()
        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    DataRealm::class,
                    CategoryDataRealm::class,
                )
            )
        )
    }
}