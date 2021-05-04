package com.kish2.hermitcrabapp.view

import android.content.Context
import android.util.AttributeSet
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.google.android.material.card.MaterialCardView
import com.kish2.hermitcrabapp.data.ThirdPersonInfo
import com.kish2.hermitcrabapp.ui.loadImageFromNet
import com.kish2.hermitcrabapp.ui.thresholdClick
import kotlinx.android.synthetic.main.hermitcrab_goods_item_view.view.*

@ModelView
class GoodsItemView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

	@ModelProp
	fun setGoodsImage(url: String?) {
		url?.let {
			iv_product_img.loadImageFromNet(it)
		}
	}

	@ModelProp
	fun setGoodsName(name: String) {
		tv_product_title.text = name
	}

	@ModelProp
	fun setGoodsPrice(price: String) {
		tv_product_price.text = price
	}

	@ModelProp
	fun setGoodsDesire(desc: String) {
		tv_desire_people.text = desc
	}

	@set:ModelProp
	var pubPeopleInfo: ThirdPersonInfo? = null

	@AfterPropsSet
	fun after() {
		pubPeopleInfo?.let {
			riv_vender_avatar.loadImageFromNet(it.avatar)
			tv_vender_name.text = it.name
		} ?: let {
			riv_vender_avatar.setImageDrawable(null)
			tv_vender_name.text = ""
		}
	}

	@ModelProp
	fun setTag(tag: String) {
		tv_product_tag.text = tag
	}

	@CallbackProp
	fun onGoodsClick(listener: OnClickListener?) {
		thresholdClick { listener?.onClick(it) }
	}

}