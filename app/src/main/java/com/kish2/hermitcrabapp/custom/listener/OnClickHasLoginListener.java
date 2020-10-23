package com.kish2.hermitcrabapp.custom.listener;

import android.view.View;

public abstract class OnClickHasLoginListener extends BaseOnClickListener {

    @Override
    public void onClick(View v) {
        if (hasLogin(v)) {
            onLoginClick(v);
        } else onNotLoginClick(v);
    }

    public abstract boolean hasLogin(View v);

    public abstract void onLoginClick(View v);

    public abstract void onNotLoginClick(View v);
}
