package com.atulit.nutrisport.shared.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.atulit.nutrisport.shared.Alpha
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.TextPrimary
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    image: DrawableResource,
    color: Color = TextPrimary
) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(60.dp),
            painter = painterResource(image),
            contentDescription = "Info image"
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = title,
            color = color,
            fontSize = FontSize.MEDIUM,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subTitle,
            color = color.copy(alpha = Alpha.HALF),
            fontSize = FontSize.REGULAR,
            textAlign = TextAlign.Center,
        )
    }


}