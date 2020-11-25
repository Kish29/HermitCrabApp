package com.kish2.hermitcrabapp.view.fragments.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapters.InformAdapter;
import com.kish2.hermitcrabapp.custom.view.CustomSwipeRefreshLayout;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.fragments.LatestPresenter;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FLatest extends FHomeBase {

    /*@BindView(R.id.tv_sub_fragment)
    TextView textView;*/

    @BindView(R.id.srl_refresh_list)
    CustomSwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_container_items)
    RecyclerView mInformList;

    InformAdapter mInformsAdapter;
    LatestPresenter mPresenter;

    public void setmInformsAdapter(InformAdapter mInformsAdapter) {
        this.mInformsAdapter = mInformsAdapter;
    }

    @Override
    protected void themeChanged() {
        mRefreshLayout.setColorSchemeColors(themeColorId);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.ADAPTER_INIT:
                        mInformList.setAdapter(mInformsAdapter);
                        ToastUtil.showToast(getContext(), "数据加载成功", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
                        break;
                    case MessageForHandler.DATA_UPDATE:
                        mInformsAdapter.notifyDataSetChanged();
                        ToastUtil.showToast(getContext(), "数据更新成功", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
                        break;
                    default:
                        ToastUtil.showToast(getContext(), "数据加载失败", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
                        break;
                }
            }
        };
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("LatestFragment", "onCreateView run.");
        View view = inflater.inflate(R.layout.sub_fragment_content, container, false);
        ButterKnife.bind(this, view);
        /* 主线程 */
        getAndSetLayoutView();
        new Thread() {
            @Override
            public void run() {
                registerViewComponentsAffairs();
            }
        }.start();
        mRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(this::getLayoutComponentsAttr);
        return view;
    }

    @Override
    public void getLayoutComponentsAttr() {

    }

    @Override
    public void getAndSetLayoutView() {
        /* 父Fragment*/
        mHome = (HomeFragment) requireParentFragment();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mInformList.setLayoutManager(layoutManager);
    }

    /* 子线程 */
    @Override
    public void loadData() {
        mRefreshLayout.setRefreshing(true);
        mPresenter.getDataFromModel();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshData() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void registerViewComponentsAffairs() {
        /* 因为onScrollChangedListener的onScrolled方法是回调方法，要等到item停下来时才调用，所以这儿直接监听touch事件 */
        mInformList.setOnTouchListener(this::touchCheck);
        mRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.getDataFromModel();
            mRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void attachPresenter() {
        this.mPresenter = new LatestPresenter(this);
        getLifecycle().addObserver(this.mPresenter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
