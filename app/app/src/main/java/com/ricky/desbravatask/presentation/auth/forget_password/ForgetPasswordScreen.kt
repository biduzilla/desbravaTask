package com.ricky.desbravatask.presentation.auth.forget_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ricky.desbravatask.R
import com.ricky.desbravatask.navigation.Screens
import com.ricky.desbravatask.presentation.auth.login.components.BtnCompose
import com.ricky.desbravatask.presentation.auth.login.components.ErrorToast
import com.ricky.desbravatask.presentation.auth.login.components.TextFieldCompose

@Composable
fun ForgetPasswordScreen(
    navController: NavController,
    state: ForgetPasswordState,
    onEvent: (ForgetPasswordEvent) -> Unit
) {

    val focusManager = LocalFocusManager.current

    ErrorToast(error = state.error) {
        onEvent(ForgetPasswordEvent.ClearError)
    }

    if (state.isOk) {
        navController.navigate(Screens.LoginScreen.route)
    }

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick =
            { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(
                    vertical = 16.dp,
                    horizontal = 32.dp
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.recuperar_senha),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextFieldCompose(
                value = state.email,
                isError = state.onErrorEmail,
                label = R.string.email,
                enable = !state.isEmailSend,
                icon = Icons.Default.Email,
                ime = ImeAction.Next
            ) {
                onEvent(ForgetPasswordEvent.OnChangeEmail(it))
            }

            BtnCompose(
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                text = R.string.enviar_email,
                onClick = {
                    focusManager.clearFocus()
                    onEvent(ForgetPasswordEvent.OnSendEmail)
                },
                enabled = !state.isLoading,
            )

            if (state.isEmailSend) {
                TextFieldCompose(
                    value = state.cod,
                    isError = state.onErrorCod,
                    label = R.string.codigo,
                    icon = Icons.Default.Numbers,
                    enable = !state.isCodVer,
                    keyboardType = KeyboardType.Number,
                    errorText = R.string.cod_invalido,
                    ime = ImeAction.Next
                ) {
                    onEvent(ForgetPasswordEvent.OnChangeCod(it))
                }
                BtnCompose(
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    text = R.string.ver_cod,
                    onClick = {
                        focusManager.clearFocus()
                        onEvent(ForgetPasswordEvent.OnSendEmail)
                    },
                    enabled = !state.isLoading,
                )
            }

            if (state.isCodVer) {
                TextFieldCompose(
                    value = state.senha,
                    isError = state.onErrorSenha,
                    label = R.string.senha,
                    isPassword = true,
                    icon = Icons.Default.Key,
                    ime = ImeAction.Next
                ) {
                    onEvent(ForgetPasswordEvent.OnChangeSenha(it))
                }

                TextFieldCompose(
                    value = state.confirmSenha,
                    isError = state.onErrorConfirmSenha,
                    label = R.string.confirmar_senha,
                    icon = Icons.Default.Key,
                    isPassword = true,
                    ime = ImeAction.Done,
                    errorText = R.string.error_confirma_senha
                ) {
                    onEvent(ForgetPasswordEvent.OnChangeConfirmSenha(it))
                }

                BtnCompose(
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    text = R.string.alterar_senha,
                    onClick = {
                        focusManager.clearFocus()
                        onEvent(ForgetPasswordEvent.OnUpdatePassword)
                    },
                    enabled = !state.isLoading,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ForgetPasswordScreenPrev() {
    val context = LocalContext.current
    val navController = NavController(context)
    ForgetPasswordScreen(navController, ForgetPasswordState()) {}

}