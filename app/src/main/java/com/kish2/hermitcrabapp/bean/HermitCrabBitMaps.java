package com.kish2.hermitcrabapp.bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.utils.App;

public class HermitCrabBitMaps {

    public static Bitmap mChecked;

    public static void loadBitMaps() {
        mChecked = BitmapFactory.decodeResource(App.getAppResources(), R.drawable.ic_check_white);
    }
}
