package com.mun.smartcalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainTopBar(
    title: String,
    titleIcon: Boolean = false,
    enableExpandButton: Boolean = false,
    enableBackButton: Boolean = false,
    onClickExpandButton: () -> Unit = {},
    onClickBackButton: () -> Unit = {},
    onClickTitle: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(all = 12.dp)
    ) {
        if (enableBackButton) {
            Icon(
                modifier = modifier
                    .align(Alignment.CenterStart)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onClickBackButton
                    ),
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null
            )
        }
        Row(
            modifier = modifier
                .align(Alignment.Center)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClickTitle
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            if (titleIcon) {
                Icon(
                    modifier = modifier.size(20.dp),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }
        if (enableExpandButton) {
            Text(
                modifier = modifier
                    .align(Alignment.CenterEnd)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onClickExpandButton
                    ),
                text = "주간 일정 접기",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}