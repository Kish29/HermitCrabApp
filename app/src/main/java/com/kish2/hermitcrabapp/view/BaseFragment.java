package com.kish2.hermitcrabapp.view;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

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
}
