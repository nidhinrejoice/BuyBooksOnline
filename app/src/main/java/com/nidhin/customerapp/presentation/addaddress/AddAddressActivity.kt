package com.nidhin.customerapp.presentation.addaddress

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nidhin.customerapp.presentation.theme.AppTheme
import com.nidhin.customerapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAddressActivity : ComponentActivity() {
    private val viewModel: AddAddressViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AddEditAddress(viewModel)
                }
            }

            viewModel.toast.observe(this) {
                showToast(it)
            }
        }
    }
}

@Composable
fun AddError(error: String) {
    Text(
        text = error,
        color = MaterialTheme.colors.error,
        style = MaterialTheme.typography.caption,
        modifier = Modifier.padding(start = 16.dp)
    )
}

@Composable
fun AddEditAddress(viewModel: AddAddressViewModel) {

    val validationALine1Error = remember {
        mutableStateOf("")
    }
    val validationALine2Error = remember {
        mutableStateOf("")
    }
    val validationLandmarkError = remember {
        mutableStateOf("")
    }
    val validationPincodeError = remember {
        mutableStateOf("")
    }
    val validationTypeError = remember {
        mutableStateOf("")
    }
    val validationLabelError = remember {
        mutableStateOf("")
    }
    val addressLine1 = remember {
        mutableStateOf("")
    }
    val addressLine1Error = remember {
        mutableStateOf(false)
    }
    val addressLine2 = remember {
        mutableStateOf("")
    }
    val addressLine2Error = remember {
        mutableStateOf(false)
    }
    val landmark = remember {
        mutableStateOf("")
    }
    val landmarkError = remember {
        mutableStateOf(false)
    }
    val pincode = remember {
        mutableStateOf("")
    }
    val pincodeError = remember {
        mutableStateOf(false)
    }
    val type = remember {
        mutableStateOf("")
    }
    val typeError = remember {
        mutableStateOf(false)
    }
    val label = remember {
        mutableStateOf("")
    }
    val labelError = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Address")
                },
                navigationIcon = {
                    IconButton(onClick = { (context as Activity).finish() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go to previous screen"
                        )
                    }
                },
                elevation = 4.dp
            )
        }, content = {

            LazyColumn() {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp), horizontalArrangement = Arrangement.Start
                        ) {
                            Text(text = "Home")
                            RadioButton(selected = type.value.contentEquals("home"), onClick = {
                                type.value = "home"
                            })
                            Text(text = "Work")
                            RadioButton(selected = type.value.contentEquals("work"), onClick = {
                                type.value = "work"
                            })
                            Text(text = "Others")
                            RadioButton(selected = type.value.contentEquals("others"), onClick = {
                                type.value = "others"
                            })
                        }

                        OutlinedTextField(
                            value = addressLine1.value,
                            onValueChange = {
                                addressLine1.value = it
                                if (addressLine1.value.length < 10) {
                                    validationALine1Error.value =
                                        "Address line 1 should be minimum 10 characters"
                                    addressLine1Error.value = true
                                } else {
                                    addressLine1Error.value = false
                                }
                            },
                            label = {
                                Text(text = "Enter address line 1")
                            },
                            isError = addressLine1Error.value,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        if (addressLine1Error.value) {
                            AddError(error = validationALine1Error.value)
                        }
                        OutlinedTextField(
                            value = addressLine2.value,
                            onValueChange = {
                                addressLine2.value = it
                                if (addressLine2.value.length < 10) {
                                    validationALine2Error.value =
                                        "Address line 2 should be minimum 10 characters"
                                    addressLine2Error.value = true
                                } else {
                                    addressLine2Error.value = false
                                }
                            },
                            label = {
                                Text(text = "Enter address line 2")
                            },
                            isError = addressLine2Error.value,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        if (addressLine2Error.value) {
                            AddError(error = validationALine2Error.value)
                        }
                        OutlinedTextField(
                            value = landmark.value,
                            onValueChange = {
                                landmark.value = it
                                if (landmark.value.length < 6) {
                                    validationLandmarkError.value =
                                        "Landmark should be minimum 6 characters"
                                    landmarkError.value = true
                                } else {
                                    landmarkError.value = false
                                }
                            },
                            label = {
                                Text(text = "Enter Landmark")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        if (landmarkError.value) {
                            AddError(error = validationLandmarkError.value)
                        }
                        OutlinedTextField(
                            value = pincode.value,
                            onValueChange = {
                                pincode.value = it
                                if (pincode.value.length < 6) {
                                    validationPincodeError.value =
                                        "Pincode should be minimum 6 digits"
                                    pincodeError.value = true
                                } else {
                                    pincodeError.value = false
                                }
                            },
                            label = {
                                Text(text = "Enter pincode")
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        if (pincodeError.value) {
                            AddError(error = validationPincodeError.value)
                        }
                        OutlinedTextField(
                            value = label.value,
                            onValueChange = {
                                label.value = it
                                if (label.value.length < 3) {
                                    validationLabelError.value =
                                        "Tag should be minimum 3 characters"
                                    labelError.value = true
                                } else {
                                    labelError.value = false
                                }
                            },
                            label = {
                                Text(text = "Enter a TAG name for this address")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        if (labelError.value) {
                            AddError(error = validationLabelError.value)
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Button(modifier = Modifier.fillMaxWidth(), onClick = {
                            viewModel.addAddress(
                                addressLine1.value,
                                addressLine2.value,
                                landmark.value,
                                pincode.value,
                                label.value,
                                type.value
                            )
                        }) {
                            Text(text = "CREATE")
                        }
                    }
                }
            }
        }
    )
}

