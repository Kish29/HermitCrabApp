package com.kish2.hermitcrabapp.view.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.kish2.hermitcrabapp.view.IBaseFragment;
import com.kish2.hermitcrabapp.view.fragments.home.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/* 此类用于设置视图 */
public class SubFragmentContent extends BaseFragment implements IBaseFragment, BGARefreshLayout.BGARefreshLayoutDelegate {

    /*@BindView(R.id.tv_sub_fragment)
    TextView textView;*/

    @BindView(R.id.srl_refresh_list)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.rv_container_items)
    ListView mListView;

    HomeFragment mParent;

    private float mFirstY;
    private float mTouchSlop;
    private float mCurrentY;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("SubFragmentContent", "onCreateView run.");
        View view = inflater.inflate(R.layout.sub_fragment_content, container, false);
        ButterKnife.bind(this, view);

        /* 测试，添加ListView，查看滑动效果 */
        String[] items = new String[2000];
        for (int i = 0; i < 2000; i++) {
            items[i] = String.valueOf((char) i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        mListView.setAdapter(adapter);
        mListView.setVerticalScrollBarEnabled(false);
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mFirstY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurrentY = event.getY();
                        if (mCurrentY - mFirstY > mTouchSlop) {
                            mParent = (HomeFragment) requireParentFragment();
                            // 下滑 显示titleBar
                            mParent.bottomBarHide(false);
                        } else if (mFirstY - mCurrentY > mTouchSlop) {
                            mParent = (HomeFragment) requireParentFragment();
                            // 上滑 隐藏titleBar
                            mParent.bottomBarHide(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                /*因为要让listView继续滑动，所以这儿返回false
                事件未被消费，向下传递，调用onTouchEvent*/
                return false;
            }
        });

        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);

        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        return view;
    }

    @Override
    public void getLayoutComponentsAttr() {

    }

    @Override
    public void getAndSetLayoutView() {

    }

    @Override
    public void loadDataComplete() {

    }

    @Override
    public void registerViewComponentsAffairs() {

    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mRefreshLayout.endRefreshing();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mRefreshLayout.endLoadingMore();
            }
        }.execute();
        return true;
    }
}
