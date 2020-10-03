package com.kish2.hermitcrabapp.fragment;

import android.view.View;

import com.kish2.hermitcrabapp.view.IBaseView;

public interface IBaseFragment extends IBaseView {


    /* 设置ViewPager的预加载数量*/
    public void setViewPagerOfScreenLimit();

    /* 设置预留statusBar高度，以透明显示图片 */
    /* 用接口表示各个子类必须实现 */
    public void setPaddingTopForStatusBar(View v);

    /* 初始化页面标题数据 */
    public void setPageTitles();

}
