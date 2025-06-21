package com.atulit.nutrisport.shared.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.TextPrimary

@Composable
fun ErrorCard(
    modifier: Modifier = Modifier,
    message: String,
    fontSize: TextUnit = FontSize.SMALL,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = TextPrimary
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = message,
            color = color,
            fontSize = fontSize,
            textAlign = textAlign
        )
    }


}