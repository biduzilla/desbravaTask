package com.ricky.desbravatask.presentation.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ricky.desbravatask.R
import com.ricky.desbravatask.presentation.auth.login.components.BtnCompose

@Composable
fun MainScreen(
    state: MainState,
    onEvent: (MainEvent) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
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
                        "Drawer Title",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider()
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
                                IconButton(onClick = {
                                    onEvent(MainEvent.OnUpdateDepartamento(departamento))
                                }) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = null
                                    )
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
                        onClick = {
                            onEvent(MainEvent.AddDepartamento)
                        },
                        text = R.string.add_novo,
                        icon = Icons.Default.Add
                    )
                }
            }
        ) { }
    }

}

@Preview
@Composable
private fun MainScreenPrev() {
    MainScreen(MainState(), {})
}