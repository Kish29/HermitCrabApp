package com.kish2.hermitcrabapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.kish2.hermitcrabapp.gizmos.CustomVideoView;
import com.kish2.hermitcrabapp.view.UserView;
import com.kish2.hermitcrabapp.view.impl.LoginViewImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements UserView {

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
            R.drawable.tab_bar_home_selector,
            R.drawable.tab_bar_forum_selector,
            R.drawable.tab_bar_service_selector,
            R.drawable.tab_bar_message_selector,
            R.drawable.tab_bar_personal_selector
    };

    @BindView(R.id.nav_tab_bar)
    TabLayout navigation;

    @BindView(R.id.login_reg_video)
    CustomVideoView bg;


    @BindView(R.id.change_activity_test)
    Button changeButton;


    private void init() {
        ButterKnife.bind(this);
        setTabs(navigation, getLayoutInflater(), TAB_TITLES, TAB_IMG);
        setStatusBar();
        setVideo();

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginViewImpl.class);
                startActivity(intent);
            }
        });
    }


    private void setVideo() {
        final String string = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bg_video).toString();
        Log.d("videoPath", string);
        bg.setVideoPath(string);
        bg.start();
        bg.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });
        bg.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                bg.setVideoPath(string);
                bg.start();
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void setStatusBar() {
        Window window = getWindow();
        window.setStatusBarColor(android.R.color.transparent);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @SuppressLint("InflateParams")
    private void setTabs(TabLayout tabs, LayoutInflater inflater, int[] titles, int[] img) {
        int len = img.length;
        if (titles.length != img.length)
            return;
        for (int i = 0; i < len; i++) {
            TabLayout.Tab newTab = tabs.newTab();
            View tabBarBasic = inflater.inflate(R.layout.ly_nav_tab_bar_basic, null);

            // 使用自定义视图，目的是为了便于修改
            newTab.setCustomView(tabBarBasic);

            // 设置各个页面的标签图片和文本
            ImageView imgTab = tabBarBasic.findViewById(R.id.nav_tab_img);
            imgTab.setImageResource(img[i]);
            TextView tabTitle = tabBarBasic.findViewById(R.id.nav_tab_title);
            tabTitle.setText(titles[i]);
            navigation.addTab(newTab);
        }
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }

    @Override
    public void navigationBack() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getVerifyCode() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("create", "MainActivity created.\n");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("intent flag", String.valueOf(intent.getFlags()));
        super.onNewIntent(intent);
    }
}
