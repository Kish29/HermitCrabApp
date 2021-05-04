package com.kish2.hermitcrabapp.intfc

import androidx.fragment.app.Fragment

interface TabInfoApi {

	fun getTabTitle(): String

	fun getTabFragment(): Fragment
}