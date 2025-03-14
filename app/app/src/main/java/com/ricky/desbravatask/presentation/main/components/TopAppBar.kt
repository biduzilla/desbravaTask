package com.ricky.desbravatask.presentation.main.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ricky.desbravatask.domain.enums.TarefaStatusEnum

@Preview
@Composable
private fun TopAppBarPrev() {
    TopAppBar(title = "Teste")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String = "Teste",
    onMenu: () -> Unit = {},
    onSettings: () -> Unit = {},
    onChangeEnum: (TarefaStatusEnum) -> Unit = {}
) {
    val listEnums = TarefaStatusEnum.entries
    var enumSelected by remember {
        mutableIntStateOf(0)
    }
    var isEsqueda by remember {
        mutableStateOf(false)
    }
    var isGestureProcessed by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(
            bottomEnd = 20.dp,
            bottomStart = 20.dp
        ),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 16.dp,
                    top = 38.dp
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onMenu
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
                IconButton(onClick = onSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (enumSelected != 0) {
                    IconButton(
                        onClick = {
                            enumSelected--
                            isEsqueda = true
                            onChangeEnum(listEnums[enumSelected])
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(48.dp))
                }

                AnimatedContent(
                    modifier = Modifier.weight(1f)
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures(
                                onDragEnd = {
                                    isGestureProcessed = false
                                }
                            ) { _, dragAmount ->
                                if (!isGestureProcessed) {
                                    when {
                                        dragAmount > 10 -> {
                                            isEsqueda = true
                                            if (enumSelected > 0) {
                                                enumSelected--
                                                isGestureProcessed = true
                                            }
                                        }
                                        dragAmount < -10 -> {
                                            isEsqueda = false
                                            if (enumSelected < listEnums.size - 1) {
                                                enumSelected++
                                                isGestureProcessed = true
                                            }
                                        }
                                    }
                                }
                            }
                        },
                    targetState = listEnums[enumSelected].value,
                    transitionSpec = {
                        if (!isEsqueda) {
                            slideInHorizontally(animationSpec = tween(300)) { it } togetherWith
                                    slideOutHorizontally(animationSpec = tween(300)) { -it }
                        } else {
                            slideInHorizontally(animationSpec = tween(300)) { -it } togetherWith
                                    slideOutHorizontally(animationSpec = tween(300)) { it }
                        }
                    }
                ) { targetText ->
                    Text(
                        modifier = Modifier.weight(1f),
                        text = targetText,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                if (enumSelected != listEnums.size - 1) {
                    IconButton(
                        onClick = {
                            enumSelected++
                            isEsqueda = false
                            onChangeEnum(listEnums[enumSelected])
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }
        }

    }
}