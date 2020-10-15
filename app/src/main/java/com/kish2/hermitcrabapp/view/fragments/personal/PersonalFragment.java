package com.kish2.hermitcrabapp.view.fragments.personal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.utils.ThemeUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.kish2.hermitcrabapp.view.activities.LoginActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalFragment extends BaseFragment {

    /**
     * @获取要用到的所有控件
     */
    /* 主要展示页面*/
    @BindView(R.id.ns_personal_main)
    NestedScrollView mNSPersonalMain;

    // 顶部条容器
    @BindView(R.id.abl_retrieve_bar_container)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.top_retrieve_bar)
    RelativeLayout mTopRetrieveBar;
    @BindView(R.id.ctl_banner_container)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private static float mCollapsingHeight = 0;

    // 左上角菜单
    ImageButton mSideMenu;
    ImageButton mTheme;
    ImageButton mSetting;


    // 用户展示界面布局
    @BindView(R.id.ll_personal_user)
    ConstraintLayout mUserBanner;
    // 用户头像
    @BindView(R.id.rl_user_avatar)
    RelativeLayout mUserAvatar;
    @BindView(R.id.tv_username)
    TextView mUsername;
    @BindView(R.id.tv_user_student_id)
    TextView mUserStdId;
    @BindView(R.id.iv_user_gender)
    RoundedImageView mUserGender;

    /* 简短展示页面*/
    @BindView(R.id.tv_user_friend)
    TextView mUserFriend;
    @BindView(R.id.tv_user_product)
    TextView mUserProduct;
    @BindView(R.id.tv_user_topic)
    TextView mUserTopic;


    /* 发布中心*/
    @BindView(R.id.ll_old_publish)
    View mOldPublish;
    /* 快速入口*/
    /* 学生服务*/

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
        Log.d("tag", "PersonalFragment createView run.");

        View fragmentPersonal = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, fragmentPersonal);

        getAndSetLayoutView();
        new Thread() {
            @Override
            public void run() {
                registerViewComponentsAffairs();
            }
        }.start();
        mUserBanner.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getLayoutComponentsAttr();
            }
        });
        return fragmentPersonal;
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
    public void getLayoutComponentsAttr() {
        mCollapsingHeight = mCollapsingToolbarLayout.getHeight();
    }

    @Override
    public void getAndSetLayoutView() {
        setPaddingTopForStatusBarHeight(mAppBarLayout);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        if (ThemeUtil.Theme.colorId != -1)  // -1表示使用透明主题
            mAppBarLayout.setBackgroundColor(getResources().getColor(ThemeUtil.Theme.colorId));
        else mAppBarLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    @Override
    public void loadData() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void registerViewComponentsAffairs() {
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float offset = mCollapsingHeight + verticalOffset;
            float ratio = offset / mCollapsingHeight;
            mCollapsingToolbarLayout.setAlpha(ratio);
        });

        mNSPersonalMain.setOnTouchListener(this::touchCheck);

        mOldPublish.setOnClickListener(v -> System.out.println("发布旧商品"));

        mUsername.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }

}
