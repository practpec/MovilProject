package com.example.state.ui.components

import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun FontBackground(
    modifier: Modifier,
    content: @Composable () ->Unit){
    val gradientColors = listOf(
        Color(0xFF7C277C),
        Color(0xFF2B2563)
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = gradientColors,
                    startY = 0f,
                    endY = 1000f
                )
            )
    ) {
        content()
    }
}