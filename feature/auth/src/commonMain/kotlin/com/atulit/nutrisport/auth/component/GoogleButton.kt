package com.atulit.nutrisport.auth.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.atulit.nutrisport.shared.BorderIdle
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.IconSecondary
import com.atulit.nutrisport.shared.Resources
import com.atulit.nutrisport.shared.SurfaceLighter
import com.atulit.nutrisport.shared.TextPrimary
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    loading: Boolean = false,
    primaryText: String = "Sign in with Google",
    secondaryText: String = "Please wait...",
    icon: DrawableResource = Resources.Image.GoogleLogo,
    shape: Shape = RoundedCornerShape(size = 99.dp),
    backgroundColor: Color = SurfaceLighter,
    borderColor: Color = BorderIdle,
    progressIndicatorColor: Color = IconSecondary,
) {
    var buttonText by remember { mutableStateOf(primaryText) }

    LaunchedEffect(loading) {
        buttonText = if (loading) secondaryText else primaryText
    }

    Surface(
        modifier = modifier
            .clip(shape = shape)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            )
            .clickable(enabled = !loading) { onClick() },
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp)
                .animateContentSize(
                    animationSpec = tween(durationMillis = 200)
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        )
        {
            AnimatedContent(
                targetState = loading,
                modifier = Modifier.padding(end = 12.dp)
            ) {
                if (!loading) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = "Google Logo",
                        tint = Color.Unspecified
                    )
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        color = progressIndicatorColor,
                        strokeWidth = 2.dp,
                    )
                }
            }

            Spacer(modifier = Modifier.padding(12.dp))
            Text(
                text = buttonText,
                color = TextPrimary,
                fontSize = FontSize.REGULAR
            )

        }

    }

}