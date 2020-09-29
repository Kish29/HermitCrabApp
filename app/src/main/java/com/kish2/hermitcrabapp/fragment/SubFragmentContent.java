package com.kish2.hermitcrabapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/* 此类用于设置视图 */
public class SubFragmentContent extends BaseFragment implements IBaseFragment {

    @BindView(R.id.tv_sub_fragment)
    TextView textView;

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
        return view;
    }

    @Override
    public void setPaddingTopForStatusBar(View v) {

    }

    @Override
    public void setPageTitles() {

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
}
