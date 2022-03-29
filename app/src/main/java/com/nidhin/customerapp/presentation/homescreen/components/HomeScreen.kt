package com.nidhin.customerapp.presentation.homescreen.components

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nidhin.customerapp.R
import com.nidhin.customerapp.presentation.homescreen.HomeScreenViewModel
import com.nidhin.customerapp.presentation.productdetails.ProductDetailsActivity
import com.nidhin.customerapp.presentation.productlisting.ProductListingActivity


@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel
) {
    val viewState by remember {
        viewModel.state
    }
    val screenSelected = remember { mutableStateOf("home") }
    val context = LocalContext.current
    if (!viewState.loading && viewState.errorMessage.isEmpty()) {
        Scaffold(
            content = { innerPadding ->

                when {
                    screenSelected.value.contentEquals("home") -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            item {
                                Text(
                                    text = "Welcome, ${viewState.user?.customerName}",
                                    modifier = Modifier.padding(8.dp),
                                    style = MaterialTheme.typography.h4
                                )
                                Column() {

                                    ScrollableItems(list = viewState.bestSellingItems,
                                        title = "Best Selling",
                                        onItemSelected = {
                                            val intent =
                                                Intent(context, ProductDetailsActivity::class.java)
                                            intent.putExtra("itemCode", it.itemCode)
                                            context.startActivity(intent)
                                        })
                                    Spacer(modifier = Modifier.size(10.dp))
                                    ScrollableItems(
                                        list = viewState.items.filter { it.newItem == true },
                                        title = "Newly Launched",
                                        onItemSelected = {
                                            val intent =
                                                Intent(context, ProductDetailsActivity::class.java)
                                            intent.putExtra("itemCode", it.itemCode)
                                            context.startActivity(intent)
                                        }
                                    )
                                    viewState.sections.forEach {
                                        ScrollableCategories(
                                            list = viewState.categories.filter { c ->
                                                it.catIds.contains(c.catId)
                                            },
                                            title = it.section, onCategoryClicked = {
                                                val intent =
                                                    Intent(
                                                        context,
                                                        ProductListingActivity::class.java
                                                    )
                                                intent.putExtra("catId", it.catId)
                                                context.startActivity(intent)
                                            }
                                        )
                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .height(50.dp)
                                    )
                                }
                            }
                        }

                    }
                    screenSelected.value.contentEquals("cart") -> {
                        Cart(viewState = viewState, viewModel = viewModel,
                            onHomeClicked = {
                                screenSelected.value = "home"
                            },
                            onPlaceOrder = {
                                viewState.cartDetails?.let { viewModel.placeOrder(it) }
                            })
                    }
                    screenSelected.value.contentEquals("orders") -> {
                        Orders(viewState = viewState, viewModel = viewModel)
                    }
                    screenSelected.value.contentEquals("profile") -> {
                        Profile(viewState = viewState, viewModel = viewModel)
                    }
                }
            },
            bottomBar = {
                BottomNavigation() {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Home, "") },
                        selected = screenSelected.value == "home",
                        onClick = {
                            screenSelected.value = "home"
                            viewModel.updateCart()
                        })
                    BottomNavigationItem(
                        icon = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                viewState.cartDetails.let {
                                    if (it != null) {
                                        if (it.totalItems > 0) {
                                            BadgedBox(
                                                badge = {
                                                    Text(
                                                        text = viewState.cartDetails?.totalItems!!.toInt()
                                                            .toString(),
                                                        style = MaterialTheme.typography.h4,
                                                        color = MaterialTheme.colors.onSurface
                                                    )
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.ShoppingCart,
                                                    contentDescription = ""
                                                )
                                            }
                                        } else {
                                            Icon(
                                                imageVector = Icons.Filled.ShoppingCart,
                                                contentDescription = ""
                                            )
                                        }
                                    } else {
                                        Icon(
                                            imageVector = Icons.Filled.ShoppingCart,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            }
                        },
                        selected = screenSelected.value == "cart",
                        onClick = {
                            screenSelected.value = "cart"
                            viewModel.updateCart()
                        })
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.List, "") },
                        selected = screenSelected.value == "orders",
                        onClick = {
                            screenSelected.value = "orders"
                            viewModel.getPreviousOrders()
                        })
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Person, "") },
                        selected = screenSelected.value == "profile",
                        onClick = {
                            screenSelected.value = "profile"
                            viewModel.getUserAddresses()
                        })
                }
            })
    } else if (!viewState.loading && viewState.errorMessage.isNotEmpty()) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec
                .RawRes(R.raw.no_internet)
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            isPlaying = true,
            restartOnPlay = true

        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition, progress,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            )
            Text(
                "Click to retry",
                color = Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable {
                        viewModel.refreshInventory()
                    },
                textAlign = TextAlign.Center
            )
        }
    } else {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec
                .RawRes(R.raw.loading)
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            isPlaying = true,
            restartOnPlay = true

        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition, progress,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            )
            Text(
                viewState.loadingMessage,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable {
                        viewModel.refreshInventory()
                    },
                textAlign = TextAlign.Center
            )
        }
    }
}