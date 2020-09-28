package com.kish2.hermitcrabapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kish2.hermitcrabapp.fragment.CommunityFragment;
import com.kish2.hermitcrabapp.fragment.HomeFragment;
import com.kish2.hermitcrabapp.fragment.MessageFragment;
import com.kish2.hermitcrabapp.fragment.PersonalFragment;
import com.kish2.hermitcrabapp.fragment.ServiceFragment;

public class MainFragmentAdapter extends FragmentPagerAdapter {

    public MainFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new CommunityFragment();
                break;
            case 2:
                fragment = new ServiceFragment();
                break;
            case 3:
                fragment = new MessageFragment();
                break;
            case 4:
                fragment = new PersonalFragment();
                break;
            default:
                fragment = new HomeFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
