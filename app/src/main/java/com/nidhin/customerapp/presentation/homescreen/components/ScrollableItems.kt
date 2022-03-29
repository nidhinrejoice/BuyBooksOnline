package com.nidhin.customerapp.presentation.homescreen.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.nidhin.customerapp.domain.model.Item


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ScrollableItems(
    list: List<Item>, title: String, onItemSelected: (Item) -> Unit
) {
    Column(
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(8.dp)
        )
        Card(
            elevation = 8.dp, modifier = Modifier.padding(all = 4.dp)
        ) {

            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
            ) {
                items(list) {
                    Card(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .width(120.dp)
                            .height(220.dp)
                            .clickable { onItemSelected(it) }
                    ) {
                        ConstraintLayout(
                        ) {

                            val (picture,title) = createRefs()
                            if (it.image!!.isNotEmpty()) {
                                val image = rememberImagePainter(data = it.image,
                                    builder = {
                                        transformations(
                                        )
                                    })
                                Image(
                                    painter = image,
                                    contentScale = ContentScale.FillBounds,
                                    contentDescription = "",
                                    alignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxHeight(0.8f)
                                        .constrainAs(picture){
                                            top.linkTo(parent.top)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        }
                                )
                            }
                            Text(
                                text = it.displayName!!,
                                style = MaterialTheme.typography.subtitle2,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .constrainAs(title){
                                        top.linkTo(picture.bottom)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                    }, maxLines = 2
                            )
                        }
                    }
                }
            }
        }
    }
}