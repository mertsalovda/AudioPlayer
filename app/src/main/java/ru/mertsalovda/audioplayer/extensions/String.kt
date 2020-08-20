package ru.mertsalovda.audioplayer.extensions

/**
 * Преобразующий миллисекунды в строку вида "mm:ss"
 *
 * @param milliseconds время в миллисекундах
 * @return строка вида "mm:ss"
 */
fun String.mSecToMin(milliseconds: Long): String {
    var result = ""
    val seconds = milliseconds / 1000
    val minutes = seconds / 60
    val secFormatted =  if (seconds < 10) "0$seconds" else seconds.toString()
    result = "$minutes:${secFormatted}"
    return result
}