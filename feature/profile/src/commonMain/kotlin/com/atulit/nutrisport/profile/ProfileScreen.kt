package com.atulit.nutrisport.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.atulit.nutrisport.shared.Resources
import com.atulit.nutrisport.shared.component.PrimaryButton

@Composable
fun ProfileScreen(
    navigateToHome: () -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,

    ) {
        PrimaryButton(
            text = "Continue",
            onClick = {
                navigateToHome()
            },
            icon = Resources.Icon.Checkmark,
            enabled = true
        )
    }

}