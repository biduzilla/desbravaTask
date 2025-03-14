package com.ricky.desbravatask.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ricky.desbravatask.R
import com.ricky.desbravatask.presentation.auth.login.components.BtnCompose
import com.ricky.desbravatask.presentation.main.components.DialogAddDepartamento
import com.ricky.desbravatask.presentation.main.components.DialogRemover
import com.ricky.desbravatask.presentation.main.components.TopAppBar
import com.ricky.desbravatask.sample.DepartamentoSample.departamentosSample
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    state: MainState,
    onEvent: (MainEvent) -> Unit,
    navController: NavController
) {

    BackHandler(enabled = true) {
        // Não faz nada, bloqueando o botão de voltar
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        stringResource(R.string.departamentos),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    if (state.departamentos.isNotEmpty()) {
                        HorizontalDivider()
                    }
                    Spacer(Modifier.height(8.dp))
                    departamentosSample.forEachIndexed { index, departamento ->
                        NavigationDrawerItem(modifier = Modifier.padding(horizontal = 16.dp),
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = Color(departamento.cor),
                                unselectedContainerColor = Color(departamento.cor),
                            ),
                            badge = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        text = departamento.qtdTarefas.toString(),
                                        modifier = Modifier.padding(
                                            vertical = 4.dp,
                                            horizontal = 8.dp
                                        ),
                                    )
                                    IconButton(onClick = {
                                        onEvent(MainEvent.OnUpdateDepartamento(departamento))
                                    }) {
                                        Icon(
                                            Icons.Default.Edit,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                    IconButton(onClick = {
                                        onEvent(MainEvent.OnDeleteDepartamento(departamento))
                                    }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }

                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            label = {
                                Text(
                                    text = departamento.nome,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    ),
                                )

                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                selectedItemIndex = index
                                onEvent(MainEvent.OnChangeDepartamento(departamento))
                            }
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    BtnCompose(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            onEvent(MainEvent.AddDepartamento)
                        },
                        text = R.string.add_novo,
                        loading = state.isLoading,
                        icon = Icons.Default.Add
                    )
                }
            }
        ) {

            if (state.isDialogDepartamento) {
                DialogAddDepartamento(
                    value = state.nomeDepartamento,
                    color = state.corDepartamento,
                    isUpdate = state.isUpdateDepartamento,
                    isLoading = state.isLoading,
                    onErrorValue = state.onErrorNomeDepartamento,
                    error = state.error,
                    onChangeColor = { onEvent(MainEvent.OnChangeCorDepartamento(it)) },
                    onChangeValue = { onEvent(MainEvent.OnChangeNomeDepartamento(it)) },
                    onDismissRequest = { onEvent(MainEvent.OnDismissDialogDepartamento) },
                    onSave = { onEvent(MainEvent.SaveDepartamento) }
                )
            }

            if (state.isDialogDeleteDepartamento) {
                DialogRemover(
                    onDimiss = {
                        onEvent(MainEvent.OnDialogDeleteDepartamento)
                    },
                    onRemover = {
                        onEvent(MainEvent.DeleteDepartamento)
                    }
                )
            }
            Scaffold(
                topBar = {
                    TopAppBar(
                        onMenu = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Box(Modifier.padding(paddingValues))
            }

        }
    }
}

@Preview
@Composable
private fun MainScreenPrev() {
    val context = LocalContext.current
    MainScreen(MainState(), {}, NavController(context))
}