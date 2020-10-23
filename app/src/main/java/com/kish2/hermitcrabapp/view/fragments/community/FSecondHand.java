package com.kish2.hermitcrabapp.view.fragments.community;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapters.RecyclerInformAdapter;
import com.kish2.hermitcrabapp.custom.view.CustomSwipeRefreshLayout;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.fragments.SecondHandPresenter;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FSecondHand extends FCommunityBase {
    @BindView(R.id.srl_refresh_list)
    CustomSwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_container_items)
    RecyclerView mProductsList;

    RecyclerInformAdapter mProductsAdapter;

    public void setmProductsAdapter(RecyclerInformAdapter mProductsAdapter) {
        this.mProductsAdapter = mProductsAdapter;
    }

    SecondHandPresenter mPresenter;

    private float mFirstY;
    private float mTouchSlop;
    private float mCurrentY;

    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.ADAPTER_INIT:
                        mProductsList.setAdapter(mProductsAdapter);
                        ToastUtil.showToast(getContext(), "数据加载成功", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
                        break;
                    case MessageForHandler.DATA_UPDATE:
                        mProductsAdapter.notifyDataSetChanged();
                        ToastUtil.showToast(getContext(), "数据加载成功", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
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
        Log.d("SecondHand", "onCreateView run.");
        View view = inflater.inflate(R.layout.sub_fragment_content, container, false);
        ButterKnife.bind(this, view);
        /* 主线程 */
        getAndSetLayoutView();
        mRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getLayoutComponentsAttr();
            }
        });
        return view;
    }

    @Override
    public void getLayoutComponentsAttr() {

    }

    @Override
    public void getAndSetLayoutView() {
        /* 父Fragment*/
        mCommunity = (CommunityFragment) requireParentFragment();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mProductsList.setLayoutManager(layoutManager);
        /*StaggeredLayout不需要设置该项
        mInformList.setVerticalScrollBarEnabled(false);*/
        mProductsList.setAdapter(mProductsAdapter);
    }

    @Override
    public void loadData() {
        registerViewComponentsAffairs();
        mRefreshLayout.setRefreshing(true);
        mPresenter.getData();
        mRefreshLayout.setRefreshing(false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void registerViewComponentsAffairs() {
        /* 因为onScrollChangedListener的onScrolled方法是回调方法，要等到item停下来时才调用，所以这儿直接监听touch事件 */
        mProductsList.setOnTouchListener(this::touchCheck);

        mRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.getData();
            mRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void attachPresenter() {
        this.mPresenter = new SecondHandPresenter(this);
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
