package com.kish2.hermitcrabapp.ui

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.kish2.hermitcrabapp.R
import com.kish2.hermitcrabapp.intfc.TabFragmentApi
import com.kish2.hermitcrabapp.intfc.TabManager
import com.kish2.hermitcrabapp.intfc.TabManagerApi
import com.kish2.hermitcrabapp.intfc.UIInteraction
import com.kish2.hermitcrabapp.intfc.UIInteractionApi
import kotlinx.android.synthetic.main.hermitcrab_basic_component.*

/**
 * @author: jiang ao ran
 * @description: 基础activity，控制5个tab页
 * @date: 2021/3/21 2:49 上午
 * @email: 875691208@qq.com
 */
class TabManageActivity : BaseActivity(), TabManagerApi, UIInteractionApi {

	companion object {
		private const val ANIM_DURATION = 300L
		private var mTabs: MutableList<TabFragmentApi> = mutableListOf()
		fun initialize(tabs: List<TabFragmentApi>) {
			mTabs = tabs.toMutableList()
		}
	}

	init {
		TabManager = this
		UIInteraction = this
	}

	private var mTabHeight: Int = 0

	override fun initView() {
		super.initView()
		setContentView(R.layout.hermitcrab_basic_component)
		vp2Contents.adapter = TabAdapter(this)
		clBottomNavContainer.alpha = 0.8f
		mTabs.forEach {
			tlBottomNav.addTab(
				tlBottomNav.newTab().apply { customView = it.getTabView(layoutInflater) }
			)
		}
	}

	override fun initAction() {
		super.initAction()
		/*禁止滑动*/
		vp2Contents.isUserInputEnabled = false
		dlTabManager.viewTreeObserver.addOnGlobalLayoutListener {
			mTabHeight = tlBottomNav.height
		}
		tlBottomNav.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
			override fun onTabReselected(tab: TabLayout.Tab?) {}

			override fun onTabUnselected(tab: TabLayout.Tab?) {}

			override fun onTabSelected(tab: TabLayout.Tab?) {
				vp2Contents.setCurrentItem(tab?.position ?: 0, false)
			}

		})
		dlTabManager.addDrawerListener(object : DrawerLayout.DrawerListener {
			override fun onDrawerStateChanged(newState: Int) {}

			override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
				clMainContent.translationX = clLeftSideMenu.width * slideOffset
			}

			override fun onDrawerClosed(drawerView: View) {}

			override fun onDrawerOpened(drawerView: View) {}

		})
	}

	override fun selectTab(pos: Int) {
		val safePos = pos.coerceIn(0, mTabs.size - 1)
		vp2Contents.setCurrentItem(safePos, false)
	}

	override fun addTabInfo(tabInfo: TabFragmentApi) {
		mTabs.add(tabInfo)
	}

	override fun removeTabInfo(tabInfo: TabFragmentApi) {
		mTabs.remove(tabInfo)
	}

	override fun wrapBottomNav() {
		if (clBottomNavContainer.translationY > 0) return
		clBottomNavContainer.animate()
			.yBy(mTabHeight.toFloat())
			.setDuration(ANIM_DURATION)
			.start()
	}

	override fun showBottomNav() {
		if (clBottomNavContainer.translationY <= 0) return
		clBottomNavContainer.animate()
			.yBy(0f)
			.setDuration(ANIM_DURATION)
			.start()
	}

	inner class TabAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
		override fun getItemCount(): Int = mTabs.size

		override fun createFragment(position: Int): Fragment = mTabs[position].getTabFragment()

	}
}