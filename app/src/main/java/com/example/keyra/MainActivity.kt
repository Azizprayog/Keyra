package com.example.keyra

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.keyra.ui.BiometricScreen
import com.example.keyra.ui.LoginScreen
import com.example.keyra.ui.RegisterScreen

// Warna Global Sesuai Figma Keyra
val KeyraBackground = Color(0xFF0F1115)
val KeyraSurface = Color(0xFF334155)
val KeyraPrimary = Color(0xFF528BEF)
val KeyraOnPrimary = Color(0xFFFFFFFF)
val KeyraTextSecondary = Color(0xFF94A3B8)

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = KeyraPrimary,
                    onPrimary = KeyraOnPrimary,
                    background = KeyraBackground,
                    onBackground = KeyraOnPrimary,
                    surface = KeyraSurface,
                    onSurface = KeyraOnPrimary
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = KeyraBackground
                ) {
                    AppNavigation(activity = this)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(activity: FragmentActivity) {
    var currentScreen by remember { mutableStateOf("login") }

    when (currentScreen) {
        "login" -> LoginScreen(
            onLoginClick = { currentScreen = "biometric" },
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
                currentScreen = "vault"
            },
            onSkipClick = { currentScreen = "vault" }
        )
        "vault" -> VaultScreen()
    }
}

// ==========================================
// VAULT SCREEN COMPOSABLE
// ==========================================
data class AccountItem(val name: String, val email: String, val category: String)

@Composable
fun VaultScreen() {
    var searchQuery by remember { mutableStateOf("") }
    val sampleAccounts = listOf(
        AccountItem("Google", "user@gmail.com", "Favorit"),
        AccountItem("GitHub", "octocat@github.com", "Favorit"),
        AccountItem("Instagram", "user_instagram", "Favorit"),
        AccountItem("Discord", "user#1234", "Sosial Media"),
        AccountItem("Netflix", "user@netflix.com", "Streaming")
    )

    val filteredAccounts = sampleAccounts.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.email.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        containerColor = KeyraBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = KeyraPrimary,
                contentColor = KeyraOnPrimary,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Akun")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Vault", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = KeyraOnPrimary)
                Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Pengaturan", tint = KeyraTextSecondary)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Cari akun...", color = KeyraTextSecondary) },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = KeyraTextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = KeyraSurface,
                    unfocusedContainerColor = KeyraSurface,
                    focusedBorderColor = KeyraPrimary,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = KeyraOnPrimary,
                    unfocusedTextColor = KeyraOnPrimary
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Semua Akun (${filteredAccounts.size})", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = KeyraTextSecondary)

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredAccounts) { account ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = KeyraSurface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = account.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = KeyraOnPrimary)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = account.email, fontSize = 14.sp, color = KeyraTextSecondary)
                            }
                            Icon(imageVector = Icons.Default.Star, contentDescription = "Favorit", tint = Color(0xFFFFC107))
                        }
                    }
                }
            }
        }
    }
}