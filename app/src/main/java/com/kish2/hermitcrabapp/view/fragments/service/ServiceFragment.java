package com.kish2.hermitcrabapp.view.fragments.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.kish2.hermitcrabapp.view.IBaseFragment;
import com.kish2.hermitcrabapp.view.activities.MyTest;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceFragment extends BaseFragment implements IBaseFragment {
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

    @BindView(R.id.tl_category_tab)
    TabLayout mNavTabBar;
    @BindView(R.id.vp_content_list)
    ViewPager mVPSubService;
    @BindView(R.id.fragment_sub_constraint_layout_for_padding_top)
    ConstraintLayout mPaddingTop;

    @BindView(R.id.change_activity_test)
    Button mBtnChange;

    /* 这三个方法必须重写 */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("tag", "ServiceFragment createView run.");

        View fragmentService = inflater.inflate(R.layout.fragment_service, container, false);
        ButterKnife.bind(this, fragmentService);

        /*setPaddingTopForStatusBar(fragmentService);*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                getLayoutComponentsAttr();
            }
        }).start();
        return fragmentService;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void getLayoutComponentsAttr() {
        /* 获取page_titles */
        /* 创建实例并作为ViewPager的适配器 */
        /*subFmCAdapter = new SubFragmentContentAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, page_titles);
        mVPSubService.setAdapter(subFmCAdapter);*/
        /* 绑定ViewPager */
        mNavTabBar.setupWithViewPager(mVPSubService);

        mDLParentView = requireActivity().findViewById(R.id.dl_maint_activity);

        /* 获取顶部retrieveBar的几个部件*/
        mUserAvatar = mTopRetrieveBar.findViewById(R.id.riv_side_menu);
        mSearch = mTopRetrieveBar.findViewById(R.id.sv_search);

        mUserAvatar.setOnClickListener(v -> {
            mDLParentView.openDrawer(GravityCompat.START);
        });
        mBtnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyTest.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getAndSetLayoutView() {

    }

    @Override
    public void loadDataComplete() {

    }

    @Override
    public void registerViewComponentsAffairs() {

    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }

}
