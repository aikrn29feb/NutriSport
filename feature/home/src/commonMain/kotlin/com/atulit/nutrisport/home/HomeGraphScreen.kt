package com.atulit.nutrisport.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.atulit.nutrisport.home.component.BottomBar
import com.atulit.nutrisport.home.component.CustomDrawer
import com.atulit.nutrisport.home.domain.BottomBarDestination
import com.atulit.nutrisport.home.domain.CustomDrawerState
import com.atulit.nutrisport.home.domain.isOpened
import com.atulit.nutrisport.home.domain.opposite
import com.atulit.nutrisport.shared.Alpha
import com.atulit.nutrisport.shared.BebasNeueFont
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.IconPrimary
import com.atulit.nutrisport.shared.Resources
import com.atulit.nutrisport.shared.Surface
import com.atulit.nutrisport.shared.SurfaceLighter
import com.atulit.nutrisport.shared.TextPrimary
import com.atulit.nutrisport.shared.navigation.Screen
import com.atulit.nutrisport.shared.util.getScreenWidth
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeGraphScreen() {

    val navController = rememberNavController()
    val startDestination = Screen.ProductsOverview
    val currentDestination = navController.currentBackStackEntryAsState()
    //var selectedDestination by remember { mutableStateOf(BottomBarDestination.ProductsOverview) }

    val selectedDestination by remember {
        derivedStateOf {
            val route = currentDestination.value?.destination?.route.toString()
            when {
                route.contains(BottomBarDestination.ProductsOverview.screen.toString()) -> BottomBarDestination.ProductsOverview
                route.contains(BottomBarDestination.Cart.screen.toString()) -> BottomBarDestination.Cart
                route.contains(BottomBarDestination.Categories.screen.toString()) -> BottomBarDestination.Categories
                else -> BottomBarDestination.ProductsOverview
            }
        }
    }
    val screenWidth = remember { getScreenWidth() }
    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }

    val offsetValue by remember { derivedStateOf { (screenWidth / 1.5).dp } }
    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
    )
    val animatedColor by animateColorAsState(
        targetValue = if (drawerState.isOpened()) SurfaceLighter else Surface,
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (drawerState.isOpened()) 0.9f else 1f,
    )

    val animatedRadius by animateDpAsState(
        targetValue = if (drawerState.isOpened()) 20.dp else 0.dp,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedColor)
            .systemBarsPadding()
    ) {

        CustomDrawer(
            onProfileClick = {},
            onContactUsClick = {},
            onSignOutClick = {},
            onAdminPanelClick = {}
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(size = animatedRadius))
                .offset(x = animatedOffset)
                .scale(scale = animatedScale)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(size = animatedRadius),
                    ambientColor = Color.Black.copy(alpha = Alpha.DISABLED),
                    spotColor = Color.Black.copy(alpha = Alpha.DISABLED)
                )


        ) {
            Scaffold(
                contentColor = Surface,
                topBar =
                    {
                        CenterAlignedTopAppBar(
                            title = {
                                AnimatedContent(
                                    targetState = selectedDestination,
                                    /*transitionSpec = {
                                    (fadeIn(animationSpec = spring(20f, 10f, 0.30f)) +
                                            scaleIn(
                                                initialScale = 0.92f,
                                                animationSpec = spring(20f, 10f, 0.30f)
                                            ))
                                        .togetherWith(fadeOut(animationSpec = tween(90)))
                            }*/

                                ) { destination ->
                                    Text(
                                        text = selectedDestination.title,
                                        color = TextPrimary,
                                        fontFamily = BebasNeueFont(),
                                        fontSize = FontSize.LARGE
                                    )

                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    drawerState = drawerState.opposite()
                                }) {
                                    val res = if(drawerState.isOpened()) Resources.Icon.Close else Resources.Icon.Menu
                                    Icon(
                                        painter = painterResource(res),
                                        contentDescription = "Menu",
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

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding()
                        )

                ) {
                    NavHost(
                        modifier = Modifier.weight(1f),
                        navController = navController,
                        startDestination = startDestination
                    ) {
                        composable<Screen.ProductsOverview> {
                            AnimatedContent(
                                targetState = selectedDestination,
                            ) {

                                Text(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Surface),
                                    text = "Products Overview",
                                    color = TextPrimary,

                                )
                            }
                        }
                        composable<Screen.Cart> {
                            AnimatedContent(
                                targetState = selectedDestination,
                            ) {

                                Text(
                                    text = "Cart",
                                    color = TextPrimary,
                                )
                            }
                        }
                        composable<Screen.Categories> {
                            AnimatedContent(
                                targetState = selectedDestination,
                            ) {

                                Text(
                                    text = "Categories",
                                    color = TextPrimary,
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier.padding(
                            start = 12.dp,
                            end = 12.dp,
                        )
                    ) {

                        BottomBar(
                            selected = selectedDestination,
                            onSelect = { destination ->

                                navController.navigate(destination.screen) {
                                    launchSingleTop = true
                                    popUpTo(Screen.ProductsOverview) {
                                        saveState = true
                                        inclusive = false
                                    }
                                    restoreState = true
                                }

                            }
                        )
                    }

                }

            }
        }
    }

}