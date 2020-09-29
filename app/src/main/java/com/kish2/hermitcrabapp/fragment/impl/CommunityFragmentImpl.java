package com.kish2.hermitcrabapp.fragment.impl;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.fragment.BaseFragment;
import com.kish2.hermitcrabapp.fragment.IBaseFragment;

import butterknife.ButterKnife;

public class CommunityFragmentImpl extends BaseFragment implements IBaseFragment {

    /* 这三个方法必须重写 */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("tag", "Community createView run.");

        View pagerView = inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, pagerView);

        return pagerView;
    }

    @Override
    public void onPause() {
        super.onPause();
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

    @Override
    public void setPaddingTopForStatusBar(View v) {

    }
}
