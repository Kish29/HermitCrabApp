package com.kish2.hermitcrabapp.fragment;

import android.view.View;

import com.kish2.hermitcrabapp.view.IBaseView;

public interface IBaseFragment extends IBaseView {

    /* 设置预留statusBar高度，以透明显示图片 */
    public void setPaddingTopForStatusBar(View v);

}
