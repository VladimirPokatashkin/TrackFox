package com.trackfox.app.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.trackfox.app.viewmodel.TrainingInfoViewModel

@Composable
fun TrainingInfoScreen(
    viewModel: TrainingInfoViewModel = hiltViewModel(checkNotNull(
        LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }, null),
    onShareClick : () -> Unit,
) {
    viewModel.getTraining()?.let {

        with(it) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(name)
                Text(date.toString())
                Text("$duration минут")
                Text("средний пульс: $averageHR")
                Text("максимальный пульс: $maxHR")
                Text("пойдёт")
                Button(onClick = onShareClick) {
                    Text("поделиться")
                }
            }
        }
    } ?: Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("тренировка не найдена. минус вайб(((")
    }
}