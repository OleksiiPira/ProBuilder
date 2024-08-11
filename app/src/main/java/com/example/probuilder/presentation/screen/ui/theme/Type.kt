package com.example.probuilder.presentation.screen.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.probuilder.R.font as FontStyle

val nunitoFamily = FontFamily(
    Font(FontStyle.nunito_regular, FontWeight.Normal),
    Font(FontStyle.nunito_bold, FontWeight.Bold),
    Font(FontStyle.nunito_light, FontWeight.Light),
    Font(FontStyle.nunito_medium, FontWeight.Medium),
    Font(FontStyle.nunito_semi_bold, FontWeight.SemiBold),
)
// Set of Material typography styles to start with
val Typography = Typography(
    //    Title
    titleLarge = TextStyle(
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),

    //    Body
    bodyLarge = TextStyle(
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Light,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.sp
    ),

    //    Label
    labelLarge = TextStyle(
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 9.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.5.sp
    )
)
