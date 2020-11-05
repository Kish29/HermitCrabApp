package com.kish2.hermitcrabapp.utils.dev;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.kish2.hermitcrabapp.utils.App;
import com.kish2.hermitcrabapp.utils.view.ThemeMatchUCrop;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class SysInteractUtil {

    /* 权限请求码*/
    public static final int request_camera_permission = 0;
    public static final int request_file_pick_permission = 1;

    /* activity的请求码 */
    public static final int request_camera_activity_no_crop = 5;
    public static final int request_camera_activity_crop = 2;
    public static final int request_gallery_activity_crop = 3;
    public static final int request_crop_activity = 4;

    private static File operateSourceFile;
    private static File operateDestFile;

    /* 定义操作系统文件的目的 */
    public enum FILE_OPERATE_PURPOSE {
        NORMAL,
        USER_AVATAR,
        BANNER_BKG,
        SIDE_MENU_BKG,
        PRODUCT_PICS,
    }

    /* 检查并申请权限 */
    public static boolean checkAndRequestPermissions(Activity activity, ArrayList<String> permissionList, int requestCode) {
        ArrayList<String> list = new ArrayList<>(permissionList);
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String permission = it.next();
            if (ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
                it.remove();
            }
        }
        /* 所有权限均通过 */
        if (list.size() == 0) {
            return true;
        }
        /* 先转化为String数组*/
        String[] permissions = list.toArray(new String[0]);
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
        return false;
    }

    public static void uploadPicture(Activity activity, @NonNull String saveFileName, FileStorageManager.DIR_TYPE dir_type, int requestActivityCode) throws IOException {
        /* 创建或获取要保存的文件 */
        operateDestFile = FileStorageManager.createFileIfNull(saveFileName, dir_type);
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(picture, requestActivityCode);
    }

    /* 注意，调用该函数应该先检查权限 */
    /* 调用该函数，并将拍摄的照片保存在对应的文件夹中 */
    public static void takePhoto(Activity activity, @NonNull String saveFileName, FileStorageManager.DIR_TYPE dir_type, int requestActivityCode) throws IOException {
        /* 创建或获取要保存的文件 */
        operateSourceFile = FileStorageManager.createFileIfNull(saveFileName, dir_type);
        System.out.println(operateSourceFile);
        /* 获取头像uri */
        Uri photoTakenUri = getProviderUriFromFile(operateSourceFile);
        Intent intent = new Intent();
        /* 拍照动作 */
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        // 拍照后原图回存入此路径下(只能放入uri值，因为系统应用相机要访问该app的资源)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoTakenUri);
        /* 设置获取的图片或视屏的质量，最高质量是1 */
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        activity.startActivityForResult(intent, requestActivityCode);
    }

    public static Uri getProviderUriFromFile(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            uri = Uri.fromFile(file);
        } else {
            /* *
             * 7.0 及以上调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            uri = FileProvider.getUriForFile(App.getAppContext(), FileStorageManager.APP_PROVIDER_AUTHORITY, file);
        }
        return uri;
    }

    public static void onTakePhotoSuccessAndNeedCrop(@NonNull Activity activity, UCrop.Options cropOptions) {
        if (operateSourceFile != null) {
            /* 在源文件上直接裁剪 */
            Uri uri = Uri.fromFile(operateSourceFile);    // 这里uri必须通过该方式转换一下
            ThemeMatchUCrop.imageUCropActivity(activity, uri, uri, cropOptions, request_crop_activity);
        }
    }

    public static void onGalleryPickSuccessAndNeedCrop(@NonNull Activity activity, @NonNull Uri selectImageUri, UCrop.Options cropOptions) {
        /* 保存的文件的路径*/
        ThemeMatchUCrop.imageUCropActivity(activity, selectImageUri, Uri.fromFile(operateDestFile), cropOptions, request_crop_activity);
    }
}
