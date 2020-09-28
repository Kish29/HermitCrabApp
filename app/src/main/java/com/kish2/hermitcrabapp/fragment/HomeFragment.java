package com.kish2.hermitcrabapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.kish2.hermitcrabapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements BaseFragment {

    @BindView(R.id.vp_tab_bar)
    TableLayout mNavTabBar;
    @BindView(R.id.vp_main)
    ViewPager mViewMain;

    /* 这三个方法必须重写 */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /* 绑定xml视图 */
        View pagerView = inflater.inflate(R.layout.fragment_home, container, false);// 视图与父容器ViewGroup不需要连接
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
}
