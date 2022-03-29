package com.nidhin.customerapp.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun AddRemoveProduct(
    modifier: Modifier,
    quantity: String,
    onMinusProduct: () -> Unit, onAddProduct: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(10.dp),
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.secondary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.clickable {
                    onMinusProduct()
                }) {

                    Text(
                        text = "-",
                        color = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
                Box(modifier = Modifier.clickable { }) {

                    Text(
                        text = quantity,
                        color = Color.Black,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(1.dp)
                            .background(Color.White)
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Justify
                    )
                }
                Box(modifier = Modifier.clickable {
                    onAddProduct()
                }) {
                    Text(
                        text = "+",
                        color = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}