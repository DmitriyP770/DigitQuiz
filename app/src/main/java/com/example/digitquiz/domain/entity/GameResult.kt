package com.example.digitquiz.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val hasWon:Boolean,
    val qtyOfRightAnswers:Int,
    val totalAnswers:Int,
    val gameSettings: GameSettings
) : Parcelable