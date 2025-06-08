package com.atulit.nutrisport

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.atulit.nutrisport.navigation.SetupNavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        SetupNavGraph()
    }
}