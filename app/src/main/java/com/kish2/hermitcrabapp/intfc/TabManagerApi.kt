package com.kish2.hermitcrabapp.intfc

interface TabManagerApi {

	fun selectTab(pos: Int)

	fun addTabInfo(tabInfo: TabFragmentApi)

	fun removeTabInfo(tabInfo: TabFragmentApi)

}
var TabManager: TabManagerApi? = null

