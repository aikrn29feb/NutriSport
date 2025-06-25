package com.atulit.nutrisport.admin_panel

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.atulit.nutrisport.shared.BebasNeueFont
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.IconPrimary
import com.atulit.nutrisport.shared.Resources
import com.atulit.nutrisport.shared.Surface
import com.atulit.nutrisport.shared.TextPrimary
import com.atulit.nutrisport.shared.component.InfoCard
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanelScreen(
    navigateBack: () -> Unit
) {
    val selectedDestination = remember { mutableStateOf("Admin Panel") }

    Scaffold(
        contentColor = Surface,
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

        InfoCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
            title = "Admin Panel",
            subTitle = "This is the admin panel",
            image = Resources.Image.ShoppingCart
        )



    }
}
