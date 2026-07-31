package com.example.keyra

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.keyra.ui.theme.KeyraTheme

val mdThemeDarkBackground = Color(0xFF121212)
val mdThemeDarkSurface = Color(0xFF1E1E1E)
val mdThemeDarkPrimary = Color(0xFF6200EE)
val mdThemeDarkOnPrimary = Color(0xFFFFFFFF)
val mdThemeDarkOnSurfaceVariant = Color(0xFFAAAAAA)

// Menggunakan FragmentActivity agar kompatibel dengan BiometricPrompt
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KeyraTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = mdThemeDarkBackground
                ) {
                    AppNavigation(activity = this)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(activity: FragmentActivity) {
    var currentScreen by remember { mutableStateOf("welcome") }

    when (currentScreen) {
        "welcome" -> WelcomeScreen(
            onUnlockClick = { currentScreen = "vault" },
            onBiometricClick = {
                // Panggil fungsi Biometrik
                showBiometricPrompt(
                    activity = activity,
                    onSuccess = { currentScreen = "vault" }
                )
            }
        )
        "vault" -> VaultScreen()
    }
}

// Fungsi untuk menampilkan dialog sensor sidik jari bawaan HP
fun showBiometricPrompt(activity: FragmentActivity, onSuccess: () -> Unit) {
    val executor = ContextCompat.getMainExecutor(activity)

    val biometricPrompt = BiometricPrompt(activity, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(activity, "Autentikasi Berhasil!", Toast.LENGTH_SHORT).show()
                onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(activity, "Error: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(activity, "Sidik jari tidak dikenali", Toast.LENGTH_SHORT).show()
            }
        })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Login Biometrik Keyra")
        .setSubtitle("Gunakan sidik jari Anda untuk membuka Vault")
        .setNegativeButtonText("Batal")
        .build()

    // Cek apakah perangkat mendukung biometrik
    val biometricManager = BiometricManager.from(activity)
    when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            biometricPrompt.authenticate(promptInfo)
        }
        else -> {
            Toast.makeText(activity, "Perangkat tidak mendukung biometrik atau belum disetel.", Toast.LENGTH_LONG).show()
            // Fallback langsung masuk kalau simulator/tidak ada sensor
            onSuccess()
        }
    }
}

@Composable
fun WelcomeScreen(onUnlockClick: () -> Unit, onBiometricClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mdThemeDarkBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(mdThemeDarkPrimary.copy(alpha = 0.15f), shape = RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = "Keyra Icon",
                modifier = Modifier.size(72.dp),
                tint = mdThemeDarkPrimary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Keyra",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = mdThemeDarkOnPrimary,
            textAlign = TextAlign.Center,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Aplikasi password manager offline, aman, modern, dan mudah digunakan.",
            fontSize = 16.sp,
            color = mdThemeDarkOnSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier.fillMaxWidth(0.85f)
        )

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = onUnlockClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = mdThemeDarkPrimary,
                contentColor = mdThemeDarkOnPrimary
            )
        ) {
            Text(text = "Unlock Vault", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = onBiometricClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Gunakan Biometrik",
                    fontSize = 16.sp,
                    color = mdThemeDarkPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

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
        containerColor = mdThemeDarkBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = mdThemeDarkPrimary,
                contentColor = mdThemeDarkOnPrimary,
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
                Text(text = "Vault", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = mdThemeDarkOnPrimary)
                Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Pengaturan", tint = mdThemeDarkOnSurfaceVariant)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Cari akun...", color = mdThemeDarkOnSurfaceVariant) },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = mdThemeDarkOnSurfaceVariant) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = mdThemeDarkSurface,
                    unfocusedContainerColor = mdThemeDarkSurface,
                    focusedBorderColor = mdThemeDarkPrimary,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = mdThemeDarkOnPrimary,
                    unfocusedTextColor = mdThemeDarkOnPrimary
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Semua Akun (${filteredAccounts.size})", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = mdThemeDarkOnSurfaceVariant)

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredAccounts) { account ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = mdThemeDarkSurface)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = account.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = mdThemeDarkOnPrimary)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = account.email, fontSize = 14.sp, color = mdThemeDarkOnSurfaceVariant)
                            }
                            Icon(imageVector = Icons.Default.Star, contentDescription = "Favorit", tint = Color(0xFFFFC107))
                        }
                    }
                }
            }
        }
    }
}