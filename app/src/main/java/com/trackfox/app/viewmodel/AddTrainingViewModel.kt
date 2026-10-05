package com.trackfox.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trackfox.app.data.entity.TrainingDB
import com.trackfox.app.data.room.dao.TrainingDao
import com.trackfox.app.service.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class AddTrainingUIState(
    val date : LocalDate = LocalDate.MIN,
    val duration : Int = -1,
    val averageHR : Int = -1,
    val maxHR : Int = -1,
    val isValid : Boolean = false
)

@HiltViewModel
class AddTrainingViewModel @Inject constructor(
    private val trainingDao : TrainingDao,
    private val userSessionManager: UserSessionManager
) : ViewModel() {

    private val innerUIState = MutableStateFlow(AddTrainingUIState())
    val outerUIState = innerUIState.asStateFlow()

    fun onDateChanged(newDate : LocalDate) {
        innerUIState.update {
            it.copy(
                date = newDate,
                isValid = it.date != LocalDate.MIN && it.duration != -1 && it.averageHR != -1 && it.maxHR != -1
            )
        }
    }

    fun onDurationChanged(newDuration : Int) {
        innerUIState.update {
            it.copy(
                duration = newDuration,
                isValid = it.date != LocalDate.MIN && it.duration != -1 && it.averageHR != -1 && it.maxHR != -1
            )
        }
    }

    fun onAverageHRChanged(newAverageHR : Int) {
        innerUIState.update {
            it.copy(
                averageHR = newAverageHR,
                isValid = it.date != LocalDate.MIN && it.duration != -1 && it.averageHR != -1 && it.maxHR != -1
            )
        }
    }

    fun onMaxHRChanged(newMaxHR : Int) {
        innerUIState.update {
            it.copy(
                maxHR = newMaxHR,
                isValid = it.date != LocalDate.MIN && it.duration != -1 && it.averageHR != -1 && it.maxHR != -1
            )
        }
    }

    fun saveTraining() {
        viewModelScope.launch {
            with(innerUIState.value) {
                trainingDao.insertTraining(
                    TrainingDB(
                        userId = userSessionManager.getCurrentUserId(),
                        date = this.date,
                        duration = this.duration,
                        averageHR = this.averageHR,
                        maxHR = this.maxHR
                    )
                )
            }
        }
    }
}