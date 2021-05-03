package com.kish2.hermitcrabapp

import android.app.Application
import com.kish2.hermitcrabapp.threadpool.childThread
import com.kish2.hermitcrabapp.utils.dev.ApplicationConfigUtil
import com.kish2.hermitcrabapp.utils.dev.FileStorageManager

/**
 * @author: jiang ao ran
 * @description: application
 * @date: 3/21/21 1:02 AM
 */
class RealApplication : Application() {

    companion object {
        private var INSTANCE: RealApplication? = null

        // 双重检查的单例模式
        fun getInstance(): RealApplication {
            return INSTANCE ?: synchronized(this) {
                val newInstance = RealApplication()
                INSTANCE = newInstance
                newInstance
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
	    childThread {
            /* 加载配置信息 */
            ApplicationConfigUtil.getConfigAndSetEditor(this.applicationContext)
            /* 加载本地数据 */
            FileStorageManager.initApplicationDirs(this.applicationContext)
            ApplicationConfigUtil.loadLocalAppData()
        }
    }

}