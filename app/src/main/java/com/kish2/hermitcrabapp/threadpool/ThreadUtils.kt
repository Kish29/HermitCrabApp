package com.kish2.hermitcrabapp.threadpool

import android.os.Looper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.TimeUnit

/**
 * @author: jiang ao ran
 * @description: 线程工具
 * @date: 3/21/21 1:37 AM
 */

/**
 * @author: jiang ao ran
 * @description:
 * @param:
 * @return:
 * @date:
 * @email: jiangaoran.kish2@bytedance.com
 */
fun doInUIThread(delay: Long = 0, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, action: () -> Unit) {
	if (isUIThread()) {
		action()
	} else {
		uiThread().scheduleDirect(action, delay, timeUnit)
	}
}

fun isUIThread() = Looper.getMainLooper() == Looper.myLooper()

fun uiThread(): Scheduler = AndroidSchedulers.mainThread()