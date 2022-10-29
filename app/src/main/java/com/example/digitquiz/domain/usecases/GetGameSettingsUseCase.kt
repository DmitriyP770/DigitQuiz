package com.example.digitquiz.domain.usecases

import com.example.digitquiz.domain.entity.Difficulty
import com.example.digitquiz.domain.entity.GameSettings
import com.example.digitquiz.domain.repo.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(difficulty: Difficulty): GameSettings{
        return repository.getGameSettings(difficulty)
    }

}