package com.kish2.hermitcrabapp.view.fragments.community;

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

public class CommunityFragment extends BaseFragment implements IBaseFragment {

    /* 父根布局 */
    DrawerLayout mDLParentView;

    /* 顶部导航条*/
    @BindView(R.id.top_retrieve_bar)
    ViewGroup mTopRetrieveBar;
    /* 顶部导航条高度*/
    private static int mTopBarHeight = 0;
    /* 用户头像*/
    RoundedImageView mUserAvatar;
    /* 搜索栏*/
    SearchView mSearch;
    /* 筛选栏*/
    /*ImageButton mFilter*/

    @BindView(R.id.tl_category_tab)
    TabLayout mNavTabBar;
    /* retrieve bar的高度 */
    private float mRBHeight;

    @BindView(R.id.vp_content_list)
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
        getLayoutComponentsAttr();
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
        /* 绑定ViewPager */
        mNavTabBar.setupWithViewPager(mVPSubCommunity);

        mDLParentView = requireActivity().findViewById(R.id.dl_maint_activity);
        mPaddingTopLayoutParam = (ViewGroup.MarginLayoutParams) mPaddingTop.getLayoutParams();

        /* 获取顶部retrieveBar的几个部件*/
        mUserAvatar = mTopRetrieveBar.findViewById(R.id.riv_side_menu);
        mSearch = mTopRetrieveBar.findViewById(R.id.sv_search);
        mRBHeight = -mTopRetrieveBar.getHeight();

        mUserAvatar.setOnClickListener(v -> {
            mDLParentView.openDrawer(GravityCompat.START);
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void registerViewComponentsAffairs() {

    }

    @Override
    public void getLayoutComponentsAttr() {

    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }

}
