package com.example.keyra

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.keyra.ui.AboutScreen
import com.example.keyra.ui.AccountScreen
import com.example.keyra.ui.AddPasswordScreen
import com.example.keyra.ui.BiometricScreen
import com.example.keyra.ui.GeneratePasswordScreen
import com.example.keyra.ui.HomeScreen
import com.example.keyra.ui.LoginScreen
import com.example.keyra.ui.RegisterScreen
import com.example.keyra.ui.SettingsScreen
import com.example.keyra.ui.ThemeScreen

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation(activity = this)
        }
    }

    fun triggerBiometricAuth(onSuccess: () -> Unit) {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val executor = ContextCompat.getMainExecutor(this)
                val biometricPrompt = BiometricPrompt(
                    this,
                    executor,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            onSuccess()
                        }
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                            super.onAuthenticationError(errorCode, errString)
                            Toast.makeText(this@MainActivity, "Error ($errorCode): $errString", Toast.LENGTH_SHORT).show()
                        }
                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            Toast.makeText(this@MainActivity, "Sidik jari tidak dikenali", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Buka Vault Keyra")
                    .setSubtitle("Gunakan sidik jari untuk mengakses password kamu")
                    .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                    .build()

                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(this, "HP ini tidak memiliki sensor biometrik!", Toast.LENGTH_LONG).show()
                onSuccess()
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(this, "Sensor biometrik sedang sibuk.", Toast.LENGTH_LONG).show()
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(this, "Belum ada sidik jari yang terdaftar di HP ini!", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun KeyraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF4E8CF7),
            onPrimary = Color(0xFFFFFFFF),
            background = Color(0xFF0F1115),
            onBackground = Color(0xFFFFFFFF),
            surface = Color(0xFF334155),
            onSurface = Color(0xFFFFFFFF)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF2563EB),
            onPrimary = Color(0xFFFFFFFF),
            background = Color(0xFFF8FAFC),
            onBackground = Color(0xFF0F172A),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF0F172A)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

@Composable
fun AppNavigation(activity: MainActivity) {
    var currentScreen by remember { mutableStateOf("login") }
    var currentThemeSetting by remember { mutableStateOf("Dark Mode") }

    val systemIsDark = isSystemInDarkTheme()
    val isDark = when (currentThemeSetting) {
        "Dark Mode" -> true
        "Light Mode" -> false
        else -> systemIsDark
    }

    KeyraTheme(darkTheme = isDark) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentScreen) {
                "login" -> LoginScreen(
                    onLoginClick = {
                        activity.triggerBiometricAuth {
                            currentScreen = "home"
                        }
                    },
                    onSignUpClick = { currentScreen = "register" },
                    onForgotPasswordClick = {
                        Toast.makeText(activity, "Fitur Reset Password belum tersedia", Toast.LENGTH_SHORT).show()
                    }
                )
                "register" -> RegisterScreen(
                    onRegisterClick = {
                        Toast.makeText(activity, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
                        currentScreen = "biometric"
                    },
                    onLoginRedirectClick = { currentScreen = "login" }
                )
                "biometric" -> BiometricScreen(
                    onSetupBiometricClick = {
                        Toast.makeText(activity, "Biometrik Berhasil Disetting!", Toast.LENGTH_SHORT).show()
                        currentScreen = "home"
                    },
                    onSkipClick = { currentScreen = "home" }
                )
                "home" -> HomeScreen(
                    onAddPasswordClick = {
                        currentScreen = "add_password"
                    },
                    onGeneratePasswordClick = {
                        currentScreen = "generate_password"
                    },
                    onSettingsClick = {
                        currentScreen = "settings"
                    },
                    onLockAppClick = {
                        currentScreen = "login"
                        Toast.makeText(activity, "Aplikasi Dikunci", Toast.LENGTH_SHORT).show()
                    }
                )
                "add_password" -> AddPasswordScreen(
                    onBackClick = {
                        currentScreen = "home"
                    },
                    onSaveClick = {
                        Toast.makeText(activity, "Akun berhasil disimpan!", Toast.LENGTH_SHORT).show()
                        currentScreen = "home"
                    }
                )
                "generate_password" -> GeneratePasswordScreen(
                    onBackClick = {
                        currentScreen = "home"
                    }
                )
                "settings" -> SettingsScreen(
                    onHomeClick = {
                        currentScreen = "home"
                    },
                    onAccountClick = {
                        currentScreen = "account"
                    },
                    onBiometricClick = {
                        Toast.makeText(activity, "Pengaturan Biometrik", Toast.LENGTH_SHORT).show()
                    },
                    onThemeClick = {
                        currentScreen = "theme"
                    },
                    onLanguageClick = {
                        Toast.makeText(activity, "Pengaturan Bahasa", Toast.LENGTH_SHORT).show()
                    },
                    onBackupClick = {
                        Toast.makeText(activity, "Backup & Restore", Toast.LENGTH_SHORT).show()
                    },
                    onAboutClick = {
                        currentScreen = "about"
                    },
                    onLogoutClick = {
                        currentScreen = "login"
                        Toast.makeText(activity, "Berhasil Keluar Akun", Toast.LENGTH_SHORT).show()
                    }
                )
                "account" -> AccountScreen(
                    onBackClick = {
                        currentScreen = "settings"
                    },
                    onChangeMasterPasswordClick = {
                        Toast.makeText(activity, "Ubah Master Password", Toast.LENGTH_SHORT).show()
                    }
                )
                "about" -> AboutScreen(
                    onBackClick = {
                        currentScreen = "settings"
                    }
                )
                "theme" -> ThemeScreen(
                    currentTheme = currentThemeSetting,
                    onThemeSelected = { selected ->
                        currentThemeSetting = selected
                        Toast.makeText(activity, "Tema diubah ke $selected", Toast.LENGTH_SHORT).show()
                    },
                    onBackClick = {
                        currentScreen = "settings"
                    }
                )
            }
        }
    }
}