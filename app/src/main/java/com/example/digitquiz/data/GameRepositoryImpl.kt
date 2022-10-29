package com.example.digitquiz.data

import com.example.digitquiz.common.Constants.MIN_SUM
import com.example.digitquiz.domain.entity.Difficulty
import com.example.digitquiz.domain.entity.GameSettings
import com.example.digitquiz.domain.entity.Question
import com.example.digitquiz.domain.repo.GameRepository
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    override fun getGameSettings(difficulty: Difficulty): GameSettings {
        return when(difficulty){
            Difficulty.TEST -> {
                GameSettings(10,20,3,
                    50)
            }
            Difficulty.EASY -> {
                GameSettings(20, 20, 5,
                    50)
            }
            Difficulty.NORMAL -> {
                GameSettings(40, 60, 15,
                    60)

            }

            Difficulty.HARD -> {
                GameSettings(60, 100, 30,
                    70)
            }
        }}


    override fun generateQuestion(maxVal: Int, numOfOptions: Int): Question {
        val sum = Random.nextInt(from = MIN_SUM, until = maxVal+1)
        val visibleNum = Random.nextInt(from = 1, until = sum - 1)
        val rightAnswer = sum-visibleNum
        val answers = HashSet<Int>()
        answers.add(rightAnswer)
        while (answers.size<numOfOptions){
            var option = Random.nextInt(from = 1, until = sum-2)
            answers.add(option)
        }
        return Question(sum,visibleNum,answers.toList())

    }


}