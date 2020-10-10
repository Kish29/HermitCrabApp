package com.kish2.hermitcrabapp.view.fragments.message;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageFragment extends BaseFragment implements IBaseFragment {

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
    ViewPager mVPSubMessage;
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
        Log.d("tag", "MessageFragment createView run.");

        View fragmentMessage = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, fragmentMessage);

        /*setPaddingTopForStatusBar(fragmentMessage);*/
        new Thread() {
            @Override
            public void run() {
                getLayoutComponentsAttr();
            }
        }.start();
        return fragmentMessage;
    }

    /**
     * @因为onPause后，FragmentManager会对组件进行重绘，某些组件会回到原始的属性
     * @而如果在之间调用了隐藏动画，那么会造成冲突，视图UI组件会消失
     */
    @Override
    public void onPause() {
        super.onPause();
        topAndBottomBarGlide(false);
    }

    @Override
    public void getLayoutComponentsAttr() {
        /* 获取page_titles */
        /* 创建实例并作为ViewPager的适配器 */
        /*subFmCAdapter = new SubFragmentContentAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, page_titles);
        mVPSubMessage.setAdapter(subFmCAdapter);*/
        /* 绑定ViewPager */
        mNavTabBar.setupWithViewPager(mVPSubMessage);

        mDLParentView = requireActivity().findViewById(R.id.dl_maint_activity);

        /* 获取顶部retrieveBar的几个部件*/
        mUserAvatar = mTopRetrieveBar.findViewById(R.id.riv_side_menu);
        mSearch = mTopRetrieveBar.findViewById(R.id.sv_search);

        mUserAvatar.setOnClickListener(v -> {
            mDLParentView.openDrawer(GravityCompat.START);
        });
    }

    @Override
    public void getAndSetLayoutView() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void registerViewComponentsAffairs() {

    }


    @Override
    public void topAndBottomBarGlide(boolean hide) {

    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }
}
