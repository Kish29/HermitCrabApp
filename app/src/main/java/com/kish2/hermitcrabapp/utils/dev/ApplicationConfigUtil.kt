package com.kish2.hermitcrabapp.utils.dev

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.kish2.hermitcrabapp.utils.view.ThemeUtil

/* 该类通过App的子线程，将应用配置存储在手机本地，需要用到的时候进行读取 */
object ApplicationConfigUtil {
	private var mAppConfig: SharedPreferences? = null
	var HAD_LOGIN = false
    var HAS_AVATAR = false
    var HAS_BANNER_BKG = false

	/* 使用系统默认颜色作为背景 */
    var BANNER_DEFAULT = true
	var HAS_SIDE_MENU_BKG = false
	var APP_SIDE_MENU_BKG_URI: Uri? = null
    var LOCAL_BANNER_URI: String? = null
    var LOCAL_AVATAR_URI: String? = null
    var sample_radius = 8
    var sample_value = 2
    var USER_AVATAR: Bitmap? = null

    fun getConfigAndSetEditor(context: Context) {
		mAppConfig = context.getSharedPreferences("app_config", Context.MODE_PRIVATE)
		HAD_LOGIN = mAppConfig?.getBoolean("had_login", false) ?: false
		HAS_AVATAR = mAppConfig?.getBoolean("has_avatar", false) ?: false
		HAS_BANNER_BKG = mAppConfig?.getBoolean("has_banner_bkg", false) ?: false
		HAS_SIDE_MENU_BKG = mAppConfig?.getBoolean("has_side_bkg", false) ?: false
		BANNER_DEFAULT = mAppConfig?.getBoolean("user_sys_default", true) ?: false
		LOCAL_BANNER_URI = mAppConfig?.getString("banner_uri", null) ?: null
		LOCAL_AVATAR_URI = mAppConfig?.getString("avatar_uri", null) ?: null
		sample_radius = mAppConfig?.getInt("sample_radius", 8) ?: 8
		sample_value = mAppConfig?.getInt("sample_value", 2)?:2
	}

    fun loadLocalAppData() {
		if (HAD_LOGIN) {
			HermitCrabApp.USER.username = mAppConfig!!.getString("username", null)
			HermitCrabApp.USER.token = mAppConfig!!.getString("user_token", null)
		}
		if (HAS_AVATAR) {
			USER_AVATAR = BitmapFactory.decodeFile(LOCAL_AVATAR_URI)
		}
		if (HAS_SIDE_MENU_BKG) {
		}
	}

	fun storeAppConfig() {
		/* 应用部分*/
		val mAppConfigEditor = mAppConfig?.edit()
		mAppConfigEditor?.putBoolean("had_login", HAD_LOGIN)
		mAppConfigEditor?.putBoolean("has_avatar", HAS_AVATAR)
		mAppConfigEditor?.putBoolean("has_banner_bkg", HAS_BANNER_BKG)
		mAppConfigEditor?.putBoolean("has_side_bkg", HAS_SIDE_MENU_BKG)
		mAppConfigEditor?.putBoolean("user_sys_default", BANNER_DEFAULT)

		if (LOCAL_BANNER_URI != null) {
			mAppConfigEditor?.putString("banner_uri", LOCAL_BANNER_URI)
		}
		if (LOCAL_AVATAR_URI != null){
			mAppConfigEditor?.putString("avatar_uri", LOCAL_AVATAR_URI)
		}
		mAppConfigEditor?.putInt("sample_radius", sample_radius)
		mAppConfigEditor?.putInt("sample_value", sample_value)

		/* 主题部分 */
		ThemeUtil.theme_config_editor.putInt(ThemeUtil.KEY_COLOR, ThemeUtil.THEME_ID)
		mAppConfigEditor?.apply()
		ThemeUtil.theme_config_editor.apply()
	}
}