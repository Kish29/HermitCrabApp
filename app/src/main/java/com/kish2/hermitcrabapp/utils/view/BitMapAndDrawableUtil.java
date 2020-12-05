package com.kish2.hermitcrabapp.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.kish2.hermitcrabapp.HermitCrabApp;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.utils.dev.FileStorageManager;
import com.nanchen.compresshelper.CompressHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitMapAndDrawableUtil {

    public static Bitmap getBitmapFromVector(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        @SuppressLint("UseCompatLoadingForDrawables") Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
        assert vectorDrawable != null;
        bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    public static GradientDrawable getGradientCircleDrawable(Context context) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);//形状
        gradientDrawable.setCornerRadius(context.getResources().getDimension(R.dimen.corner_radius));//设置圆角Radius
        gradientDrawable.setColor(ThemeUtil.Theme.afterGetResourcesColorId);//颜色
        return gradientDrawable;
    }


    public static Drawable centerCropDrawableToFitView(Context context, Drawable drawable, View fitView) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        return new BitmapDrawable(context.getResources(), centerCropBitmapToFitView(bitmap, fitView));
    }

    public static Bitmap centerCropBitmapToFitView(Bitmap bitmap, View fitView) {
        int dWidth = bitmap.getWidth();
        int dHeight = bitmap.getHeight();
        int vWidth = fitView.getWidth();
        int vHeight = fitView.getHeight();
        if (dWidth * vHeight < vWidth * dHeight) {
            float ratio = (float) (dWidth * vHeight) / (vWidth * dHeight);
            dHeight *= ratio;
        } else {
            float ratio = (float) (vWidth * dHeight) / (dWidth * vHeight);
            dWidth *= ratio;
        }
        /* x、y是从源bitmap开始截取的坐标点 */
        return Bitmap.createBitmap(bitmap, (bitmap.getWidth() - dWidth) / 2, (bitmap.getHeight() - dHeight) / 2, dWidth, dHeight);
    }

    /**
     * 创建背景颜色
     *
     * @param color       填充色
     * @param strokeColor 线条颜色
     * @param strokeWidth 线条宽度  单位px
     * @param radius      角度  px
     */
    public static Drawable getRoundRectangleDrawable(@ColorInt int color, @ColorInt int strokeColor, int strokeWidth, float radius) {
        try {
            GradientDrawable radiusBg = new GradientDrawable();
            //设置Shape类型
            radiusBg.setShape(GradientDrawable.RECTANGLE);
            //设置填充颜色
            radiusBg.setColor(color);
            if (strokeColor != -1 && strokeWidth != -1)
                //设置线条粗细和颜色,px
                radiusBg.setStroke(strokeWidth, strokeColor);
            //设置圆角角度,如果每个角度都一样,则使用此方法
            radiusBg.setCornerRadius(radius);
            return radiusBg;
        } catch (Exception e) {
            return new GradientDrawable();
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
    public static GradientDrawable createRectangleDrawable(@ColorInt int color, @ColorInt int strokeColor, int strokeWidth, float[] radius) {
        try {
            GradientDrawable radiusBg = new GradientDrawable();
            //设置Shape类型
            radiusBg.setShape(GradientDrawable.RECTANGLE);
            //设置填充颜色
            radiusBg.setColor(color);
            //设置线条粗心和颜色,px
            radiusBg.setStroke(strokeWidth, strokeColor);
            //每连续的两个数值表示是一个角度,四组:左上,右上,右下,左下
            if (radius != null && radius.length == 4) {
                radiusBg.setCornerRadii(new float[]{radius[0], radius[0], radius[1], radius[1], radius[2], radius[2], radius[3], radius[3]});
            }
            return radiusBg;
        } catch (Exception e) {
            return new GradientDrawable();
        }
    }

    public static void setSeekBarColor(@NonNull SeekBar seekBar, int colorId) {
        LayerDrawable drawable = (LayerDrawable) seekBar.getProgressDrawable();
        /* 0 背景， 1 二级进度条，2 当前进度条*/
        Drawable drawable1 = drawable.getDrawable(2);
        drawable1.setColorFilter(colorId, PorterDuff.Mode.SRC);
        Drawable thumb = seekBar.getThumb();
        thumb.setColorFilter(colorId, PorterDuff.Mode.SRC_ATOP);
        seekBar.invalidate();
    }

    /**
     * @param file 待压缩的图片文件
     * @param size 指定压缩之后的大小范围，单位：KB
     */
    public static Bitmap compressImageToSize(File file, int size) {
        /* 默认保留50%的质量 */
        CompressHelper compressHelper = new CompressHelper.Builder(HermitCrabApp.getAppContext())
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setQuality(50)
                .build();
        Bitmap bitmap = compressHelper.compressToBitmap(file);
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, buff);
        while (buff.size() / 1024 > size && quality - 10 >= 0) {
            buff.reset();
            quality -= 10;// 每次都减少5
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, buff);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            buff.writeTo(fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeFile(file.getPath());
    }

    /**
     * @param bitmap 待压缩的图片文件
     * @param size   指定压缩之后的大小范围
     */
    public static Bitmap compressImageToSize(Bitmap bitmap, int size) {
        try {
            File cache = FileStorageManager.createFileIfNull(System.currentTimeMillis() + ".png", FileStorageManager.DIR_TYPE.CACHE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(cache));
            long totalSpace = cache.getTotalSpace();
            return compressImageToSize(cache, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
