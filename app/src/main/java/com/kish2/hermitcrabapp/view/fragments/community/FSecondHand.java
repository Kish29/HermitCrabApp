package com.kish2.hermitcrabapp.view.fragments.community;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.custom.view.CustomSwipeRefreshLayout;
import com.kish2.hermitcrabapp.presenter.fragments.SecondHandPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class FSecondHand extends FCommunityBase {
    @BindView(R.id.srl_refresh_list)
    CustomSwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_container_items)
    RecyclerView mSecondHandList;

    public CustomSwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return mSecondHandList;
    }

    SecondHandPresenter mPresenter;

    @Override
    protected void themeChanged() {
        refreshLayout.setColorSchemeColors(themeColorId);
    }

    @Override
    public void initHandler() {
       /* mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.DATA_LOADING:
                        refreshLayout.setRefreshing(true);
                        break;
                    case MessageForHandler.ADAPTER_INIT:
                        mSecondHandList.setAdapter((SecondHandListAdapter) msg.obj);
                    case MessageForHandler.DATA_LOADED:
                    case MessageForHandler.DATA_UPDATE:
                    default:
                        refreshLayout.setRefreshing(false);
                        break;
                }
            }
        };*/
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fSecond = inflater.inflate(R.layout.sub_fragment_content, container, false);
        ButterKnife.bind(this, fSecond);
        getAndSetLayoutView();
        registerViewComponentsAffairs();
        refreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(this::getLayoutComponentsAttr);
        return fSecond;
    }

    @Override
    public void getLayoutComponentsAttr() {

    }

    @Override
    public void getAndSetLayoutView() {
        /* 父Fragment*/
        mCommunity = (CommunityFragment) requireParentFragment();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mSecondHandList.setLayoutManager(layoutManager);
        /*StaggeredLayout不需要设置该项
        mInformList.setVerticalScrollBarEnabled(false);*/
    }

    @Override
    public void loadData() {
        mPresenter.initDataAdapter();
        mPresenter.registerItemEvent();
    }

    @Override
    public void refreshData() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void registerViewComponentsAffairs() {
        /* 因为onScrollChangedListener的onScrolled方法是回调方法，要等到item停下来时才调用，所以这儿直接监听touch事件 */
        mSecondHandList.setOnTouchListener(this::touchCheck);

        refreshLayout.setOnRefreshListener(() -> {
            mPresenter.getDataFromModel();
            refreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void attachPresenter() {
        this.mPresenter = new SecondHandPresenter(this);
        getLifecycle().addObserver(this.mPresenter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
