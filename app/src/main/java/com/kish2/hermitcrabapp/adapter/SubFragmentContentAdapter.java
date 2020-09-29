package com.kish2.hermitcrabapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/* 子页面布局fragment适配器
 * 通过对SubFragmentContent传递数据来实例化页面UI */
public class SubFragmentContentAdapter extends BaseSubFragmentAdapter {

    public SubFragmentContentAdapter(@NonNull FragmentManager fm, int behavior, List<String> page_titles) {
        super(fm, behavior, page_titles);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
