package com.kish2.hermitcrabapp.model

import android.os.Handler
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.TypedEpoxyController

abstract class AsyncTypedEpoxyController<S> : TypedEpoxyController<S> {

	constructor() : this(true)

	constructor(async: Boolean) : this(async, async)

	constructor(
		asyncModelBuilding: Boolean,
		asyncDiffing: Boolean
	) : super(
		modelBuildingHandler(asyncModelBuilding),
		diffingHandler(asyncDiffing)
	)

	abstract fun buildLoadingView(s: S)

	abstract fun buildSuccessView(s: S)

	abstract fun buildFailedView(s: S)

	abstract fun buildEmptyView(s: S)
}

internal fun modelBuildingHandler(async: Boolean): Handler {
	return if (async) {
		EpoxyAsyncUtil.AYSNC_MAIN_THREAD_HANDLER
	} else EpoxyAsyncUtil.MAIN_THREAD_HANDLER
}

internal fun diffingHandler(async: Boolean): Handler {
	return if (async) {
		EpoxyAsyncUtil.getAsyncBackgroundHandler()
	} else EpoxyAsyncUtil.MAIN_THREAD_HANDLER
}