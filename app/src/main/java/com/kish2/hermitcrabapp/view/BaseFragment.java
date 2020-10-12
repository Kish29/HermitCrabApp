package com.kish2.hermitcrabapp.view;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    /* 是否是第一次加载数据 */
    protected boolean isLoaded = false;

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
    }

    /* 子类重写 */
    /* 加载数据或者设置adapter，放在Handle中，等待子线程完成数据的加载 */
    protected void loadData() {
    }

    /* viewpager预加载页面，提升流畅度 */
    protected int VIEW_PAGER_OF_SCREEN_LIMIT = 10;
    /* 滑动时间*/
    protected static final long glideTime = 300;

    /* 父根布局 */
    /* 只需要在HomeFragment中实例一次就行 */
    protected static DrawerLayout mDLParentView;

    /* 底部Tab条以及高度 */
    /* 同样只需要在HomeFragment中实例一次 */
    protected static ConstraintLayout mBottomTab;
    protected static int mBottomTabHeight = 0;

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

    /* 重写该方法，以便activity或者子fragment能够访问*/
    public void bottomBarHide(boolean hide) {

    }


    protected void setPaddingTopForStatusBarHeight(View view) {
        /* 设置虚拟statusBar高度 */
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            view.setPadding(0, getResources().getDimensionPixelOffset(identifier), 0, 0);
        }
    }

    protected void setHeightForStatusBar(View view) {
        /* 设置虚拟statusBar高度 */
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            view.getLayoutParams().height = getResources().getDimensionPixelOffset(identifier);
        }
    }

}
