package com.trackfox.app.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.trackfox.app.viewmodel.TrainingListViewModel

@Composable
fun TrainingListScreen(
    viewModel: TrainingListViewModel = hiltViewModel(checkNotNull(
        LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }, null),
    onNavigateToTrainingInfo : (Long) -> Unit,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier) {
        items(viewModel.allTrainings) { training ->
            Column(
                modifier = Modifier
                    .clickable { onNavigateToTrainingInfo(training.id) }
                    .padding(16.dp)
            ) {
                Text(training.name)
                Text(training.date.toString())
            }
        }
    }
}