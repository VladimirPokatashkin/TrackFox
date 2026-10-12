package com.trackfox.app.viewmodel

import androidx.lifecycle.ViewModel
import com.trackfox.app.data.entity.TrainingDB
import com.trackfox.app.data.room.dao.TrainingDao
import com.trackfox.app.service.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TrainingListViewModel @Inject constructor(
    private val trainingDao: TrainingDao,
    private val userSessionManager: UserSessionManager
) : ViewModel() {
    val allTrainings : List<TrainingDB>
        get() = runBlocking{ trainingDao.getAllUsersTrainings(userSessionManager.getCurrentUserId()) }
}