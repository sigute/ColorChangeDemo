package com.github.sigute.colorchangedemo

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("unused")
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)

            // https://github.com/InsertKoinIO/koin/issues/847
            koin.loadModules(listOf(appModule))
            koin.createRootScope()
        }
    }
}
