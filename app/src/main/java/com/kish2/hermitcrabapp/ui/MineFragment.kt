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

class MineFragment : BaseFragment(R.layout.hermitcrab_fragment_mine), MavericksView, TabFragmentApi {

	override fun getTabView(layoutInflater: LayoutInflater): View {
		val tabView = layoutInflater.inflate(R.layout.hermitcrab_tab_item_view, null, false)
		tabView.findViewById<ImageView>(R.id.ivTabIcon).setImageDrawable(
			AppVectorImg.viMineIconDft?.let { dft ->
				AppVectorImg.viMineIconAcv?.let { acv ->
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
			text = R.string.hermitcrab_nav_personal_title.getString()
		}
		return tabView
	}

	override fun getTabFragment(): Fragment {
		return this
	}

	override fun invalidate() {
	}

}