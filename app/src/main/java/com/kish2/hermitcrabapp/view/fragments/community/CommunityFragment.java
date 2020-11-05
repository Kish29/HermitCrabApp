package com.kish2.hermitcrabapp.view.fragments.community;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapters.viewpager.CommunityFragmentAdapter;
import com.kish2.hermitcrabapp.bean.HermitCrabVectorIllustrations;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.utils.dev.ApplicationConfigUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunityFragment extends BaseFragment {

    /* 顶部导航条*/
    @BindView(R.id.abl_retrieve_bar_container)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.ctl_banner_container)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.top_retrieve_bar)
    ViewGroup mTopRetrieveBar;
    /* 占位statusView */
    @BindView(R.id.v_status_bar)
    View mStatusBar;
    /* 顶部导航条高度*/
    private static int mAppBarHeight = 0;
    /* 用户头像*/
    RoundedImageView mUserAvatar;

    /* 浮动搜索按钮 */
    FloatingActionButton mSearch;
    /* 筛选栏*/
    /*ImageButton mFilter*/

    SlidingTabLayout mCategoryTab;

    @BindView(R.id.vp_content_list)
    ViewPager mVPSubCommunity;

    CommunityFragmentAdapter communityFragmentAdapter;
    private Handler mHandler;

    @Override
    protected void themeChanged() {
        /* 设置AppBarLayout的颜色 */
        mAppBarLayout.setBackgroundColor(ThemeUtil.Theme.afterGetResourcesColorId);
        mCategoryTab.setIndicatorColor(HermitCrabVectorIllustrations.colorWhite);
        mCategoryTab.setTextSelectColor(HermitCrabVectorIllustrations.colorWhite);
    }

    /* 这三个方法必须重写 */
    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.ADAPTER_INIT:
                        mVPSubCommunity.setAdapter(communityFragmentAdapter);
                        /* 绑定ViewPager */
                        mCategoryTab.setViewPager(mVPSubCommunity);
                        break;
                    case 2:
                    case 3:
                    case 4:
                        break;
                }
            }
        };
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View fragmentCommunity = inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, fragmentCommunity);

        getAndSetLayoutView();
        registerViewComponentsAffairs();
        mVPSubCommunity.getViewTreeObserver().addOnGlobalLayoutListener(this::getLayoutComponentsAttr);
        return fragmentCommunity;
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
    public void getAndSetLayoutView() {
        /* 设置StatusBar的占位高度 */
        setHeightForStatusBar(mStatusBar);
        /* 设置AppBarLayout的颜色 */
        mAppBarLayout.setBackgroundColor(ThemeUtil.Theme.afterGetResourcesColorId);
        /* 获取顶部retrieveBar的几个部件*/
        mUserAvatar = mTopRetrieveBar.findViewById(R.id.riv_side_menu);
        mCategoryTab = mTopRetrieveBar.findViewById(R.id.tl_category_tab);
        mVPSubCommunity.setOffscreenPageLimit(VIEW_PAGER_OF_SCREEN_LIMIT);
        mCategoryTab.setIndicatorColor(HermitCrabVectorIllustrations.colorWhite);
        mCategoryTab.setTextSelectColor(HermitCrabVectorIllustrations.colorWhite);
        mCategoryTab.setTabSpaceEqual(true);
    }

    @Override
    public void loadData() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("易贝壳");
        strings.add("话题");
        communityFragmentAdapter = new CommunityFragmentAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, strings);
        mHandler.sendEmptyMessage(MessageForHandler.ADAPTER_INIT);
    }

    @Override
    public void refreshData() {
        if (ApplicationConfigUtil.HAS_AVATAR && ApplicationConfigUtil.USER_AVATAR != null) {
            mUserAvatar.setImageBitmap(ApplicationConfigUtil.USER_AVATAR);
        }
    }

    @Override
    public void registerViewComponentsAffairs() {
        mUserAvatar.setOnClickListener(v -> {
            mDLParentView.openDrawer(GravityCompat.START);
        });
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float offset = mAppBarHeight + verticalOffset;
            float ratio = offset / mAppBarHeight;
            mCollapsingToolbarLayout.setAlpha(ratio);
        });
    }

    @Override
    public void getLayoutComponentsAttr() {
        /* 这里我们全部隐藏 */
        mAppBarHeight = mAppBarLayout.getHeight();
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }
}
