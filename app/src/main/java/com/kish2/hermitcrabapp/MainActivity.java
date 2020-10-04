package com.kish2.hermitcrabapp;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.kish2.hermitcrabapp.adapter.MainFragmentAdapter;
import com.kish2.hermitcrabapp.custom.NoScrollViewPager;
import com.kish2.hermitcrabapp.utils.DeviceInfo;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kish2.hermitcrabapp.view.UserView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements UserView {

    /* 菜单标题 */
    private final int[] TAB_TITLES = new int[]{
            R.string.nav_home_title,
            R.string.nav_forum_title,
            R.string.nav_service_title,
            R.string.nav_message_title,
            R.string.nav_personal
    };

    /* 菜单图标 */
    private final int[] TAB_IMG = new int[]{
            R.drawable.tab_bar_home_selector,
            R.drawable.tab_bar_community_selector,
            R.drawable.tab_bar_service_selector,
            R.drawable.tab_bar_message_selector,
            R.drawable.tab_bar_personal_selector
    };

    /* 根布局DrawerLayout*/
    @BindView(R.id.activity_main_drawer_layout)
    DrawerLayout mRootView;

    /* 展开侧边栏*/
    /*@BindView(R.id.show_side_menu)
    Button mBtnShowSideMenu;*/

    /* 收回侧边栏菜单 */
    @BindView(R.id.btn_wrap_back)
    Button mBtnWrapSideMenu;

    /* 侧边栏*/
    @BindView(R.id.left_side_menu)
    ConstraintLayout mSideMenu;
    /* 对应宽度*/
    private float mSideMenuWidth;

    /* 主内容*/
    @BindView(R.id.main_content)
    ConstraintLayout mMainContent;

    /* 底部tab条*/
    @BindView(R.id.nav_tab_bar)
    TabLayout mNavigation;

    /* 主viewpager*/
    @BindView(R.id.vp_main)
    NoScrollViewPager mViewMain;

    /* 用户两次返回的间隔时间 */
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("create", "MainActivity created.\n");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("intent flag", String.valueOf(intent.getFlags()));
        super.onNewIntent(intent);
    }


    @SuppressLint("InflateParams")
    private void initTabs(TabLayout tabs, LayoutInflater inflater, int[] titles, int[] img) {
        int len = img.length;
        if (titles.length != img.length)
            return;

        /* 设置背景透明度 */
        mNavigation.getBackground().setAlpha(204);
        for (int i = 0; i < len; i++) {
            TabLayout.Tab newTab = tabs.newTab();
            View tabBarBasic = inflater.inflate(R.layout.ly_bottom_nav_tab_bar_basic, null);

            // 使用自定义视图，目的是为了便于修改
            newTab.setCustomView(tabBarBasic);

            // 设置各个页面的标签图片和文本
            ImageView imgTab = tabBarBasic.findViewById(R.id.nav_tab_img);
            imgTab.setImageResource(img[i]);
            TextView tabTitle = tabBarBasic.findViewById(R.id.nav_tab_title);
            tabTitle.setText(titles[i]);
            mNavigation.addTab(newTab);
        }
    }

    private void initPagerViews() {
        /* 设置预加载fragment数量，提升流畅度 */
        mViewMain.setOffscreenPageLimit(VIEW_PAGER_OF_SCREEN_LIMIT);
        /* 设置适配器，并与当且Activity绑定  */
        MainFragmentAdapter pagerAdapter = new MainFragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        // 只使用一个pagerAdapter来达到可以通过滑动在TabLayout之间切换
        mViewMain.setAdapter(pagerAdapter);

        // 关联切换
        // 并且设置页面切换时底部的TabLayout也要切换
        // mViewMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mNavigation));
        /* 设置TabLayout切换时ViewPager的切换 */
        mNavigation.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /* 通过TabLayout的Item来设置ViewPager的显示
                 *  此处是一一对应的 */
                // 取消按下tab切换页面时的动画效果
                Log.d("tab.getPosition", String.valueOf(tab.getPosition()));
                mViewMain.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /* 设置主内容跟随移动 */
    private void initDrawerListener() {
        mRootView.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                /* 跟随侧边栏向右移动 */
                mMainContent.setTranslationX(mSideMenuWidth * slideOffset);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {

        /*RecordSearchHistorySQLite sqLite = new RecordSearchHistorySQLite(this);
        String string = "第一条搜索数据";
        sqLite.insert(string);
        System.out.println(sqLite.matchedHistory("一"));*/


        ButterKnife.bind(this);
        initTabs(mNavigation, getLayoutInflater(), TAB_TITLES, TAB_IMG);
        initPagerViews();
        setSinkStatusBar(false, true);
        initDeviceInfo();
        initDrawerListener();

        mBtnWrapSideMenu.setOnClickListener(v -> {
            /* 收起左边栏*/
            mRootView.closeDrawer(GravityCompat.START);
            mNavigation.selectTab(mNavigation.getTabAt(4));
        });
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }

    @Override
    protected void onDestroy() {
        detachPresenter();
        super.onDestroy();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getVerifyCode() {
        return null;
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        /* 如果按下了返回键 */
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_DOWN &&
                event.getRepeatCount() == 0) {
            // 重写键盘事件分发，onKeyDown方法某些情况下捕获不到，只能在这里写
            if (System.currentTimeMillis() - exitTime > 2000) {
                showToast("再按一次退出程序", TOAST_DURATION.TOAST_SHORT, TOAST_POSITION.TOAST_BOTTOM);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void initDeviceInfo() {
        if (this.getSharedPreferences("device_info", MODE_PRIVATE) == null) {
            Log.d("initDeviceInfo", "create DeviceInfo");
            new DeviceInfo(this, this);
        } else Log.d("no device info create", "no device info create");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mSideMenuWidth = mSideMenu.getWidth();
    }
}
