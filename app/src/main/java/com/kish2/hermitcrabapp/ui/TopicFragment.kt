package com.kish2.hermitcrabapp.ui

import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.kish2.hermitcrabapp.R
import com.kish2.hermitcrabapp.intfc.TabInfoApi

class TopicFragment : BaseFragment(R.layout.hermitcrab_fragment_base), MavericksView, TabInfoApi {
	override fun invalidate() {
	}

	override fun getTabTitle(): String {
		return "话题"
	}

	override fun getTabFragment(): Fragment {
		return this
	}
}