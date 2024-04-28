package com.example.kmm_playground.android

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CheckButton(
    checked: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .clickable(
                onClick = { onClick() },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .padding(8.dp)
    ) {
        val contentSize = 28.dp
        if (checked) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Checked",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(contentSize),
            )
        } else {
            Canvas(
                modifier = Modifier
                    .size(contentSize)
                    .padding(4.dp)
            ) {
                drawCircle(
                    color = Color.LightGray,
                    radius = size.minDimension / 2,
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCheckButton() {
    Surface {
        CheckButton(checked = false, onClick = {})
    }
}
