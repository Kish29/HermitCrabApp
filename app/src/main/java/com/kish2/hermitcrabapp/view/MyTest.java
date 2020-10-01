package com.kish2.hermitcrabapp.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.custom.CustomRefreshView;
import com.kish2.hermitcrabapp.custom.Refresh;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTest extends BaseActivity implements IBaseView {

    @BindView(R.id.custom_refresh_view)
    Refresh customRefreshView;

    @BindView(R.id.list_view)
    ListView listView;
    ArrayAdapter<String> adapter;
    String[] items = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_test);

        /*customRefreshView = findViewById(R.id.custom_refresh_view);
        listView = findViewById(R.id.list_view);*/
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void initView() {
        setSinkStatusBar(true, true);
        customRefreshView.setRefresh_type(Refresh.REFRESH_TYPE.POP_REFRESH);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        customRefreshView.setOnRefreshListener(new Refresh.RefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                customRefreshView.refreshComplete();
            }
        });
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }
}
