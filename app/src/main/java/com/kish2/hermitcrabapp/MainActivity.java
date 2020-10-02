package com.kish2.hermitcrabapp;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.kish2.hermitcrabapp.adapter.MainFragmentAdapter;
import com.kish2.hermitcrabapp.custom.CustomSlideBar;
import com.kish2.hermitcrabapp.utils.DeviceInfo;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kish2.hermitcrabapp.view.UserView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements UserView, View.OnTouchListener {

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

    /*@BindView(R.id.activity_main_constraint_layout)
    ConstraintLayout mMainActivity;*/

    @BindView(R.id.nav_tab_bar)
    TabLayout mNavigation;

    @BindView(R.id.vp_main)
    ViewPager mViewMain;

    @BindView(R.id.btn_wrap_back)
    Button mSideMenu;

    @BindView(R.id.activity_main_left_content)
    LinearLayout mLeftSlideBar;

    @BindView(R.id.activity_main_content)
    ConstraintLayout mMainContent;

    @BindView(R.id.activity_main_layer)
    View mMainLayer;

    /* 滑出时间 */
    private long wrapTime = 300;

    /* 退出间隔时间 */
    private long exitTime = 300;

    /* 是否显示了侧边菜单 */
    public static boolean isShowingSlideBar = false;

    /* 用户点击的位置
     * 是当前侧边栏的部分还是旁边的主部分*/
    private enum TOUCH_POSITION {
        POSITION_CURRENT,
        POSITION_SIDE
    }

    /* 根布局宽度 */
    private int slideBarWidth;

    private static TOUCH_POSITION touch_position;

    /* 在被判定为滚动之前用户手指可以移动的最大值 */
    private static int touchSlop;

    /* 用户点击屏幕的第一个位置 */
    private float firstTouchX;

    /* 滑动状态 */
    private enum SLIDE_STATUS {
        SLIDE_NOT_MEET_WRAP,
        RELEASE_TO_WRAP,
        WRAPPING,
        WRAP_FINISHED
    }

    /* 当前状态 */
    private SLIDE_STATUS currentStatus = SLIDE_STATUS.WRAP_FINISHED;

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
            View tabBarBasic = inflater.inflate(R.layout.ly_nav_tab_bar_basic, null);

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
        /* 设置适配器，并与当且Activity绑定  */
        MainFragmentAdapter pagerAdapter = new MainFragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        // 只使用一个pagerAdapter来达到可以通过滑动在TabLayout之间切换
        mViewMain.setAdapter(pagerAdapter);

        // 关联切换
        // 并且设置页面切换时底部的TabLayout也要切换
        mViewMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mNavigation));
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

    /* 滑出侧边栏 */
    private void showSlideBar() {
        float width = mLeftSlideBar.getWidth();
        mLeftSlideBar.animate().translationXBy(-width).translationX(0).setDuration(wrapTime).start();
        mMainContent.animate().translationXBy(0).translationX(width).setDuration(wrapTime).start();
        mMainLayer.animate().alphaBy(0).alpha(1).setDuration(wrapTime).start();
        isShowingSlideBar = true;
    }

    /* 收起侧边栏 */
    private void wrapSlideBar() {
        float width = mLeftSlideBar.getWidth();
        mLeftSlideBar.animate().translationXBy(0).translationX(-width).setDuration(wrapTime).start();
        mMainContent.animate().translationXBy(width).translationX(0).setDuration(wrapTime).start();
        mMainLayer.animate().alphaBy(1).alpha(0).setDuration(wrapTime).start();
        isShowingSlideBar = false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        ButterKnife.bind(this);
        initTabs(mNavigation, getLayoutInflater(), TAB_TITLES, TAB_IMG);
        initPagerViews();
        setSinkStatusBar(false, true);
        initDeviceInfo();

        /* 注册监听事件*/
        mLeftSlideBar.setOnTouchListener(this);
        mMainLayer.setOnTouchListener(this);
        touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        mSideMenu.setOnClickListener(v -> {
            wrapSlideBar();
        });
    }

    /* 在onCreate的方法中子类还没有进行绘制 */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        slideBarWidth = mLeftSlideBar.getWidth();
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("isShowingSlideBar", String.valueOf(isShowingSlideBar));
        if (isShowingSlideBar) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    firstTouchX = event.getRawX();
                    /* 虽然event的坐标原点在左上方，但不影响x坐标的判断，y坐标则要取相反数 */
                    if (firstTouchX > slideBarWidth) {
                        touch_position = TOUCH_POSITION.POSITION_SIDE;
                    } else {
                        touch_position = TOUCH_POSITION.POSITION_CURRENT;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    int distance = (int) (firstTouchX - event.getRawX());
                    if (distance <= touchSlop)
                        return false;
                    currentStatus = SLIDE_STATUS.SLIDE_NOT_MEET_WRAP;
                    if (mLeftSlideBar.getTranslationX() <= -((float) slideBarWidth / 2)) {
                        currentStatus = SLIDE_STATUS.RELEASE_TO_WRAP;
                        return false;
                    }
                    if (distance / 2 >= slideBarWidth) {
                        distance = 2 * slideBarWidth;
                    }
                    /* distance / 2 实现较为平滑的效果 */
                    mLeftSlideBar.setTranslationX(mLeftSlideBar.getTranslationX() - ((float) distance / 2));
                    mMainContent.setTranslationX(mMainContent.getTranslationX() - ((float) distance / 2));
                    /* 计算透明比率 */
                    float alpha = Math.abs(mLeftSlideBar.getTranslationX()) / slideBarWidth;
                    mMainLayer.setAlpha(alpha);
                    break;
                case MotionEvent.ACTION_UP:
                    /* 点击本区域不做出反应*/
                    if (touch_position == TOUCH_POSITION.POSITION_CURRENT)
                        return true;
                    /* 点击外侧直接收起 */
                    if (touch_position == TOUCH_POSITION.POSITION_SIDE && Math.abs(event.getRawX() - firstTouchX) < 1) {
                        wrapSlideBar();
                        return true;
                    }
                    if (currentStatus == SLIDE_STATUS.RELEASE_TO_WRAP) {
                        wrapSlideBar();
                    } else {
                        showSlideBar();
                    }
                    break;
            }
            return true;
        } else
            return false;
    }
}
