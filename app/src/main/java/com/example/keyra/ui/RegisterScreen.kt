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
fun RegisterScreen(
    onRegisterClick: () -> Unit = {},
    onLoginRedirectClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var masterPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1115)) // color_15_17_21 (Background utama Figma)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.width(294.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header Logo & Title
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

            // Form Input Fields (Email, Password Master, Confirm Password)
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
                            focusedContainerColor = Color(0xFF334155),   // color_51_65_85 (bg_rounded_email)
                            unfocusedContainerColor = Color(0xFF334155),
                            focusedBorderColor = Color(0xFF528BEF),      // color_82_139_239
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                }

                // Password Master Field
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Password Master", fontSize = 12.sp, color = Color.White)
                    OutlinedTextField(
                        value = masterPassword,
                        onValueChange = { masterPassword = it },
                        placeholder = { Text("Enter Password Master", color = Color(0x4AFFFFF9), fontSize = 12.sp) },
                        modifier = Modifier
                            .width(224.dp)
                            .height(55.dp),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF334155),   // bg_rounded_password_master
                            unfocusedContainerColor = Color(0xFF334155),
                            focusedBorderColor = Color(0xFF528BEF),
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                }

                // Confirm Password Master Field
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Confirm Password Master", fontSize = 12.sp, color = Color.White)
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = { Text("Enter Confirm Password", color = Color(0x4AFFFFF9), fontSize = 12.sp) },
                        modifier = Modifier
                            .width(224.dp)
                            .height(55.dp),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF334155),   // bg_rounded_confirm_password_master
                            unfocusedContainerColor = Color(0xFF334155),
                            focusedBorderColor = Color(0xFF528BEF),
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                }
            }

            // Buttons Footer (Next / Register & Login Redirect)
            Column(
                modifier = Modifier.width(294.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onRegisterClick,
                    modifier = Modifier
                        .width(294.dp)
                        .height(57.dp), // group_16 height dari dimens.xml
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF528BEF)) // color_82_139_239 (bg_rounded_login)
                ) {
                    Text(text = "Next", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }

                TextButton(onClick = onLoginRedirectClick) {
                    Text(text = "Already have an account? Login", fontSize = 12.sp, color = Color.White)
                }
            }
        }
    }
}