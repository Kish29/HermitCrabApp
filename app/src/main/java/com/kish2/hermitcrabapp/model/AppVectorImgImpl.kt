package com.kish2.hermitcrabapp.model

import android.content.Context
import android.graphics.drawable.VectorDrawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.kish2.hermitcrabapp.R
import com.kish2.hermitcrabapp.intfc.AppVectorImgApi
import com.kish2.hermitcrabapp.ui.getColor
import java.util.concurrent.ConcurrentHashMap

class AppVectorImgImpl : AppVectorImgApi {

	var viHomeTabIconAcv: VectorDrawable? = null
	var viHomeTabIconDft: VectorDrawable? = null
	var viCommunityIconAcv: VectorDrawable? = null
	var viCommunityIconDft: VectorDrawable? = null
	var viServiceIconAcv: VectorDrawable? = null
	var viServiceIconDft: VectorDrawable? = null
	var viChatIconAcv: VectorDrawable? = null
	var viChatIconDft: VectorDrawable? = null
	var viMineIconAcv: VectorDrawable? = null
	var viMineIconDft: VectorDrawable? = null

	var viBack: VectorDrawable? = null
	var viBackWhite: VectorDrawable? = null
	var viUser: VectorDrawable? = null
	var viPassword: VectorDrawable? = null
	var viClear: VectorDrawable? = null
	var viCheck: VectorDrawable? = null
	var viNotCheck: VectorDrawable? = null
	var viMobile: VectorDrawable? = null
	var viVerify: VectorDrawable? = null
	var viMenu: VectorDrawable? = null
	var viTheme: VectorDrawable? = null
	var viSetting: VectorDrawable? = null
	var viArrowDown: VectorDrawable? = null
	var viCircle: VectorDrawable? = null
	var viMale: VectorDrawable? = null
	var viFemale: VectorDrawable? = null
	var viCheckTheme: VectorDrawable? = null

	private val mVectors: ConcurrentHashMap<String, VectorDrawable> = ConcurrentHashMap()

	private val white = 0xFFFFFF
	private val black = 0x000000

	override fun addVectorDrawable(context: Context, @DrawableRes res: Int, name: String) {
		val drawable = ContextCompat.getDrawable(context, res) as VectorDrawable
		mVectors[name] = drawable
	}

	override fun changeVectorImgColor(color: Int) {
		val realColor = color.getColor()
		// tab icon active color
		viHomeTabIconAcv?.setTint(realColor)
		viCommunityIconAcv?.setTint(realColor)
		viServiceIconAcv?.setTint(realColor)
		viChatIconAcv?.setTint(realColor)
		viMineIconAcv?.setTint(realColor)
		// other
		viBack?.setTint(realColor)
		viUser?.setTint(realColor)
		viPassword?.setTint(realColor)
		viClear?.setTint(realColor)
		viCheck?.setTint(realColor)
		viNotCheck?.setTint(realColor)
		viMobile?.setTint(realColor)
		viVerify?.setTint(realColor)
		viArrowDown?.setTint(realColor)
		viCircle?.setTint(realColor)
		viCheckTheme?.setTint(realColor)
	}

	override fun initialize(context: Context) {
		viHomeTabIconAcv = ContextCompat.getDrawable(context, R.drawable.ai_home_active) as VectorDrawable
		viHomeTabIconDft = ContextCompat.getDrawable(context, R.drawable.ai_home_default) as VectorDrawable
		viCommunityIconAcv = ContextCompat.getDrawable(context, R.drawable.ai_community_active) as VectorDrawable
		viCommunityIconDft = ContextCompat.getDrawable(context, R.drawable.ai_community_default) as VectorDrawable
		viServiceIconAcv = ContextCompat.getDrawable(context, R.drawable.ai_service_active) as VectorDrawable
		viServiceIconDft = ContextCompat.getDrawable(context, R.drawable.ai_service_default) as VectorDrawable
		viChatIconAcv = ContextCompat.getDrawable(context, R.drawable.ai_chat_active) as VectorDrawable
		viChatIconDft = ContextCompat.getDrawable(context, R.drawable.ai_chat_default) as VectorDrawable
		viMineIconAcv = ContextCompat.getDrawable(context, R.drawable.ai_mine_active) as VectorDrawable
		viMineIconDft = ContextCompat.getDrawable(context, R.drawable.ai_mine_default) as VectorDrawable

		viBack = ContextCompat.getDrawable(context, R.drawable.ai_back) as VectorDrawable
		viBackWhite = ContextCompat.getDrawable(context, R.drawable.ai_back_white) as VectorDrawable
		viUser = ContextCompat.getDrawable(context, R.drawable.ai_user) as VectorDrawable
		viPassword = ContextCompat.getDrawable(context, R.drawable.ai_password) as VectorDrawable
		viClear = ContextCompat.getDrawable(context, R.drawable.ai_input_clear) as VectorDrawable
		viCheck = ContextCompat.getDrawable(context, R.drawable.ai_check) as VectorDrawable
		viNotCheck = ContextCompat.getDrawable(context, R.drawable.ai_not_check) as VectorDrawable
		viVerify = ContextCompat.getDrawable(context, R.drawable.ai_verify) as VectorDrawable
		viMobile = ContextCompat.getDrawable(context, R.drawable.ai_mobile) as VectorDrawable
		viMenu = ContextCompat.getDrawable(context, R.drawable.ai_menu) as VectorDrawable
		viTheme = ContextCompat.getDrawable(context, R.drawable.ai_theme) as VectorDrawable
		viSetting = ContextCompat.getDrawable(context, R.drawable.ai_personal_setting) as VectorDrawable
		viArrowDown = ContextCompat.getDrawable(context, R.drawable.ai_arrow_down) as VectorDrawable
		viCircle = ContextCompat.getDrawable(context, R.drawable.ai_circle) as VectorDrawable
		viMale = ContextCompat.getDrawable(context, R.drawable.ai_male) as VectorDrawable
		viFemale = ContextCompat.getDrawable(context, R.drawable.ai_female) as VectorDrawable
		viCheckTheme = ContextCompat.getDrawable(context, R.drawable.ai_checked) as VectorDrawable
	}

	override fun changeLightTone(isLightTone: Boolean) {
		if (isLightTone) {
			viMenu?.setTint(black)
			viTheme?.setTint(black)
			viSetting?.setTint(black)
		} else {
			viMenu?.setTint(white)
			viTheme?.setTint(white)
			viSetting?.setTint(white)
		}
	}
}