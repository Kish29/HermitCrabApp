package com.kish2.hermitcrabapp.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.kish2.hermitcrabapp.custom.view.CustomTipDialog;
import com.kish2.hermitcrabapp.custom.view.CustomWaitDialog;
import com.kish2.hermitcrabapp.utils.dev.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.view.KZDialogUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;

public abstract class BaseFragment extends Fragment implements IBaseView {

    /* viewpager预加载页面，提升流畅度 */
    protected int VIEW_PAGER_OF_SCREEN_LIMIT = 10;
    /* 滑动时间*/
    public static final long glideTime = 300;

    /* 提供给Presenter使用 */
    public Handler mHandler;

    private float mFirstY;
    private float mTouchSlop;

    /* 父根布局 */
    /* 只需要在HomeFragment中实例一次就行 */
    protected static DrawerLayout mDLParentView;

    /* 底部Tab条以及高度 */
    /* 同样只需要在HomeFragment中实例一次 */
    protected static ConstraintLayout mBottomTab;
    protected static int mBottomTabHeight = 0;
    /* 是否是第一次加载数据 */
    protected boolean isLoaded = false;

    protected Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    /* 为实现懒加载，需要在resume中执行该判断*/
    /* 所以子类必须实现loadData方法 */
    @Override
    public void onResume() {
        super.onResume();
        if (!isLoaded) {
            new Thread() {
                @Override
                public void run() {
                    loadData();
                    /* 数据加载完后置true */
                    isLoaded = true;
                }
            }.start();
        }
        /* onResume必须要刷新数据 */
        refreshData();
        if (ThemeUtil.Theme.afterGetResourcesColorId != themeColorId) {
            themeColorId = ThemeUtil.Theme.afterGetResourcesColorId;
            themeChanged();
        }
    }

    /* 判断标准，theme类保存的colorId是否等于原来的colorId*/
    protected int themeColorId = -1;

    protected abstract void themeChanged();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        themeColorId = ThemeUtil.Theme.afterGetResourcesColorId;
    }

    /* 用CoordinatorLayout之后只需要下降底部Tab条即可 */

    /**
     * @param hide   是否隐藏
     * @param btmTab 底部Tab条
     * @param bH     底部Tab条高度
     */
    protected void bottomBarHide(boolean hide, View btmTab, int bH) {
        if (hide) {
            if (btmTab.getTranslationY() > 0)
                return;
            btmTab.animate().translationYBy(btmTab.getTranslationY()).translationY(bH).setDuration(glideTime).start();
        } else {
            if (btmTab.getTranslationY() == 0)
                return;
            btmTab.animate().translationYBy(btmTab.getTranslationY()).translationY(0).setDuration(glideTime).start();
        }
    }

    protected void setPaddingTopForStatusBarHeight(View view) {
        /* 设置虚拟statusBar高度 */
        view.setPadding(0, ThemeUtil.STATUS_BAR_HEIGHT, 0, 0);
    }

    protected void setHeightForStatusBar(View view) {
        /* 设置虚拟statusBar高度 */
        view.getLayoutParams().height = ThemeUtil.STATUS_BAR_HEIGHT;
    }

    protected boolean touchCheck(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFirstY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distance = event.getY() - mFirstY;
                if (distance > mTouchSlop) {
                    // 下滑 显示titleBar
                    bottomBarHide(false, mBottomTab, mBottomTabHeight);
                } else if (-distance > mTouchSlop) {
                    // 上滑 隐藏titleBar
                    bottomBarHide(true, mBottomTab, mBottomTabHeight);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
            /*因为要让listView继续滑动，所以这儿返回false
            事件未被消费，向下传递，调用onTouchEvent*/
        return false;
    }

    public void setTextAndIconColor(boolean isDarkTheme) {
    }
}
