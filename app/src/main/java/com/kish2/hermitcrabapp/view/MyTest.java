package com.kish2.hermitcrabapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.custom.CustomRefreshView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTest extends BaseActivity implements IBaseView {

    /*@BindView(R.id.custom_refresh_view)
    CustomRefreshView customRefreshView;

    @BindView(R.id.list_view)
    ListView listView;
    ArrayAdapter<String> adapter;
    String[] items = {"A", "B", "C"};*/

    @BindView(R.id.btn_left)
    Button left;
    @BindView(R.id.btn_right)
    Button right;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout_test);

        /*customRefreshView = findViewById(R.id.custom_refresh_view);
        listView = findViewById(R.id.list_view);*/
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void initView() {
        setSinkStatusBar(true, true);
        /*customRefreshView.setRefresh_type(CustomRefreshView.REFRESH_TYPE.NORMAL_REFRESH);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        customRefreshView.setOnRefreshListener(new CustomRefreshView.RefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                customRefreshView.refreshComplete();
            }
        });*/

        String TAG = "drawerListener";
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                Log.d(TAG, "onDrawerSlide");
                Log.d(TAG, String.valueOf(left.getTranslationX()));
                Log.d(TAG, String.valueOf(right.getTranslationX()));
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                Log.d(TAG, "onDrawerOpened");
                Log.d(TAG, String.valueOf(left.getTranslationX()));
                Log.d(TAG, String.valueOf(right.getTranslationX()));
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Log.d(TAG, "onDrawerClosed");
                Log.d(TAG, String.valueOf(left.getTranslationX()));
                Log.d(TAG, String.valueOf(right.getTranslationX()));
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.d(TAG, "onDrawerStateChanged");
                Log.d(TAG, String.valueOf(left.getTranslationX()));
                Log.d(TAG, String.valueOf(right.getTranslationX()));
            }
        });

        left.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
        right.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.END);
        });
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }
}
