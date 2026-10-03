package com.trackfox.app.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MainScreen(onNavigateToDetails : () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onNavigateToDetails, shape = CircleShape) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Text(text = "aboba", modifier = Modifier.padding(innerPadding), textAlign = TextAlign.Center)
    }
}