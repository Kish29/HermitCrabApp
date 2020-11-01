package com.kish2.hermitcrabapp.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.kish2.hermitcrabapp.R;

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
}
