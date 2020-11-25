package com.kish2.hermitcrabapp.view;

import android.Manifest;
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

import com.kish2.hermitcrabapp.utils.dev.FileStorageManager;
import com.kish2.hermitcrabapp.utils.dev.GlideResourceRecycleManager;
import com.kish2.hermitcrabapp.utils.dev.SysInteractUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.yalantis.ucrop.UCrop;

import java.io.IOException;
import java.util.ArrayList;


public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    protected int DEFAULT_COLOR_ID = -1;

    protected int VIEW_PAGER_OF_SCREEN_LIMIT = 10;

    /* 供给于GlideManager用于图片的回收 */
    protected String simpleClassName = null;

    protected SysInteractUtil.FILE_OPERATE_PURPOSE file_operate_purpose = SysInteractUtil.FILE_OPERATE_PURPOSE.NORMAL;

    public void setFileOperatePurpose(SysInteractUtil.FILE_OPERATE_PURPOSE purpose) {
        file_operate_purpose = purpose;
    }

    /* 提供给Presenter使用 */
    public Handler mHandler;

    /* 给fragment使用 */

    protected float cropRatio = 1;

    protected int requestCode = SysInteractUtil.request_file_pick_permission;

    protected FileStorageManager.DIR_TYPE dir_type = FileStorageManager.DIR_TYPE.FILE_DOWNLOAD;

    protected String file_name = null;

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    protected UCrop.Options mCropOptions;

    public void setCropOption(UCrop.Options options) {
        mCropOptions = options;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
        if (ThemeUtil.Theme.afterGetResourcesColorId != themeColorId) {
            themeChanged();
            themeColorId = ThemeUtil.Theme.afterGetResourcesColorId;
        }
    }

    /* 判断标准，theme类保存的colorId是否等于原来的colorId*/
    protected int themeColorId = -1;

    protected abstract void themeChanged();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHandler();
        simpleClassName = getClass().getSimpleName();
        /* 第一次加载时初始化theme color id*/
        themeColorId = ThemeUtil.Theme.afterGetResourcesColorId;
        attachPresenter();
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

    protected void onCameraPermissionGranted() {
        takePhoto();
    }

    protected void onFilePickPermissionGranted() {
        uploadPic();
    }

    /* 重写权限的请求处理结果 */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SysInteractUtil.request_camera_permission) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onCameraPermissionGranted();
            } else {
                ToastUtil.showToast(this, "请开启权限来使用相应功能", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
            }
        }
        if (requestCode == SysInteractUtil.request_file_pick_permission) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onFilePickPermissionGranted();
            } else {
                ToastUtil.showToast(this, "请开启权限来使用相应功能", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /* 释放Glide的资源 */
    @Override
    protected void onDestroy() {
        /* 解除与presenter的关系 */
        GlideResourceRecycleManager.recycleBitmapList(simpleClassName);
        super.onDestroy();
    }

    /* 与SysInteract交互的接口 */
    public void takePhoto() {
        /* 要用到的权限 */
        ArrayList<String> list = new ArrayList<>();
        list.add(Manifest.permission.CAMERA);
        list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (SysInteractUtil.checkAndRequestPermissions(this, list, SysInteractUtil.request_camera_permission)) {
            try {
                SysInteractUtil.takePhoto(this, file_name, dir_type, requestCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadPic() {
        /* 要用到的权限 */
        ArrayList<String> list = new ArrayList<>();
        list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (SysInteractUtil.checkAndRequestPermissions(this, list, SysInteractUtil.request_file_pick_permission)) {
            try {
                SysInteractUtil.uploadPicture(this, file_name, dir_type, requestCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSysInteractArgs(
            SysInteractUtil.FILE_OPERATE_PURPOSE purpose,
            String file_name,
            FileStorageManager.DIR_TYPE dir_type,
            int requestCode,
            UCrop.Options options) {
        /* 先赋值再调用 */
        this.file_operate_purpose = purpose;
        this.file_name = file_name;
        this.dir_type = dir_type;
        this.mCropOptions = options;
        this.requestCode = requestCode;
    }

    @Override
    public void initHandler() {

    }
}
