package com.nidhin.customerapp.presentation.components

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.nidhin.customerapp.domain.model.ListingItem
import com.nidhin.customerapp.presentation.productdetails.ProductDetailsActivity
import kotlin.math.roundToInt


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun CartListing(
    onAdd: (String) -> Unit, onMinus: (String) -> Unit,
    item: ListingItem
) {
    var context = LocalContext.current
    var color = Color.Transparent
    if (item.available == false)
        color = Color.LightGray
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .background(color)
            .height(100.dp)
            .clickable {
                val intent = Intent(
                    context,
                    ProductDetailsActivity::class.java
                )
                intent.putExtra("itemCode", item.itemCode)
                context.startActivity(intent)
            }
    ) {
        if (item.image?.isNotEmpty() == true) {
            val image = rememberImagePainter(data = item.image,
                builder = {
                    transformations(
                    )
                })
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxHeight()
            ) {
                Card(
                    elevation = 8.dp, modifier = Modifier.padding(4.dp)
                ) {
                    Image(
                        painter = image,
                        contentScale = ContentScale.FillHeight,
                        alignment = Alignment.Center,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(60.dp)
                            .padding(4.dp)
                            .clickable {
                            }
                    )
                }
            }
        }
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {

            val (title, pricing, addButton, availability) = createRefs()
            item.displayName?.let { it1 ->
                Text(text = it1,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    })
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(pricing) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                }) {
                if (item.itemPrice != item.specialPrice) {
                    Text(
                        text = "Rs. ${item.itemPrice.roundToInt()}",
                        style = TextStyle(
                            textDecoration = TextDecoration.LineThrough
                        )
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = "Rs. ${item.specialPrice.roundToInt()}",
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(text = "(${(100 - item.specialPrice / item.itemPrice * 100).roundToInt()} % off)")
                } else {
                    Text(
                        text = "Rs. ${item.specialPrice.roundToInt()}",
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.primary
                    )
                }
            }

            if (item.available == true) {
                AddRemoveProduct(modifier = Modifier
                    .constrainAs(addButton) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                    quantity = item.quantity.toString(),
                    onMinusProduct = { onMinus(item.itemCode) },
                    onAddProduct = { onAdd(item.itemCode) }
                )
            } else {
                Text(text = "Product not available", color = Color.Red,
                    modifier = Modifier.constrainAs(availability) {
                        top.linkTo(pricing.bottom)
                        start.linkTo(parent.start)
                    })
            }
        }

    }
}