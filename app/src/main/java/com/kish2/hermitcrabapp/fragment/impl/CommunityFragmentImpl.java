package com.kish2.hermitcrabapp.fragment.impl;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapter.SubFragmentContentAdapter;
import com.kish2.hermitcrabapp.fragment.BaseFragment;
import com.kish2.hermitcrabapp.fragment.IBaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunityFragmentImpl extends BaseFragment implements IBaseFragment {

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
    /* retrieve bar的高度 */
    private float mRBHeight;

    @BindView(R.id.vp_sub)
    ViewPager mVPSubCommunity;
    @BindView(R.id.v_layer)
    View mLayer;

    @BindView(R.id.fragment_sub_constraint_layout_for_padding_top)
    ConstraintLayout mPaddingTop;

    ViewGroup.MarginLayoutParams mPaddingTopLayoutParam;

    /* 系统的最小触摸判定 */
    private float touchSlop;
    private float firstTouchY;

    /* 这三个方法必须重写 */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("tag", "Community createView run.");

        View fragmentCommunity = inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, fragmentCommunity);

        // setPaddingTopForStatusBar(fragmentCommunity);
        initView();
        return fragmentCommunity;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setViewPagerOfScreenLimit() {
        mVPSubCommunity.setOffscreenPageLimit(VIEW_PAGER_OF_SCREEN_LIMIT);
    }

    @Override
    public void initView() {
        /* 获取page_titles */
        setPageTitles();
        setViewPagerOfScreenLimit();
        /* 创建实例并作为ViewPager的适配器 */
        subFmCAdapter = new SubFragmentContentAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, page_titles);
        mVPSubCommunity.setAdapter(subFmCAdapter);

        /* 绑定ViewPager */
        mNavTabBar.setupWithViewPager(mVPSubCommunity);

        mDLParentView = requireActivity().findViewById(R.id.activity_main_drawer_layout);
        mPaddingTopLayoutParam = (ViewGroup.MarginLayoutParams) mPaddingTop.getLayoutParams();

        /* 获取顶部retrieveBar的几个部件*/
        mUserAvatar = mTopRetrieveBar.findViewById(R.id.riv_user_avatar);
        mSearch = mTopRetrieveBar.findViewById(R.id.sv_search);
        mRBHeight = -mTopRetrieveBar.getHeight();

        mUserAvatar.setOnClickListener(v -> {
            mDLParentView.openDrawer(GravityCompat.START);
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
        /*int identifier = view.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            int paddingTop = getResources().getDimensionPixelOffset(identifier);
            Log.d("height", String.valueOf(paddingTop));
            mPaddingTop.setPadding(0, paddingTop, 0, 0);
        }*/
    }

    @Override
    public void setPageTitles() {
        page_titles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.home_page_titles)));
    }
}
