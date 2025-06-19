package com.atulit.nutrisport.shared.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.atulit.nutrisport.shared.Alpha
import com.atulit.nutrisport.shared.ButtonDisabled
import com.atulit.nutrisport.shared.ButtonPrimary
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.TextPrimary
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    icon: DrawableResource? = null,
) {
    Button(
        modifier = modifier.fillMaxWidth()
            .padding(16.dp),
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonPrimary,
            contentColor = TextPrimary,
            disabledContainerColor = ButtonDisabled,
            disabledContentColor = TextPrimary.copy(alpha = Alpha.DISABLED)
        ),
        contentPadding = PaddingValues(all = 20.dp)

    ) {
        if (icon != null) {
            Icon(
                painter = painterResource(icon),
                modifier = Modifier.size(14.dp),
                contentDescription = "Button Icon"
            )
        }
        Spacer(
            modifier = Modifier.width(12.dp)
        )
        Text(
            text = text,
            fontSize = FontSize.REGULAR,
            fontWeight = FontWeight.Medium
        )
    }

}