package com.nidhin.customerapp.presentation.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.nidhin.customerapp.presentation.theme.AppTheme
import com.nidhin.customerapp.presentation.login.components.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen(viewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        viewModel.onDestroy(this)
        super.onDestroy()
    }

}
