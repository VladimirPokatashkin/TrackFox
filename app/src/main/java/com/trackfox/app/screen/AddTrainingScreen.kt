package com.trackfox.app.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trackfox.app.R
import com.trackfox.app.viewmodel.AddTrainingViewModel

@Composable
fun AddTrainingScreen(viewModel : AddTrainingViewModel = hiltViewModel(), onBack : () -> Unit) {
    val uiState = viewModel.outerUIState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = uiState.value.duration.toString(),
            onValueChange = { viewModel.onDurationChanged(it.toInt()) },
            label = { Text(text = stringResource(R.string.durationLabel)) },
            placeholder = { Text(text = stringResource(R.string.durationPlaceholder)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.value.averageHR.toString(),
            onValueChange = { viewModel.onAverageHRChanged(it.toInt()) },
            label = { Text(text = stringResource(R.string.averageHRLabel)) },
            placeholder = { Text(text = stringResource(R.string.averageHRPlaceHolder)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.value.maxHR.toString(),
            onValueChange = { viewModel.onMaxHRChanged(it.toInt()) },
            label = { Text(text = stringResource(R.string.maxHRLabel)) },
            placeholder = { Text(text = stringResource(R.string.maxHRPlaceHolder)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = viewModel::saveTraining) { Text(text = stringResource(R.string.save)) }

        Button(onClick = onBack) { Text(text = stringResource(R.string.back)) }
    }
}