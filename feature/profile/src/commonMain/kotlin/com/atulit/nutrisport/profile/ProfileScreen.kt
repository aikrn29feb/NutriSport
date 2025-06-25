package com.atulit.nutrisport.profile

import ContentWithMessageBar
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atulit.nutrisport.shared.BebasNeueFont
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.IconPrimary
import com.atulit.nutrisport.shared.Resources
import com.atulit.nutrisport.shared.Surface
import com.atulit.nutrisport.shared.TextPrimary
import com.atulit.nutrisport.shared.component.ErrorCard
import com.atulit.nutrisport.shared.component.InfoCard
import com.atulit.nutrisport.shared.component.LoadingCard
import com.atulit.nutrisport.shared.component.PrimaryButton
import com.atulit.nutrisport.shared.component.ProfileForm
import com.atulit.nutrisport.shared.util.DisplayResult
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit
) {

    val viewModel = koinViewModel<ProfileViewModel>()
    val screenState = viewModel.screenState
    val screenReady = viewModel.screenReady
    val isFormValid = viewModel.isFormValid

    val selectedDestination = remember { mutableStateOf("My Profile") }
    val messageBarState = rememberMessageBarState()


    Scaffold(
        contentColor = Surface,
        topBar =
            {
                CenterAlignedTopAppBar(
                    title = {
                        AnimatedContent(
                            targetState = selectedDestination,
                        ) { destination ->
                            Text(
                                text = selectedDestination.value,
                                color = TextPrimary,
                                fontFamily = BebasNeueFont(),
                                fontSize = FontSize.LARGE
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigateBack()
                        }) {
                            val res = Resources.Icon.BackArrow
                            Icon(
                                painter = painterResource(res),
                                contentDescription = "Back Arrow",
                                tint = TextPrimary
                            )
                        }

                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Surface,
                        scrolledContainerColor = Surface,
                        navigationIconContentColor = IconPrimary,
                        titleContentColor = TextPrimary,
                        actionIconContentColor = IconPrimary
                    )
                )
            }) { padding ->

        ContentWithMessageBar(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
            messageBarState = messageBarState,
            errorMaxLines = 2,
            contentBackgroundColor = Surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Surface)
                    .padding(
                        horizontal = 24.dp
                    ).padding(
                        top = 12.dp,
                        bottom = 12.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            )
            {

                screenReady.DisplayResult(
                    onSuccess = { state ->

                        Column(modifier = Modifier.fillMaxSize()) {
                            ProfileForm(
                                modifier = Modifier.weight(1f),
                                country = screenState.country,
                                onCountrySelect = viewModel::updateCountry ,
                                firstName = screenState.firstName,
                                onFirstNameChange = viewModel::updateFirstName,
                                lastName = screenState.lastName,
                                onLastNameChange =  viewModel::updateLastName ,
                                email = screenState.email,
                                city = screenState.city,
                                onCityChange =  viewModel::updateCity ,
                                postalCode = screenState.postalCode,
                                onPostalCodeChange =  viewModel::updatePostalCode ,
                                address = screenState.address,
                                onAddressChange =  viewModel::updateAddress ,
                                phoneNumber = "${screenState.phoneNumber?.number}",
                                onPhoneNumberChange = viewModel::updatePhoneNumber,
                            )

                            Spacer(modifier = Modifier.height(12.dp))


                            PrimaryButton(
                                text = "Update",
                                enabled = isFormValid,
                                onClick = {
                                    viewModel.updateCustomer(
                                        onSuccess = {
                                            messageBarState.addSuccess("Profile updated successfully")
                                            navigateToHome()
                                        },
                                        onError = { errorMessage ->
                                            messageBarState.addError(errorMessage)
                                        },
                                    )
                                }, icon = Resources.Icon.Checkmark
                            )

                        }


                    },
                    onError = { errorMessage ->

                        InfoCard(
                            modifier = Modifier.fillMaxSize(),
                            title = "Error",
                            subTitle = errorMessage,
                            image = Resources.Image.Cat
                        )
                        messageBarState.addError(errorMessage)
                    },
                    onLoading = { LoadingCard(modifier = Modifier.fillMaxSize()) }

                )


            }

        }
    }
}