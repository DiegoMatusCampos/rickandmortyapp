package com.example.rickandmorty.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.rickandmorty.R

val Orbitron = FontFamily(
    Font(R.font.orbitron_bold, FontWeight.Bold),
    Font(R.font.orbitron_medium, FontWeight.Medium)
)

val Sora = FontFamily(
    Font(R.font.sora_regular, FontWeight.Normal),
    Font(R.font.sora_medium, FontWeight.Medium),
    Font(R.font.sora_bold, FontWeight.Bold)
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Orbitron,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Orbitron,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),

    titleMedium = TextStyle(
        fontFamily = Sora,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    )
    ,
    bodyLarge = TextStyle(
        fontFamily = Sora,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Sora,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)
