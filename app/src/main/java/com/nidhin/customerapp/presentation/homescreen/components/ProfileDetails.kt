package com.nidhin.customerapp.presentation.homescreen.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nidhin.customerapp.data.remote.dto.User


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ProfileDetails(
    user: User,
    onUpdate: (User) -> Unit
) {
    val editable = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.padding(8.dp)) {
        var update = user
        OutlinedTextField(
            label = { Text("First Name") },
            value = user.firstName,
            onValueChange = { update.firstName = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            enabled = editable.value
        )
        OutlinedTextField(
            label = { Text("Last Name") },
            value = user.lastName, onValueChange = {
                update.lastName = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            enabled = editable.value
        )
        OutlinedTextField(
            label = { Text("Email") },
            value = user.email, onValueChange = {
                update.email = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            enabled = editable.value
        )
        OutlinedTextField(
            label = { Text("Mobile") },
            value = user.mobile, onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            enabled = false
        )
        Spacer(modifier = Modifier.size(10.dp))
        var buttonLabel = "Edit"
        if (editable.value)
            buttonLabel = "Save"
        Button(onClick = {
            if (editable.value) {
                onUpdate(update)
            }
            editable.value = !editable.value

        }) {
            Text(
                text = buttonLabel,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}