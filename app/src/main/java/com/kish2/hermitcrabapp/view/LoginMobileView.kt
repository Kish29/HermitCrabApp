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
import kotlinx.android.synthetic.main.hermitcrab_login_mobile_view.view.*

@ModelView
class LoginMobileView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

	@ModelProp(ModelProp.Option.DoNotHash)
	fun setMobileIcon(icon: Drawable) {
		ivMobileIcon.setImageDrawable(icon)
	}

	@ModelProp(ModelProp.Option.DoNotHash)
	fun setVerifyIcon(icon: Drawable) {
		ivVerify.setImageDrawable(icon)
	}

	@ModelProp(ModelProp.Option.DoNotHash)
	fun setClearMobileIcon(icon: Drawable) {
		ivClearMobile.setImageDrawable(icon)
	}

	@CallbackProp
	fun onLoginByUsernameClick(listener: OnClickListener?) {
		tvLoginByUsername.thresholdClick {
			listener?.onClick(it)
		}
	}

	@AfterPropsSet
	fun after() {
		ivClearMobile.setOnClickListener {
			et_mobile_input.text.clear()
		}
	}
}