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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.atulit.nutrisport.shared.SurfaceBrand
import com.atulit.nutrisport.shared.SurfaceError
import com.atulit.nutrisport.shared.TextPrimary
import com.atulit.nutrisport.shared.TextSecondary
import com.atulit.nutrisport.shared.TextWhite
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

@Composable
fun AuthScreen() {
    val viewModel = koinViewModel<AuthViewModel> ()

    val messageBarState = rememberMessageBarState()
    var loadingState by remember { mutableStateOf(false) }

    Scaffold { padding ->
        ContentWithMessageBar(
            modifier = Modifier.padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            ),
            contentBackgroundColor = Surface,
            messageBarState = messageBarState,
            errorMaxLines = 2,
            errorContentColor = TextWhite,
            errorContainerColor = SurfaceError,
            successContentColor = TextPrimary,
            successContainerColor = SurfaceBrand,
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

                GoogleButtonUiContainerFirebase(
                    linkAccount = false,
                    onResult = { result ->
                        result.onSuccess { user ->
                            viewModel.createCustomer(
                                user = user,
                                onSuccess = {
                                    messageBarState.addSuccess("Successfully Authenticated!")
                                    loadingState = false
                                },
                                onError = { error ->
                                    messageBarState.addError(error)
                                    loadingState = false
                                }
                            )

                            loadingState = false
                        }
                        result.onFailure {error ->
                            if(error.message?.contains("A Network error") == true) {
                                messageBarState.addError("Internet connection unavailable")
                            } else if(error.message?.contains("Id token is null") == true) {
                                messageBarState.addError("Sign in cancelled.")
                            } else {
                                messageBarState.addError(error.message ?: "Unknown Error")
                            }
                            loadingState = false

                            //messageBarState.addError(error)
                        }
                    }
                ) {
                    GoogleButton(
                        loading = loadingState,
                        onClick = {
                            loadingState = true
                            this@GoogleButtonUiContainerFirebase.onClick()
                        }
                    )
                }

            }
        }
    }
}