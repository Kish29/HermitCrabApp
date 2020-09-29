package com.kish2.hermitcrabapp.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kish2.hermitcrabapp.fragment.impl.CommunityFragmentImpl;
import com.kish2.hermitcrabapp.fragment.impl.HomeFragmentImpl;
import com.kish2.hermitcrabapp.fragment.impl.MessageFragmentImpl;
import com.kish2.hermitcrabapp.fragment.impl.PersonalFragmentImpl;
import com.kish2.hermitcrabapp.fragment.impl.ServiceFragmentImpl;

public class MainFragmentAdapter extends FragmentPagerAdapter {

    public MainFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d("position", String.valueOf(position));
        /* fragment具有预加载功能，并且最多预加载3个（左、中、右）*/
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new CommunityFragmentImpl();
                break;
            case 2:
                fragment = new ServiceFragmentImpl();
                break;
            case 3:
                fragment = new MessageFragmentImpl();
                break;
            case 4:
                fragment = new PersonalFragmentImpl();
                break;
            case 0:
            default:
                fragment = new HomeFragmentImpl();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
