package com.ricky.desbravatask.presentation.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ricky.desbravatask.R
import com.ricky.desbravatask.presentation.auth.login.components.TextFieldCompose

@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(250.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primaryContainer
        )
        Spacer(Modifier.height(16.dp))
        TextFieldCompose(
            value = state.email,
            isError = state.onErrorEmail,
            label = R.string.email,
            icon = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
            ime = ImeAction.Next
        ) { onEvent(LoginEvent.OnChangeEmail(it)) }
        Spacer(Modifier.height(16.dp))
        TextFieldCompose(
            value = state.senha,
            isError = state.onErrorSenha,
            isPassword = true,
            label = R.string.senha,
            icon = Icons.Default.Key,
            ime = ImeAction.Done,
            onDone = {},
            onChange = { onEvent(LoginEvent.OnChangeSenha(it)) }
        )
    }
}

@Preview
@Composable
private fun LoginScreenPrev() {
    LoginScreen(LoginState()) { }
}