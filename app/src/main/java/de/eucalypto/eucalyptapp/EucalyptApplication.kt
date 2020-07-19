package de.eucalypto.eucalyptapp

import android.app.Application
import timber.log.Timber

class EucalyptApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}