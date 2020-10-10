package com.kish2.hermitcrabapp.view;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    /* viewpager预加载页面，提升流畅度 */
    protected int VIEW_PAGER_OF_SCREEN_LIMIT = 10;
    /* 滑动时间*/
    protected static final long glideTime = 300;

    /**
     * @param topGlide    要滑动的上面的视图
     * @param tH          上面的视图的高度
     * @param middleView  中间的视图
     * @param bottomGlide 要滑动的下面的视图
     * @param bH          要滑动的下面的高度
     */
    public void viewGlide(boolean hide, View topGlide, int tH, View middleView, View bottomGlide, int bH) {
        if (hide) {
            if (topGlide.getTranslationY() == -tH) {
                return;
            }
            topGlide.animate().translationYBy(topGlide.getTranslationY()).translationY(-tH).setDuration(glideTime).start();
            middleView.animate().translationYBy(middleView.getTranslationY()).translationY(0).setDuration(glideTime).start();
            bottomGlide.animate().translationYBy(bottomGlide.getTranslationY()).translationY(bH).setDuration(glideTime).start();
        } else {
            if (topGlide.getTranslationY() == 0) {
                return;
            }
            topGlide.animate().translationYBy(topGlide.getTranslationY()).translationY(0).setDuration(glideTime).start();
            middleView.animate().translationYBy(middleView.getTranslationY()).translationY(tH).setDuration(glideTime).start();
            bottomGlide.animate().translationYBy(bottomGlide.getTranslationY()).translationY(0).setDuration(glideTime).start();
        }
    }

    protected void topAndBottomBarGlide(boolean hide) {

    }

}
