package com.zx

import android.app.Application


/**
 * @description:
 * @author: zhouxiang
 * @created: 2025/03/05 11:52
 * @version: V1.0
 */
class App : Application()  {
    companion object {
        @JvmStatic
        lateinit var INSTANCE: App
            private set
    }
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}