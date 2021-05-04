package com.kish2.hermitcrabapp.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import kotlinx.android.synthetic.main.hermitcrab_common_top_bar_view.view.*

@ModelView
class CommonTopBarView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


	@ModelProp(ModelProp.Option.DoNotHash)
	fun setNavIcon(icon: Drawable) {
		ivNavBack.setImageDrawable(icon)
	}

	@CallbackProp
	fun onNavIconClick(@Nullable listener: OnClickListener?) {
		ivNavBack.setOnClickListener { listener?.onClick(it) }
	}

	@TextProp
	fun setTopBarTitle(title: CharSequence?) {
		tvTitle.text = title
	}

}