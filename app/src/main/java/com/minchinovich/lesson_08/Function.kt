package com.minchinovich.lesson_08

import kotlin.math.abs

enum class Function(
    val func: ((tempAnswer: Int, num: Int) -> Int)
) {

    DEFAULT({ _, num -> num }) {
        override fun detectOverflow(answer: Int, num: Int): Int {
            throw NotImplementedError()
        }
    },

    PLUS({ answer, num ->
        PLUS.detectOverflow(answer, num)
    }) {
        override fun detectOverflow(answer: Int, num: Int): Int {
            return when {
                answer > 0 && Int.MAX_VALUE - answer <= num -> throw IllegalArgumentException("Int overflow")
                answer < 0 && Int.MIN_VALUE - answer >= num -> throw IllegalArgumentException("Int overflow")
                else -> answer + num
            }
        }
    },

    MINUS({ answer, num ->
        MINUS.detectOverflow(answer, num)
    }) {
        override fun detectOverflow(answer: Int, num: Int): Int {
            return when {
                answer > 0 && Int.MAX_VALUE - answer <= num -> throw IllegalArgumentException("Int overflow")
                answer < 0 && Int.MIN_VALUE - answer >= num -> throw IllegalArgumentException("Int overflow")
                else -> answer - num
            }
        }
    },

    MULTIPLICATION({ answer, num ->
        MULTIPLICATION.detectOverflow(answer, num)
    }) {
        override fun detectOverflow(answer: Int, num: Int): Int {
            return when {
                answer != 0 && Int.MAX_VALUE / abs(answer) <= abs(num) -> throw IllegalArgumentException(
                    "Int overflow"
                )
                else -> answer * num
            }
        }
    },

    DIVISION({ answer, num -> answer / num }) {
        override fun detectOverflow(answer: Int, num: Int): Int {
            throw NotImplementedError()
        }
    };

    protected abstract fun detectOverflow(answer: Int, num: Int): Int

}