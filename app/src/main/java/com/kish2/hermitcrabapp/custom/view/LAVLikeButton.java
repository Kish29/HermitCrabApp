package com.kish2.hermitcrabapp.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class LAVLikeButton extends LottieAnimationView {

    public LAVLikeButton(Context context) {
        super(context);
        setAnimation("twitter-heart-purchase.json");
        setOnClickListener(this::favorIt);
    }

    public LAVLikeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAnimation("twitter-heart-purchase.json");
        setOnClickListener(this::favorIt);
    }

    public LAVLikeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAnimation("twitter-heart-purchase.json");
        setOnClickListener(this::favorIt);
    }

    public void startFavor() {
        playAnimation();
    }

    public void cancelFavor() {
        setFrame(1);
        cancelAnimation();
    }

    public void reverseAnimation() {
        setSpeed(-1);
        playAnimation();
        setSpeed(1);
    }


    public void setFavored() {
        setFrame((int) getMaxFrame());
        cancelAnimation();
    }

    public void setUnFavored() {
        setFrame(1);
        cancelAnimation();
    }

    public boolean isFavored() {
        return getFrame() >= getMaxFrame();
    }

    public void favorIt(View view) {
        if (isFavored())
            cancelFavor();
        else startFavor();
    }
}
