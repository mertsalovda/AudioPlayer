package ru.mertsalovda.audioplayer.extensions

/**
 * Преобразующий миллисекунды в строку вида "mm:ss"
 *
 * Объект приёмник - время в миллисекундах
 * @return строка вида "m:ss"
 */
fun Int.millisecToTime(): String {
    var result = ""
    val minutes: Long = this.toLong() / 1000 / 60
    var seconds = (this / 1000 % 60)
    val secFormatted = if (seconds < 10) {
        "0$seconds"
    } else {
        seconds.toString()
    }
    result = "$minutes:${secFormatted}"

    return result
}