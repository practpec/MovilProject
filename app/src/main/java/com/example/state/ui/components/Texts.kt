package com.example.state.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrincipalText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 50,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Left
) {
    Text(
        text = text,
        modifier = modifier.fillMaxWidth()
            .padding(start = 16.dp),
        fontSize = fontSize.sp,
        fontWeight = FontWeight.Bold,
        color = color,
        textAlign = textAlign
    )
}