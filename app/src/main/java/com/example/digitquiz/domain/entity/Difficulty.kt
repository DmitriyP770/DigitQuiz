package com.example.digitquiz.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Difficulty : Parcelable{
    TEST, EASY, NORMAL, HARD
}