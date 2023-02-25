package xyz.liusui.anki.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Error
 */
fun Any?.logE(): String =
    with(SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS", Locale.CHINESE)) {
        format(Date())
    }.also {
        println("\u001b[4;31m$it ${this.toString()}")
    }


/**
 * Debug
 */
fun Any?.logD(): String =
    with(SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS", Locale.CHINESE)) {
        return@with format(Date())
    }.also {
        println("\u001b[4,43m$it ${this@logD.toString()}")
    }

/**
 * Info
 */
fun Any?.logI(): String = with(SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS", Locale.CHINESE)) {
    format(Date())
}.also {
    println("$it ${this@logI.toString()}")
}