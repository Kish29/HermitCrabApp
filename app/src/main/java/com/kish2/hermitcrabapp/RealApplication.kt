package com.kish2.hermitcrabapp

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.kish2.hermitcrabapp.AppThreadPool.common
import com.kish2.hermitcrabapp.AppThreadPool.io
import com.kish2.hermitcrabapp.intfc.AppVectorImg
import com.kish2.hermitcrabapp.intfc.TabFragmentApi
import com.kish2.hermitcrabapp.ui.ChatFragment
import com.kish2.hermitcrabapp.ui.CommunityFragment
import com.kish2.hermitcrabapp.ui.HomeFragment
import com.kish2.hermitcrabapp.ui.MineFragment
import com.kish2.hermitcrabapp.ui.ServiceFragment
import com.kish2.hermitcrabapp.ui.TabManageActivity

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
		mavericksTask()
		tabTask()
		imgTask()
		configTask()
	}

	private fun mavericksTask() {
		Mavericks.initialize(this)
	}

	private fun tabTask() {
		common {
			val tabs = mutableListOf<TabFragmentApi>()
			tabs.add(HomeFragment())
			tabs.add(CommunityFragment())
			tabs.add(ServiceFragment())
			tabs.add(ChatFragment())
			tabs.add(MineFragment())
			TabManageActivity.initialize(tabs)
		}
	}

	private fun imgTask() {
		common {
			AppVectorImg.initialize(this)
		}
	}

	private fun configTask() {
		io {
			/* 加载配置信息 *//*
			ApplicationConfigUtil.getConfigAndSetEditor(this.applicationContext)
			*//* 加载本地数据 *//*
			FileStorageManager.initApplicationDirs(this.applicationContext)
			ApplicationConfigUtil.loadLocalAppData()*/
		}
	}

}