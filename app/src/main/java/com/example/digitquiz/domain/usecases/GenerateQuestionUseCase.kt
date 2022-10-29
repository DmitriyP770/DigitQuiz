package com.example.digitquiz.domain.usecases

import com.example.digitquiz.common.Constants.NUM_OF_OPTIONS
import com.example.digitquiz.domain.entity.Question
import com.example.digitquiz.domain.repo.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSum: Int): Question{
        return repository.generateQuestion(maxSum,NUM_OF_OPTIONS)
    }

}