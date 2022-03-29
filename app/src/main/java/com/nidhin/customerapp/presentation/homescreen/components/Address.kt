package com.nidhin.customerapp.presentation.homescreen.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun Address(
    addressString: String, iconVal: ImageVector, labelString: String,
    onDelete:()->Unit
) {
    Column {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (icon, label, address, delete) = createRefs()

            Icon(
                iconVal,
                contentDescription = labelString,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.constrainAs(icon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
            )
            Text(text = labelString,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .constrainAs(label) {
                        start.linkTo(icon.end)
                        top.linkTo(icon.top)
                    }
                    .padding(4.dp)
            )
            Text(text = addressString,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .constrainAs(address) {
                        start.linkTo(icon.end)
                        top.linkTo(label.bottom)
                    }
                    .padding(4.dp)
            )
            Text(text = "DELETE",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .constrainAs(delete) {
                        start.linkTo(icon.end)
                        top.linkTo(address.bottom)
                    }
                    .padding(4.dp)
                    .clickable { onDelete()}
            )
        }
    }
}