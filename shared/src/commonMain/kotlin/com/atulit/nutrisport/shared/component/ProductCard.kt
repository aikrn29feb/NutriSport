package com.atulit.nutrisport.shared.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atulit.nutrisport.shared.Alpha
import com.atulit.nutrisport.shared.BorderIdle
import com.atulit.nutrisport.shared.FontSize
import com.atulit.nutrisport.shared.Resources
import com.atulit.nutrisport.shared.RobotoCondensedFont
import com.atulit.nutrisport.shared.SurfaceLighter
import com.atulit.nutrisport.shared.TextPrimary
import com.atulit.nutrisport.shared.TextSecondary
import com.atulit.nutrisport.shared.domain.Product
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProductCard(
    modifier: Modifier,
    product: Product,
    onClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .size(180.dp)
            .padding(
                start = 4.dp,
                end = 4.dp,
                top = 6.dp,
                bottom = 6.dp
            ),

        colors = CardDefaults.cardColors(
            containerColor = SurfaceLighter,
            contentColor = TextPrimary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        onClick =
            {
                //onClick(product.id)
                println("Product Card Clicked")
            }


    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = BorderIdle,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(SurfaceLighter)
                .clickable {
                    onClick(product.id)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                modifier = Modifier
                    .width(120.dp)
                    //.height(IntrinsicSize.Min)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp,
                        color = BorderIdle,
                        shape = RoundedCornerShape(12.dp)
                    ),
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(product.thumbnail)
                    .crossfade(enable = true)
                    .build(),
                contentDescription = product.title,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(4.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = 12.dp)
            ) {
                Text(
                    text = product.title,
                    color = TextPrimary,
                    fontFamily = RobotoCondensedFont(),
                    fontSize = FontSize.MEDIUM,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(alpha = Alpha.SEVENTY_PERCENT),
                    text = product.description,
                    color = TextPrimary.copy(alpha = Alpha.SEVENTY_PERCENT),
                    fontSize = FontSize.REGULAR,
                    maxLines = 3,
                    lineHeight = FontSize.REGULAR * 1.3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    AnimatedContent(
                        targetState = product.category,
                    ) { category ->
                        /*if (ProductCategory.valueOf(category) == ProductCategory.Accessories) {
                            Spacer(modifier = Modifier.weight(1f))
                        } else {*/
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            //horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                painter = painterResource(Resources.Icon.Weight),
                                contentDescription = "Weight Icon",
                                tint = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = product.weight.toString() + " g",
                                color = TextPrimary,
                                fontSize = FontSize.EXTRA_SMALL
                            )

                        }
                        // }

                    }

                    Text(
                        text = "$ " + product.price.toString(),
                        color = TextSecondary,
                        fontSize = FontSize.EXTRA_REGULAR,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

}