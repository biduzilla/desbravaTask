package com.ricky.desbravatask.presentation.tarefaDetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ricky.desbravatask.domain.models.Comentario
import com.ricky.desbravatask.sample.Exemplos.comentarios
import com.ricky.desbravatask.utils.formatLocalDateTimeToString

@Preview
@Composable
private fun ComentarioItemPrev() {
    ComentarioItem(comentario = comentarios[0])
}

@Composable
fun ComentarioItem(
    modifier: Modifier = Modifier,
    comentario: Comentario = Comentario()
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier = Modifier.padding(8.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = Icons.Default.Person,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = comentario.usuario.name
                )
            }

            Column(
                modifier = Modifier.padding(8.dp),
            ) {
                Text(
                    text = "${comentario.usuario.name} - ${comentario.createdAt?.formatLocalDateTimeToString()}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = comentario.comentario,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }

}