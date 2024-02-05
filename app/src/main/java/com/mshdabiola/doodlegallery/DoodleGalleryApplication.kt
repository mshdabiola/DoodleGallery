/*
 *abiola 2024
 */

package com.mshdabiola.doodlegallery

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DoodleGalleryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (packageName.contains("debug")) {
            Timber.plant(Timber.DebugTree())
            Timber.e("log on app create")
        }
    }
}
