package com.kish2.hermitcrabapp.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.kish2.hermitcrabapp.ui.thresholdClick
import kotlinx.android.synthetic.main.hermitcrab_login_control_view.view.*

@ModelView
class LoginControlView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

	@CallbackProp
	fun onLoginClick(listener: OnClickListener?) {
		cpbLogin.thresholdClick {
			listener?.onClick(it)
		}
	}

	@CallbackProp
	fun onRegisterClick(listener: OnClickListener?) {
		btnGotoRegister.thresholdClick {
			listener?.onClick(it)
		}
	}

	@CallbackProp
	fun onRememberClick(listener: OnClickListener?) {

	}

	@CallbackProp
	fun onForgetPasswordClick(listener: OnClickListener?) {

	}
}