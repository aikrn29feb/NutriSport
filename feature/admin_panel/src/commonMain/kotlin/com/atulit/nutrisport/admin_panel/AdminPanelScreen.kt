package com.atulit.nutrisport.admin_panel

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atulit.nutrisport.shared.BebasNeueFont
import com.atulit.nutrisport.shared.ButtonPrimary
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.IconPrimary
import com.atulit.nutrisport.shared.Resources
import com.atulit.nutrisport.shared.Surface
import com.atulit.nutrisport.shared.TextPrimary
import com.atulit.nutrisport.shared.component.InfoCard
import com.atulit.nutrisport.shared.component.LoadingCard
import com.atulit.nutrisport.shared.component.ProductCard
import com.atulit.nutrisport.shared.util.DisplayResult
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanelScreen(
    navigateBack: () -> Unit,
    navigateToManageProduct: (String?) -> Unit,
) {

    val viewModel = koinViewModel<AdminPanelViewModel>()
    val products = viewModel.products.collectAsState()

    val selectedDestination = remember { mutableStateOf("Admin Panel") }

    Scaffold(
        contentColor = Surface,
        containerColor = Surface,
        topBar =
            {
                TopAppBar(
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
                    actions = {
                        IconButton(onClick = {

                        }) {
                            val res = Resources.Icon.Search
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
            },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateToManageProduct(null)
                },
                containerColor = ButtonPrimary,
                contentColor = IconPrimary,
                content = {
                    val res = Resources.Icon.Plus
                    Icon(
                        painter = painterResource(res),
                        contentDescription = "Add Icon",
                        tint = IconPrimary
                    )
                }
            )

        }) { padding ->

        products.value.DisplayResult(
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
            onLoading = {
                LoadingCard(
                    modifier = Modifier
                        .fillMaxSize()
                )
            },
            onSuccess = { lastProducts ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            all = 12.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = lastProducts.size,
                        key = { index ->
                            lastProducts[index].id
                        }
                    ) { product ->
                        ProductCard(
                            modifier = Modifier
                                .fillMaxWidth(),
                            product = lastProducts[product],
                            onClick = {
                                navigateToManageProduct(lastProducts[product].id)
                            }
                        )


                    }


                }

            },
            onError = { message ->
                InfoCard(
                    modifier = Modifier
                        .fillMaxSize(),
                    title = "Oops!",
                    subTitle = message,
                    image = Resources.Image.Cat
                )

            }
        )


        /* InfoCard(
             modifier = Modifier
                 .fillMaxSize()
                 .padding(
                     top = padding.calculateTopPadding(),
                     bottom = padding.calculateBottomPadding()
                 ),
             title = "Admin Panel",
             subTitle = "This is the admin panel",
             image = Resources.Image.ShoppingCart
         )*/
        /*val product = Product(
            id = "1",
            title = "Product 1",
            description = "This is the first product \n " +
                    "It has to cover atleast 3 lines \n " +
                    "out of which 2 are already covered",
            thumbnail = "https://picsum.photos/200",
            category = "Category 1",
            flavors = listOf("Flavor 1", "Flavor 2"),
            weight = 100,
            price = 10.0
        )

        ProductCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
            product = product,
            onClick = {
                navigateToManageProduct(it)
            }
        )*/


    }
}
