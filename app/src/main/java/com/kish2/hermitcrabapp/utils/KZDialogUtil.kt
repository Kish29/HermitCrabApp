package com.kish2.hermitcrabapp.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.kish2.hermitcrabapp.custom.CustomTipDialog
import com.kongzue.dialog.util.DialogSettings
import com.kongzue.dialog.v3.InputDialog
import com.kongzue.dialog.v3.MessageDialog

object KZDialogUtil {
	/* 竖直排列的messageDialog，要得到水平排列的话，在获取后设置orientation即可 */
	fun IOS_LIGHT_VER_ONE_BUTTON_MESSAGE(
		context: Context?,
		title: String?,
		msg: String?,
		okBtnMsg: String?
	): MessageDialog {
		val msgDialog = MessageDialog.build((context as AppCompatActivity?)!!)
		msgDialog.style = DialogSettings.STYLE.STYLE_IOS
		msgDialog.theme = DialogSettings.THEME.LIGHT
		if (title != null) {
			msgDialog.title = title
		}
		if (msg != null) {
			msgDialog.message = msg
		}
		if (okBtnMsg != null) {
			msgDialog.setOkButton(okBtnMsg)
		}
		//        msgDialog.setButtonTextInfo();
		msgDialog.buttonOrientation = LinearLayout.VERTICAL
		return msgDialog
	}

	fun IOS_LIGHT_VER_TWO_BUTTON_MESSAGE(
		context: Context?,
		title: String?,
		msg: String?,
		okBtnMsg: String?,
		cancelBtnMsg: String?
	): MessageDialog {
		val msgDialog = IOS_LIGHT_VER_ONE_BUTTON_MESSAGE(context, title, msg, okBtnMsg)
		if (cancelBtnMsg != null) {
			msgDialog.cancelButton = cancelBtnMsg
		}
		return msgDialog
	}

	fun IOS_LIGHT_VER_THREE_BUTTON_MESSAGE(
		context: Context?,
		title: String?,
		msg: String?,
		okBtnMsg: String?,
		cancelBtnMsg: String?,
		otherBtnMsg: String?
	): MessageDialog {
		val msgDialog = IOS_LIGHT_VER_TWO_BUTTON_MESSAGE(context, title, msg, okBtnMsg, cancelBtnMsg)
		if (otherBtnMsg != null) {
			msgDialog.otherButton = otherBtnMsg
		}
		return msgDialog
	}

	/* 同理，黑色主题时，重新设置一下即可 */
	fun IOS_LIGHT_WAIT_DIALOG(
		context: Context?,
		tip: Drawable?,
		msg: String?,
		tipTime: Int,
		cancelable: Boolean
	): CustomTipDialog {
		val tipDialog = CustomTipDialog.build(context as AppCompatActivity?)
		tipDialog.theme = DialogSettings.THEME.LIGHT
		if (tip != null) {
			tipDialog.setTipDrawable(tip)
		}
		if (msg != null) {
			tipDialog.message = msg
		}
		/* 值位负值时，需要程序员手动关闭或用户按返回关闭 */if (tipTime >= 0) {
			tipDialog.setTipTime(tipTime)
		} else {
			tipDialog.setNoAutoDismiss(true)
		}
		tipDialog.cancelable = cancelable
		return tipDialog
	}

	fun IOS_LIGHT_WAIT_NO_STOP_DIALOG(context: Context?): CustomTipDialog {
		return IOS_LIGHT_WAIT_DIALOG(context, null, "请求中...", -1, false)
	}

	fun IOS_LIGHT_ERROR_DIALOG(context: Context?, msg: String?): CustomTipDialog {
		val tipDialog = CustomTipDialog.build(context as AppCompatActivity?)
		tipDialog.setTip(CustomTipDialog.TYPE.ERROR)
			.setTheme(DialogSettings.THEME.LIGHT)
			.setMessage(msg)
			.setTipTime(1000)
		return tipDialog
	}

	fun IOS_LIGHT_ERROR_DIALOG(context: Context?, tip: Drawable?, msg: String?, tipTime: Int): CustomTipDialog {
		val errorDialog = IOS_LIGHT_WAIT_DIALOG(context, tip, msg, tipTime, true)
		errorDialog.setTip(CustomTipDialog.TYPE.ERROR)
		return errorDialog
	}

	fun IOS_LIGHT_INPUT(context: Context?, msg: String?): InputDialog {
		val inputDialog = InputDialog.build((context as AppCompatActivity?)!!)
			.setOkButton("确定")
			.setCancelButton("取消")
			.setTheme(DialogSettings.THEME.LIGHT)
			.setStyle(DialogSettings.STYLE.STYLE_IOS)
		if (msg != null) inputDialog.message = msg
		//        inputDialog.setButtonTextInfo(FONT_COLOR);
		return inputDialog as InputDialog
	}
}