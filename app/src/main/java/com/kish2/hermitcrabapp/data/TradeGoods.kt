package com.kish2.hermitcrabapp.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TradeGoods(
	@SerializedName("goods_image")
	val img: String,
	@SerializedName("goods_tag")
	val tag: String,
	@SerializedName("goods_name")
	val goodsName: String,
	@SerializedName("goods_price")
	val price: Float,
	@SerializedName("third_person_info")
	val pubPeopleInfo: ThirdPersonInfo,
	@SerializedName("goods_desire_desc")
	val desireDesc: String
) : Serializable {
	companion object {
		private const val serialVersionId = 10087L
	}
}

data class ThirdPersonInfo(
	@SerializedName("pub_people_name")
	val name: String,
	@SerializedName("pub_people_avatar")
	val avatar: String
) : Serializable {
	companion object {
		private const val serialVersionId = 10088L
	}
}