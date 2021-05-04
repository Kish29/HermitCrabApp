package com.kish2.hermitcrabapp.model

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Environment
import androidx.core.content.ContextCompat
import com.kish2.hermitcrabapp.RealApplication
import com.kish2.hermitcrabapp.intfc.LocalStorageMangerApi
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel
import java.util.concurrent.ConcurrentHashMap

class LocalStorageImpl : LocalStorageMangerApi {

	companion object {
		private const val IMAGE_DIR_NAME = "images"
		private const val VIDEO_DIR_NAME = "videos"
	}

	private val mAppDir =
		ContextCompat.getExternalFilesDirs(RealApplication.getInstance().applicationContext, Environment.DIRECTORY_PICTURES)[0].toString()
	private val mAppCacheDir =
		ContextCompat.getExternalCacheDirs(RealApplication.getInstance().applicationContext)[0].toString()

	private val mSpGroup: ConcurrentHashMap<String, SharedPreferences> = ConcurrentHashMap()

	override fun saveBoolean(spName: String, key: String, value: Boolean) {
		checkSpExist(spName)
		mSpGroup[spName]?.edit()?.apply {
			putBoolean(key, value)
			apply()
		}
	}

	override fun getBoolean(spName: String, key: String): Boolean {
		checkSpExist(spName)
		return mSpGroup[spName]?.getBoolean(key, false) ?: false
	}

	override fun saveInt(spName: String, key: String, value: Int) {
		checkSpExist(spName)
		mSpGroup[spName]?.edit()?.apply {
			putInt(key, value)
			apply()
		}
	}

	override fun getInt(spName: String, key: String): Int {
		checkSpExist(spName)
		return mSpGroup[spName]?.getInt(key, 0) ?: 0
	}

	override fun saveString(spName: String, key: String, value: String) {
		checkSpExist(spName)
		mSpGroup[spName]?.edit()?.apply {
			putString(key, value)
			apply()
		}
	}

	override fun getString(spName: String, key: String): String? {
		checkSpExist(spName)
		return mSpGroup[spName]?.getString(key, null)
	}

	override fun getCacheDir(): String {
		return mAppCacheDir
	}

	override fun getImgDir(): String {
		val imgDir = "$mAppDir${File.separator}$IMAGE_DIR_NAME"
		try {
			val file = File(imgDir)
			if (!file.exists()) file.mkdirs()
		} catch (e: IOException) {
			e.printStackTrace()
		}
		return imgDir
	}

	override fun getVideoDir(): String {
		val videoDir = "$mAppDir${File.separator}$VIDEO_DIR_NAME"
		try {
			val file = File(videoDir)
			if (!file.exists()) file.mkdirs()
		} catch (e: IOException) {
			e.printStackTrace()
		}
		return videoDir
	}

	override fun saveImage(bitmap: Bitmap, name: String, dir: String, onComplete: ((Boolean) -> Unit)?) {
		try {
			val f = File("$dir${File.separator}$name")
			if (!f.exists()) f.createNewFile()
			val fos = FileOutputStream(f)
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
			fos.flush()
			fos.close()
			onComplete?.invoke(true)
		} catch (e: IOException) {
			e.printStackTrace()
			onComplete?.invoke(false)
		}
	}

	override fun copyFile(srcPath: String, dstPath: String, onComplete: ((Boolean) -> Unit)?) {
		try {
			val cSrc: FileChannel = FileInputStream(File(srcPath)).channel
			val cDes: FileChannel = FileOutputStream(File(dstPath)).channel
			cDes.transferFrom(cSrc, 0, cSrc.size())
			cSrc.close()
			cDes.close()
			onComplete?.invoke(true)
		} catch (e: IOException) {
			e.printStackTrace()
			onComplete?.invoke(false)
		}
	}

	override fun createFileIfNull(name: String, dir: String, onComplete: ((File?) -> Unit)?) {
		try {
			val f = File("$dir${File.separator}$name")
			if (!f.exists()) f.createNewFile()
			onComplete?.invoke(f)
		} catch (e: IOException) {
			e.printStackTrace()
			onComplete?.invoke(null)
		}
	}

	private fun checkSpExist(spName: String) {
		if (mSpGroup[spName] == null) {
			mSpGroup[spName] = RealApplication.getInstance()
				.applicationContext.getSharedPreferences(spName, Context.MODE_PRIVATE)
		}
	}
}