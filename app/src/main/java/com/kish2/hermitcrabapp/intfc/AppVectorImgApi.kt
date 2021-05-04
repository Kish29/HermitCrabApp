package com.kish2.hermitcrabapp.intfc

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.kish2.hermitcrabapp.model.AppVectorImgImpl

interface AppVectorImgApi {

	fun addVectorDrawable(context: Context, @DrawableRes res: Int, name: String)

	fun changeVectorImgColor(@ColorRes color: Int)

	fun initialize(context: Context)

	/*是否需要为亮色调转换颜色*/
	fun changeLightTone(isLightTone: Boolean)
}

val AppVectorImg: AppVectorImgImpl = AppVectorImgImpl()