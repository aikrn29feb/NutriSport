package com.atulit.nutrisport

import androidx.compose.ui.window.ComposeUIViewController
import com.atulit.nutrisport.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initializeKoin()
    }
) { App() }