package com.nidhin.customerapp.presentation.productdetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.nidhin.customerapp.presentation.components.AddRemoveProduct
import com.nidhin.customerapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class ProductDetailsActivity : ComponentActivity() {
    private val viewModel: ProductDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemCode = intent.getStringExtra("itemCode")
        itemCode?.let { viewModel.getDetails(it) }
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShowProductDetails()

                }
            }
        }
    }

    @Composable
    private fun ShowProductDetails() {

        val state by remember { viewModel.state }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Product Details")
                    },
                    navigationIcon = {
                        IconButton(onClick = { finish() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Go to previous screen"
                            )
                        }
                    },
                    elevation = 4.dp
                )
            },
            content = {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                    item {
                        Column(modifier = Modifier.padding(4.dp)) {
                            state.item?.let { item ->
                                if (item.image?.isNotEmpty() == true) {
                                    val image = rememberImagePainter(data = item.image,
                                        builder = {
                                            transformations(
                                            )
                                        })
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxWidth()
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
                                                    .height(300.dp)
                                                    .padding(4.dp)
                                                    .clickable { }
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(10.dp))
                                    }
                                }
                                item.displayName?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.h2,
                                        textAlign = TextAlign.Start
                                    )
                                }
                                Spacer(modifier = Modifier.size(10.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
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
                                    if (item.available == true) {
                                        Row(horizontalArrangement = Arrangement.End,
                                        modifier = Modifier.fillMaxWidth()){
                                            state.cartItem?.let { it1 ->
                                                AddRemoveProduct(
                                                    quantity = it1.quantity.toString(),
                                                    onMinusProduct = { viewModel.minusItem(item.itemCode) },
                                                    onAddProduct = { viewModel.addItem(item.itemCode) },
                                                    modifier = Modifier.padding(1.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.size(10.dp))
                                Text(
                                    text = "${item.category.toUpperCase(Locale.current)} > ${
                                        item.subCategory.toUpperCase(
                                            Locale.current
                                        )
                                    }",
                                    style = MaterialTheme.typography.h4
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                                item.shortDescription?.let {
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.h5,
                                        textAlign = TextAlign.Start
                                    )
                                }
                                item.description?.let {
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.subtitle2,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.padding(4.dp)
                                    )
                                }
                            }

                        }
                    }
                }

            }
        )
    }
}

