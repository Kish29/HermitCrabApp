package com.kish2.hermitcrabapp

import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @author: jiang ao ran
 * @description: app线程池
 * @date: 3/21/21 1:15 AM
 */
object AppThreadPool {

	private val CPU_NUM = Runtime.getRuntime().availableProcessors()

	private const val LIVE_TIME = 15L

	private const val WEIGHER = 2

	private val tpIO by lazy {
		ThreadPoolExecutor(
			CPU_NUM,
			CPU_NUM * WEIGHER,
			LIVE_TIME,
			TimeUnit.SECONDS,
			// no fair
			SynchronousQueue<Runnable>(),
			Executors.defaultThreadFactory(),
			ThreadPoolExecutor.DiscardOldestPolicy()
		)
	}

	private val tpCommon by lazy {
		ThreadPoolExecutor(
			CPU_NUM,
			CPU_NUM,
			LIVE_TIME,
			TimeUnit.SECONDS,
			SynchronousQueue<Runnable>(),
			Executors.defaultThreadFactory(),
			ThreadPoolExecutor.DiscardOldestPolicy()
		)
	}

	private val tpSingle by lazy {
		ThreadPoolExecutor(
			1,
			1,
			LIVE_TIME,
			TimeUnit.SECONDS,
			LinkedBlockingDeque<Runnable>(),
			Executors.defaultThreadFactory(),
			ThreadPoolExecutor.DiscardOldestPolicy()
		)
	}

	fun io(action: () -> Unit) {
		tpIO.execute(action)
	}

	fun common(action: () -> Unit) {
		tpCommon.execute(action)
	}

	fun serial(action: () -> Unit) {
		tpSingle.execute(action)
	}
}

