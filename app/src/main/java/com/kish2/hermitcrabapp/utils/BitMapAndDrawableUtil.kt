package com.kish2.hermitcrabapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.util.TypedValue
import android.view.View
import android.widget.SeekBar
import androidx.annotation.ColorInt
import cn.hutool.core.lang.UUID
import com.kish2.hermitcrabapp.R
import com.kish2.hermitcrabapp.RealApplication.Companion.getInstance
import com.kish2.hermitcrabapp.intfc.LocalStorageManger
import com.kish2.hermitcrabapp.intfc.ThemeManager
import com.nanchen.compresshelper.CompressHelper
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object BitMapAndDrawableUtil {

	private const val K_1 = 1024

	fun dip2px(context: Context, dp: Float): Int {
		val scale = context.resources.displayMetrics.density
		return (dp * scale + 0.5f).toInt()
	}

	fun px2dip(context: Context, px: Float): Int {
		val scale = context.resources.displayMetrics.density
		return (px / scale + 0.5f).toInt()
	}

	fun sp2px(context: Context, sp: Float): Int {
		val scale = context.resources.displayMetrics.scaledDensity
		return (sp * scale + 0.5f).toInt()
	}

	fun dip2px(context: Context, dp: Int): Int {
		return TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
			context.resources.displayMetrics
		).toInt()
	}

	fun px2sp(context: Context, px: Float): Int {
		val scale = context.resources.displayMetrics.scaledDensity
		return (px / scale + 0.5f).toInt()
	}

	fun getBitmapFromVector(context: Context, vectorDrawableId: Int): Bitmap? {
		var bitmap: Bitmap? = null
		@SuppressLint("UseCompatLoadingForDrawables") val vectorDrawable = context.getDrawable(vectorDrawableId)!!
		bitmap = Bitmap.createBitmap(
			vectorDrawable.intrinsicWidth,
			vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
		)
		val canvas = Canvas(bitmap)
		vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
		vectorDrawable.draw(canvas)
		return bitmap
	}

	fun getGradientCircleDrawable(context: Context, radiusDip: Int): Drawable {
		val gradientDrawable = GradientDrawable()
		gradientDrawable.shape = GradientDrawable.RECTANGLE //形状
		if (radiusDip == -1) gradientDrawable.cornerRadius = context.resources.getDimension(R.dimen.hermitcrab_corner_radius) //设置圆角Radius
		else gradientDrawable.cornerRadius = dip2px(context, radiusDip).toFloat() //设置圆角Radius
		gradientDrawable.setColor(ThemeManager.getThemeColor()) //颜色
		return gradientDrawable
	}

	@JvmStatic
	fun getGradientCircleDrawable(context: Context): Drawable {
		return getGradientCircleDrawable(context, -1)
	}

	fun centerCropDrawableToFitView(context: Context, drawable: Drawable, fitView: View): Drawable {
		val bitmap = (drawable as BitmapDrawable).bitmap
		return BitmapDrawable(
			context.resources,
			centerCropBitmapToFitView(bitmap, fitView)
		)
	}

	@JvmStatic
	fun centerCropBitmapToFitView(bitmap: Bitmap, fitView: View): Bitmap {
		var dWidth = bitmap.width
		var dHeight = bitmap.height
		val vWidth = fitView.width
		val vHeight = fitView.height
		if (dWidth * vHeight < vWidth * dHeight) {
			val ratio = (dWidth * vHeight).toFloat() / (vWidth * dHeight)
			dHeight *= ratio.toInt()
		} else {
			val ratio = (vWidth * dHeight).toFloat() / (dWidth * vHeight)
			dWidth *= ratio.toInt()
		}
		/* x、y是从源bitmap开始截取的坐标点 */return Bitmap.createBitmap(
			bitmap,
			(bitmap.width - dWidth) / 2,
			(bitmap.height - dHeight) / 2,
			dWidth,
			dHeight
		)
	}

	/**
	 * 创建背景颜色
	 *
	 * @param color       填充色
	 * @param strokeColor 线条颜色
	 * @param strokeWidth 线条宽度  单位px
	 * @param radius      角度  px
	 */
	@JvmStatic
	fun getRoundRectangleDrawable(
		@ColorInt color: Int,
		@ColorInt strokeColor: Int,
		strokeWidth: Int,
		radius: Float
	): Drawable {
		return try {
			val radiusBg = GradientDrawable()
			//设置Shape类型
			radiusBg.shape = GradientDrawable.RECTANGLE
			//设置填充颜色
			radiusBg.setColor(color)
			if (strokeColor != -1 && strokeWidth != -1) //设置线条粗细和颜色,px
				radiusBg.setStroke(strokeWidth, strokeColor)
			//设置圆角角度,如果每个角度都一样,则使用此方法
			radiusBg.cornerRadius = radius
			radiusBg
		} catch (e: Exception) {
			GradientDrawable()
		}
	}

	/**
	 * 创建背景颜色
	 *
	 * @param color       填充色
	 * @param strokeColor 线条颜色
	 * @param strokeWidth 线条宽度  单位px
	 * @param radius      角度  px,长度为4,分别表示左上,右上,右下,左下的角度
	 */
	fun createRectangleDrawable(
		@ColorInt color: Int,
		@ColorInt strokeColor: Int,
		strokeWidth: Int,
		radius: FloatArray?
	): GradientDrawable {
		return try {
			val radiusBg = GradientDrawable()
			//设置Shape类型
			radiusBg.shape = GradientDrawable.RECTANGLE
			//设置填充颜色
			radiusBg.setColor(color)
			//设置线条粗心和颜色,px
			radiusBg.setStroke(strokeWidth, strokeColor)
			//每连续的两个数值表示是一个角度,四组:左上,右上,右下,左下
			if (radius != null && radius.size == 4) {
				radiusBg.cornerRadii = floatArrayOf(
					radius[0],
					radius[0],
					radius[1],
					radius[1],
					radius[2],
					radius[2],
					radius[3],
					radius[3]
				)
			}
			radiusBg
		} catch (e: Exception) {
			GradientDrawable()
		}
	}

	@JvmStatic
	fun setSeekBarColor(seekBar: SeekBar, colorId: Int) {
		val drawable = seekBar.progressDrawable as LayerDrawable
		/* 0 背景， 1 二级进度条，2 当前进度条*/
		val drawable1 = drawable.getDrawable(2)
		drawable1.setColorFilter(colorId, PorterDuff.Mode.SRC)
		val thumb = seekBar.thumb
		thumb.setColorFilter(colorId, PorterDuff.Mode.SRC_ATOP)
		seekBar.invalidate()
	}

	/**
	 * @param file 待压缩的图片文件
	 * @param size 指定压缩之后的大小范围，单位：KB
	 */
	private fun compressImageToSize(file: File, size: Int): Bitmap {
		if (file.length() <= size * K_1) return BitmapFactory.decodeFile(file.path)
		/* 默认保留50%的质量 */
		val compressHelper = CompressHelper.Builder(getInstance().applicationContext)
			.setCompressFormat(Bitmap.CompressFormat.JPEG)
			.setQuality(50)
			.build()
		val bitmap = compressHelper.compressToBitmap(file)
		val buff = ByteArrayOutputStream()
		var quality = 100
		bitmap.compress(Bitmap.CompressFormat.JPEG, quality, buff)
		while (buff.size() / K_1 > size && quality - 10 >= 0) {
			buff.reset()
			quality -= 10 // 每次都减少5
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, buff)
		}
		try {
			val fos = FileOutputStream(file)
			buff.writeTo(fos)
			buff.close()
			fos.flush()
			fos.close()
		} catch (e: IOException) {
			e.printStackTrace()
		}
		return BitmapFactory.decodeFile(file.path)
	}

	/**
	 * @param bitmap 待压缩的图片文件
	 * @param size   指定压缩之后的大小范围
	 */
	fun compressImageToSize(bitmap: Bitmap, size: Int): Bitmap? {
		val cache = "${UUID.randomUUID()}"
		var res: Bitmap? = bitmap
		LocalStorageManger.createFileIfNull(cache) {
			if (it != null) {
				try {
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(it))
					/* 如果图片大小小于等于size，返回原图 */
					if (it.length() >= size * K_1)
						res = compressImageToSize(it, size)
				} catch (e: IOException) {
					res = null
				}
			} else {
				res = null
			}
		}
		return res
	}

	/**
	 * @param uri  待压缩的图片文件uri
	 * @param size 指定压缩之后的大小范围，单位：KB
	 */
	fun compressImageToSize(uri: Uri, size: Int): Bitmap {
		val file = File(uri.path)
		return if (file.length() <= size) BitmapFactory.decodeFile(file.path) else compressImageToSize(
			file,
			size
		)
	}
}