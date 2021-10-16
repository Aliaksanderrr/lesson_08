package com.minchinovich.lesson_08

enum class FunctionalButton(
    val func: ((tempAnswer: Int, num: Int) -> Int)?
) {
    DEFAULT({ _, num -> num }),
    PLUS({ answer, num -> answer + num }),
    MINUS({ answer, num -> answer - num }),
    MULTIPLICATION({ answer, num -> answer * num }),
    DIVISION({ answer, num -> answer / num }),
}