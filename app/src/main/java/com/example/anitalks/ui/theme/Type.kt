package com.example.anitalks.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.anitalks.R

// Fuente principal y de cuerpo: Roboto (legibilidad y consistencia)
val RobotoFamily = FontFamily(
    Font(R.font.roboto, FontWeight.Normal),
    Font(R.font.roboto, FontWeight.Medium),
    Font(R.font.roboto_2, FontWeight.Bold)
)

// Fuente de acento: Oswald
val OswaldFamily = FontFamily(
    Font(R.font.oswald, FontWeight.Bold)
)



val AnimeTalksTypography = Typography(

    headlineLarge = TextStyle(
        fontFamily = OswaldFamily,
        fontWeight = FontWeight.Bold,
        fontSize = Typography().headlineLarge.fontSize
    ),
    headlineMedium = TextStyle(
        fontFamily = OswaldFamily,
        fontWeight = FontWeight.Bold,
        fontSize = Typography().headlineMedium.fontSize
    ),
    titleLarge = TextStyle(
        fontFamily = OswaldFamily,
        fontWeight = FontWeight.Bold,
        fontSize = Typography().titleLarge.fontSize
    ),


    bodyLarge = TextStyle(
        fontFamily = RobotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = Typography().bodyLarge.fontSize
    ),
    bodyMedium = TextStyle(
        fontFamily = RobotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = Typography().bodyMedium.fontSize
    ),
    labelMedium = TextStyle(
        fontFamily = RobotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = Typography().labelMedium.fontSize
    )
)