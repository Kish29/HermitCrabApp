package com.kish2.hermitcrabapp.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

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
}
