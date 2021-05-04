package com.kish2.hermitcrabapp.ui

import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.viewModel
import com.kish2.hermitcrabapp.R
import com.kish2.hermitcrabapp.model.AsyncTypedEpoxyController
import com.kish2.hermitcrabapp.state.LoadStatus
import com.kish2.hermitcrabapp.state.LoginState
import com.kish2.hermitcrabapp.utils.ImmerseStatusBar
import com.kish2.hermitcrabapp.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.hermitcrab_activity_login.*

class LoginActivity : BaseActivity(), MavericksView {

	private val mLoginViewModel: LoginViewModel by viewModel()

	private val mController: LoginController = LoginController()

	override fun initView() {
		super.initView()
		setContentView(R.layout.hermitcrab_activity_login)
		ImmerseStatusBar.setTranslucentStatus(this)
		ImmerseStatusBar.adjustForStatusBarP(this, llLoginContainer)
	}

	override fun initAction() {
		super.initAction()
		mLoginViewModel.changeLoginType(true)
	}

	override fun invalidate() {
		mController.requestModelBuild()
	}

	inner class LoginController : AsyncTypedEpoxyController<LoginState>() {

		override fun buildModels(p0: LoginState?) {
			p0?.let {
				when (it.status) {
					LoadStatus.Uninitialized,
					LoadStatus.Loading -> {
						buildLoadingView(it)
					}
					LoadStatus.Failed -> {
						buildFailedView(it)
					}
					LoadStatus.Empty -> {
						buildEmptyView(it)
					}
					LoadStatus.Success -> {
						buildSuccessView(it)
					}
				}
			}
		}

		override fun buildLoadingView(s: LoginState) {
		}

		override fun buildSuccessView(s: LoginState) {
		}

		override fun buildFailedView(s: LoginState) {
		}

		override fun buildEmptyView(s: LoginState) {
		}

	}

}