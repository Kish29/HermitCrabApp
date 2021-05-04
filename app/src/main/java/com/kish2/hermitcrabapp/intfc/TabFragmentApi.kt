package com.kish2.hermitcrabapp.intfc

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment

interface TabFragmentApi {

	fun getTabView(layoutInflater: LayoutInflater): View

	fun getTabFragment(): Fragment
}