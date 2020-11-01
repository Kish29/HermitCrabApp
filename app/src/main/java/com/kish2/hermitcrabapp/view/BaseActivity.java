package com.kish2.hermitcrabapp.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.kish2.hermitcrabapp.utils.dev.GlideResourceRecycleManager;
import com.kish2.hermitcrabapp.utils.dev.SysInteractUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.yalantis.ucrop.UCrop;

import java.io.IOException;


public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    protected int DEFAULT_COLOR_ID = -1;

    protected int VIEW_PAGER_OF_SCREEN_LIMIT = 10;

    /* 供给于GlideManager用于图片的回收 */
    protected String simpleClassName = null;

    /* 定义操作系统文件的目的 */
    public enum FILE_OPERATE_PURPOSE {
        NORMAL,
        USER_AVATAR,
        BANNER_BKG,
        SIDE_MENU_BKG,
        PRODUCT_PICS,
    }

    protected FILE_OPERATE_PURPOSE file_operate_purpose = FILE_OPERATE_PURPOSE.NORMAL;

    public void setFileOperatePurpose(FILE_OPERATE_PURPOSE purpose) {
        file_operate_purpose = purpose;
    }

    /* 提供给Presenter使用 */
    public Handler mHandler;

    protected UCrop.Options mCropOptions;

    public void setCropOption(UCrop.Options options) {
        mCropOptions = options;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleClassName = getClass().getSimpleName();
    }

    protected void changeFragment(@IdRes int containerViewId, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerViewId, fragment).commit();
    }

    protected void onImageCropSuccess(Uri uri) {

    }

    /* 处理返回值逻辑 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SysInteractUtil.request_camera_activity_crop:
                    SysInteractUtil.onTakePhotoSuccessAndNeedCrop(this, mCropOptions);
                    break;
                case SysInteractUtil.request_gallery_activity_crop:
                    if (data != null) {
                        Uri selectImageUri = data.getData();
                        if (selectImageUri != null) {
                            SysInteractUtil.onGalleryPickSuccessAndNeedCrop(this, selectImageUri, mCropOptions);
                        }
                    }
                    break;
                case SysInteractUtil.request_crop_activity:
                    if (data != null) {
                        Uri uri = UCrop.getOutput(data);
                        onImageCropSuccess(uri);
                    }
                default:
                    break;
            }
        }
    }

    protected void onCameraPermissionGranted() throws IOException {

    }

    protected void onFilePickPermissionGranted() throws IOException {

    }

    /* 重写权限的请求处理结果 */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SysInteractUtil.request_camera_permission) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    onCameraPermissionGranted();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtil.showToast(this, "请开启权限来使用相应功能", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
            }
        }
        if (requestCode == SysInteractUtil.request_file_pick_permission) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    onFilePickPermissionGranted();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtil.showToast(this, "请开启权限来使用相应功能", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /* 释放Glide的资源 */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlideResourceRecycleManager.recycleBitmapList(simpleClassName);
    }
}
