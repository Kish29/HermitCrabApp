package com.kish2.hermitcrabapp.viewmodel

import com.airbnb.mvrx.MavericksViewModel
import com.kish2.hermitcrabapp.state.LoginState

class LoginViewModel(initialState: LoginState) : MavericksViewModel<LoginState>(initialState) {

	fun renderView() {
		onEach(LoginState::loginByMobile) {

		}
	}

	fun changeLoginType(byMobile: Boolean) {
		setState {
			copy(
				loginByMobile = byMobile
			)
		}
	}

}