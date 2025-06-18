package com.atulit.nutrisport.home.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.atulit.nutrisport.home.domain.BottomBarDestination
import com.atulit.nutrisport.shared.IconPrimary
import com.atulit.nutrisport.shared.IconSecondary
import com.atulit.nutrisport.shared.Surface
import com.atulit.nutrisport.shared.SurfaceLighter
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    selected: BottomBarDestination,
    onSelect: (BottomBarDestination) -> Unit
) {

    Row(
        modifier = modifier

            .border(width = 4.dp, color = Surface, shape = RoundedCornerShape(size = 12.dp))
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 12.dp))
            .background(SurfaceLighter)
            .padding(
                horizontal = 36.dp,
                vertical = 24.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        BottomBarDestination.entries.forEach { destination ->
            val animatedTint by animateColorAsState(
                targetValue = if (selected == destination) IconSecondary else IconPrimary
            )

            Icon(
                modifier = modifier.clickable { onSelect(destination) },
                painter = painterResource(destination.icon),
                contentDescription = destination.title,
                tint = animatedTint
            )

        }
    }


}