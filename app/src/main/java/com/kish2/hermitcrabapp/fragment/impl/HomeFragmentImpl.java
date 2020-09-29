package com.kish2.hermitcrabapp.fragment.impl;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.fragment.BaseFragment;
import com.kish2.hermitcrabapp.fragment.IBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragmentImpl extends BaseFragment implements IBaseFragment {

    @BindView(R.id.vp_nav_tab_bar)
    TableLayout mNavTabBar;
    @BindView(R.id.vp_sub_home)
    ViewPager mVPSubHome;
    @BindView(R.id.fragment_sub_constraint_layout_for_padding_top)
    ConstraintLayout mPaddingTop;

    /* 这三个方法必须重写 */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("tag", "HomeFragment createView run.");
        /* 绑定xml视图 */
        View pagerView = inflater.inflate(R.layout.fragment_home, container, false);// 视图与父容器ViewGroup不需要连接
        ButterKnife.bind(this, pagerView);

        setPaddingTopForStatusBar(pagerView);

        return pagerView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void initView() {

    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }

    @Override
    public void setPaddingTopForStatusBar(View view) {
        int identifier = view.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            int paddingTop = getResources().getDimensionPixelOffset(identifier);
            Log.d("height", String.valueOf(paddingTop));
            mPaddingTop.setPadding(0, paddingTop, 0, 0);
        }
    }
}
