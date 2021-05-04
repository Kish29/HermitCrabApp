package com.kish2.hermitcrabapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.appbar.AppBarLayout
import com.kish2.hermitcrabapp.R
import com.kish2.hermitcrabapp.intfc.AppVectorImg
import com.kish2.hermitcrabapp.intfc.TabFragmentApi
import com.kish2.hermitcrabapp.intfc.ThemeManager
import com.kish2.hermitcrabapp.model.AsyncTypedEpoxyController
import com.kish2.hermitcrabapp.state.HomeState
import com.kish2.hermitcrabapp.state.LoadStatus
import com.kish2.hermitcrabapp.utils.ImmerseStatusBar
import com.kish2.hermitcrabapp.view.informItemView
import com.kish2.hermitcrabapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.hermitcrab_fragment_home.*

class HomeFragment : BaseFragment(R.layout.hermitcrab_fragment_home), MavericksView, TabFragmentApi {

	companion object {
		private const val TAG = "HomeFragment "
	}

	private val mHomeViewModel: HomeViewModel by fragmentViewModel()

	private val mHomeController = HomeController()

	private var mLastTouchY = 0f

	override fun getTabView(layoutInflater: LayoutInflater): View {
		val tabView = layoutInflater.inflate(R.layout.hermitcrab_tab_item_view, null, false)
		tabView.findViewById<ImageView>(R.id.ivTabIcon).setImageDrawable(
			AppVectorImg.viHomeTabIconDft?.let { dft ->
				AppVectorImg.viHomeTabIconAcv?.let { acv ->
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
			setText(R.string.hermitcrab_nav_home_title)
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
		ervInformList.setController(mHomeController)
	}

	@SuppressLint("ClickableViewAccessibility")
	override fun initAction() {
		super.initAction()
		ablTopBarContainer.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
			val offset = ctlTop.height + verticalOffset
			val ratio = offset.toFloat() / ctlTop.height
			ctlTop.alpha = ratio
		})
	}

	override fun thisController(): EpoxyController? {
		return HomeController()
	}

	override fun invalidate() {
		withState(mHomeViewModel) {
			mHomeController.setData(it)
		}
	}

	override fun onStart() {
		super.onStart()
		mHomeViewModel.fetchCampusMessageList()
	}

	inner class HomeController : AsyncTypedEpoxyController<HomeState>() {


		override fun buildModels(state: HomeState?) {
			state?.let {
				when (it.status) {
					LoadStatus.Uninitialized,
					LoadStatus.Loading -> {
						buildLoadingView(it)
					}
					LoadStatus.Success -> {
						buildSuccessView(it)
					}
					LoadStatus.Failed -> {
						buildSuccessView(it)
					}
					LoadStatus.Empty -> {
						buildEmptyView(it)
					}
				}
			}
		}

		override fun buildLoadingView(s: HomeState) {}

		override fun buildSuccessView(s: HomeState) {
			s.informList.forEach {
				informItemView {
					id("${it.title}_${it.activityDate}")
					image(it.img)
					weekDay(it.weekDay)
					pubDate(it.activityDate)
					activityAddress(it.address)
					informTitle(it.title)
				}
			}
		}

		override fun buildFailedView(s: HomeState) {
		}

		override fun buildEmptyView(s: HomeState) {
		}
	}

}