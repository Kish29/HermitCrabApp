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

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapters.viewpager.CommunityFragmentAdapter;
import com.kish2.hermitcrabapp.utils.ThemeUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.kish2.hermitcrabapp.view.IBaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunityFragment extends BaseFragment implements IBaseFragment {

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
    private static int mTopRetrieveBarHeight = 0;
    /* 用户头像*/
    RoundedImageView mUserAvatar;

    /* 浮动搜索按钮 */
    FloatingActionButton mSearch;
    /* 筛选栏*/
    /*ImageButton mFilter*/

    TabLayout mCategoryTab;

    @BindView(R.id.vp_content_list)
    ViewPager mVPSubCommunity;

    CommunityFragmentAdapter communityFragmentAdapter;
    private Handler mHandler;

    /* 这三个方法必须重写 */
    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1:
                        loadDataComplete();
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
    protected void loadData() {
        registerViewComponentsAffairs();
        mVPSubCommunity.setOffscreenPageLimit(VIEW_PAGER_OF_SCREEN_LIMIT);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("最新");
        strings.add("教务处");
        communityFragmentAdapter = new CommunityFragmentAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, strings);
        Message msg = new Message();
        msg.what = 1;
        mHandler.sendMessage(msg);
    }

    @Override
    public void getAndSetLayoutView() {
        /* 设置StatusBar的占位高度 */
        setHeightForStatusBar(mStatusBar);
        /* 设置AppBarLayout的颜色 */
        if (ThemeUtil.Theme.colorId != -1)  // -1表示使用透明主题
            mAppBarLayout.setBackgroundColor(getResources().getColor(ThemeUtil.Theme.colorId));
        /* 获取顶部retrieveBar的几个部件*/
        mUserAvatar = mTopRetrieveBar.findViewById(R.id.riv_side_menu);
        mCategoryTab = mTopRetrieveBar.findViewById(R.id.tl_category_tab);
    }

    @Override
    public void loadDataComplete() {
        mVPSubCommunity.setAdapter(communityFragmentAdapter);
        /* 绑定ViewPager */
        mCategoryTab.setupWithViewPager(mVPSubCommunity);
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
    public void getLayoutComponentsAttr() {
        /* 这里我们全部隐藏 */
        mTopRetrieveBarHeight = mAppBarLayout.getHeight();
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }

}
