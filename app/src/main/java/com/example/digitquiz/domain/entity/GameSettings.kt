package com.example.digitquiz.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings (
    val timeInSeconds : Int,
    val  maxValue: Int,
    val minQuantityOfRightAswers: Int,
    val minPercentageOfRightAnswers: Int,

    ) : Parcelable

