package com.kish2.hermitcrabapp.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.appbar.AppBarLayout
import com.kish2.hermitcrabapp.R
import com.kish2.hermitcrabapp.intfc.AppVectorImg
import com.kish2.hermitcrabapp.intfc.TabFragmentApi
import com.kish2.hermitcrabapp.intfc.TabInfoApi
import com.kish2.hermitcrabapp.intfc.ThemeManager
import com.kish2.hermitcrabapp.utils.ImmerseStatusBar
import kotlinx.android.synthetic.main.hermitcrab_fragment_community.*
import kotlinx.android.synthetic.main.hermitcrab_top_bar_community_view.*

class CommunityFragment : BaseFragment(R.layout.hermitcrab_fragment_community), TabFragmentApi {

	private val tabList: List<TabInfoApi> = listOf(
		GoodsListFragment(),
		TopicFragment()
	)

	override fun getTabView(layoutInflater: LayoutInflater): View {
		val tabView = layoutInflater.inflate(R.layout.hermitcrab_tab_item_view, null, false)
		tabView.findViewById<ImageView>(R.id.ivTabIcon).setImageDrawable(
			AppVectorImg.viCommunityIconDft?.let { dft ->
				AppVectorImg.viCommunityIconAcv?.let { acv ->
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
			text = R.string.hermitcrab_nav_forum_title.getString()
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
		ablTopBarContainer.setBackgroundColor(ThemeManager.getThemeColor())
		vpCommunity.adapter = CommunityFragmentAdapter(childFragmentManager)
		tlCategoryTab.indicatorColor = Color.WHITE
		tlCategoryTab.textSelectColor = Color.WHITE
		tlCategoryTab.isTabSpaceEqual = true
	}

	override fun initAction() {
		super.initAction()
		tlCategoryTab.setViewPager(vpCommunity)
		ablTopBarContainer.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
			val offset = ctlTop.height + verticalOffset
			val ratio = offset.toFloat() / ctlTop.height
			ctlTop.alpha = ratio
		})
	}

	inner class CommunityFragmentAdapter(fragmentManager: FragmentManager) :
		FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

		override fun getItem(position: Int): Fragment {
			return tabList[position].getTabFragment()
		}

		override fun getCount(): Int {
			return tabList.size
		}

		override fun getPageTitle(position: Int): CharSequence? {
			return tabList[position].getTabTitle()
		}

	}

}