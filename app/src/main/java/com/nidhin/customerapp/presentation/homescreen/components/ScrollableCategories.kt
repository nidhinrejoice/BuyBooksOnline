package com.nidhin.customerapp.presentation.homescreen.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import com.nidhin.customerapp.domain.model.Category


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ScrollableCategories(
    list: List<Category>, title: String, onCategoryClicked: (Category) -> Unit
) {
    Column(
//            .background(backgroundAnimateable.value)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            items(list) {
                Card(
                    elevation = 4.dp,
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .width(100.dp)
                        .height(100.dp)
                        .clickable {onCategoryClicked(it) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = it.catName.toUpperCase(Locale.current),
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onSecondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}