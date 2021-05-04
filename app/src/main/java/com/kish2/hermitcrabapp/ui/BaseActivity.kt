package com.kish2.hermitcrabapp.ui

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import com.kish2.hermitcrabapp.intfc.ThemeChangedListener
import com.kish2.hermitcrabapp.intfc.ThemeManager

/**
 * @author: jiang ao ran
 * @description:
 * @date: 2021/3/21 2:51 上午
 * @email: 875691208@qq.com
 */
abstract class BaseActivity : FragmentActivity(), ThemeChangedListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		initView()
		initAction()
	}

	@CallSuper
	open fun initView() {
	}

	@CallSuper
	open fun initAction() {
		ThemeManager.addThemeChangedListener(this)
	}

	override fun onThemeChanged(color: Int) {
		// do nothing
	}

}