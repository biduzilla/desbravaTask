package com.ricky.desbravatask.presentation.main.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ricky.desbravatask.R
import com.ricky.desbravatask.domain.models.UsuarioUpdate
import com.ricky.desbravatask.presentation.auth.login.components.TextFieldCompose

@Composable
fun TextFieldAutoComplete(
    modifier: Modifier = Modifier,
    value: String = "",
    isErrorResponsavel: Boolean = false,
    usuarios: List<UsuarioUpdate> = emptyList(),
    onChange: (String) -> Unit = {},
    onSelected: (UsuarioUpdate) -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val filteredSuggestions by remember { mutableStateOf(usuarios.map { it.name }) }
    var showSuggestions by remember { mutableStateOf(usuarios.isNotEmpty()) }

    LaunchedEffect(usuarios) {
        showSuggestions = usuarios.isNotEmpty()
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldCompose(
            modifier = Modifier.focusRequester(focusRequester),
            value = value,
            isError = isErrorResponsavel,
            label = R.string.responsavel,
            icon = Icons.Default.Person,
            keyboardType = KeyboardType.Text,
            ime = ImeAction.Next,
            errorText = R.string.error_escolha_responsavel
        ) { onChange(it) }
    }

    // Exibe as sugestões apenas se showSuggestions for true
    if (showSuggestions) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(8.dp)
                )
                .animateContentSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                items(
                    items = usuarios,
                    key = { usuario -> usuario.id } // Usa o ID do usuário como chave
                ) { usuario ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelected(usuario)
                                showSuggestions = false // Fecha as sugestões após seleção
                            }
                            .padding(12.dp)
                    ) {
                        Text(
                            text = usuario.name,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .shadow(8.dp, RoundedCornerShape(8.dp))
//            .background(
//                MaterialTheme.colorScheme.onPrimary,
//                shape = RoundedCornerShape(8.dp)
//            )
//            .animateContentSize()
//    ) {
//        if (showSuggestions && filteredSuggestions.isNotEmpty()) {
//            LazyColumn(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.Start
//            ) {
//                items(
//                    items = filteredSuggestions,
//                    key = { suggestion -> suggestion } // Using the suggestion string as the key
//                ) { suggestion ->
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clickable {
//                                onSelected(usuarios.first { it.name == suggestion })
//                                showSuggestions = false
//                            }
//                            .padding(12.dp)
//                    ) {
//                        Text(
//                            text = suggestion,
//                            modifier = Modifier.fillMaxWidth()
//                        )
//                    }
//                }
//            }
//        }
//    }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
