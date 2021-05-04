package com.kish2.hermitcrabapp.state

import com.airbnb.mvrx.MavericksState
import com.kish2.hermitcrabapp.data.TradeGoods

data class GoodsState(
	val status: LoadStatus = LoadStatus.Uninitialized,
	val goodsList: List<TradeGoods> = emptyList()
) : MavericksState