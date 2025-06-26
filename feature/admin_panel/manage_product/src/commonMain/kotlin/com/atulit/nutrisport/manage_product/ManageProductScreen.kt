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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.atulit.nutrisport.shared.BebasNeueFont
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.IconPrimary
import com.atulit.nutrisport.shared.Resources
import com.atulit.nutrisport.shared.Surface
import com.atulit.nutrisport.shared.SurfaceDarker
import com.atulit.nutrisport.shared.SurfaceLighter
import com.atulit.nutrisport.shared.TextPrimary
import com.atulit.nutrisport.shared.component.AlertTextField
import com.atulit.nutrisport.shared.component.CustomTextField
import com.atulit.nutrisport.shared.component.PrimaryButton
import com.atulit.nutrisport.shared.component.dialog.CategoriesDialog
import com.atulit.nutrisport.shared.domain.ProductCategory
import org.jetbrains.compose.resources.painterResource
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageProductScreen(
    id: String?,
    navigateBack: () -> Unit
) {
    val messageBarState = rememberMessageBarState()

    var category by remember { mutableStateOf(ProductCategory.Protein)}
    var showCategoriesDialog by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = showCategoriesDialog
    ) {
        CategoriesDialog(
            category = category,
            onDismiss = {
                showCategoriesDialog =false
            },
            onConfirmClick = { selectedCategory ->
                category = selectedCategory
                showCategoriesDialog =false
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
                            .clickable {

                            }
                            .background(SurfaceLighter),
                        contentAlignment = Alignment.Center
                    ){

                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(Resources.Icon.Plus),
                            contentDescription = "Add Image",
                            tint = IconPrimary
                        )

                    }

                    CustomTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = "Title",
                    )
                    CustomTextField(
                        modifier = Modifier.height(168.dp),
                        value = "",
                        onValueChange = {},
                        placeholder = "Description",
                        expanded = true,
                    )
                    AlertTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = category.title,
                        onClick = {
                            showCategoriesDialog = true
                        }
                    )

                    CustomTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = "Weight (Optional)",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    CustomTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = "Flavors (Optional)",
                    )

                    CustomTextField(
                        value = "",
                        onValueChange = {},
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
                    onClick = { }
                )

            }

        }
        /*InfoCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
            title = "Add Product Page",
            subTitle = "This is the admin panel to add products",
            image = Resources.Image.ShoppingCart
        )*/


    }
}
