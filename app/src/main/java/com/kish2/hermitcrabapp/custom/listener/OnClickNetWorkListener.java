package com.kish2.hermitcrabapp.custom.listener;

import android.view.View;

import com.kish2.hermitcrabapp.utils.network.NetWorkUtil;

public abstract class OnClickNetWorkListener extends BaseOnClickListener {

    @Override
    public void onClick(View v) {
        boolean netWorkActive = NetWorkUtil.isNetWorkActive(v.getContext());
        if (netWorkActive)
            onClickNetWorkOn();
        else onClickNetWorkOff();
    }

    /* 网络连接情况下的具体实现 */
    public abstract void onClickNetWorkOn();

    /* 无网络连接 */
    public abstract void onClickNetWorkOff();
}
