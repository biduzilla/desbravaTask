package com.ricky.desbravatask.presentation.auth.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ricky.desbravatask.R
import com.ricky.desbravatask.navigation.Screens
import com.ricky.desbravatask.presentation.auth.login.components.BtnCompose
import com.ricky.desbravatask.presentation.auth.login.components.ErrorToast
import com.ricky.desbravatask.presentation.auth.login.components.TextFieldCompose

@Composable
fun LoginScreen(
    state: LoginState,
    navController: NavController,
    onEvent: (LoginEvent) -> Unit
) {
    BackHandler(enabled = true) {
    }
    val focusManager = LocalFocusManager.current

    ErrorToast(error = state.error) {
        onEvent(LoginEvent.ClearError)
    }

    if (state.onLogin) {
        navController.navigate(Screens.MainScreen.route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 16.dp,
                horizontal = 32.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(Modifier.height(46.dp))
        Image(
            modifier = Modifier.size(250.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(16.dp))
        TextFieldCompose(
            value = state.email,
            isError = state.onErrorEmail,
            label = R.string.email,
            icon = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
            ime = ImeAction.Next,
        ) { onEvent(LoginEvent.OnChangeEmail(it)) }
        TextFieldCompose(
            value = state.senha,
            isError = state.onErrorSenha,
            isPassword = true,
            label = R.string.senha,
            icon = Icons.Default.Key,
            ime = ImeAction.Done,
            onDone = {
                onEvent(LoginEvent.OnLogin)
            },
            onChange = { onEvent(LoginEvent.OnChangeSenha(it)) }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = state.isLembrarSenha,
                onCheckedChange = {
                    onEvent(LoginEvent.OnToggleLembrarSenha(it))
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primaryContainer,
                    uncheckedColor = MaterialTheme.colorScheme.primaryContainer
                )
            )

            Text(
                text = stringResource(id = R.string.lembrar_senha),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }

        Spacer(Modifier.height(8.dp))
        BtnCompose(
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            text = R.string.login,
            enabled = !state.isLoading,
            loading = state.isLoading,
            onClick = {
                onEvent(LoginEvent.OnLogin)
            }
        )

        Spacer(Modifier.height(16.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextButton(onClick = {
                focusManager.clearFocus()
                navController.navigate(Screens.ForgetPasswordScreen.route)
            }) {
                Text(
                    text = stringResource(id = R.string.esqueci_senha),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = R.string.nao_tem_conta),
                    style = MaterialTheme.typography.labelLarge
                )
                TextButton(onClick = {
                    focusManager.clearFocus()
                    navController.navigate(Screens.RegisterScreen.route)
                }) {
                    Text(
                        text = stringResource(id = R.string.criar_conta),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPrev() {
    val context = LocalContext.current
    val navController = NavController(context)
    LoginScreen(LoginState(), navController) { }
}