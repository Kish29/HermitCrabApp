package com.kish2.hermitcrabapp.view

import android.content.Context
import android.util.AttributeSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.google.android.material.card.MaterialCardView
import com.kish2.hermitcrabapp.ui.loadImageFromNet
import com.kish2.hermitcrabapp.ui.thresholdClick
import kotlinx.android.synthetic.main.hermitcrab_inform_item_view.view.*

@ModelView
class InformItemView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

	@CallbackProp
	fun onClick(listener: OnClickListener?) {
		thresholdClick { listener?.onClick(it) }
	}

	@ModelProp
	fun setImage(url: String?) {
		url?.let {
			iv_inform_img.loadImageFromNet(it)
		}
	}

	@ModelProp
	fun setWeekDay(day: String) {
		tv_inform_day.text = day
	}

	@ModelProp
	fun setPubDate(date: String) {
		tv_inform_date.text = date
	}

	@ModelProp
	fun setInformTitle(title: String) {
		tvInformTitle.text = title
	}

	@ModelProp
	fun setActivityAddress(address: String) {
		tvAddress.text = address
	}

}