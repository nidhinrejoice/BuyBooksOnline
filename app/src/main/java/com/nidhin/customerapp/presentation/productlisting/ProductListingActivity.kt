package com.nidhin.customerapp.presentation.productlisting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nidhin.customerapp.presentation.components.ItemListing
import com.nidhin.customerapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class ProductListingActivity : ComponentActivity() {
    var catId=0
    private val viewModel: ProductListingViewModel by viewModels()
    override fun onResume() {
        super.onResume()
        catId.let { viewModel.getProducts(catId) }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catId = intent.getIntExtra("catId", 1)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShowListing()
                }
            }
        }
    }

    @Composable
    fun ShowListing() {

        val state by remember { viewModel.state }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = state.category)
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
                ItemListing(
                    onAdd = { itemCode ->
                        viewModel.addItem(itemCode)

                    },
                    onMinus = { itemCode ->
                        viewModel.minusItem(itemCode)
                    },
                    items = state.items
                )

            }
        )
    }
}
