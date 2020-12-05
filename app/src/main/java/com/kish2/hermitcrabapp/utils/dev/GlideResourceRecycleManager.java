package com.kish2.hermitcrabapp.utils.dev;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* 用户回收presenter或者view中的所有Glide加载的图片资源
 * */
public class GlideResourceRecycleManager {

    /* viewBitmaps*/
    private static final Map<String, ArrayList<Bitmap>> viewBitmaps = new HashMap<>();
    private static final Map<String, ArrayList<Drawable>> viewDrawables = new HashMap<>();
    private static final String TAG = "GlideRecycler";

    public static ArrayList<Bitmap> createBitmapList(String key) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        viewBitmaps.put(key, bitmaps);
        return bitmaps;
    }

    /* 根据activity的名字创建相应的bitmapList */
    public static void addBitmapIntoList(String simpleClassName, Bitmap bitmap) {
        ArrayList<Bitmap> bitmaps = viewBitmaps.get(simpleClassName);
        if (bitmaps == null)
            bitmaps = createBitmapList(simpleClassName);
        if (bitmap != null) {
            bitmaps.add(bitmap);
        }
    }

    public static void recycleBitmapList(String simpleClassName) {
        ArrayList<Bitmap> bitmaps = viewBitmaps.get(simpleClassName);
        if (bitmaps != null) {
            Log.d(TAG, "recycle bitmaps->" + bitmaps.size() + " from" + simpleClassName);
            /* 置空，让cg回收*/
            bitmaps.clear();
            bitmaps = null;
            viewBitmaps.remove(simpleClassName);
        }
    }

    public static ArrayList<Drawable> createDrawableList(String key) {
        ArrayList<Drawable> drawables = new ArrayList<>();
        viewDrawables.put(key, drawables);
        return drawables;
    }

    /* 根据activity的名字创建相应的drawableList */
    public static void addDrawableIntoList(String simpleClassName, Drawable drawable) {
        ArrayList<Drawable> drawables = viewDrawables.get(simpleClassName);
        if (drawables == null)
            drawables = createDrawableList(simpleClassName);
        if (drawable != null) {
            drawables.add(drawable);
        }
    }

    public static void recycleDrawableList(String simpleClassName) {
        ArrayList<Drawable> drawables = viewDrawables.get(simpleClassName);
        if (drawables != null) {
            Log.d(TAG, "recycle drawables->" + drawables.size() + " from" + simpleClassName);
            /* 置空，让cg回收*/
            drawables.clear();
            drawables = null;
            viewDrawables.remove(simpleClassName);
        }
    }
}
