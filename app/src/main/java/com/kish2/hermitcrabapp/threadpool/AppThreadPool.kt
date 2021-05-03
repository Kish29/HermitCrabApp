package com.kish2.hermitcrabapp.threadpool

import java.util.concurrent.Executors
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

    private const val LIVE_TIME = 10L

    private const val WEIGHER = 20

    internal val APP_THREAD_POOL by lazy {
        ThreadPoolExecutor(
                CPU_NUM,
                CPU_NUM * WEIGHER,
                LIVE_TIME,
                TimeUnit.SECONDS,
                // no faire
                SynchronousQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                ThreadPoolExecutor.CallerRunsPolicy()
        )
    }

}

fun childThread(action: ()->Unit) {
	AppThreadPool.APP_THREAD_POOL.execute(action)
}

