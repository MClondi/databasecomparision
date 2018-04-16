package com.mjanotta.databasecomparison.util

import android.util.Log
import java.util.concurrent.TimeUnit

class Stopwatch {

    private var startTimeNano: Long = 0

    fun start() {
        startTimeNano = System.nanoTime()
        Log.d("STOPWATCH", "start")
    }

    fun stop(timeUnit: TimeUnit): Long {
        val elapsedNano = System.nanoTime() - startTimeNano
        Log.d("STOPWATCH", "stop")
        return timeUnit.convert(elapsedNano, TimeUnit.NANOSECONDS)
    }
}