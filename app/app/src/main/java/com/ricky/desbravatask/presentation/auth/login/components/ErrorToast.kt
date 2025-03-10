package com.ricky.desbravatask.presentation.auth.login.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ErrorToast(
    error: String,
    onAction: () -> Unit
) {
    val context = LocalContext.current
    if (error.isNotBlank()) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        onAction()
    }
}