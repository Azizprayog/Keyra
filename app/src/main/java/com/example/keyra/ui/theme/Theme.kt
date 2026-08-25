package com.example.keyra.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

// Warna kustom Keyra (sesuai Figma)
private val KeyraDarkColorScheme = darkColorScheme(
    primary = Color(0xFF528BEF),       // Biru terang tombol & border fokus
    onPrimary = Color.White,
    background = Color(0xFF0F1115),    // Background utama gelap
    onBackground = Color.White,
    surface = Color(0xFF334155),       // Warna kotak input / card slate
    onSurface = Color.White
)

@Composable
fun KeyraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Ubah default dynamicColor jadi false supaya warna kustom kita tidak tertimpa tema HP/Material
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> KeyraDarkColorScheme // Memakai skema warna kustom Keyra
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}