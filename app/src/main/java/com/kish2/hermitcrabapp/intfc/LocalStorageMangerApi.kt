package com.kish2.hermitcrabapp.intfc

import android.graphics.Bitmap
import com.kish2.hermitcrabapp.model.LocalStorageImpl
import java.io.File

const val defaultSpName = "default_config"

interface LocalStorageMangerApi {

	fun saveBoolean(spName: String = defaultSpName, key: String, value: Boolean)

	fun getBoolean(spName: String = defaultSpName, key: String): Boolean

	fun saveInt(spName: String = defaultSpName, key: String, value: Int)

	fun getInt(spName: String = defaultSpName, key: String): Int

	fun saveString(spName: String = defaultSpName, key: String, value: String)

	fun getString(spName: String = defaultSpName, key: String): String?

	fun getCacheDir(): String

	fun getImgDir(): String

	fun getVideoDir(): String

	fun saveImage(bitmap: Bitmap, name: String, dir: String = getImgDir(), onComplete: ((Boolean) -> Unit)?)

	fun copyFile(srcPath: String, dstPath: String, onComplete: ((Boolean) -> Unit)?)

	fun createFileIfNull(name: String, dir: String =getCacheDir(), onComplete: ((File?)-> Unit)?)
}

val LocalStorageManger: LocalStorageMangerApi = LocalStorageImpl()