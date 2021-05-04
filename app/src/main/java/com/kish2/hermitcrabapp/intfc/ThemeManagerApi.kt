package com.kish2.hermitcrabapp.intfc

import androidx.annotation.ColorRes
import androidx.palette.graphics.Palette
import com.kish2.hermitcrabapp.ui.ThemeMangerImpl

interface ThemeManagerApi {

	fun getThemeColor(): Int

	fun addThemeChangedListener(listener: ThemeChangedListener)

	fun setThemeColor(@ColorRes color: Int)

	/*判断是否是亮色调*/
	fun isLightTone(palette: Palette, standard: Int = 0xa): Boolean

}

val ThemeManager: ThemeManagerApi = ThemeMangerImpl()

interface ThemeChangedListener {

	fun onThemeChanged(@ColorRes color: Int)

}