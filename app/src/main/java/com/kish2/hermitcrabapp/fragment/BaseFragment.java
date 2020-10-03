package com.kish2.hermitcrabapp.fragment;

import androidx.fragment.app.Fragment;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapter.SubFragmentContentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseFragment extends Fragment {

    /* viewpager预加载页面，提升流畅度 */
    protected int VIEW_PAGER_OF_SCREEN_LIMIT = 10;
    /* 子Fragment适配器 */
    protected SubFragmentContentAdapter subFmCAdapter;
    /* 页面标题 */
    protected List<String> page_titles;


}
