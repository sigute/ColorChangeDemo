package com.github.sigute.colorchangedemo

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

@Suppress("unused")
class KoinTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
      cl: ClassLoader?,
      className: String?,
      context: Context?
    ): Application {
        return super.newApplication(
          cl, KoinTestApp::class.java.name, context
        )
    }
}
