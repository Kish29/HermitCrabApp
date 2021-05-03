package com.kish2.hermitcrabapp.splash

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.kish2.hermitcrabapp.BaseActivity
import com.kish2.hermitcrabapp.R
import com.kish2.hermitcrabapp.threadpool.doInUIThread
import com.kish2.hermitcrabapp.utils.view.ThemeUtil
import com.kish2.hermitcrabapp.view.activities.MainActivity
import kotlinx.android.synthetic.main.hremitcrab_splash_page.*

/**
 * @author: jiang ao ran
 * @description:
 * @date: 2021/3/21 2:17 上午
 * @email: 875691208@qq.com
 */
class HermitCrabSplash : BaseActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.hremitcrab_splash_page)
		initView()
		initAction()
	}

	override fun initView() {
		Glide.with(this).load(R.drawable.background).into(ivSplash)
	}

	override fun initAction() {
		doInUIThread {
			ThemeUtil.loadThemeAndColorsVI()
			ThemeUtil.setThemeTabSelectors()
			doInUIThread {
				startActivity(Intent(this, MainActivity::class.java))
			}
		}
	}
}