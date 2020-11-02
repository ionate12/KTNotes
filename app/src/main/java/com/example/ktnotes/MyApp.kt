package com.example.ktnotes

import android.app.Application
import com.example.ktnotes.db.AppDatabase
import com.example.ktnotes.db.NoteSampleData
import com.example.ktnotes.util.SharedPrefUtil

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        //Init Room DB here
        AppDatabase.initInstance(this.applicationContext)

        //Init some fake data.
        if (SharedPrefUtil.getIsFirstUse(this.applicationContext)) {
            NoteSampleData.initSampleData()
            SharedPrefUtil.setIsFirstUse(this.applicationContext, false)
        }


    }
}