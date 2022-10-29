package com.example.digitquiz.presentation.fragments.models

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitquiz.domain.entity.Difficulty

class GameViewModelFactory(private val application: Application, private val difficulty: Difficulty)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(application, difficulty) as T
        }
        throw RuntimeException()
    }
}