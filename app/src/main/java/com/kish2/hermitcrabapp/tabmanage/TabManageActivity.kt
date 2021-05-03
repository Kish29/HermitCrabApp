package com.kish2.hermitcrabapp.tabmanage

import android.os.Bundle
import com.kish2.hermitcrabapp.BaseActivity
import com.kish2.hermitcrabapp.R

/**
 * @author: jiang ao ran
 * @description: 基础activity，控制5个tab页
 * @date: 2021/3/21 2:49 上午
 * @email: 875691208@qq.com
 */
class TabManageActivity : BaseActivity() {

	companion object {
		private val sTabTitles = intArrayOf(
			R.string.nav_home_title,
			R.string.nav_forum_title,
			R.string.nav_service_title,
			R.string.nav_message_title,
			R.string.nav_personal
		)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		initView()
		initAction()
	}

	override fun initView() {
		super.initView()
	}

	override fun initAction() {
		super.initAction()
	}

}