package com.kish2.hermitcrabapp.view.fragments.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapters.viewpager.HomeFragmentAdapter;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.utils.ThemeUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.kish2.hermitcrabapp.view.IBaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment {
    /* 主内容*/
    @BindView(R.id.cl_fragment_content)
    ConstraintLayout mCLFragmentContent;

    /* 顶部导航条AppBarLayout */
    @BindView(R.id.abl_retrieve_bar_container)
    AppBarLayout mAppBarLayout;

    /* 顶部导航条*/
    @BindView(R.id.top_retrieve_bar)
    ViewGroup mTopRetrieveBar;
    @BindView(R.id.ctl_banner_container)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private static float mTopRetrieveBarHeight = 0;

    /* 用户头像*/
    RoundedImageView mUserAvatar;
    /* 搜索栏*/
    SearchView mSearch;
    /* 筛选栏*/
    /*ImageButton mFilter*/

    /* 分类Tab*/
    @BindView(R.id.tl_category_tab)
    TabLayout mCategoryTab;
    @BindView(R.id.vp_content_list)
    ViewPager mVPSubHome;

    HomeFragmentAdapter homeFragmentAdapter;
    private Handler mHandler;

    /* 这三个方法必须重写 */

    /**
     * 该Fragment创建时调用，只调用一次，可以操作除View之外的一切东西
     */
    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.ADAPTER_INIT:
                        mVPSubHome.setAdapter(homeFragmentAdapter);
                        /* 绑定ViewPager */
                        mCategoryTab.setupWithViewPager(mVPSubHome);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    /**
     * 第一次绘制这个Fragment的UI调用的方法
     *
     * @param inflater           布局转化实体
     * @param container          Fragment在Activity中的父控件
     * @param savedInstanceState 封装的上一个实例的数据
     *                           <p>
     *                           第三个参数表示该布局是否连接到父容器控件
     *                           因为系统已经自动插入该布局到父容器控件，所以不再需要连接
     *                           否则多出一个ViewGroup
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /* 通过inflate将xml布局文件转化为View*/
        View fragmentHome = inflater.inflate(R.layout.fragment_home, container, false);// 视图与父容器ViewGroup不需要连接
        ButterKnife.bind(this, fragmentHome);
        /* 主线程*/
        getAndSetLayoutView();
        mCLFragmentContent.getViewTreeObserver().addOnGlobalLayoutListener(this::getLayoutComponentsAttr);
        return fragmentHome;
    }

    /**
     * @因为onPause后，FragmentManager会对组件进行重绘，某些组件会回到原始的属性
     * @而如果在之间调用了隐藏动画，那么会造成冲突，视图UI组件会消失
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void bottomBarHide(boolean hide) {
        bottomBarHide(hide, mBottomTab, mBottomTabHeight);
    }

    @Override
    public void loadData() {
        /* 注册事件 */
        registerViewComponentsAffairs();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("最新");
        strings.add("教务处");
        /* 创建实例并作为ViewPager的适配器 */
        homeFragmentAdapter = new HomeFragmentAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, strings);
        Message msg = new Message();
        msg.what = MessageForHandler.ADAPTER_INIT;
        mHandler.sendMessage(msg);
    }

    @Override
    public void getLayoutComponentsAttr() {
        /* 获取高度 */
        mBottomTabHeight = mBottomTab.getHeight();
        mTopRetrieveBarHeight = mCollapsingToolbarLayout.getHeight();
    }

    @Override
    public void getAndSetLayoutView() {
        /* 用padding设置StatusBar的占位高度 */
        setPaddingTopForStatusBarHeight(mAppBarLayout);
        /* 设置AppBarLayout的颜色 */
        if (ThemeUtil.Theme.colorId != -1)  // -1表示使用透明主题
            mAppBarLayout.setBackgroundColor(getResources().getColor(ThemeUtil.Theme.colorId));
        /* 父组件 */
        mDLParentView = requireActivity().findViewById(R.id.dl_maint_activity);
        mBottomTab = requireActivity().findViewById(R.id.cl_bottom_tab_bar);
        /* 获取顶部retrieveBar的几个部件*/
        mUserAvatar = mTopRetrieveBar.findViewById(R.id.riv_side_menu);
        mSearch = mTopRetrieveBar.findViewById(R.id.sv_search);
        /* 设置最大缓存fragment */
        mVPSubHome.setOffscreenPageLimit(VIEW_PAGER_OF_SCREEN_LIMIT);
    }

    @Override
    public void registerViewComponentsAffairs() {
        mUserAvatar.setOnClickListener(v -> {
            mDLParentView.openDrawer(GravityCompat.START);
        });
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float offset = mTopRetrieveBarHeight + verticalOffset;
            float ratio = offset / mTopRetrieveBarHeight;
            mCollapsingToolbarLayout.setAlpha(ratio);
        });
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }

}
