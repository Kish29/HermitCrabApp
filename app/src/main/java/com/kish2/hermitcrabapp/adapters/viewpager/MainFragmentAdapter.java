package com.kish2.hermitcrabapp.adapters.viewpager;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kish2.hermitcrabapp.view.fragments.community.CommunityFragment;
import com.kish2.hermitcrabapp.view.fragments.home.HomeFragment;
import com.kish2.hermitcrabapp.view.fragments.message.MessageFragment;
import com.kish2.hermitcrabapp.view.fragments.personal.PersonalFragment;
import com.kish2.hermitcrabapp.view.fragments.service.ServiceFragment;

public class MainFragmentAdapter extends FragmentStatePagerAdapter {

    /* 当前展示的实例，用于MainActivity进行操作 */
    private static Fragment currentFragment;

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
            case 0:
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

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        /* 赋值当前实例 */
        currentFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public static Fragment getCurrentFragment() {
        return currentFragment;
    }
}
