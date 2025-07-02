package com.atulit.nutrisport.manage_product

import ContentWithMessageBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atulit.nutrisport.shared.BebasNeueFont
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.IconPrimary
import com.atulit.nutrisport.shared.Resources
import com.atulit.nutrisport.shared.Surface
import com.atulit.nutrisport.shared.SurfaceBrand
import com.atulit.nutrisport.shared.SurfaceDarker
import com.atulit.nutrisport.shared.SurfaceLighter
import com.atulit.nutrisport.shared.TextPrimary
import com.atulit.nutrisport.shared.TextSecondary
import com.atulit.nutrisport.shared.component.AlertTextField
import com.atulit.nutrisport.shared.component.CustomTextField
import com.atulit.nutrisport.shared.component.ErrorCard
import com.atulit.nutrisport.shared.component.LoadingCard
import com.atulit.nutrisport.shared.component.PrimaryButton
import com.atulit.nutrisport.shared.component.dialog.CategoriesDialog
import com.atulit.nutrisport.shared.domain.ProductCategory
import com.atulit.nutrisport.shared.util.DisplayResult
import com.atulit.nutrisport.shared.util.RequestState
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageProductScreen(
    id: String?,
    navigateBack: () -> Unit
) {
    val viewModel = koinViewModel<ManageProductViewModel>()
    val screenState = viewModel.screenState
    val isFormValid = viewModel.isFormValid
    val thumbnailUploaderState = viewModel.thumbnailUploaderState
    val messageBarState = rememberMessageBarState()
    var showCategoriesDialog by remember { mutableStateOf(false) }

    val photoPicker = koinInject<PhotoPicker>()
    photoPicker.InitializePhotoPicker(
        onImageSelect = { file ->
            viewModel.uploadThumbnailToStorage(
                file = file,
                onSuccess = {
                    messageBarState.addSuccess("Thumbnail uploaded successfully")
                    viewModel.updateThumbnailUploaderState(RequestState.Success(Unit))
                },
            )
        }
    )

    AnimatedVisibility(
        visible = showCategoriesDialog
    ) {
        CategoriesDialog(
            category = screenState.category,
            onDismiss = {
                showCategoriesDialog = false
            },
            onConfirmClick = { selectedCategory ->
                viewModel.updateCategory(selectedCategory)
                showCategoriesDialog = false
            }
        )

    }

    Scaffold(
        contentColor = Surface,
        containerColor = Surface,
        topBar =
            {
                TopAppBar(
                    title = {
                        Text(
                            text = if (id == null) "New Product" else "Edit Product",
                            color = TextPrimary,
                            fontFamily = BebasNeueFont(),
                            fontSize = FontSize.LARGE
                        )
                    },
                    actions = {
                        IconButton(onClick = {

                        }) {
                            val res = if (id == null)
                                Resources.Icon.Close
                            else
                                Resources.Icon.VerticalMenu
                            Icon(
                                painter = painterResource(res),
                                contentDescription = "Search Icon",
                                tint = TextPrimary
                            )
                        }

                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigateBack()
                        }) {
                            val res = if (id == null)
                                Resources.Icon.BackArrow
                            else
                                Resources.Icon.VerticalMenu
                            Icon(
                                painter = painterResource(res),
                                contentDescription = "Action Icon",
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
            },
    ) { padding ->

        ContentWithMessageBar(
            messageBarState = messageBarState,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
            errorMaxLines = 2,
            contentBackgroundColor = Surface

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 24.dp,
                    )
                    .padding(
                        top = 12.dp,
                        bottom = 24.dp
                    )
                    .imePadding()
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)

                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = 12.dp
                            )
                            .height(300.dp)
                            .clip(
                                RoundedCornerShape(size = 12.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = SurfaceDarker,
                                shape = RoundedCornerShape(size = 12.dp)
                            )
                            .clickable(
                                enabled = thumbnailUploaderState.isIdle()
                            ) {
                                photoPicker.open()

                            }
                            .background(SurfaceLighter),
                        contentAlignment = Alignment.Center
                    ) {

                        thumbnailUploaderState.DisplayResult(
                            onIdle = {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(Resources.Icon.Plus),
                                    contentDescription = "Add Image",
                                    tint = IconPrimary
                                )
                            },
                            onError = { message ->
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {

                                    ErrorCard(
                                        message = message
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    TextButton(
                                        onClick = {
                                            viewModel.updateThumbnailUploaderState(RequestState.Idle)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Transparent,
                                            contentColor = TextSecondary
                                        )
                                    ) {
                                        Text(
                                            text = "Retry",
                                            color = TextPrimary,
                                            fontFamily = BebasNeueFont(),
                                            fontSize = FontSize.MEDIUM
                                        )
                                    }

                                }

                            },
                            onLoading = {
                                LoadingCard(
                                    modifier = Modifier.fillMaxSize(),
                                    alignment = Alignment.Center,
                                )
                            },
                            onSuccess = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                ) {
                                    AsyncImage(
                                        modifier = Modifier.fillMaxSize(),
                                        model = ImageRequest.Builder(
                                            LocalPlatformContext.current
                                        ).data(screenState.thumbnail)
                                            .crossfade(enable = true)
                                            .build(),
                                        contentDescription = "Product Thumbnail image",
                                        contentScale = ContentScale.Crop
                                    )

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .padding(
                                                top = 12.dp,
                                                end = 12.dp
                                            )
                                            .background(SurfaceBrand)
                                            .padding(all = 12.dp)
                                            .clickable {
                                                viewModel.deleteImageFromStorage(
                                                    onSuccess = {
                                                        messageBarState.addSuccess("Thumbnail removed successfully")
                                                    },
                                                    onError = { message ->
                                                        messageBarState.addError(
                                                            message
                                                        )
                                                    }
                                                )
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(14.dp),
                                            painter = painterResource(Resources.Icon.Delete),
                                            contentDescription = "Add Image",
                                            tint = IconPrimary
                                        )
                                    }
                                }

                            }

                        )

                    }

                    CustomTextField(
                        value = screenState.title,
                        onValueChange = viewModel::updateTitle,
                        placeholder = "Title",
                    )
                    CustomTextField(
                        modifier = Modifier.height(168.dp),
                        value = screenState.description,
                        onValueChange = viewModel::updateDescription,
                        placeholder = "Description",
                        expanded = true,
                    )
                    AlertTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = screenState.category.title,
                        onClick = {
                            showCategoriesDialog = true
                        }
                    )
                    AnimatedVisibility(
                        visible = screenState.category != ProductCategory.Accessories
                    ) {

                        CustomTextField(
                            value = "${screenState.weight ?: ""}",
                            onValueChange = { viewModel.updateWeight(it.toIntOrNull() ?: 0) },
                            placeholder = "Weight ",
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )

                        CustomTextField(
                            value = screenState.flavors,
                            onValueChange = viewModel::updateFlavors,
                            placeholder = "Flavors ",
                        )


                    }
                    CustomTextField(
                        value = screenState.price.toString(),
                        onValueChange = { value ->
                            if (value.toDoubleOrNull() != null || value.isEmpty()) {
                                viewModel.updatePrice(value.toDoubleOrNull() ?: 0.0)
                            }
                        },
                        placeholder = "Price",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))


                }

                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (id == null) "Add new product" else "Update",
                    icon = if (id == null) Resources.Icon.Plus else Resources.Icon.Checkmark,
                    onClick = {
                        viewModel.createNewProduct(
                            onSuccess = {
                                messageBarState.addSuccess("Product added successfully")
                            },
                            onError = { message ->
                                messageBarState.addError(message)
                            }
                        )
                    },
                    enabled = isFormValid
                )

            }

        }


    }
}
