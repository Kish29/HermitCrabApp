package com.kish2.hermitcrabapp.utils.view;

import android.app.Activity;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.yalantis.ucrop.UCrop;

public class ThemeMatchUCrop {

    public static UCrop.Options setOptionsToMatchTheme(UCrop.Options cropOptions) {
        if (cropOptions == null)
            cropOptions = new UCrop.Options();
        cropOptions.setActiveControlsWidgetColor(ThemeUtil.Theme.afterGetResourcesColorId);
        /* 保留一半的质量 */
        cropOptions.setCompressionQuality(50);
        return cropOptions;
    }

    public static void imageUCropActivity(@NonNull Activity activity, @NonNull Uri sourceImage, @NonNull Uri destinationImage, UCrop.Options cropOptions, int requestCode) {
        UCrop uCrop = UCrop.of(sourceImage, destinationImage);
        cropOptions = setOptionsToMatchTheme(cropOptions);
        uCrop.withOptions(cropOptions);
        uCrop.start(activity, requestCode);
    }

}
