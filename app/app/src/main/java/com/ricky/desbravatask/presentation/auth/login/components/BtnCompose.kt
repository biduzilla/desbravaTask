package com.ricky.desbravatask.presentation.auth.login.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    enabled: Boolean = true,
    loading: Boolean = false,
    iconColor: Color = MaterialTheme.colorScheme.onPrimary,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    )
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        shape = RoundedCornerShape(10.dp),
        enabled = enabled
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 4.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            } else {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        tint = iconColor,
                        contentDescription = if (text != null) stringResource(text) else null
                    )
                    Spacer(Modifier.width(8.dp))
                }

                if (text != null) {
                    Text(
                        text = stringResource(id = text),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = iconColor
                        )
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun BtnComposePrev() {
    BtnCompose(
        text = R.string.app_name,
        onClick = {},
        loading = false,
        enabled = true
//        icon = Icons.Default.Anchor
    )
}