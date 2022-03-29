package com.nidhin.customerapp.presentation.homescreen.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nidhin.customerapp.presentation.homescreen.HomeScreenState
import com.nidhin.customerapp.presentation.homescreen.HomeScreenViewModel
import com.nidhin.customerapp.utils.convertToAnotherFormat
import kotlin.math.roundToInt


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun Orders(
    viewState: HomeScreenState,
    viewModel: HomeScreenViewModel
) {
    Column {
        Text(
            text = "My Orders",
            style = MaterialTheme.typography.h1,
            color = Color.White,
            modifier = Modifier
                .background(MaterialTheme.colors.secondary)
                .padding(8.dp)
                .fillMaxWidth()
        )
        LazyColumn() {
            items(viewState.orders) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp)
                        .clickable { }
                ) {

                    it.timestampDateTime?.let { it1 ->
                        Text(
                            text = "${
                                it1.convertToAnotherFormat(
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS",
                                    "dd-MM-yyyy hh:mm aa"
                                )
                            } (${it.orderStatus})",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    it.orderItems?.forEach {
                        Text(
                            text = "${it.itemName} X ${it.quantity}",
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.subtitle2, textAlign = TextAlign.Start
                        )
                    }

                    Text(
                        text = "Rs. ${it.totalAmount.roundToInt()}",
                        style = MaterialTheme.typography.h3,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    )
                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    ) {
                        drawLine(
                            color = Color.LightGray,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            pathEffect = pathEffect
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }
}