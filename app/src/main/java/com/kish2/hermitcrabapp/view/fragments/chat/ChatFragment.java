package com.kish2.hermitcrabapp.view.fragments.chat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapters.ChatListAdapter;
import com.kish2.hermitcrabapp.custom.view.CustomSwipeRefreshLayout;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.fragments.ChatPresenter;
import com.kish2.hermitcrabapp.utils.dev.ApplicationConfigUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class ChatFragment extends BaseFragment {
    /* 顶部导航条*/
    @BindView(R.id.top_retrieve_bar)
    ViewGroup mTopRetrieveBar;
    @BindView(R.id.ctl_banner_container)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private static float mCollapsingHeight = 0;
    /* 顶部导航条AppBarLayout */
    @BindView(R.id.abl_retrieve_bar_container)
    AppBarLayout mAppBarLayout;

    /* 用户头像*/
    RoundedImageView mUserAvatar;

    @BindView(R.id.srl_refresh_list)
    CustomSwipeRefreshLayout refreshLayout;


    @BindView(R.id.rv_messages_list)
    RecyclerView mChatList;

    public RecyclerView getRecyclerView() {
        return mChatList;
    }

    ChatPresenter mPresenter;

    public CustomSwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    @Override
    protected void themeChanged() {
        /* 设置AppBarLayout的颜色 */
        mAppBarLayout.setBackgroundColor(themeColorId);
        refreshLayout.setColorSchemeColors(themeColorId);
    }

    @Override
    public void initHandler() {
        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.DATA_LOADING:
                        refreshLayout.setRefreshing(true);
                        break;
                    case MessageForHandler.ADAPTER_INIT:
                        mChatList.setAdapter((ChatListAdapter) msg.obj);
                    case MessageForHandler.DATA_LOADED:
                    case MessageForHandler.DATA_UPDATE:
                    default:
                        refreshLayout.setRefreshing(false);
                        break;
                }
            }
        };
    }

    /* 这三个方法必须重写 */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentMessage = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, fragmentMessage);

        getAndSetLayoutView();
        registerViewComponentsAffairs();
        refreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(this::getLayoutComponentsAttr);
        return fragmentMessage;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void getLayoutComponentsAttr() {
        mUserAvatar.setOnClickListener(v -> {
            mDLParentView.openDrawer(GravityCompat.START);
        });
        mChatList.setOnTouchListener(this::touchCheck);
        refreshLayout.setOnRefreshListener(() -> {
            mPresenter.getDataFromModel();
            refreshLayout.setRefreshing(false);
        });
        mCollapsingHeight = mCollapsingToolbarLayout.getHeight();
    }

    @Override
    public void getAndSetLayoutView() {
        setPaddingTopForStatusBarHeight(mAppBarLayout);
        /* 获取顶部retrieveBar的几个部件*/
        mUserAvatar = mTopRetrieveBar.findViewById(R.id.riv_side_menu);
        /* 设置AppBarLayout的颜色 */
        mAppBarLayout.setBackgroundColor(ThemeUtil.Theme.afterGetResourcesColorId);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mChatList.setLayoutManager(layoutManager);
    }

    @Override
    public void loadData() {
        mPresenter.initDataAdapter();
        mPresenter.registerItemEvent();
    }

    @Override
    public void refreshData() {
        if (ApplicationConfigUtil.HAS_AVATAR && ApplicationConfigUtil.USER_AVATAR != null) {
            mUserAvatar.setImageBitmap(ApplicationConfigUtil.USER_AVATAR);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void registerViewComponentsAffairs() {
        /* 因为onScrollChangedListener的onScrolled方法是回调方法，要等到item停下来时才调用，所以这儿直接监听touch事件 */
        mChatList.setOnTouchListener(this::touchCheck);

        refreshLayout.setOnRefreshListener(() -> {
            mPresenter.getDataFromModel();
            refreshLayout.setRefreshing(false);
        });
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float offset = mCollapsingHeight + verticalOffset;
            float ratio = offset / mCollapsingHeight;
            mCollapsingToolbarLayout.setAlpha(ratio);
        });
    }

    @Override
    public void attachPresenter() {
        this.mPresenter = new ChatPresenter(this);
        getLifecycle().addObserver(this.mPresenter);
    }
}
