package com.trackfox.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.trackfox.app.data.entity.TrainingDB
import com.trackfox.app.data.room.Database
import com.trackfox.app.model.service.UserSessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class AddTrainingViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = Database.getDatabase(application).trainingDao()
    private val prefs = UserSessionManager(application)

    fun saveTraining(date : LocalDate, duration: Int, averageHR : Int, maxHR : Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dao.insertTraining(
                    TrainingDB(
                        userId = prefs.getCurrentUserId(),
                        date = date,
                        duration = duration,
                        averageHR = averageHR,
                        maxHR = maxHR
                    )
                )
            }
        }
    }
}