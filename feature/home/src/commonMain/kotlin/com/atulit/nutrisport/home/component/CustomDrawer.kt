package com.atulit.nutrisport.home.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.atulit.nutrisport.home.domain.DrawerItem
import com.atulit.nutrisport.shared.BebasNeueFont
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.TextPrimary
import com.atulit.nutrisport.shared.TextSecondary
import com.atulit.nutrisport.shared.domain.Customer
import com.atulit.nutrisport.shared.util.RequestState

@Composable
fun CustomDrawer(
    customer: RequestState<Customer>,
    onProfileClick: () -> Unit,
    onContactUsClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onAdminPanelClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.6f)
            .padding(horizontal = 12.dp),
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Nutri Sport",
            textAlign = TextAlign.Center,
            color = TextSecondary,
            fontFamily = BebasNeueFont(),
            fontSize = FontSize.EXTRA_LARGE

        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Healthy Lifestyle",
            textAlign = TextAlign.Center,
            color = TextPrimary,
            fontSize = FontSize.REGULAR

        )
        Spacer(modifier = Modifier.height(50.dp))

        DrawerItem.entries.take(5).forEach { item ->
            DrawerItemCard(item, onClick = {
                when (item) {
                    DrawerItem.Profile -> onProfileClick()
                    DrawerItem.Contact -> onContactUsClick()
                    DrawerItem.SignOut -> onSignOutClick()
                    else -> {

                    }
                }
            })
            Spacer(modifier = Modifier.height(12.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        AnimatedContent(
            targetState = customer
        ) { customerState ->
            if (customerState.isSuccess() && customerState.getSuccessData().isAdmin) {
                DrawerItemCard(
                    drawerItem = DrawerItem.Admin,
                    onClick = {
                        onAdminPanelClick()
                    },
                )
            }

        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}