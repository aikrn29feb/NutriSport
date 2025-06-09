package com.atulit.nutrisport

import android.app.Application
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

class NutriSportApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Firebase.initialize(context = this)

    }
}