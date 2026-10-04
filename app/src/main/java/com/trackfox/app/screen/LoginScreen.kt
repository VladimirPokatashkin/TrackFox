package com.trackfox.app.screen

import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.trackfox.app.R
import com.trackfox.app.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), onNavigateToRegister : () -> Unit) {
    val uiState = viewModel.outerUIState.collectAsState()
    var placeholder by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = uiState.value.userName,
            onValueChange = { viewModel.onUserNameChanged(it) },
            label = { Text(text = stringResource(R.string.userNameLabel)) },
            placeholder = { Text(text = stringResource(R.string.userNamePlaceholder)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = placeholder,
            onValueChange = {
                placeholder = "*".repeat(it.length)
                viewModel.onPasswordChanged(it)
            },
            label = { Text(text = stringResource(R.string.passwordLabel)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = viewModel::login,
            enabled = uiState.value.isValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.login))
        }

        Button(
            onClick = onNavigateToRegister,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.register))
        }
    }
}