package com.kish2.hermitcrabapp.ui

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.view.ViewConfiguration
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.kish2.hermitcrabapp.AppThreadPool
import com.kish2.hermitcrabapp.RealApplication
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.TimeUnit

/**
 * @author: jiang ao ran
 * @description: UI工具
 * @date: 3/21/21 1:37 AM
 * @email: 875691208@qq.com
 */
fun doInUIThread(delay: Long = 0, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, action: () -> Unit) {
	uiThread().scheduleDirect(action, delay, timeUnit)
}

fun doInChildThread(delay: Long = 0, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, action: () -> Unit) {
	AppThreadPool.common {
		if (delay > 0) {
			Thread.sleep(delay)
		}
		action()
	}
}

fun uiThread(): Scheduler = AndroidSchedulers.mainThread()

fun Int.getString() =
	RealApplication.getInstance().getString(
		this
	)

fun Int.getColor() =
	ContextCompat.getColor(
		RealApplication.getInstance().applicationContext,
		this
	)

fun Int.getDimension() =
	RealApplication.getInstance().resources.getDimension(this)

fun Int.getDimensionPixelSize() =
	RealApplication.getInstance().resources.getDimensionPixelSize(this)


fun iconSelector(defaultIcon: Drawable, activeIcon: Drawable): StateListDrawable {
	return StateListDrawable().apply {
		addState(intArrayOf(-android.R.attr.state_selected), defaultIcon)
		/* 添加选中模式的颜色 */
		addState(intArrayOf(android.R.attr.state_selected), activeIcon)
		addState(intArrayOf(android.R.attr.state_pressed), activeIcon)
	}
}

fun textColorSelector(
	@ColorInt defaultColor: Int,
	@ColorInt color: Int
): ColorStateList {
	return ColorStateList(
		/* 字体颜色选择器 */
		arrayOf(
			intArrayOf(-android.R.attr.state_selected),
			intArrayOf(android.R.attr.state_selected)
		),
		intArrayOf(defaultColor, color)
	)
}

fun View.thresholdClick(time: Long = 500L, action: (View) -> Unit) {
	var canClick = true
	setOnClickListener {
		if (canClick) {
			action(it)
			canClick = false
		}
		doInChildThread(time) {
			canClick = true
		}
	}
}

fun View.scale(zoomOut: Boolean) {
	if (zoomOut) {
		this.scaleX = 0.8f
		this.scaleY = 0.8f
	} else {
		this.scaleX = 1f
		this.scaleY = 1f
	}
}

fun getTouchSlop(): Int {
	return ViewConfiguration.get(RealApplication.getInstance().applicationContext).scaledTouchSlop
}

fun ImageView.loadImageFromNet(url: String) {
	Glide.with(RealApplication.getInstance().applicationContext)
		.load(url)
		.into(this)
}