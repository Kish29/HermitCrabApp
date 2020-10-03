package com.kish2.hermitcrabapp.fragment.impl;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kish2.hermitcrabapp.MainActivity;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapter.SubFragmentContentAdapter;
import com.kish2.hermitcrabapp.fragment.BaseFragment;
import com.kish2.hermitcrabapp.fragment.IBaseFragment;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragmentImpl extends BaseFragment implements IBaseFragment {

    /* 父根布局 */
    DrawerLayout mDLParentView;

    /* 顶部导航条*/
    @BindView(R.id.top_retrieve_bar)
    ViewGroup mTopRetrieveBar;
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
    @BindView(R.id.fragment_sub_constraint_layout_for_padding_top)
    ConstraintLayout mPaddingTop;

    @BindView(R.id.btn_test)
    Button button;

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

        /* 设置沉浸式状态栏预留空间 */
        setPaddingTopForStatusBar(fragmentHome);
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

    @Override
    public void initView() {
        /* 获取page_titles */
        setPageTitles();
        /* 创建实例并作为ViewPager的适配器 */
        subFmCAdapter = new SubFragmentContentAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, page_titles);
        mVPSubHome.setAdapter(subFmCAdapter);
        setViewPagerOfScreenLimit();
        /* 绑定ViewPager */
        mNavTabBar.setupWithViewPager(mVPSubHome);

        mDLParentView = requireActivity().findViewById(R.id.activity_main_drawer_layout);

        /* 获取顶部retrieveBar的几个部件*/
        mUserAvatar = mTopRetrieveBar.findViewById(R.id.riv_user_avatar);
        mSearch = mTopRetrieveBar.findViewById(R.id.sv_search);

        mUserAvatar.setOnClickListener(v -> {
            mDLParentView.openDrawer(GravityCompat.START);
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayout rootTabBar = requireActivity().findViewById(R.id.nav_tab_bar);
                rootTabBar.selectTab(rootTabBar.getTabAt(4));
            }
        });


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

    @Override
    public void setPageTitles() {
        page_titles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.home_page_titles)));
    }
}
