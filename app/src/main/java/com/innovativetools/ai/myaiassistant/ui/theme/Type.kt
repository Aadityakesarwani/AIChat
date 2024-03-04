package com.innovativetools.ai.myaiassistant.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.innovativetools.ai.myaiassistant.R

val OpenSans = FontFamily(
    Font(R.font.opensans_font_family)
)


val Typography = Typography(
    body1 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        lineHeight = 25.sp,
        color = TextColor
    ),
    caption = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W100,
        textAlign = TextAlign.Center
    ),

    h1 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),

    h2 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),

    h3 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),

    h4 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),

    h5 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    h6 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
)


val TypographyDark = Typography(
    body1 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        lineHeight = 25.sp,
        color = TextColorDark
    ),
    caption = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W100,
        textAlign = TextAlign.Center
    ),

    h1 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),

    h2 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),

    h3 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),

    h4 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),

    h5 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    h6 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
)