package com.kish2.hermitcrabapp.ui

import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.kish2.hermitcrabapp.R
import com.kish2.hermitcrabapp.intfc.TabInfoApi
import com.kish2.hermitcrabapp.model.AsyncTypedEpoxyController
import com.kish2.hermitcrabapp.state.GoodsState
import com.kish2.hermitcrabapp.state.LoadStatus
import com.kish2.hermitcrabapp.view.goodsItemView
import com.kish2.hermitcrabapp.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.hermitcrab_fragment_base.*

class GoodsListFragment : BaseFragment(R.layout.hermitcrab_fragment_base), MavericksView, TabInfoApi {

	private val mGoodsViewModel: GoodsViewModel by fragmentViewModel()

	private val mGoodsListController = GoodsListController()

	override fun invalidate() {
		withState(mGoodsViewModel) {
			mGoodsListController.setData(it)
		}
	}

	override fun getTabTitle(): String {
		return "易贝壳"
	}

	override fun getTabFragment(): Fragment {
		return this
	}

	override fun initView() {
		super.initView()
		recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
		recyclerView.setController(mGoodsListController)
	}

	override fun onStart() {
		super.onStart()
		mGoodsViewModel.fetchGoodsList()
	}

	inner class GoodsListController : AsyncTypedEpoxyController<GoodsState>() {
		override fun buildLoadingView(s: GoodsState) {
		}

		override fun buildSuccessView(s: GoodsState) {
			s.goodsList.forEach {
				goodsItemView {
					id("${it.goodsName}_${it.price}")
					goodsImage(it.img)
					goodsName(it.goodsName)
					tag(it.tag)
					goodsPrice(it.price.toString())
					goodsDesire(it.desireDesc)
					pubPeopleInfo(it.pubPeopleInfo)
				}
			}
		}

		override fun buildFailedView(s: GoodsState) {
		}

		override fun buildEmptyView(s: GoodsState) {
		}

		override fun buildModels(data: GoodsState?) {
			data?.let {
				when (it.status) {
					LoadStatus.Success -> {
						buildSuccessView(it)
					}
				}
			}
		}

	}
}