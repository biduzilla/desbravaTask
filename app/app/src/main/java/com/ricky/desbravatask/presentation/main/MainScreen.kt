package com.ricky.desbravatask.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ricky.desbravatask.R
import com.ricky.desbravatask.presentation.auth.login.components.BtnCompose
import com.ricky.desbravatask.presentation.main.components.DialogAddDepartamento
import com.ricky.desbravatask.presentation.main.components.DialogRemover
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainState,
    onEvent: (MainEvent) -> Unit
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
                        style = MaterialTheme.typography.titleLarge
                    )
                    if (state.departamentos.isNotEmpty()) {
                        HorizontalDivider()
                    }
                    state.departamentos.forEachIndexed { index, departamento ->
                        NavigationDrawerItem(
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = Color(
                                    android.graphics.Color.parseColor(
                                        departamento.cor
                                    )
                                ),
                                unselectedContainerColor = Color(
                                    android.graphics.Color.parseColor(
                                        departamento.cor
                                    )
                                ),
                            ),
                            badge = {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    IconButton(onClick = {
                                        onEvent(MainEvent.OnUpdateDepartamento(departamento))
                                    }) {
                                        Icon(
                                            Icons.Default.Edit,
                                            contentDescription = null
                                        )
                                    }
                                    IconButton(onClick = {
                                        onEvent(MainEvent.OnDeleteDepartamento(departamento))
                                    }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = null
                                        )
                                    }

                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            label = {
                                Text(text = departamento.nome)
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                selectedItemIndex = index
                                onEvent(MainEvent.OnChangeDepartamento(departamento))
                            }
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    BtnCompose(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            onEvent(MainEvent.AddDepartamento)
                        },
                        text = R.string.add_novo,
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

            if(state.isDialogDeleteDepartamento){
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
                        title = {
                            Text(text = "Todo App")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
                                )
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
    MainScreen(MainState(), {})
}