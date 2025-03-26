package com.ricky.desbravatask.presentation.tarefaDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ricky.desbravatask.R
import com.ricky.desbravatask.domain.enums.TarefaPrioridadeEnum
import com.ricky.desbravatask.domain.enums.TarefaStatusEnum
import com.ricky.desbravatask.presentation.auth.login.components.BtnCompose
import com.ricky.desbravatask.presentation.auth.login.components.TextFieldCompose
import com.ricky.desbravatask.presentation.main.components.DialogRemover
import com.ricky.desbravatask.presentation.main.components.DialogTarefa
import com.ricky.desbravatask.presentation.tarefaDetails.components.ComentarioItem
import com.ricky.desbravatask.sample.Exemplos.comentarios
import com.ricky.desbravatask.utils.formatLocalDateTimeToString

@Preview
@Composable
private fun TarefaDetailsScreenPrev() {
    TarefaDetailsScreen(
        TarefaDetailsState(),
        navController = NavController(LocalContext.current)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefaDetailsScreen(
    state: TarefaDetailsState,
    onEvent: (TarefaDetailsEvent) -> Unit = {},
    navController: NavController
) {
    val status = TarefaStatusEnum.entries
    val focusManager: FocusManager = LocalFocusManager.current

    if (state.isVoltar) {
        navController.popBackStack()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Voltar",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onEvent(TarefaDetailsEvent.DialogEdit)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onEvent(TarefaDetailsEvent.DialogDelete)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (state.isDialogDelete) {
            DialogRemover(
                onRemover = {
                    onEvent(TarefaDetailsEvent.DeleteTarefa)
                },
                onDimiss = {
                    onEvent(TarefaDetailsEvent.DialogDelete)
                }
            )
        }

        if (state.isDialogEdit) {
            DialogTarefa(
                isLoading = state.isLoading,
                nome = state.tarefa.name,
                nomeResponsavel = state.nomeResponsavel,
                descricao = state.tarefa.name,
                prioridadeEnum = state.tarefaPrioridade,
                departamentos = state.departamentos,
                usuarios = state.usuarios,
                departamento = state.departamentoTarefa,
                isErrorNome = state.onErrorNomeTarefa,
                isErrorDescricao = state.onErrorDescricaoTarefa,
                isErrorResponsavel = state.onErrorResponsavel,
                onNomeChange = {
                    onEvent(TarefaDetailsEvent.OnChangeNomeTarefa(it))
                },
                onNomeResponsavelChange = {
                    onEvent(TarefaDetailsEvent.OnChangeNomeResponsavel(it))
                },
                onResponsavelChange = {
                    onEvent(TarefaDetailsEvent.OnChangeResponsavel(it))
                },
                onDescricaoChange = {
                    onEvent(TarefaDetailsEvent.OnChangeDescricaoTarefa(it))
                },
                onDepartamentoChange = {
                    onEvent(TarefaDetailsEvent.OnChangeDepartamentoTarefa(it))
                },
                onPrioridadeChange = {
                    onEvent(TarefaDetailsEvent.OnChangePrioridade(it))
                },
                onDismiss = {
                    onEvent(TarefaDetailsEvent.DialogEdit)
                },
                onFinish = {
                    onEvent(TarefaDetailsEvent.OnSaveTarefa)
                }
            )
        }

        LazyColumn(
            Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = state.tarefa.name,
                    style = MaterialTheme.typography.headlineMedium
                        .copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        ),
                )
                Spacer(Modifier.height(16.dp))
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(10.dp),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = state.tarefa.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            item {
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            stringResource(R.string.criada_por),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            modifier = Modifier
                                .padding(vertical = 2.dp),
                            text = state.tarefa.criadoPor.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            stringResource(R.string.criada_em),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        state.tarefa.createdAt?.let {
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 2.dp),
                                text = it.formatLocalDateTimeToString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            stringResource(R.string.departamento),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.height(4.dp))
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(state.tarefa.departamento.cor)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    ),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    state.tarefa.departamento.nome,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            stringResource(R.string.prioridade),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                        ) {
                            Icon(
                                Icons.Default.FilterList,
                                contentDescription = stringResource(R.string.prioridade),
                                tint = when (state.tarefa.prioridade) {
                                    TarefaPrioridadeEnum.BAIXA -> {
                                        Color(0xFF006400) // Verde escuro
                                    }

                                    TarefaPrioridadeEnum.MEDIA -> {
                                        Color(0xFFCC5500) // Laranja escuro
                                    }

                                    TarefaPrioridadeEnum.ALTA -> {
                                        Color(0xFF8B0000) // Vermelho escuro
                                    }
                                }
                            )

                            Text(
                                state.tarefa.prioridade.value,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                }
            }
            item {
                Spacer(Modifier.height(48.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    if (state.tarefa.status != TarefaStatusEnum.A_FAZER) {
                        BtnCompose(
                            modifier = Modifier
                                .weight(1f),
                            onClick = {
                                focusManager.clearFocus()
                                val statusUpdateIndex = status.indexOf(state.tarefa.status) - 1
                                onEvent(
                                    TarefaDetailsEvent.OnChangeStatus(status[statusUpdateIndex])
                                )
                            },
                            icon = Icons.Default.ArrowBackIosNew,
                            enabled = !state.isLoading,
                            loading = state.isLoading
                        )

                    } else {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .weight(2f)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            text = state.tarefa.status.value,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    BtnCompose(
                        modifier = Modifier
                            .weight(1f),
                        onClick = {
                            focusManager.clearFocus()
                            val statusUpdateIndex = status.indexOf(state.tarefa.status) + 1
                            onEvent(
                                TarefaDetailsEvent.OnChangeStatus(status[statusUpdateIndex])
                            )
                        },
                        icon = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        enabled = !state.isLoading,
                        loading = state.isLoading
                    )
                }
            }
            item {
                Spacer(Modifier.height(28.dp))
                TextFieldCompose(
                    value = state.comentario,
                    label = R.string.comentario,
                    icon = Icons.Default.AddComment,
                    keyboardType = KeyboardType.Text,
//                            colors = TextFieldDefaults.colors(
//                                focusedContainerColor = MaterialTheme.colorScheme.primary,
//                                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
//                                focusedIndicatorColor = Color.Transparent,
//                                unfocusedIndicatorColor = Color.Transparent,
//                            ),
                    ime = ImeAction.Done,
                    iconAction = Icons.AutoMirrored.Filled.Send,
                    isError = false,
                    onAction = {
                        focusManager.clearFocus()
                        onEvent(TarefaDetailsEvent.SaveComentario)
                    },
                    onDone = {
                        focusManager.clearFocus()
                        onEvent(TarefaDetailsEvent.SaveComentario)
                    }
                ) {
                    onEvent(TarefaDetailsEvent.OnChangeComentario(it))
                }

            }
            items(comentarios) { item ->
                ComentarioItem(comentario = item)
            }
        }
    }
}
