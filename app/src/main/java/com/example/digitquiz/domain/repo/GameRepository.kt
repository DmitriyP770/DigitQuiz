package com.example.digitquiz.domain.repo

import com.example.digitquiz.domain.entity.Difficulty
import com.example.digitquiz.domain.entity.GameSettings
import com.example.digitquiz.domain.entity.Question

interface GameRepository {

    fun getGameSettings(difficulty: Difficulty):GameSettings

    fun generateQuestion(maxVal: Int, numOfOptions: Int): Question

}