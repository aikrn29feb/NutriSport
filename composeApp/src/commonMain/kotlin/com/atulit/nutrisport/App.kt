package com.atulit.nutrisport

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.atulit.nutrisport.data.domain.CustomerRepository
import com.atulit.nutrisport.navigation.Screen
import com.atulit.nutrisport.navigation.SetupNavGraph
import com.atulit.nutrisport.shared.Constants
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MaterialTheme {
        val customerRepository = koinInject<CustomerRepository>()
        val isUserAuthenticated = remember { customerRepository.getCustomerId() != null }
        var appReady by remember { mutableStateOf(false) }

        val startDestination = if (isUserAuthenticated) {
            Screen.HomeGraph
        } else {
            Screen.Auth
        }

        LaunchedEffect(Unit) {
            GoogleAuthProvider.create(
                credentials = GoogleAuthCredentials(
                    serverId = Constants.WEB_CLIENT_ID
                )
            )
            appReady = true
        }

        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = appReady
        ) {
            SetupNavGraph(startDestination = startDestination)
        }
    }
}