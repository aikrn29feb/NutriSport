package com.atulit.nutrisport.shared.component.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.atulit.nutrisport.shared.Alpha
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.IconPrimary
import com.atulit.nutrisport.shared.Resources
import com.atulit.nutrisport.shared.Surface
import com.atulit.nutrisport.shared.TextPrimary
import com.atulit.nutrisport.shared.TextSecondary
import com.atulit.nutrisport.shared.domain.ProductCategory
import org.jetbrains.compose.resources.painterResource

@Composable
fun CategoriesDialog(
    category: ProductCategory,
    onDismiss: () -> Unit,
    onConfirmClick: (ProductCategory) -> Unit,
) {
    var selectedCategory by remember(category) { mutableStateOf(category) }

    AlertDialog(
        containerColor = Surface,
        title = {
            Text(
                text = "Pick a Category",
                fontSize = FontSize.EXTRA_MEDIUM,
                color = TextPrimary
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                ,
            ) {

                ProductCategory.entries.forEach { currentCategory ->
                    val animatedBackground by animateColorAsState(
                        targetValue =
                            if (selectedCategory == currentCategory)
                                currentCategory.color.copy(alpha = Alpha.TWENTY_PERCENT)
                            else
                                Color.Transparent
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(size = 6.dp))
                            .background(animatedBackground)
                            .clickable {
                                selectedCategory = currentCategory
                            }
                            .padding(
                                vertical = 12.dp,
                                horizontal = 12.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        Text(
                            text = currentCategory.title,
                            fontSize = FontSize.REGULAR,
                            color = TextPrimary,
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        Selector(
                            modifier = Modifier,
                            isSelected = selectedCategory == currentCategory
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                    }
                }
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onConfirmClick(selectedCategory) },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = TextSecondary
                )
            ) {
                Text(
                    text = "Confirm",
                    fontSize = FontSize.REGULAR,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = TextPrimary.copy(alpha = Alpha.HALF)
                )
            ) {
                Text(
                    text = "Cancel",
                    fontSize = FontSize.REGULAR,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    )
}

@Composable
private fun Selector(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {

    Box(
        modifier = modifier
            .size(20.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isSelected
        ) {
            Icon(
                modifier = Modifier.size(14.dp),
                painter = painterResource(Resources.Icon.Checkmark),
                contentDescription = "Checkmark icon",
                tint = IconPrimary
            )
        }
    }
}
