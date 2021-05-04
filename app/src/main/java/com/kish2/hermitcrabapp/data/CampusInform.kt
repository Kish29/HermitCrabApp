package com.kish2.hermitcrabapp.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CampusInform(
	@SerializedName("image_url")
	val img: String,
	@SerializedName("activity_date")
	val activityDate: String,
	@SerializedName("inform_title")
	val title: String,
	@SerializedName("activity_address")
	val address: String,
	val weekDay: String = date2WeekDay(activityDate)
) : Serializable {
	companion object {
		private const val serialVersionId = 10086L
	}
}

fun date2WeekDay(date: String): String {
	return "星期四"
}
