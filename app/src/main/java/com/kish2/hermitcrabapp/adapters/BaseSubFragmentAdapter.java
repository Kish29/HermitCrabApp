package com.kish2.hermitcrabapp.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kish2.hermitcrabapp.view.fragments.SubFragmentContent;

import java.util.ArrayList;
import java.util.List;

public class BaseSubFragmentAdapter extends FragmentPagerAdapter {

    /* 子Fragment适配器 */
    protected SubFragmentContentAdapter subFragmentsAdapter;

    /* 子页面布局fragment适配器
     * 在getItem方法中生成一个新的SubFragmentContent
     * 并且向SubFragmentContent传递数据来实例化页面UI */

    /* 传递的bundle键值 */
    private final static String tsf_key = "page_titles";
    /* pages_titles */
    /* 保存所有页面名称 */
    private List<String> page_tiles;

    BaseSubFragmentAdapter(@NonNull FragmentManager fm, int behavior, List<String> page_tiles) {
        super(fm, behavior);
        setPage_tiles(page_tiles);
    }

    private void setPage_tiles(List<String> page_tiles) {
        this.page_tiles = new ArrayList<>();
        this.page_tiles = page_tiles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        SubFragmentContent content = new SubFragmentContent();
        /* 向子fragment传入数据 */
        Bundle currentPageTile = new Bundle();
        currentPageTile.putString(tsf_key, page_tiles.get(position));
        content.setArguments(currentPageTile);
        return content;
    }

    @Override
    public int getCount() {
        return page_tiles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = page_tiles.get(position);
        if (title == null) {
            title = "";
        } else if (title.length() > 15) {
            title = title.substring(0, 15) + "...";
        }
        return title;
    }
}
