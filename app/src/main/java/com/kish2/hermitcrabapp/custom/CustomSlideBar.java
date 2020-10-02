/* 2020-10-02
 * 该类是作为侧边栏进行定制的
 * 将其translationX设置为 ±width隐藏在左边或者右边
 */

package com.kish2.hermitcrabapp.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.kish2.hermitcrabapp.R;

public class CustomSlideBar extends LinearLayout implements View.OnTouchListener {

    /* 是否已加载过一次Layout，这里onLayout中的初始化只需加载一次 */
    private boolean loadOnce = false;

    /* 在被判定为滚动之前用户手指可以移动的最大值 */
    private static int touchSlop;

    /* 用户点击屏幕的第一个位置 */
    private float firstTouchX;

    /* 用户点击的位置
     * 是当前侧边栏的部分还是旁边的主部分*/
    private enum TOUCH_POSITION {
        POSITION_CURRENT,
        POSITION_SIDE
    }

    private static TOUCH_POSITION touch_position;

    /* 根布局宽度 */
    private int slideBarWidth;

    /* 完整收回的时间 */
    private int wrapTime = 300;

    /* 根布局成员 */
    View mSlideBar;

    /* 左边主要内容 */
    View mSideView;
    /* 左边遮罩 */
    View mSideViewLayer;

    /* 子类视图
     * 可以是自定义视图 */
    View mSubView;

    /* 滑动状态 */
    private enum SLIDE_STATUS {
        SLIDE_NOT_MEET_WRAP,
        RELEASE_TO_WRAP,
        WRAPPING,
        WRAP_FINISHED
    }

    /* 当前状态 */
    private SLIDE_STATUS currentStatus = SLIDE_STATUS.WRAP_FINISHED;

    @SuppressLint("InflateParams")
    public CustomSlideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        /* 一定要是获取根布局的方式 */
        mSlideBar = LayoutInflater.from(context).inflate(R.layout.slide_bar_view, null, true);

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        addView(mSlideBar, 0);
    }

    /* onLayout函数用于布置子View，初始化子布局必须在这儿写 */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        /**
         * @注意这儿一定要加上对loadOnce的判断
         * @因为要在onLayout中进行setLayoutParams
         * @而setLayoutParams又会调用onLayout方法，造成死循环 */
        if (changed && !loadOnce) {

            mSideViewLayer = findViewById(R.id.activity_main_layer);
            /* 获取宽度 */
            slideBarWidth = mSlideBar.getWidth();

            /* 获取子类视图 */
            mSubView = getChildAt(1);
            mSlideBar.setOnTouchListener(this);

            /* 注册子类视图的touch监听 */
            mSubView.setOnTouchListener(this);

            loadOnce = true;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
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
                if (mSlideBar.getTranslationX() <= -((float) slideBarWidth / 2)) {
                    currentStatus = SLIDE_STATUS.RELEASE_TO_WRAP;
                    return false;
                }
                if (distance / 2 >= slideBarWidth) {
                    distance = 2 * slideBarWidth;
                }
                /* distance / 2 实现较为平滑的效果 */
                mSlideBar.setTranslationX(mSlideBar.getTranslationX() - ((float) distance / 2));
                mSideView.setTranslationX(mSideView.getTranslationX() - ((float) distance / 2));
                /* 计算透明比率 */
                float alpha = Math.abs(mSlideBar.getTranslationX()) / slideBarWidth;
                mSideViewLayer.setAlpha(alpha);
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
                return true;
        }
        return true;
    }

    private long getTimeRatio() {
        return (long) (Math.abs(mSlideBar.getTranslationX()) / slideBarWidth * wrapTime);
    }

    private void wrapSlideBar() {
        /* 计算需要的时间*/
        long time = getTimeRatio();
        mSlideBar.animate().translationXBy(mSlideBar.getTranslationX()).translationX(-slideBarWidth).setDuration(time).start();
        mSideView.animate().translationXBy(mSideView.getTranslationX()).translationX(0).setDuration(time).start();
        mSideViewLayer.animate().alphaBy(mSideViewLayer.getAlpha()).alpha(0).setDuration(time).start();
    }

    private void showSlideBar() {
        /* 计算需要的时间*/
        long time = getTimeRatio();
        mSlideBar.animate().translationXBy(mSlideBar.getTranslationX()).translationX(0).setDuration(time).start();
        mSideView.animate().translationXBy(mSideView.getTranslationX()).translationX(slideBarWidth).setDuration(time).start();
        mSideViewLayer.animate().alphaBy(mSideViewLayer.getAlpha()).alpha(1).setDuration(time).start();
    }
}


















































