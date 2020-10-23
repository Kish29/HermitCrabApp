package com.kish2.hermitcrabapp.view.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.custom.view.StatusFixedToolBar;
import com.kish2.hermitcrabapp.utils.dev.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileActivity extends BaseActivity {

    @BindView(R.id.sft_toolbar_top)
    StatusFixedToolBar mToolBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ButterKnife.bind(this);
        getAndSetLayoutView();
        new Thread() {
            @Override
            public void run() {
                registerViewComponentsAffairs();
            }
        }.start();
    }

    @Override
    public void getAndSetLayoutView() {
        StatusBarUtil.setSinkStatusBar(this, false, ThemeUtil.Theme.afterGetResourcesColorId);
        mToolBar.bindAndSetThisToolbar(this, true, "个人信息");
    }

    @Override
    public void getLayoutComponentsAttr() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void registerViewComponentsAffairs() {
        mToolBar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }
}
