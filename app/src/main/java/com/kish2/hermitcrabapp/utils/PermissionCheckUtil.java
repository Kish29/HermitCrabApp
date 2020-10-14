package com.kish2.hermitcrabapp.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Iterator;

public class PermissionCheckUtil {

    /* 检查并申请权限 */
    public static void checkAndRequestPermissions(Activity activity, ArrayList<String> permissionList) {
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
            return;
        }
        /* 先转化为String数组*/
        String[] permissions = list.toArray(new String[0]);
        ActivityCompat.requestPermissions(activity, permissions, 3000);
    }
}
