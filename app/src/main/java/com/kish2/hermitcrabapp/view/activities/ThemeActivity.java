package com.kish2.hermitcrabapp.view.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.tabs.TabLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.custom.StatusFixedToolBar;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.utils.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.ThemeUtil;
import com.kish2.hermitcrabapp.utils.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemeActivity extends BaseActivity {

    @BindView(R.id.sft_action_bar)
    StatusFixedToolBar mToolBar;
    @BindView(R.id.tl_color_list)
    TabLayout mThemeList;
    @BindView(R.id.btn_theme_save)
    Button mSave;

    private static int THEME_SELECT_ID = -1;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.LOCAL_DATA_LOADED:
                        initColorList();
                        break;
                    case MessageForHandler.SYSTEM_FAILURE:
                    default:
                        break;
                }
            }
        };

        setContentView(R.layout.activity_theme);
        ButterKnife.bind(this);

        getAndSetLayoutView();
        new Thread() {
            @Override
            public void run() {
                registerViewComponentsAffairs();
                loadData();
            }
        }.start();
    }


    TabLayout.Tab[] mItems;

    @Override
    public void getAndSetLayoutView() {
        StatusBarUtil.setSinkStatusBar(this, false, ThemeUtil.Theme.afterGetResourcesColorId);
        mToolBar.bindAndSetThisToolbar(this, true, "主题颜色");
        mItems = new TabLayout.Tab[9];
        for (int i = 0; i < 9; i++) {
            mItems[i] = mThemeList.newTab();
        }
    }


    private void initColorList() {
        for (int i = 0; i < 9; i++) {
            mThemeList.addTab(mItems[i]);
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public void loadData() {
        for (int i = 0; i < 9; i++) {
            View view = getLayoutInflater().inflate(R.layout.ly_item_theme, null);
            View colorBlock = view.findViewById(R.id.v_color_block);
            colorBlock.setBackgroundColor(ContextCompat.getColor(this, ThemeUtil.AppTheme[i]));
            mItems[i].setCustomView(view);
        }
        mHandler.sendEmptyMessage(MessageForHandler.LOCAL_DATA_LOADED);
    }

    @Override
    public void registerViewComponentsAffairs() {
        mToolBar.setNavigationOnClickListener(v -> {
            finish();
        });
        mThemeList.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                THEME_SELECT_ID = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mSave.setOnClickListener(v -> {
            if (THEME_SELECT_ID != ThemeUtil.THEME_ID) {
                SharedPreferences theme_config = getSharedPreferences(ThemeUtil.THEME_CONFIG_FILE_NAME, MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = theme_config.edit();
                editor.putInt(ThemeUtil.KEY_COLOR, THEME_SELECT_ID);
                if (editor.commit()) {
                    ToastUtil.showToast(this, "修改主题成功，重启后生效", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
                } else {
                    ToastUtil.showToast(this, "修改主题失败", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
                }
            }
        });
    }


    @Override
    public void getLayoutComponentsAttr() {
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }
}
