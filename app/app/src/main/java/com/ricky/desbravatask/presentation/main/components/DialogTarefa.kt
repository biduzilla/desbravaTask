package com.ricky.desbravatask.presentation.main.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ricky.desbravatask.R
import com.ricky.desbravatask.domain.enums.TarefaPrioridadeEnum
import com.ricky.desbravatask.domain.models.Departamento
import com.ricky.desbravatask.domain.models.UsuarioUpdate
import com.ricky.desbravatask.presentation.auth.login.components.BtnCompose
import com.ricky.desbravatask.presentation.auth.login.components.TextFieldCompose

@Preview
@Composable
private fun DialogTarefaPrev() {
    DialogTarefa()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogTarefa(
    modifier: Modifier = Modifier,
    isUpdate: Boolean = false,
    isLoading: Boolean = false,
    nome: String = "",
    nomeResponsavel: String = "",
    descricao: String = "",
    prioridadeEnum: TarefaPrioridadeEnum = TarefaPrioridadeEnum.BAIXA,
    departamentos: List<Departamento> = emptyList(),
    usuarios: List<UsuarioUpdate> = emptyList(),
    departamento: Departamento = Departamento(),
    isErrorNome: Boolean = false,
    isErrorDescricao: Boolean = false,
    isErrorResponsavel: Boolean = false,
    onNomeChange: (String) -> Unit = {},
    onNomeResponsavelChange: (String) -> Unit = {},
    onResponsavelChange: (UsuarioUpdate) -> Unit = {},
    onDescricaoChange: (String) -> Unit = {},
    onDepartamentoChange: (Departamento) -> Unit = {},
    onPrioridadeChange: (TarefaPrioridadeEnum) -> Unit = {},
    onDismiss: () -> Unit = {},
    onFinish: () -> Unit = {}
) {
    val focusManager: FocusManager = LocalFocusManager.current

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
        ) {
            LazyColumn(
                modifier = Modifier
                    .heightIn(max = 650.dp)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    Text(
                        text = if (isUpdate) stringResource(R.string.atualizar_tarefa) else stringResource(
                            R.string.criar_tarefa
                        ),
                        style = MaterialTheme.typography.titleLarge
                    )

                    TextFieldCompose(
                        value = nome,
                        isError = isErrorNome,
                        label = R.string.nome,
                        icon = Icons.Default.Description,
                        keyboardType = KeyboardType.Text,
                        ime = ImeAction.Next,
                    ) { onNomeChange(it) }

                    TextFieldCompose(
                        value = descricao,
                        isError = isErrorDescricao,
                        label = R.string.descricao,
                        icon = Icons.Default.Description,
                        keyboardType = KeyboardType.Text,
                        ime = ImeAction.Next,
                        onNext = {
                            focusManager.clearFocus()
                        }
                    ) { onDescricaoChange(it) }

                    DropdownCompose(
                        label = R.string.prioridade,
                        value = prioridadeEnum.value,
                        list = TarefaPrioridadeEnum.entries.toTypedArray()
                    ) {
                        onPrioridadeChange(it)
                    }
                    Spacer(Modifier.height(8.dp))

                    DropdownCompose(
                        label = R.string.departamentos,
                        value = departamento.nome,
                        list = departamentos.map { it.nome }.toTypedArray()
                    ) {
                        onDepartamentoChange(departamentos.first { departamento -> departamento.nome == it })
                    }
                    TextFieldCompose(
                        value = nomeResponsavel,
                        isError = isErrorResponsavel,
                        label = R.string.responsavel,
                        icon = Icons.Default.Person,
                        keyboardType = KeyboardType.Text,
                        ime = ImeAction.Next,
                        errorText = R.string.error_escolha_responsavel,
                    ) { onNomeResponsavelChange(it) }
                }

                items(usuarios) { user ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.primaryContainer

                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .clickable {
                                    focusManager.clearFocus()
                                    onResponsavelChange(user)
                                },
                            text = user.name,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                item {
                    BtnCompose(
                        text = if (isUpdate) R.string.atualizar else R.string.salvar,
                        enabled = !isLoading,
                        onClick = {onFinish()},
                        loading = isLoading
                    )
                }
            }
        }
    }
}