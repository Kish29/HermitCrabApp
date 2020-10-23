/*package com.kish2.hermitcrabapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.custom.view.CustomRefreshView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTest extends BaseActivity implements IBaseView {

    *//*@BindView(R.id.ly_refresh_view)
    CustomRefreshView customRefreshView;

    @BindView(R.id.list_view)
    ListView listView;
    ArrayAdapter<String> adapter;
    String[] items = {"A", "B", "C"};*//*

    @BindView(R.id.btn_left)
    Button left;
    @BindView(R.id.btn_right)
    Button right;
    @BindView(R.id.drawer_left_content)
    RelativeLayout leftContent;
    @BindView(R.id.drawer_main_content)
    ConstraintLayout mainContent;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout_test);

        *//*customRefreshView = findViewById(R.id.ly_refresh_view);
        listView = findViewById(R.id.list_view);*//*
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d("width", String.valueOf(leftContent.getWidth()));
    }

    @Override
    public void initView() {
        setSinkStatusBar(false, true);
        *//*customRefreshView.setRefresh_type(CustomRefreshView.REFRESH_TYPE.NORMAL_REFRESH);
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
        });*//*

        String TAG = "drawerListener";
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                mainContent.setTranslationX(leftContent.getWidth() * slideOffset);
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
}*/


package com.kish2.hermitcrabapp.view.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.kish2.hermitcrabapp.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("Registered")
public class MyTest extends AppCompatActivity {
    private ListView mListView;
    private RelativeLayout mTitle;
    private int mTouchSlop;
    private ArrayAdapter mAdapter;
    private float mFirstY;
    private float mCurrentY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.test_for_up_glide);
        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();

        initViews();
        showHideTitleBar(true);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews() {
        mListView = (ListView) findViewById(R.id.id_lv);
        mTitle = (RelativeLayout) findViewById(R.id.id_title);
        String[] items = new String[26];
        for (int i = 0; i < 26; i++) {
            items[i] = String.valueOf((char) (i + 96));
        }
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        mListView.setAdapter(mAdapter);
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
                            System.out.println("mtouchislop:" + mTouchSlop);
                            // 下滑 显示titleBar
                            showHideTitleBar(true);
                        } else if (mFirstY - mCurrentY > mTouchSlop) {
                            // 上滑 隐藏titleBar
                            showHideTitleBar(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
    }

    private Animator mAnimatorTitle;
    private Animator mAnimatorContent;

    private void showHideTitleBar(boolean tag) {
        if (mAnimatorTitle != null && mAnimatorTitle.isRunning()) {
            mAnimatorTitle.cancel();
        }
        if (mAnimatorContent != null && mAnimatorContent.isRunning()) {
            mAnimatorContent.cancel();
        }
        if (tag) {
            mAnimatorTitle = ObjectAnimator.ofFloat(mTitle, "translationY", mTitle.getTranslationY(), 0);
            mAnimatorContent = ObjectAnimator.ofFloat(mListView, "translationY", mListView.getTranslationY(), mTitle.getHeight());

        } else {
            mAnimatorTitle = ObjectAnimator.ofFloat(mTitle, "translationY", mTitle.getTranslationY(), -mTitle.getHeight());
            mAnimatorContent = ObjectAnimator.ofFloat(mListView, "translationY", mListView.getTranslationY(), 0);
        }
        mAnimatorTitle.start();
        mAnimatorContent.start();

    }


    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 'A'; i < 'Z'; i++) {
            data.add(String.valueOf((char) i));
        }
        return data;
    }


}
