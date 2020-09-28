package com.kish2.hermitcrabapp.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class CustomVideoView extends VideoView {

    /* 所有构造方法必须写上 */
    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /* 重写onMeasure方法 */
    /* VideoView源码中，会根据Video的大小对该控件进行大小的更改
     * 所以这儿跳过根据Video大小更换大小 */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*LinearLayout下，使用MeasureSpec的EXACTLY的模式，与第一个参数size无关*/
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        /* 调用 setMeasuredDimensionRaw方法，赋值 */
        setMeasuredDimension(width, height);
    }
}
