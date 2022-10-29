package com.example.digitquiz.domain.entity

data class Question(
    val sum:Int,
    val visibleDigit: Int,
    val options: List<Int>
) {

    val rightAnser : Int
        get()  = sum-visibleDigit

}