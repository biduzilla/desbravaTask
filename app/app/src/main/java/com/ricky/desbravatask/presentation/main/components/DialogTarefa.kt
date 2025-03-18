package com.ricky.desbravatask.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
                ) { onNomeChange(it) }

                DropdownCompose(
                    label = R.string.prioridade,
                    value = prioridadeEnum.value,
                    list = TarefaPrioridadeEnum.entries.toTypedArray()
                ) {
                    onPrioridadeChange(it)
                }

                DropdownCompose(
                    label = R.string.departamentos,
                    value = departamento.nome,
                    list = departamentos.toTypedArray()
                ) {
                    onDepartamentoChange(it)
                }

                ExposedDropdownMenuBox(
                    expanded = usuarios.isNotEmpty(),
                    onExpandedChange = { }
                ) {
                    TextFieldCompose(
                        value = nomeResponsavel,
                        isError = isErrorResponsavel,
                        label = R.string.responsavel,
                        icon = Icons.Default.Person,
                        keyboardType = KeyboardType.Text,
                        ime = ImeAction.Next,
                        errorText = R.string.error_escolha_responsavel
                    ) { onNomeChange(it) }
                    ExposedDropdownMenu(
                        expanded = usuarios.isNotEmpty(),
                        onDismissRequest = {},
                    ) {
                        usuarios.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item.name,
                                        style = TextStyle(
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    )
                                },
                                onClick = {
                                    onResponsavelChange(item)
                                })
                        }
                    }
                }

                BtnCompose(
                    text = if (isUpdate) R.string.atualizar else R.string.salvar,
                    enabled = !isLoading,
                    onClick = onFinish,
                    loading = isLoading
                )
            }
        }
    }
}