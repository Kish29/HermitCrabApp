package com.kish2.hermitcrabapp.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.tabs.TabLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapters.viewpager.MainFragmentAdapter;
import com.kish2.hermitcrabapp.custom.view.NoScrollViewPager;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.utils.dev.ApplicationConfigUtil;
import com.kish2.hermitcrabapp.utils.dev.GlideResourceRecycleManager;
import com.kish2.hermitcrabapp.utils.dev.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.dev.SysInteractUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kish2.hermitcrabapp.view.fragments.personal.PersonalFragment;
import com.yalantis.ucrop.UCrop;

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
    @BindView(R.id.iv_side_menu_bg)
    ImageView mSideMenuBG;
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
    /* 为了能动态地快速设置主题，这里获取实例资源*/
    ImageView[] mTabIcons;
    TextView[] mTabTitles;

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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"HandlerLeak", "ResourceType"})
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
        /* 程注册事务 */
        registerViewComponentsAffairs();
        new Thread() {
            @Override
            public void run() {
                /* loadData放在最后面*/
                mCropOptions = new UCrop.Options();
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
        mTLMainNavBar.getBackground().setAlpha(ThemeUtil.TAB_TRANSPARENT);
        /*布局规则(xml中已设置)*/
        /*mTLMainNavBar.setTabMode(TabLayout.MODE_FIXED);
        mTLMainNavBar.setTabGravity(TabLayout.GRAVITY_FILL);*/
        mTabs = new TabLayout.Tab[ThemeUtil.BOTTOM_TAB_NUM];
        mTabIcons = new ImageView[ThemeUtil.BOTTOM_TAB_NUM];
        mTabTitles = new TextView[ThemeUtil.BOTTOM_TAB_NUM];
        for (int i = 0; i < ThemeUtil.BOTTOM_TAB_NUM; i++) {
            mTabs[i] = mTLMainNavBar.newTab();
        }
        Glide.with(this).load(R.mipmap.bg_side_menu).into(mSideMenuBG);
    }

    @SuppressLint("InflateParams")
    @Override
    public void loadData() {
        for (int i = 0; i < ThemeUtil.BOTTOM_TAB_NUM; i++) {
            View tabBarBasic = getLayoutInflater().inflate(R.layout.view_tab_bottom, null);

            // 使用自定义视图，目的是为了便于修改
            mTabs[i].setCustomView(tabBarBasic);

            // 设置各个页面的标签图片和文本
            mTabIcons[i] = tabBarBasic.findViewById(R.id.nav_tab_img);
            mTabIcons[i].setImageDrawable(ThemeUtil.BOTTOM_TAB_SELECTOR[i]);
            mTabTitles[i] = tabBarBasic.findViewById(R.id.nav_tab_title);
            mTabTitles[i].setTextColor(ThemeUtil.TEXT_SELECTOR);
            mTabTitles[i].setText(TAB_TITLES[i]);
        }
        mHandler.sendEmptyMessage(MessageForHandler.DATA_LOADED);

        /* adapter也应当在子线程中执行 */
        /*设置适配器，并与当前Activity的fragmentManager绑定*/
        pagerAdapter = new MainFragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mHandler.sendEmptyMessage(MessageForHandler.ADAPTER_INIT);

        /* 因为我们要使用自己定义的tab来点击切换，而setupWithViewPager方法默认使用标题*/
        /*mTLMainNavBar.setupWithViewPager(mVPMain);*/
    }

    @Override
    public void refreshData() {

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
        mLYSideMenuUserCard.setOnClickListener(v -> {
            mDLRootView.closeDrawer(GravityCompat.START);
            mTLMainNavBar.selectTab(mTLMainNavBar.getTabAt(4));
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
        ApplicationConfigUtil.storeAppConfig();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void themeChanged() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < ThemeUtil.BOTTOM_TAB_NUM; i++) {
                    mTabIcons[i].setImageDrawable(ThemeUtil.BOTTOM_TAB_SELECTOR[i]);
                    mTabTitles[i].setTextColor(ThemeUtil.TEXT_SELECTOR);
                }
            }
        }.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onImageCropSuccess(Uri uri) {
        switch (file_operate_purpose) {
            case USER_AVATAR:
                // 把原图交给PersonalFragment
                // Bitmap bitmap = CompressHelper.getDefault(this).compressToBitmap(newAvatarFile);
                CustomTarget<Bitmap> customTarget = new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        GlideResourceRecycleManager.addBitmapIntoList(simpleClassName, bitmap);
                        ((PersonalFragment) MainFragmentAdapter.getCurrentFragment()).onUserAvatarUploadSuccess(bitmap);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                };
                // 不要使用缓存机制
                Glide.with(this).asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).load(uri).into(customTarget);
            case BANNER_BKG:
                if (ApplicationConfigUtil.HAS_BANNER_BKG &&
                        file_operate_purpose == SysInteractUtil.FILE_OPERATE_PURPOSE.USER_AVATAR)
                    return;
                CustomTarget<Bitmap> customTarget1 = new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        GlideResourceRecycleManager.addBitmapIntoList(simpleClassName, resource);
                        ((PersonalFragment) MainFragmentAdapter.getCurrentFragment()).onBannerBackgroundUploadSuccess(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                };
                // 不要使用缓存机制
                Glide.with(this)
                        .asBitmap()
                        .load(uri)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(customTarget1);
                break;
            case PRODUCT_PICS:
            case SIDE_MENU_BKG:
            case NORMAL:
            default:
                break;
        }
    }

}
