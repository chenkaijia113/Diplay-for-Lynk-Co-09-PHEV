package com.shilapi.xcertplay.media

import kotlin.math.pow
import kotlin.math.roundToInt

/** Per-track navigation gain, applied before the head unit mixes its audio streams. */
object NavigationAudioGain {
    const val MIN_DB = -30
    const val MAX_DB = 6

    fun clamp(db: Int): Int = db.coerceIn(MIN_DB, MAX_DB)

    /** Amplitude scale; -20 dB = 0.1 and 0 dB = 1.0. */
    fun linear(db: Int): Float = 10.0.pow(clamp(db) / 20.0).toFloat()

    /** Scales 16-bit little-endian PCM in place by [db]; 0 dB leaves it untouched. */
    fun scalePcm16(data: ByteArray, offset: Int, length: Int, db: Int) {
        val gain = linear(db)
        if (gain == 1f) return
        val end = offset + (length and 1.inv())
        var i = offset
        while (i < end) {
            val sample = (data[i + 1].toInt() shl 8) or (data[i].toInt() and 0xff)
            val scaled = (sample * gain).roundToInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt())
            data[i] = scaled.toByte()
            data[i + 1] = (scaled shr 8).toByte()
            i += 2
        }
    }
}
