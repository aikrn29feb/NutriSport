package com.atulit.nutrisport

import android.app.Application
import com.atulit.nutrisport.di.initializeKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import org.koin.android.ext.koin.androidContext

class NutriSportApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin(
            config = {
                androidContext(this@NutriSportApp)
            }
        )
        Firebase.initialize(context = this)

    }
}