package com.ricky.desbravatask.presentation.tarefaDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ricky.desbravatask.R
import com.ricky.desbravatask.domain.enums.TarefaPrioridadeEnum
import com.ricky.desbravatask.presentation.auth.login.components.BtnCompose
import com.ricky.desbravatask.presentation.auth.login.components.TextFieldCompose
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
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
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
                    BtnCompose(
                        modifier = Modifier
                            .weight(1f),
                        onClick = {},
                        icon = Icons.Default.ArrowBackIosNew,
                        enabled = !state.isLoading,
                        loading = state.isLoading
                    )
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
                        onClick = {},
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
                    isError = false,
                    onDone = { onEvent(TarefaDetailsEvent.SaveComentario) }
                ) { onEvent(TarefaDetailsEvent.OnChangeComentario(it)) }

            }
            items(comentarios) { item ->
                ComentarioItem(comentario = item)
            }
        }
    }
}
