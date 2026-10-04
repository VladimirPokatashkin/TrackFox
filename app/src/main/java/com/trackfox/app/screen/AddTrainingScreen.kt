package com.trackfox.app.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.trackfox.app.R

@Composable
fun AddTrainingScreen(onBack : () -> Unit) {
    var durationText by remember { mutableStateOf("") }
    var averageHRText by remember { mutableStateOf("") }
    var maxHRText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = durationText,
            onValueChange = { durationText = it },
            label = { Text(text = stringResource(R.string.durationLabel)) },
            placeholder = { Text(text = stringResource(R.string.durationPlaceholder)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = averageHRText,
            onValueChange = { averageHRText = it },
            label = { Text(text = stringResource(R.string.averageHRLabel)) },
            placeholder = { Text(text = stringResource(R.string.averageHRPlaceHolder)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = maxHRText,
            onValueChange = { maxHRText = it },
            label = { Text(text = stringResource(R.string.maxHRLabel)) },
            placeholder = { Text(text = stringResource(R.string.maxHRPlaceHolder)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = onBack) { Text("back") }
    }
}