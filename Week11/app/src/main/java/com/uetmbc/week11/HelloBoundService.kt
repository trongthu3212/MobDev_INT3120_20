package com.uetmbc.week11

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.util.*

class HelloBoundService : Service() {
    // Binder given to clients.
    private val binder = LocalBinder()
    private val generator = Random()

    val randomNumber: Int
        get() = generator.nextInt();

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods.
        fun getService(): HelloBoundService = this@HelloBoundService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}