package com.atulit.nutrisport.auth

import ContentWithMessageBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.atulit.nutrisport.auth.component.GoogleButton
import com.atulit.nutrisport.shared.Alpha
import com.atulit.nutrisport.shared.BebasNeueFont
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.Surface
import com.atulit.nutrisport.shared.TextPrimary
import com.atulit.nutrisport.shared.TextSecondary
import rememberMessageBarState

@Composable
fun AuthScreen() {

    val messageBarState = rememberMessageBarState()

    Scaffold { padding ->
        ContentWithMessageBar(
            modifier = Modifier.padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            ),
            contentBackgroundColor = Surface,
            messageBarState = messageBarState,
            errorMaxLines = 2
        ) {
            Column(modifier = Modifier
                .padding(all = 24.dp)
                .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "NUTRISPORT",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontFamily = BebasNeueFont(),
                        fontSize = FontSize.EXTRA_LARGE,
                        maxLines = 1,
                        color = TextSecondary
                    )

                    Text(
                        text = "Sign in to continue",
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(Alpha.HALF),
                        textAlign = TextAlign.Center,
                        fontSize = FontSize.EXTRA_REGULAR,
                        maxLines = 1,
                        color = TextPrimary
                    )

                }

                GoogleButton(
                    loading = false,
                    onClick = {

                    }
                )

            }
        }
    }
}