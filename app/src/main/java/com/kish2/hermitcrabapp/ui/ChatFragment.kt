package com.kish2.hermitcrabapp.ui

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.kish2.hermitcrabapp.R
import com.kish2.hermitcrabapp.intfc.AppVectorImg
import com.kish2.hermitcrabapp.intfc.TabFragmentApi
import com.kish2.hermitcrabapp.intfc.ThemeManager
import com.kish2.hermitcrabapp.utils.ImmerseStatusBar
import kotlinx.android.synthetic.main.hermitcrab_fragment_community.*

class ChatFragment : BaseFragment(R.layout.hermitcrab_fragment_chat), MavericksView, TabFragmentApi {

	override fun getTabView(layoutInflater: LayoutInflater): View {
		val tabView = layoutInflater.inflate(R.layout.hermitcrab_tab_item_view, null, false)
		tabView.findViewById<ImageView>(R.id.ivTabIcon).setImageDrawable(
			AppVectorImg.viChatIconDft?.let { dft ->
				AppVectorImg.viChatIconAcv?.let { acv ->
					iconSelector(dft, acv)
				}
			}
		)
		tabView.findViewById<TextView>(R.id.tvNavName).apply {
			setTextColor(
				textColorSelector(
					R.color.hermitcrab_text_default_color.getColor(),
					ThemeManager.getThemeColor()
				)
			)
			text = R.string.hermitcrab_nav_chat_title.getString()
		}
		return tabView
	}

	override fun getTabFragment(): Fragment {
		return this
	}

	override fun initView() {
		super.initView()
		ImmerseStatusBar.setSinkStatusBar(requireActivity(), false)
		ImmerseStatusBar.adjustForStatusBarP(requireActivity(), ablTopBarContainer)
	}

	override fun invalidate() {
	}
}