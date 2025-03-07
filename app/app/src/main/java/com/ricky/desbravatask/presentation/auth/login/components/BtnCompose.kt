package com.ricky.desbravatask.presentation.auth.login.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ricky.desbravatask.R

@Composable
fun BtnCompose(
    modifier: Modifier = Modifier,
    @StringRes text: Int? = null,
    icon: ImageVector? = null,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary
    )
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = if (text != null) stringResource(text) else null
                )
            }
            Spacer(Modifier.width(8.dp))
            if (text != null) {
                Text(
                    text = stringResource(id = text),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun BtnComposePrev() {
    BtnCompose(
        text = R.string.app_name,
        onClick = {}
    )
}