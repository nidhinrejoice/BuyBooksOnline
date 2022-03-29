package com.nidhin.customerapp.presentation.login.components

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nidhin.customerapp.R
import com.nidhin.customerapp.presentation.LoginEvent
import com.nidhin.customerapp.presentation.homescreen.HomeScreenActivity
import com.nidhin.customerapp.presentation.login.LoginViewModel


@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    viewModel: LoginViewModel
) {
    val state by remember { viewModel.state }
//    var backgroundAnimateable = remember {
//        Animatable(Color(Color.LightGray.value))
//    }
    val defaultMessage = "Click Done to get an otp in this number"
    var mobile by remember { mutableStateOf(state.mobileEntered) }
    var message by remember { mutableStateOf("") }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
//            .background(backgroundAnimateable.value)
    ) {

        val (login, signup, changeNumber, retryOtp) = createRefs()
        Column(
            modifier = Modifier.constrainAs(login) {
                bottom.linkTo(signup.top)
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.height(100.dp)
            )
            Text(text = "THE OSHO BOOKSHOP", fontFamily = FontFamily.Monospace)
            Spacer(modifier = Modifier.height(30.dp))
            AnimatedVisibility(
                visible = !state.waitingForOtp,
                enter = slideInHorizontally() + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                OutlinedTextField(
                    value = mobile,
                    onValueChange = {
                        mobile = it
                        if (!state.waitingForOtp && !state.inProgress)
                            message = if (it.length > 9) "$defaultMessage +91-$it"
                            else defaultMessage
                    },
                    label = {
                        Text(text = "Enter your mobile number")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    leadingIcon = { Icon(Icons.Filled.Phone, "") },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.onEvent(LoginEvent.SendOtp(mobile))
                            keyboardController?.hide()
                        }
                    ),
                    enabled = !state.inProgress && !state.waitingForOtp
                )
            }
            if (state.showMessage.isNotEmpty()) {
                message = state.showMessage
            }
            if (state.waitingForOtp) {
                var otp by remember { mutableStateOf("") }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val density = LocalDensity.current
                    AnimatedVisibility(
                        visible = state.waitingForOtp,
                        enter = slideInHorizontally() + expandVertically(
                            // Expand from the top.
                            expandFrom = Alignment.Top
                        ) + fadeIn(
                            // Fade in with the initial alpha of 0.3f.
                            initialAlpha = 0.3f
                        ),
                        exit = slideOutVertically() + shrinkVertically() + fadeOut()
                    ) {
                        OutlinedTextField(value = otp,
                            onValueChange = { otp = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    viewModel.onEvent(LoginEvent.VerifyOtp(otp, mobile))
                                    keyboardController?.hide()
                                }
                            ), label = {
                                Text("Enter OTP")
                            })
                    }
                    Text(
                        text = state.timerMessage,
                        modifier = Modifier
                            .padding(4.dp),
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
            if(state.otpVerified){
                LocalContext.current.startActivity(Intent(LocalContext.current,HomeScreenActivity::class.java))
                (LocalContext.current as Activity).finish()
            }
            Text(
                text = message,
                modifier = Modifier
                    .offset(y = 10.dp)
                    .padding(4.dp),
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.size(30.dp))
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(4.dp)
                .constrainAs(changeNumber) {
                    start.linkTo(parent.start)
                    top.linkTo(login.bottom)
                },
            visible = state.waitingForOtp,
            enter = slideInHorizontally() + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            OutlinedButton(onClick = {
                mobile = ""
                message = ""
                viewModel.onEvent(LoginEvent.ChangeNumber)
            }) {
                Text(text = "Change Number")
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(4.dp)
                .constrainAs(retryOtp) {
                    end.linkTo(parent.end)
                    top.linkTo(login.bottom)
                },
            visible = state.retryOtpEnabled,
            enter = slideInHorizontally() + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            OutlinedButton(onClick = {
                message = ""
                viewModel.onEvent(LoginEvent.ResendOtp(mobile))
            }) {
                Text(text = "Resend OTP")
            }
        }

        TextButton(modifier = Modifier
            .offset(y = (-10).dp)
            .constrainAs(signup) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }, onClick = { }) {
            Text(text = "Create an account with us!")
        }
    }
}