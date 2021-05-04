package com.kish2.hermitcrabapp.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.kish2.hermitcrabapp.ui.thresholdClick
import kotlinx.android.synthetic.main.hermitcrab_login_username_view.view.*

@ModelView
class LoginUsernameView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
	@ModelProp(ModelProp.Option.DoNotHash)
	fun setUserIcon(icon: Drawable) {
		ivUserIcon.setImageDrawable(icon)
	}

	@ModelProp(ModelProp.Option.DoNotHash)
	fun setPasswordIcon(icon: Drawable) {
		ivPasswordIcon.setImageDrawable(icon)
	}

	@ModelProp(ModelProp.Option.DoNotHash)
	fun setClearUsernameIcon(icon: Drawable) {
		ivClearPassword.setImageDrawable(icon)
	}

	@CallbackProp
	fun onLoginByMobileClick(listener: OnClickListener?) {
		tvLoginByMobile.thresholdClick {
			listener?.onClick(it)
		}
	}

	@AfterPropsSet
	fun after() {
		ivClearUsername.setOnClickListener {
			etUsername.text.clear()
		}
		ivClearPassword.setOnClickListener {
			et_password_input.text.clear()
		}
	}
}