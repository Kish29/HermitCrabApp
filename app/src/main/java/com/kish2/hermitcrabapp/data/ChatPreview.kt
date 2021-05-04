package com.kish2.hermitcrabapp.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChatPreview(
	@SerializedName("third_person_info")
	val chatPartner: ThirdPersonInfo,
	@SerializedName("partener_nick_name")
	val nickName: String? = null,
	@SerializedName("last_msg_time")
	val lastMsgTime: String,
	@SerializedName("latest_message")
	val latestMsg: String
) : Serializable {
	companion object {
		private const val serialVersionId = 10089L
	}
}