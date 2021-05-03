package com.kish2.hermitcrabapp

import android.app.Activity

/**
 * @author: jiang ao ran
 * @description:
 * @date: 2021/3/21 2:51 上午
 * @email: 875691208@qq.com
 */
abstract class BaseActivity : Activity() {

	open fun initView() {}

	open fun initAction() {}
}