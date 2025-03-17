package com.ricky.desbravatask.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ricky.desbravatask.R
import com.ricky.desbravatask.domain.enums.TarefaPrioridadeEnum
import com.ricky.desbravatask.domain.models.Tarefa
import com.ricky.desbravatask.sample.Exemplos.tarefaSample
import com.ricky.desbravatask.utils.formatLocalDateTimeToString

@Preview
@Composable
private fun TarefaComposePrev() {
    TarefaCompose(tarefa = tarefaSample[0])
}

@Composable
fun TarefaCompose(
    modifier: Modifier = Modifier,
    tarefa: Tarefa
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        tarefa.name,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Column(
                modifier = Modifier.padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            stringResource(R.string.departamento),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.height(4.dp))
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(tarefa.departamento.cor)
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
                                    tarefa.departamento.nome,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Column(
                        modifier = Modifier.weight(1f),
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
                                tint = when (tarefa.prioridade) {
                                    TarefaPrioridadeEnum.BAIXA -> {
                                        Color.Green
                                    }

                                    TarefaPrioridadeEnum.MEDIA -> {
                                        Color.Yellow
                                    }

                                    TarefaPrioridadeEnum.ALTA -> {
                                        Color.Red
                                    }
                                }

                            )

                            Text(
                                tarefa.prioridade.value,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                Row(
                    Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            stringResource(R.string.responsavel),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            text = tarefa.responsavel.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            stringResource(R.string.criada_em),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        tarefa.departamento.createdAt?.let {
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 8.dp),
                                text = it.formatLocalDateTimeToString(),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}