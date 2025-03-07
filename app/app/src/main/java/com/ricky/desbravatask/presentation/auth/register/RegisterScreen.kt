package com.ricky.desbravatask.presentation.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ricky.desbravatask.R
import com.ricky.desbravatask.presentation.auth.login.components.BtnCompose
import com.ricky.desbravatask.presentation.auth.login.components.TextFieldCompose

@Composable
fun RegisterScreen(
    state: RegisterState,
    navController: NavController,
    onEvent: (RegisterEvent) -> Unit
) {
    Column(
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                text = stringResource(id = if (state.isUpdate) R.string.atualizar_conta else R.string.criar_conta),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(48.dp))
            TextFieldCompose(
                value = state.nome,
                isError = state.onErrorNome,
                label = R.string.nome,
                icon = Icons.Default.Person,
                ime = ImeAction.Next,

            ) {
                onEvent(RegisterEvent.OnChangeNome(it))
            }
            TextFieldCompose(
                value = state.email,
                isError = state.onErrorEmail,
                label = R.string.email,
                icon = Icons.Default.Email,
                ime = ImeAction.Next
            ) {
                onEvent(RegisterEvent.OnChangeEmail(it))
            }
            TextFieldCompose(
                value = state.senha,
                isError = state.onErrorSenha,
                label = R.string.senha,
                icon = Icons.Default.Key,
                isPassword = true,
                ime = ImeAction.Next
            ) {
                onEvent(RegisterEvent.OnChangeSenha(it))
            }

            TextFieldCompose(
                value = state.confirmacaoSenha,
                isError = state.onErrorConfirmacaoSenha,
                label = R.string.confirmar_senha,
                icon = Icons.Default.Key,
                isPassword = true,
                ime = ImeAction.Done,
                errorText = R.string.error_confirma_senha
            ) {
                onEvent(RegisterEvent.OnChangeConfirmSenha(it))
            }
            Spacer(Modifier.height(16.dp))
            BtnCompose(
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                text = if (state.isUpdate) R.string.atualizar_conta else R.string.criar_conta,
                onClick = { onEvent(RegisterEvent.OnRegister) },
                enabled = !state.isLoading,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPrev() {
    val context = LocalContext.current
    val navController = NavController(context)
    RegisterScreen(RegisterState(), navController) { }
}