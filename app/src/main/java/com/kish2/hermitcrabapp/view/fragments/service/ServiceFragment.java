package com.kish2.hermitcrabapp.view.fragments.service;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.utils.view.ToastUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceFragment extends BaseFragment {

    /* 主内容*/
    @BindView(R.id.gl_service_list)
    GridLayout mGLFragmentContent;

    /* 顶部导航条*/
    @BindView(R.id.top_retrieve_bar)
    ViewGroup mTopRetrieveBar;
    @BindView(R.id.ctl_banner_container)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private static float mCollapsingHeight = 0;
    /* 顶部导航条AppBarLayout */
    @BindView(R.id.abl_retrieve_bar_container)
    AppBarLayout mAppBarLayout;

    /* 用户头像*/
    RoundedImageView mUserAvatar;
    /* 搜索栏*/
    SearchView mSearch;

    /* 服务列表的各项标题 */
    private static String[] ITEM_TITLES;
    private final int[] ITEM_IMAGE = new int[]{

    };
    View[] mServiceItems;
    GridLayout.LayoutParams[] mItemsParams;

    /* 这三个方法必须重写 */
    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.LOCAL_DATA_LOADED:
                        initGirdLayoutList();
                        ToastUtil.showToast(getContext(), "服务列表获取成功", ToastUtil.TOAST_DURATION.TOAST_SHORT, ToastUtil.TOAST_POSITION.TOAST_BOTTOM);
                        break;
                    case MessageForHandler.SYSTEM_FAILURE:
                    default:
                        break;
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentService = inflater.inflate(R.layout.fragment_service, container, false);
        ButterKnife.bind(this, fragmentService);

        getAndSetLayoutView();
        new Thread() {
            @Override
            public void run() {
                registerViewComponentsAffairs();
            }
        }.start();
        mGLFragmentContent.getViewTreeObserver().addOnGlobalLayoutListener(this::getLayoutComponentsAttr);
        return fragmentService;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initGirdLayoutList() {
        int len = ITEM_TITLES.length;
        for (int i = 0; i < len; i++) {
            mGLFragmentContent.addView(mServiceItems[i], mItemsParams[i]);
        }
        /* 加上底部的tab条 */
        mGLFragmentContent.setPadding(0, 0, 0, mBottomTabHeight);
    }

    @Override
    public void getLayoutComponentsAttr() {
        mCollapsingHeight = mCollapsingToolbarLayout.getHeight();
    }

    @Override
    public void getAndSetLayoutView() {
        setPaddingTopForStatusBarHeight(mAppBarLayout);
        mUserAvatar = mTopRetrieveBar.findViewById(R.id.riv_side_menu);
        /* 设置AppBarLayout的颜色 */
        mAppBarLayout.setBackgroundColor(ThemeUtil.Theme.afterGetResourcesColorId);
    }

    @SuppressLint("InflateParams")
    @Override
    public void loadData() {
        /* 初始化 */
        ITEM_TITLES = new String[0];
        ITEM_TITLES = getResources().getStringArray(R.array.service_list_titles);
        int len = ITEM_TITLES.length;
        int columnLen = mGLFragmentContent.getColumnCount();
        mServiceItems = new View[len];
        mItemsParams = new GridLayout.LayoutParams[len];
        for (int i = 0; i < len; i++) {
            mServiceItems[i] = getLayoutInflater().inflate(R.layout.ly_item_general, null);
            ImageView itemSurface = mServiceItems[i].findViewById(R.id.iv_item_surface);
            itemSurface.setImageResource(R.drawable.ic_app_icon);
            TextView itemDesc = mServiceItems[i].findViewById(R.id.tv_item_desc);
            itemDesc.setText(ITEM_TITLES[i]);
            mItemsParams[i] = new GridLayout.LayoutParams();
            mItemsParams[i].width = mGLFragmentContent.getWidth() / columnLen;
            mItemsParams[i].bottomMargin = getResources().getDimensionPixelSize(R.dimen.reg_page_ht_margin);
        }
        mHandler.sendEmptyMessage(MessageForHandler.LOCAL_DATA_LOADED);
    }

    @Override
    public void registerViewComponentsAffairs() {
        mUserAvatar.setOnClickListener(v -> {
            mDLParentView.openDrawer(GravityCompat.START);
        });
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float offset = mCollapsingHeight + verticalOffset;
            float ratio = offset / mCollapsingHeight;
            mCollapsingToolbarLayout.setAlpha(ratio);
        });
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }

}
