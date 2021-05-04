package com.kish2.hermitcrabapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.Window
import android.view.WindowManager
import java.lang.reflect.Field
import java.lang.reflect.Method


object ImmerseStatusBar {

	enum class OSType {
		TYPE_MIUI,
		TYPE_FLYME,
		TYPE_M
	}

	fun setSinkStatusBar(activity: Activity, isDarkTheme: Boolean, colorId: Int = -1) {
		//当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
		setRootViewFitsSystemWindows(activity, false)
		//设置状态栏透明
		setTranslucentStatus(activity)
		/* 深色主题 */if (isDarkTheme) {
			//一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
			//所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
			if (!setStatusBarDarkTheme(activity, true)) {
				//如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
				//这样半透明+白=灰, 状态栏的文字能看得清
				setStatusBarColor(activity, 0x55000000)
			}
		}
		if (colorId != -1) {
			setStatusBarColor(activity, colorId)
		}
	}

	fun setRootViewFitsSystemWindows(activity: Activity, fit: Boolean) {
		val winContent = activity.findViewById<View>(android.R.id.content) as ViewGroup
		if (winContent.childCount > 0) {
			val rootView = winContent.getChildAt(0) as ViewGroup
			rootView.fitsSystemWindows = fit
		}
	}

	fun setTranslucentStatus(activity: Activity) {
		//5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
		val window = activity.window
		val decorView = window.decorView
		//两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
		val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
			or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
		decorView.systemUiVisibility = option
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
		window.statusBarColor = Color.TRANSPARENT
		//导航栏颜色也可以正常设置
		//window.setNavigationBarColor(Color.TRANSPARENT);
	}

	/**
	 * 设置状态栏深色浅色切换
	 */
	fun setStatusBarDarkTheme(activity: Activity, dark: Boolean): Boolean {
		when {
			Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
				setStatusBarFontIconDark(activity, OSType.TYPE_M, dark)
			}
			OSUtils.isMiui -> {
				setStatusBarFontIconDark(activity, OSType.TYPE_MIUI, dark)
			}
			OSUtils.isFlyme -> {
				setStatusBarFontIconDark(activity, OSType.TYPE_FLYME, dark)
			}
			else -> { //其他情况
				return false
			}
		}
		return true
	}

	fun setStatusBarFontIconDark(activity: Activity, type: OSType, dark: Boolean): Boolean {
		return when (type) {
			OSType.TYPE_MIUI -> setMiuiUI(activity, dark)
			OSType.TYPE_FLYME -> setFlymeUI(activity, dark)
			OSType.TYPE_M -> setCommonUI(activity, dark)
		}
	}

	fun setStatusBarColor(activity: Activity, colorId: Int) {
		val window = activity.window
		window.statusBarColor = colorId
	}

	fun adjustForStatusBarM(activity: Activity, view: View) {
		(view.layoutParams as MarginLayoutParams).topMargin = getStatusBarHeight(activity)
	}

	fun adjustForStatusBarP(activity: Activity, view: View) {
		view.setPadding(0, getStatusBarHeight(activity), 0, 0)
	}

	//设置6.0 状态栏深色浅色切换
	fun setCommonUI(activity: Activity, dark: Boolean): Boolean {
		val decorView = activity.window.decorView
		var vis = decorView.systemUiVisibility
		vis = if (dark) {
			vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
		} else {
			vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
		}
		if (decorView.systemUiVisibility != vis) {
			decorView.systemUiVisibility = vis
		}
		return true
	}

	//设置Flyme 状态栏深色浅色切换
	fun setFlymeUI(activity: Activity, dark: Boolean): Boolean {
		return try {
			val window: Window = activity.window
			val lp: WindowManager.LayoutParams = window.attributes
			val darkFlag: Field = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
			val meizuFlags: Field = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
			darkFlag.isAccessible = true
			meizuFlags.isAccessible = true
			val bit: Int = darkFlag.getInt(null)
			var value: Int = meizuFlags.getInt(lp)
			value = if (dark) {
				value or bit
			} else {
				value and bit.inv()
			}
			meizuFlags.setInt(lp, value)
			window.attributes = lp
			true
		} catch (e: Exception) {
			e.printStackTrace()
			false
		}
	}

	//设置MIUI 状态栏深色浅色切换
	fun setMiuiUI(activity: Activity, dark: Boolean): Boolean {
		return try {
			val window = activity.window
			val clazz: Class<*> = activity.window.javaClass
			@SuppressLint("PrivateApi") val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
			val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
			val darkModeFlag = field.getInt(layoutParams)
			val extraFlagField: Method = clazz.getDeclaredMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
			extraFlagField.isAccessible = true
			if (dark) {    //状态栏亮色且黑色字体
				extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
			} else {
				extraFlagField.invoke(window, 0, darkModeFlag)
			}
			true
		} catch (e: java.lang.Exception) {
			e.printStackTrace()
			false
		}
	}

	//获取状态栏高度
	fun getStatusBarHeight(context: Context): Int {
		var result = 0
		val resourceId: Int = context.resources.getIdentifier(
			"status_bar_height", "dimen", "android"
		)
		if (resourceId > 0) {
			result = context.resources.getDimensionPixelSize(resourceId)
		}
		return result
	}

}