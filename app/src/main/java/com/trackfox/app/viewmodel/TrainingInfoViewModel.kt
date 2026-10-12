package com.trackfox.app.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.trackfox.app.data.room.dao.TrainingDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TrainingInfoViewModel @Inject constructor(
    private val trainingDao: TrainingDao,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val id = savedStateHandle.get<Long>("trainingId")
    fun getTraining() = runBlocking {
        if (id != null) {
            trainingDao.getById(id)
        } else null
    }
}