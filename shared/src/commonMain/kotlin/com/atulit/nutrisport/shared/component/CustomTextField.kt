package com.atulit.nutrisport.shared.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.atulit.nutrisport.shared.Alpha
import com.atulit.nutrisport.shared.BorderError
import com.atulit.nutrisport.shared.BorderIdle
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.SurfaceLighter
import com.atulit.nutrisport.shared.TextPrimary

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String? = null,
    enabled: Boolean = true,
    error: Boolean = false,
    expanded: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text
    ),
) {

    val borderColor = animateColorAsState(
        targetValue = if (error) {
            BorderError
        } else BorderIdle,
    )

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor.value,
                shape = RoundedCornerShape(size = 99.dp)
            ),
        value = value,
        onValueChange = onValueChanged,
        placeholder = if (placeholder != null) {
            {
                Text(
                    modifier = Modifier.alpha(Alpha.HALF),
                    text = placeholder,
                    fontSize = FontSize.REGULAR
                )
            }
        } else null,
        enabled = enabled,
        singleLine = !expanded,
        isError = error,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(size = 6.dp),
        colors =
            TextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                focusedIndicatorColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                focusedContainerColor = SurfaceLighter,
                unfocusedContainerColor = SurfaceLighter,
                disabledTextColor = TextPrimary.copy(alpha = Alpha.HALF),
                focusedPlaceholderColor = TextPrimary.copy(alpha = Alpha.HALF),
                unfocusedPlaceholderColor = TextPrimary.copy(alpha = Alpha.HALF),
                disabledPlaceholderColor = TextPrimary.copy(alpha = Alpha.DISABLED),

            )
        /*TextFieldDefaults().TextFieldColors(
                textColor = TextPrimary,
                disabledTextColor = TextPrimary.copy(alpha = Alpha.HALF),
                backgroundColor = SurfaceLighter
            )*/
    )


}