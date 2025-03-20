package com.ricky.desbravatask.presentation.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ricky.desbravatask.R
import com.ricky.desbravatask.presentation.auth.login.components.BtnCompose
import com.ricky.desbravatask.presentation.auth.login.components.ErrorToast
import com.ricky.desbravatask.presentation.auth.login.components.TextFieldCompose

@Preview
@Composable
private fun DialogAddDepartamentoPrev() {
    DialogAddDepartamento()
}

@Composable
fun DialogAddDepartamento(
    modifier: Modifier = Modifier,
    value: String = "teste",
    color: Color? = null,
    isLoading: Boolean = false,
    isUpdate: Boolean = false,
    onErrorValue: Boolean = false,
    error: String = "",
    onChangeValue: (String) -> Unit = {},
    onChangeColor: (Color) -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onSave: () -> Unit = {}
) {
    var selectedColor by remember { mutableStateOf<Color?>(color) }
    val focusManager = LocalFocusManager.current
    ErrorToast(error) { }

    Dialog(onDismissRequest = onDismissRequest) {
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
                    text = if (isUpdate) stringResource(R.string.atualizar_departamentos) else stringResource(
                        R.string.criar_departamentos
                    ),
                    style = MaterialTheme.typography.titleLarge
                )

                TextFieldCompose(
                    value = value,
                    isError = onErrorValue,
                    label = R.string.nome,
                    icon = Icons.Default.Description,
                    keyboardType = KeyboardType.Text,
                    ime = ImeAction.Next,
                ) { onChangeValue(it) }

                Text(
                    text = stringResource(R.string.escolher_cor),
                    style = MaterialTheme.typography.titleMedium,
                )

                ColorPickerGrid(
                    color = selectedColor,
                    onColorSelected = { color ->
                        selectedColor = color
                        onChangeColor(color)
                    })

                BtnCompose(
                    text = if (isUpdate) R.string.atualizar else R.string.salvar,
                    enabled = !isLoading,
                    onClick = {
                        onSave()
                        focusManager.clearFocus()
                    },
                    loading = isLoading
                )

            }
        }
    }
}

@Composable
fun ColorPickerGrid(
    onColorSelected: (Color) -> Unit,
    color: Color? = null
) {
    var selectedColor by remember { mutableStateOf<Color?>(color) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(colors) { color ->
            ColorCard(
                color = color,
                isSelected = color == selectedColor,
                onClick = {
                    selectedColor = color
                    onColorSelected(color)
                }
            )
        }
    }
}

@Composable
fun ColorCard(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        border = if (isSelected) BorderStroke(2.dp, Color.Black) else null
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color.White
                )
            }
        }
    }
}

val colors = listOf(
    Color(0xFFEF5350), Color(0xFFEC407A), Color(0xFFAB47BC), Color(0xFF7E57C2),
    Color(0xFF5C6BC0), Color(0xFF42A5F5), Color(0xFF26C6DA), Color(0xFF26A69A),
    Color(0xFF66BB6A), Color(0xFF9CCC65), Color(0xFFD4E157), Color(0xFFFFEE58),
    Color(0xFFFFCA28), Color(0xFFFFA726), Color(0xFFFF7043), Color(0xFF78909C)
)

/*
    Color(0xFFEF5350), Color(0xFFEC407A), Color(0xFFAB47BC), Color(0xFF7E57C2),
    Color(0xFF5C6BC0), Color(0xFF42A5F5), Color(0xFF26C6DA), Color(0xFF26A69A),
    Color(0xFF66BB6A), Color(0xFF9CCC65), Color(0xFFD4E157), Color(0xFFFFEE58),
    Color(0xFFFFCA28), Color(0xFFFFA726), Color(0xFFFF7043), Color(0xFF8D6E63),
    Color(0xFFBDBDBD), Color(0xFF78909C)
 */