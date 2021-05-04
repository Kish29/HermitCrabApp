package com.kish2.hermitcrabapp.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.airbnb.epoxy.EpoxyController
import com.kish2.hermitcrabapp.intfc.ThemeChangedListener
import com.kish2.hermitcrabapp.intfc.ThemeManager

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes), ThemeChangedListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		ThemeManager.addThemeChangedListener(this)
	}

	override fun onThemeChanged(color: Int) {
		// do nothing
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initView()
		initAction()
	}

	@CallSuper
	open fun initView() {
	}

	@CallSuper
	open fun initAction() {
	}

	open fun thisController(): EpoxyController? = null

}
