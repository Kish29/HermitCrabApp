package com.kish2.hermitcrabapp.fragment.impl;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapter.HomeFragmentAdapter;
import com.kish2.hermitcrabapp.adapter.SubFragmentContentAdapter;
import com.kish2.hermitcrabapp.fragment.BaseFragment;
import com.kish2.hermitcrabapp.fragment.IBaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragmentImpl extends BaseFragment implements IBaseFragment {

    /* 父根布局 */
    DrawerLayout mDLParentView;

    /* 主内容*/
    @BindView(R.id.ly_fragment_content)
    ConstraintLayout mLYFragmentContent;

    /* 顶部导航条*/
    @BindView(R.id.top_retrieve_bar)
    ViewGroup mTopRetrieveBar;
    /* 顶部导航条高度*/
    private static int mTopBarHeight = 0;
    /* 底部导航条*/
    ConstraintLayout mBottomNavTabBar;
    /* 底部导航条高度*/
    private static int mBottomBarHeight = 0;

    /* 用户头像*/
    RoundedImageView mUserAvatar;
    /* 搜索栏*/
    SearchView mSearch;
    /* 筛选栏*/
    /*ImageButton mFilter*/

    @BindView(R.id.vp_nav_tab_bar)
    TabLayout mNavTabBar;
    @BindView(R.id.vp_sub)
    ViewPager mVPSubHome;
    @BindView(R.id.btn_test)
    Button button;

    private float mFirstY;
    private float mTouchSlop;
    private float mCurrentY;

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
        View fragmentHome = inflater.inflate(R.layout.fragment_home, container, false);// 视图与父容器ViewGroup不需要连接
        ButterKnife.bind(this, fragmentHome);

        initView();
        return fragmentHome;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setViewPagerOfScreenLimit() {
        mVPSubHome.setOffscreenPageLimit(this.VIEW_PAGER_OF_SCREEN_LIMIT);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        /* 获取父ViewPager */

        /* 获取page_titles */
        setPageTitles();

        ArrayList<String> strings = new ArrayList<>();
        strings.add("最新");
        strings.add("教务处");
        /* 创建实例并作为ViewPager的适配器 */
        HomeFragmentAdapter homeFragmentAdapter = new HomeFragmentAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, strings);
        mVPSubHome.setAdapter(homeFragmentAdapter);
        setViewPagerOfScreenLimit();
        /* 绑定ViewPager */
        mNavTabBar.setupWithViewPager(mVPSubHome);

        mDLParentView = requireActivity().findViewById(R.id.activity_main_drawer_layout);
        mBottomNavTabBar = requireActivity().findViewById(R.id.ly_bottom_tab_bar);

        /* 获取顶部retrieveBar的几个部件*/
        mUserAvatar = mTopRetrieveBar.findViewById(R.id.riv_side_menu);
        mSearch = mTopRetrieveBar.findViewById(R.id.sv_search);
        mTopRetrieveBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mTopBarHeight == 0 || mBottomBarHeight == 0) {
                    /* 获取高度 */
                    mTopBarHeight = mTopRetrieveBar.getHeight();
                    mBottomBarHeight = mBottomNavTabBar.getHeight();
                    /* 设置translation */
                    mLYFragmentContent.setTranslationY(mTopBarHeight);
                }
            }
        });

        mUserAvatar.setOnClickListener(v -> {
            mDLParentView.openDrawer(GravityCompat.START);
        });

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    }

    public void topBarUpGlide(boolean hide) {
        viewGlide(hide, mTopRetrieveBar, mTopBarHeight, mLYFragmentContent, mBottomNavTabBar, mBottomBarHeight);
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }

    @Override
    public void setPaddingTopForStatusBar(View view) {
        /*int identifier = view.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            int paddingTop = getResources().getDimensionPixelOffset(identifier);
            Log.d("height", String.valueOf(paddingTop));
            mPaddingTop.setPadding(0, paddingTop, 0, 0);
        }*/
    }

    @Override
    public void setPageTitles() {
        /*page_titles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.home_page_titles)));*/
    }
}
