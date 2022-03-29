package com.nidhin.customerapp.presentation.homescreen.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nidhin.customerapp.presentation.components.CartListing
import com.nidhin.customerapp.presentation.homescreen.HomeScreenState
import com.nidhin.customerapp.presentation.homescreen.HomeScreenViewModel
import kotlin.math.roundToInt


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun Cart(
    viewState: HomeScreenState,
    viewModel: HomeScreenViewModel,
    onHomeClicked: () -> Unit,
    onPlaceOrder: () -> Unit
) {
    var context = LocalContext.current
    Column {
        Text(
            text = "Cart",
            style = MaterialTheme.typography.h1,
            color = Color.White,
            modifier = Modifier
                .background(MaterialTheme.colors.secondary)
                .padding(8.dp)
                .fillMaxWidth()
        )
        if (viewState.cartDetails?.cartItems?.isNotEmpty() == true) {

            LazyColumn {
                viewState.cartDetails?.let {
                    items(it.cartItems) { item ->
                        CartListing(
                            onAdd = { viewModel.addItem(it) },
                            onMinus = { viewModel.minusItem(it) },
                            item = item
                        )
                    }
                }
                item {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Spacer(modifier = Modifier.size(10.dp))
                        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                        ) {
                            drawLine(
                                color = Color.LightGray,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                pathEffect = pathEffect
                            )
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "Rs.${viewState.cartDetails.totalDiscount.roundToInt()} total savings",
                                modifier = Modifier
                                    .background(Color(0xFFDCF5DC))
                                    .padding(8.dp),
                                style = MaterialTheme.typography.h3,
                                color = Color(0xFF2A752D)
                            )
                        }
                        ConstraintLayout(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val (promoCode, applyPromo) = createRefs()
                            var promoCodeValue by remember {
                                mutableStateOf("")
                            }
                            OutlinedTextField(value = promoCodeValue,
                                onValueChange = { promoCodeValue = it },
                                label = {
                                    Text(text = "Enter promo code")
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .constrainAs(promoCode)
                                    {
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                    })
                            Text(text = "Apply", modifier = Modifier
                                .fillMaxHeight()
                                .padding(8.dp)
                                .clickable { }
                                .constrainAs(applyPromo)
                                {
                                    end.linkTo(parent.end)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                })
                        }
                        Spacer(modifier = Modifier.size(10.dp))

                        ConstraintLayout(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val (cartTotalLabel, cartTotal) = createRefs()
                            Text(
                                text = "Cart Total",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier
                                    .constrainAs(cartTotalLabel) {
                                        start.linkTo(parent.start)
                                        top.linkTo(parent.top)
                                    }
                                    .padding(4.dp)
                            )
                            Text(
                                text = "Rs.${viewState.cartDetails.payableAmount.roundToInt()}",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .constrainAs(cartTotal) {
                                        end.linkTo(parent.end)
                                        top.linkTo(parent.top)
                                    }
                            )
                        }
                        ConstraintLayout(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val (
                                deliveryChargesLabel, deliveryCharge, totalBillLabel, totalBill) = createRefs()

                            Text(
                                text = "Delivery Charges",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .constrainAs(deliveryChargesLabel) {
                                        start.linkTo(parent.start)
                                    }
                            )
                            Text(
                                text = "Rs.${viewState.cartDetails.deliveryCharges.roundToInt()}",
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .constrainAs(deliveryCharge) {
                                        end.linkTo(parent.end)
                                    }
                            )
                        }
                        ConstraintLayout(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val (totalBillLabel, totalBill) = createRefs()

                            Text(
                                text = "Total Payable Amount",
                                style = MaterialTheme.typography.h4,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .constrainAs(totalBillLabel) {
                                        start.linkTo(parent.start)
                                    }
                            )
                            Text(
                                text = "Rs.${viewState.cartDetails.totalBill.roundToInt()}",
                                style = MaterialTheme.typography.h3,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .constrainAs(totalBill) {
                                        end.linkTo(parent.end)
                                    }
                            )
                        }
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        ) {
                            drawLine(
                                color = Color.LightGray,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f)
                            )
                        }
                        Text(
                            text = "Delivery Address",
                            style = MaterialTheme.typography.h3,
                            modifier = Modifier.padding(4.dp)
                        )
                        if (viewState.user?.addresses?.isNotEmpty() == true) {
                            val address = viewState.user.addresses[0]
                            val addressString =
                                "${address.label}\n${address.addressLine1},${address.addressLine2}," +
                                        "${address.landmark},${address.pincode}"
                            Text(
                                text = addressString,
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(onClick = {
                            onPlaceOrder()
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Place Order")
                        }
                        Spacer(modifier = Modifier.height(70.dp))

                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Cart Empty",
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Your Cart is empty.",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    text = "Add products to continue",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    text = "Browse products ->",
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onHomeClicked() }
                )
            }
        }
    }
}