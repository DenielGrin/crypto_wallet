package com.degrin.bitcoinwallet

import android.app.Application
import com.degrin.bitcoinwallet.di.getKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() = startKoin {
        androidContext(this@MainApplication)
        modules(getKoinModules())
        logger(AndroidLogger())
    }
}
