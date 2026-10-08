package com.trackfox.app.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trackfox.app.R
import com.trackfox.app.viewmodel.LoginStatus
import com.trackfox.app.viewmodel.RegistrationStatus
import com.trackfox.app.viewmodel.RegistrationViewModel

@Composable
fun RegistrationScreen(
    viewModel : RegistrationViewModel = hiltViewModel(),
    onSuccess : () -> Unit
) {
    val uiState = viewModel.outerUIState.collectAsState()
    var placeholder by remember { mutableStateOf("") }

    LaunchedEffect(uiState.value.status) {
        if (uiState.value.isSuccess) {
            onSuccess()
        }
    }

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = uiState.value.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text(text = stringResource(R.string.emailLabel)) },
            placeholder = { Text(text = stringResource(R.string.emailPlaceholder)) },
            singleLine = true,
            enabled = !uiState.value.isLoading,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.value.name,
            onValueChange = { viewModel.onUserNameChanged(it) },
            label = { Text(text = stringResource(R.string.userNameLabel)) },
            placeholder = { Text(text = stringResource(R.string.userNamePlaceholder)) },
            singleLine = true,
            enabled = !uiState.value.isLoading,
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
            enabled = !uiState.value.isLoading,
            modifier = Modifier.fillMaxWidth()
        )

        placeholder = ""

        OutlinedTextField(
            value = placeholder,
            onValueChange = {
                placeholder = "*".repeat(it.length)
                viewModel.onRepeatedPasswordChanged(it)
            },
            label = { Text(text = stringResource(R.string.repeatPasword)) },
            singleLine = true,
            enabled = !uiState.value.isLoading,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = viewModel::registerUser,
            enabled = uiState.value.isValid && !uiState.value.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.register))
        }

        if (uiState.value.isError) {
            Text(
                text = (uiState.value.status as RegistrationStatus.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}