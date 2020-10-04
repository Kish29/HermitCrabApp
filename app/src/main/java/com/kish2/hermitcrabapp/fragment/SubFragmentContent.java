package com.kish2.hermitcrabapp.fragment;

import android.annotation.SuppressLint;
import android.icu.text.CaseMap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.custom.CustomRefreshView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/* 此类用于设置视图 */
public class SubFragmentContent extends BaseFragment implements IBaseFragment, BGARefreshLayout.BGARefreshLayoutDelegate {

    /*@BindView(R.id.tv_sub_fragment)
    TextView textView;*/

    @BindView(R.id.bga_refresh_layout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.lv_child)
    ListView mListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("SubFragmentContent", "onCreateView run.");
        View view = inflater.inflate(R.layout.sub_fragment_content, container, false);
        ButterKnife.bind(this, view);


        /* 测试，添加ListView，查看滑动效果 */
        String[] items = new String[26];
        for (int i = 0; i < 26; i++) {
            items[i] = String.valueOf((char) (i + 96));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        mListView.setAdapter(adapter);
        mListView.setVerticalScrollBarEnabled(false);

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
