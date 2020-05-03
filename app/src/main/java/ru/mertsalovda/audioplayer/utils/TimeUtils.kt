package ru.mertsalovda.audioplayer.utils

class TimeUtils {
    companion object {
        fun msecToMin(msec: Int): String {
            var result = ""
            val sec = msec / 1000
            val min = sec / 60
            result = "$min:${format(sec)}"
            return result
        }

        private fun format(sec: Int): String = if (sec < 10) "0$sec" else sec.toString()
    }
}