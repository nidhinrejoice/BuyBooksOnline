package com.nidhin.customerapp.presentation.homescreen.components

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nidhin.customerapp.presentation.addaddress.AddAddressActivity
import com.nidhin.customerapp.presentation.homescreen.HomeScreenState
import com.nidhin.customerapp.presentation.homescreen.HomeScreenViewModel


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun Profile(
    viewState: HomeScreenState,
    viewModel: HomeScreenViewModel
) {
    val context = LocalContext.current
    Column {
        Text(
            text = "My Account",
            style = MaterialTheme.typography.h1,
            color = Color.White,
            modifier = Modifier
                .background(MaterialTheme.colors.secondary)
                .padding(8.dp)
                .fillMaxWidth()
        )
        LazyColumn(
        ) {
            item {
                viewState.user?.let {
                    ProfileDetails(user = viewState.user) { viewModel.updateUserDetails(it) }
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

                if (viewState.user?.addresses?.isNotEmpty() == true) {
                    Text(
                        text = "SAVED ADDRESSES",
                        style = MaterialTheme.typography.subtitle2, modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFDFDFDF))
                            .padding(8.dp)
                    )
                    var address = ""
                    viewState.user.addresses.forEach {
                        address =
                            "${it.addressLine1},${it.addressLine2}," +
                                    "${it.landmark},${it.pincode}"

                        var icon = (Icons.Filled.Home)
                        if (!it.type.contentEquals("home")) {
                            icon = (Icons.Filled.LocationOn)
                        }
                        Address(
                            addressString = address,
                            iconVal = icon,
                            labelString = it.label.toString(),
                            onDelete = { viewModel.deleteAddress(it._id.toString()) })
                    }

                }

                Text(
                    text = "ADD NEW ADDRESS ->",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            context.startActivity(Intent(context, AddAddressActivity::class.java))
                        },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primaryVariant
                )
                Text(
                    text = "Logout",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            viewModel.logout()
                        },
                    textAlign = TextAlign.End
                )
            }
        }
    }
}