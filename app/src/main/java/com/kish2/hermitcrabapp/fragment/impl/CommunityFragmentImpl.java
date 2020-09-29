package com.kish2.hermitcrabapp.fragment.impl;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapter.SubFragmentContentAdapter;
import com.kish2.hermitcrabapp.fragment.BaseFragment;
import com.kish2.hermitcrabapp.fragment.IBaseFragment;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunityFragmentImpl extends BaseFragment implements IBaseFragment {
    /* TabLayout的tabBar、ViewPager、预留padding */
    @BindView(R.id.retrieve_bar)
    LinearLayout mRetrieveBar;
    @BindView(R.id.vp_nav_tab_bar)
    TabLayout mNavTabBar;
    @BindView(R.id.vp_sub)
    ViewPager mVPSubCommunity;
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
        Log.d("tag", "Community createView run.");

        View pagerView = inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, pagerView);

        setPaddingTopForStatusBar(pagerView);
        initView();
        return pagerView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void initView() {
        /* 获取page_titles */
        setPageTitles();
        /* 创建实例并作为ViewPager的适配器 */
        subFmCAdapter = new SubFragmentContentAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, page_titles);
        mVPSubCommunity.setAdapter(subFmCAdapter);
        /* 绑定ViewPager */
        mNavTabBar.setupWithViewPager(mVPSubCommunity);
        TextView viewById = mRetrieveBar.findViewById(R.id.top_navigation_label);
        viewById.setText("社区");
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
