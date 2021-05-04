package com.kish2.hermitcrabapp.state

import com.airbnb.mvrx.MavericksState
import com.kish2.hermitcrabapp.data.CampusInform

data class HomeState(
	val status: LoadStatus = LoadStatus.Uninitialized,
	val informList: List<CampusInform> = emptyList(),
	/*是否有自己的通知*/
	val hasMessage: Boolean = false
) : MavericksState