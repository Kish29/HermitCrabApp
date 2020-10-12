package com.kish2.hermitcrabapp.adapters.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kish2.hermitcrabapp.view.fragments.community.FSecondHand;
import com.kish2.hermitcrabapp.view.fragments.community.FTopic;
import com.kish2.hermitcrabapp.view.fragments.home.FAcademicAffairs;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragmentAdapter extends FragmentStatePagerAdapter {

    /* 页面标题*/
    private List<String> pageTitles;

    public CommunityFragmentAdapter(@NonNull FragmentManager fm, int behavior, List<String> pageTitles) {
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
                fragment = new FTopic();
                break;
            case 0:
            default:
                fragment = new FSecondHand();
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
