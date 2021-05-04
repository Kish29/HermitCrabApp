package com.kish2.hermitcrabapp.ui

import androidx.palette.graphics.Palette
import com.kish2.hermitcrabapp.R
import com.kish2.hermitcrabapp.intfc.ThemeChangedListener
import com.kish2.hermitcrabapp.intfc.ThemeManagerApi
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicInteger

class ThemeMangerImpl : ThemeManagerApi {

	private val mListeners: MutableList<WeakReference<ThemeChangedListener>> = mutableListOf()

	private var mThemeColorId: AtomicInteger = AtomicInteger(R.color.colorPrimary.getColor())

	override fun getThemeColor(): Int {
		return mThemeColorId.get()
	}

	@Synchronized
	override fun addThemeChangedListener(listener: ThemeChangedListener) {
		mListeners.add(WeakReference(listener))
	}

	@Synchronized
	override fun setThemeColor(color: Int) {
		mThemeColorId.getAndSet(color.getColor())
		// dispatch to listeners
		for (l in mListeners) {
			l.get()?.onThemeChanged(mThemeColorId.get())
		}
	}

	override fun isLightTone(palette: Palette, standard: Int): Boolean {
		var res = 0
		var count = 0
		val s = standard.coerceAtLeast(0xa)
		val dominantSwatch = palette.dominantSwatch
		if (dominantSwatch != null) {
			val rgb = dominantSwatch.rgb
			var string = Integer.toHexString(rgb)
			string = string.substring(2)
			val length = string.length
			for (i in 0 until length) {
				val j = string[i] - '0'
				if (j != 0) {
					count++
					res += j
				}
			}
		}
		return res / count >= s
	}
}