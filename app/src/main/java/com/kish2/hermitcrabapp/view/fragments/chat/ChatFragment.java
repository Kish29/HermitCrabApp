package com.kish2.hermitcrabapp.view.fragments.chat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapters.RecyclerInformAdapter;
import com.kish2.hermitcrabapp.custom.view.CustomSwipeRefreshLayout;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.fragments.ChatPresenter;
import com.kish2.hermitcrabapp.utils.dev.ApplicationConfigUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindView(R.id.ly_message_category)
    LinearLayout mChatCategory;
    @BindView(R.id.srl_refresh_list)
    CustomSwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_messages_list)
    RecyclerView mChatList;

    RecyclerInformAdapter mChatsAdapter;
    ChatPresenter mPresenter;


    public void setmChatsAdapter(RecyclerInformAdapter mChatsAdapter) {
        this.mChatsAdapter = mChatsAdapter;
    }


    @Override
    protected void themeChanged() {
        /* 设置AppBarLayout的颜色 */
        mAppBarLayout.setBackgroundColor(themeColorId);
        mRefreshLayout.setColorSchemeColors(themeColorId);
    }

    /* 这三个方法必须重写 */
    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.DATA_LOADING:
                        mRefreshLayout.setRefreshing(true);
                        break;
                    case MessageForHandler.DATA_LOADED:
                        mRefreshLayout.setRefreshing(false);
                        break;
                    case MessageForHandler.ADAPTER_INIT:
                        mChatList.setAdapter(mChatsAdapter);
                        ToastUtil.showToast(getContext(), "数据加载成功", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
                        break;
                    case MessageForHandler.DATA_UPDATE:
                        mChatsAdapter.notifyDataSetChanged();
                        ToastUtil.showToast(getContext(), "数据更新成功", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
                        break;
                    default:
                        ToastUtil.showToast(getContext(), "数据加载失败", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
                        break;
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentMessage = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, fragmentMessage);

        getAndSetLayoutView();
        registerViewComponentsAffairs();
        mRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(this::getLayoutComponentsAttr);
        return fragmentMessage;
    }

    /**
     * @因为onPause后，FragmentManager会对组件进行重绘，某些组件会回到原始的属性
     * @而如果在之间调用了隐藏动画，那么会造成冲突，视图UI组件会消失
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void getLayoutComponentsAttr() {
        mUserAvatar.setOnClickListener(v -> {
            mDLParentView.openDrawer(GravityCompat.START);
        });
        mChatList.setOnTouchListener(this::touchCheck);
        mRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.loadDataFromServer();
            mRefreshLayout.setRefreshing(false);
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
        mHandler.sendEmptyMessage(MessageForHandler.DATA_LOADING);
        mPresenter.loadDataFromServer();
        mHandler.sendEmptyMessage(MessageForHandler.DATA_LOADED);
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

        mRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.loadDataFromServer();
            mRefreshLayout.setRefreshing(false);
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
    }

    @Override
    public void detachPresenter() {
        this.mPresenter.detachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachPresenter();
    }
}
