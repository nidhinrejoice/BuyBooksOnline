package com.nidhin.customerapp.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nidhin.customerapp.R

val fonts = FontFamily(
    Font(R.font.regular),
    Font(R.font.bold, weight = FontWeight.Bold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    h4 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    h5 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp
    ),
    h6 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    ),
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = fonts,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp
    ),
    caption = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    )
)