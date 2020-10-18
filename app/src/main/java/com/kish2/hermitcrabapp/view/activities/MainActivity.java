package com.kish2.hermitcrabapp.view.activities;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapters.viewpager.MainFragmentAdapter;
import com.kish2.hermitcrabapp.custom.NoScrollViewPager;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.utils.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.ThemeUtil;
import com.kish2.hermitcrabapp.utils.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.kish2.hermitcrabapp.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

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
            R.drawable.ic_tab_home_selector,
            R.drawable.ic_tab_community_selector,
            R.drawable.ic_tab_service_selector,
            R.drawable.ic_tab_message_selector,
            R.drawable.ic_tab_personal_selector
    };

    /* 根布局DrawerLayout*/
    @BindView(R.id.dl_maint_activity)
    DrawerLayout mDLRootView;

    /* 占位StatusBar*/
    /*@BindView(R.id.v_virtual_status_bar)
    View mVStatusBarHolder;*/

    /* 收回侧边栏菜单 */
    @BindView(R.id.ly_user_card)
    Button mLYSideMenuUserCard;

    /* 侧边栏*/
    @BindView(R.id.cl_left_side_menu)
    ConstraintLayout mCLSideMenu;
    /* 对应宽度*/
    private float mSideMenuWidth;

    @BindView(R.id.iv_app_background)
    ImageView mAppBackground;
    /* 主内容*/
    @BindView(R.id.fl_main_content)
    FrameLayout mFLMainContent;

    /* 底部tab条以及父布局 */
    @BindView(R.id.cl_bottom_tab_bar)
    ConstraintLayout mCLBottomTab;
    @BindView(R.id.tl_main_nav_bar)
    TabLayout mTLMainNavBar;

    TabLayout.Tab[] mTabs;

    /* 主viewpager*/
    @BindView(R.id.vp_main_fragment)
    NoScrollViewPager mVPMain;

    /* 主Fragment的adapter*/
    MainFragmentAdapter pagerAdapter;

    /* 用户两次返回的间隔时间 */
    private long exitTime;

    /* 当前的tab位置 */
    private static int tabPos = 0;

    /* 在onCreate中调用3个属性*/
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.DATA_LOADED:
                        //*******底部Tab初始化视图*********/
                        for (TabLayout.Tab mTab : mTabs) {
                            mTLMainNavBar.addTab(mTab);
                        }
                        break;
                    case MessageForHandler.ADAPTER_INIT:
                        /* 设置预加载fragment数量，提升流畅度 */
                        mVPMain.setOffscreenPageLimit(VIEW_PAGER_OF_SCREEN_LIMIT);
                        mVPMain.setAdapter(pagerAdapter);
                }
            }
        };

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /* 主线程执行*/
        getAndSetLayoutView();
        /* 子线程注册事务 */
        new Thread() {
            @Override
            public void run() {
                registerViewComponentsAffairs();
                loadData();
            }
        }.start();
        /* 监听获取组件属性 */
        /* 使用任意一个view来执行 getLayoutComponentsAttr() 方法*/
        mDLRootView.getViewTreeObserver().addOnGlobalLayoutListener(this::getLayoutComponentsAttr);
    }

    @Override
    public void getAndSetLayoutView() {
        /* 透明状态栏*/
        StatusBarUtil.setSinkStatusBar(this, ThemeUtil.Theme.isDarkTheme, -1);
        /* 设置颜色*/
        /*设置底部tabBar的透明度*/
        mTLMainNavBar.getBackground().setAlpha(216);
        /* 用户使用自定义背景 */
        if (ThemeUtil.Theme.colorId == -1) {
            mAppBackground.setImageResource(R.mipmap.login_background);
        }
        /*布局规则(xml中已设置)*/
        /*mTLMainNavBar.setTabMode(TabLayout.MODE_FIXED);
        mTLMainNavBar.setTabGravity(TabLayout.GRAVITY_FILL);*/
        int len = TAB_IMG.length;
        mTabs = new TabLayout.Tab[len];
        for (int i = 0; i < len; i++) {
            mTabs[i] = mTLMainNavBar.newTab();
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public void loadData() {
        int len = TAB_IMG.length;
        for (int i = 0; i < len; i++) {
            View tabBarBasic = getLayoutInflater().inflate(R.layout.ly_tab_bottom, null);

            // 使用自定义视图，目的是为了便于修改
            mTabs[i].setCustomView(tabBarBasic);

            // 设置各个页面的标签图片和文本
            ImageView imgTab = tabBarBasic.findViewById(R.id.nav_tab_img);
            imgTab.setImageResource(TAB_IMG[i]);
            TextView tabTitle = tabBarBasic.findViewById(R.id.nav_tab_title);
            tabTitle.setText(TAB_TITLES[i]);
        }
        Message message = new Message();
        message.what = MessageForHandler.DATA_LOADED;
        mHandler.sendMessage(message);

        /* adapter也应当在子线程中执行 */
        /*设置适配器，并与当前Activity的fragmentManager绑定*/
        pagerAdapter = new MainFragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        Message message1 = new Message();
        message1.what = MessageForHandler.ADAPTER_INIT;
        mHandler.sendMessage(message1);

        /* 因为我们要使用自己定义的tab来点击切换，而setupWithViewPager方法默认使用标题*/
        /*mTLMainNavBar.setupWithViewPager(mVPMain);*/
    }

    @Override
    public void getLayoutComponentsAttr() {
        mSideMenuWidth = mCLSideMenu.getWidth();
    }

    /* 事务注册 */
    @Override
    public void registerViewComponentsAffairs() {
        /* 设置TabLayout切换时ViewPager的切换 */
        mTLMainNavBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                 /*通过TabLayout的Item来设置ViewPager的显示
                   此处是一一对应的 */
                tabPos = tab.getPosition();
                mVPMain.setCurrentItem(tabPos, false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        /* 设置主内容跟随侧边栏移动 */
        mDLRootView.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                /* 跟随侧边栏向右移动 */
                mFLMainContent.setTranslationX(mSideMenuWidth * slideOffset);
            }
        });
        mLYSideMenuUserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDLRootView.closeDrawer(GravityCompat.START);
                mTLMainNavBar.selectTab(mTLMainNavBar.getTabAt(4));
            }
        });
    }


    @Override
    public void onBackPressed() {
        /* 如果按下了返回键 */
        /* 先判断底部导航条是否是收起的状态 */
        /* 收起状态*/
        if (mCLBottomTab.getTranslationY() > 0) {
            mCLBottomTab.animate().translationYBy(mCLBottomTab.getTranslationY()).translationY(0).setDuration(BaseFragment.glideTime);
            return;
        }
        if (mDLRootView.isDrawerOpen(GravityCompat.START)) {
            mDLRootView.closeDrawer(GravityCompat.START);
            return;
        }
        // 重写键盘事件分发，onKeyDown方法某些情况下捕获不到，只能在这里写
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.showToast(this, "再按一次退出程序", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
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
    protected void onPause() {
        super.onPause();
    }
}
