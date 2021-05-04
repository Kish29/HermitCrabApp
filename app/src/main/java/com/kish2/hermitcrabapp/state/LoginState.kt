package com.kish2.hermitcrabapp.state

import com.airbnb.mvrx.MavericksState

data class LoginState(
	val status: LoadStatus = LoadStatus.Success,
	val loginByMobile: Boolean = true
) : MavericksState