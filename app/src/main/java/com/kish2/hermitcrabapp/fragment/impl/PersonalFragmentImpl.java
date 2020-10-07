package com.kish2.hermitcrabapp.fragment.impl;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.fragment.BaseFragment;
import com.kish2.hermitcrabapp.fragment.IBaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalFragmentImpl extends BaseFragment implements IBaseFragment {

    /**
     * @获取要用到的所有控件
     */
    // 顶部条
    @BindView(R.id.top_retrieve_bar)
    RelativeLayout mTopRetrieveBar;
    // 左上角菜单
    ImageButton mSideMenu;
    ImageButton mTheme;
    ImageButton mSetting;
    /* 底部导航条*/
    ConstraintLayout mBottomNavTabBar;
    /* 底部导航条高度*/
    private static int mBottomBarHeight = 0;


    // 用户展示界面布局
    @BindView(R.id.ll_personal_user)
    ConstraintLayout mUserShow;
    // 高度
    private int mUserShowHeight;
    // 用户头像
    @BindView(R.id.riv_user_avatar)
    RoundedImageView mUserAvatar;
    @BindView(R.id.tv_username)
    TextView mUsername;
    @BindView(R.id.tv_user_student_id)
    TextView mUserStdId;
    @BindView(R.id.iv_user_gender)
    ImageView mUserGender;

    /* 简短展示页面*/
    @BindView(R.id.tv_user_friend)
    TextView mUserFriend;
    @BindView(R.id.tv_user_product)
    TextView mUserProduct;
    @BindView(R.id.tv_user_topic)
    TextView mUserTopic;

    /* 主要展示页面*/
    @BindView(R.id.cl_personal_main)
    ConstraintLayout mPersonalMain;

    /* 发布中心*/
    @BindView(R.id.ll_old_publish)
    View mOldPublish;
    /* 快速入口*/
    /* 学生服务*/

    // 滑动监听layer
    @BindView(R.id.v_personal_layer)
    View mLayer;

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

        /*setPaddingTopForStatusBar(fragmentPersonal);*/
        initView();
        return fragmentPersonal;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setViewPagerOfScreenLimit() {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        mBottomNavTabBar = requireActivity().findViewById(R.id.ly_bottom_tab_bar);
        mUserShow.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mUserShowHeight = mUserShow.getHeight();
                mBottomBarHeight = mBottomNavTabBar.getHeight();
                mPersonalMain.setTranslationY(mUserShowHeight);
            }
        });

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mPersonalMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mFirstY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurrentY = event.getY();
                        if (mCurrentY - mFirstY > mTouchSlop) {
                            // 下滑 显示titleBar
                            viewGlide(false, mUserShow, mUserShowHeight, mPersonalMain, mBottomNavTabBar, mBottomBarHeight);
                        } else if (mFirstY - mCurrentY > mTouchSlop) {
                            // 上滑 隐藏titleBar
                            viewGlide(true, mUserShow, mUserShowHeight, mPersonalMain, mBottomNavTabBar, mBottomBarHeight);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                /* 不向下传递*/
                return true;
            }
        });

        mOldPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("发布旧商品");
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
    }

    @Override
    public void setPageTitles() {
    }
}
