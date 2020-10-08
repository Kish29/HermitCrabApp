package com.kish2.hermitcrabapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kish2.hermitcrabapp.fragment.impl.HomeFragmentImpl;
import com.kish2.hermitcrabapp.fragment.impl.home.AcademicAffairsFragment;
import com.kish2.hermitcrabapp.fragment.impl.home.LatestFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentAdapter extends FragmentPagerAdapter {

    /* 页面标题*/
    private List<String> pageTitles;

    public HomeFragmentAdapter(@NonNull FragmentManager fm, int behavior, List<String> pageTitles) {
        super(fm, behavior);
        this.pageTitles = new ArrayList<>();
        this.pageTitles = pageTitles;
    }

    /* 返回的fragment视图 */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new AcademicAffairsFragment();
                break;
            case 0:
            default:
                fragment = new LatestFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return pageTitles.size();
    }

    /* 页面标题 */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles.get(position);
    }
}
