package com.nidhin.customerapp.presentation.homescreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nidhin.customerapp.presentation.homescreen.components.HomeScreen
import com.nidhin.customerapp.presentation.login.LoginActivity
import com.nidhin.customerapp.presentation.paymentscreen.PaymentActivity
import com.nidhin.customerapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class HomeScreenActivity : ComponentActivity() {

    override fun onResume() {
        super.onResume()
        viewModel.updateCart()
    }

    private val viewModel: HomeScreenViewModel by viewModels()
    var loggedIn = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gotoPayment.collect {
                    startActivity(Intent(this@HomeScreenActivity, PaymentActivity::class.java)
                        .apply {
                            putExtra("paymentMetaData", it)
                        })
                }
            }
        }
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    viewModel.checkUserLoggedIn()
                    val loggedIn by viewModel.loggedIn.observeAsState()
                    if (loggedIn == true) {
                        HomeScreen(viewModel)
                    } else {
                        startActivity(
                            Intent(
                                this,
                                LoginActivity::class.java
                            )
                        )
                        finish()
                    }
                }
            }
        }
    }
}

