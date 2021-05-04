package com.kish2.hermitcrabapp.ui

import android.content.Intent
import com.kish2.hermitcrabapp.R
import kotlinx.android.synthetic.main.hermitcrab_splash_page.*
import java.util.concurrent.TimeUnit

/**
 * @author: jiang ao ran
 * @description:
 * @date: 2021/3/21 2:17 上午
 * @email: 875691208@qq.com
 */
class HermitCrabSplash : BaseActivity() {

	override fun initView() {
		super.initView()
		setContentView(R.layout.hermitcrab_splash_page)
		vvSplash.setVideoPath("http://vfx.mtime.cn/Video/2019/07/12/mp4/190712140656051701.mp4")
		vvSplash.start()
	}

	override fun initAction() {
		super.initAction()
		doInUIThread(5L, timeUnit = TimeUnit.SECONDS) {
			startActivity(Intent(this, TabManageActivity::class.java))
			vvSplash.stopPlayback()
		}
	}
}