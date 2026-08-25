package com.example.keyra.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.keyra.R

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1115)) // Background utama gelap ala Figma
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.width(294.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Frame Logo & Title
            Column(
                modifier = Modifier.width(219.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.keyra),
                    contentDescription = "Logo Keyra",
                    modifier = Modifier
                        .width(95.dp)
                        .height(105.dp)
                )
                Text(
                    text = "Keyra",
                    fontSize = 33.18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Password Manager",
                    fontSize = 20.73.sp,
                    color = Color.White
                )
            }

            // Form Input Fields
            Column(
                modifier = Modifier.width(224.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Email Field
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Email", fontSize = 12.sp, color = Color.White)
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("name@gmail.com", color = Color(0x4AFFFFF9), fontSize = 12.sp) },
                        modifier = Modifier
                            .width(224.dp)
                            .height(55.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF334155),   // Warna kotak input slate
                            unfocusedContainerColor = Color(0xFF334155), // Warna kotak input slate
                            focusedBorderColor = Color(0xFF528BEF),      // Warna garis fokus biru terang
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                }

                // Password Field
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Password", fontSize = 12.sp, color = Color.White)
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Enter Password", color = Color(0x4AFFFFF9), fontSize = 12.sp) },
                        modifier = Modifier
                            .width(224.dp)
                            .height(55.dp),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF334155),
                            unfocusedContainerColor = Color(0xFF334155),
                            focusedBorderColor = Color(0xFF528BEF),
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                }

                TextButton(
                    onClick = onForgotPasswordClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Forget password?", fontSize = 12.sp, color = Color(0xFF94A3B8))
                }
            }

            // Buttons Footer
            Column(
                modifier = Modifier.width(294.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .width(294.dp)
                        .height(57.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF528BEF)) // Tombol warna biru terang sesuai Figma
                ) {
                    Text(text = "Login", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }

                TextButton(onClick = onSignUpClick) {
                    Text(text = "No account? Sign up", fontSize = 12.sp, color = Color.White)
                }
            }
        }
    }
}