package com.kish2.hermitcrabapp.fragment.impl.home;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapter.RecyclerInformAdapter;
import com.kish2.hermitcrabapp.bean.Inform;
import com.kish2.hermitcrabapp.fragment.IBaseFragment;
import com.kish2.hermitcrabapp.fragment.impl.HomeFragmentImpl;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

public class LatestFragment extends HomeBaseFragment implements IBaseFragment, BGARefreshLayout.BGARefreshLayoutDelegate {

    /*@BindView(R.id.tv_sub_fragment)
    TextView textView;*/

    @BindView(R.id.bga_refresh_layout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.lv_child)
    RecyclerView mInformList;


    private float mFirstY;
    private float mTouchSlop;
    private float mCurrentY;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    }

    class MyThread implements Runnable {

        ArrayList<Inform> data;

        public MyThread(ArrayList<Inform> data) {
            this.data = data;
        }

        @Override
        public void run() {
            /* 测试，添加ListView，查看滑动效果 */
            for (int i = 0; i < 20; i++) {
                Inform inform = new Inform();
                inform.setTitle("这是标题");
                inform.setDate(new Date());
                inform.setImgSrc("这是图片");
                this.data.add(inform);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("SubFragmentContent", "onCreateView run.");
        View view = inflater.inflate(R.layout.sub_fragment_content, container, false);
        ButterKnife.bind(this, view);

        mHome = (HomeFragmentImpl) requireParentFragment();
        ArrayList<Inform> informArrayList = new ArrayList<>();

        new Thread(new MyThread(informArrayList)).start();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerInformAdapter adapter = new RecyclerInformAdapter(informArrayList, getContext());
        mInformList.setLayoutManager(linearLayoutManager);
        mInformList.setAdapter(adapter);
        mInformList.setVerticalScrollBarEnabled(false);
        mInformList.setOnTouchListener(new View.OnTouchListener() {
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
                            mHome.topBarUpGlide(false);
                        } else if (mFirstY - mCurrentY > mTouchSlop) {
                            // 上滑 隐藏titleBar
                            mHome.topBarUpGlide(true);
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
    public void setPaddingTopForStatusBar(View v) {

    }

    @Override
    public void setPageTitles() {

    }

    @Override
    public void setViewPagerOfScreenLimit() {

    }

    @Override
    public void initView() {

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
