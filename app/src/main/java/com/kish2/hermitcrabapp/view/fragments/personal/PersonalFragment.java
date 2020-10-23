package com.kish2.hermitcrabapp.view.fragments.personal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.bean.VectorIllustrations;
import com.kish2.hermitcrabapp.custom.view.CustomTipDialog;
import com.kish2.hermitcrabapp.custom.listener.OnClickMayTriggerFastRepeatListener;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.utils.view.BitMapAndDrawableUtil;
import com.kish2.hermitcrabapp.utils.view.KZDialogUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.kish2.hermitcrabapp.view.activities.LoginActivity;
import com.kish2.hermitcrabapp.view.activities.ThemeActivity;
import com.kish2.hermitcrabapp.view.activities.UserProfileActivity;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.MessageDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class PersonalFragment extends BaseFragment {

    /**
     * @获取要用到的所有控件
     */
    /* 主要展示页面*/
    @BindView(R.id.ns_personal_main)
    NestedScrollView mNSPersonalMain;

    // 顶部条容器
    @BindView(R.id.abl_retrieve_bar_container)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.top_retrieve_bar)
    RelativeLayout mTopRetrieveBar;
    @BindView(R.id.ctl_banner_container)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private static float mCollapsingHeight = 0;

    // 左上角菜单
    ImageButton mSideMenu;
    ImageButton mTheme;
    ImageButton mSetting;


    // 用户头像部分
    @BindView(R.id.rl_user_avatar)
    RelativeLayout mUserAvatar;
    /* 遮罩 */
    @BindView(R.id.iv_mask_bg)
    RoundedImageView mMask;
    @BindView(R.id.iv_user_gender)
    RoundedImageView mUserGender;

    @BindView(R.id.ll_user_brief)
    LinearLayout mUserBrief;
    /* 简短展示页面*/
    TextView username;
    TextView userBindInfo;
    LinearLayout mUserFriend;
    TextView userFriend;
    LinearLayout mUserProduct;
    TextView userProduct;
    LinearLayout mUserTopic;
    TextView userTopic;


    /* 发布中心*/
    @BindView(R.id.ll_old_publish)
    View mOldPublish;
    /* 快速入口*/
    /* 学生服务*/

    private Context mContext;

    /* 这三个方法必须重写 */
    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.ADAPTER_INIT:
                        break;
                    case MessageForHandler.NETWORK_FAILURE:
                        CustomTipDialog errorDialog = KZDialogUtil.IOS_LIGHT_ERROR_DIALOG(mContext, null, "网络连接失败，请检查您的网络状况", 2000);
                        errorDialog.show();
                        break;
                }
            }
        };
        this.mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentPersonal = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, fragmentPersonal);

        getAndSetLayoutView();
        new Thread() {
            @Override
            public void run() {
                registerViewComponentsAffairs();
            }
        }.start();
        mAppBarLayout.getViewTreeObserver().addOnGlobalLayoutListener(this::getLayoutComponentsAttr);
        return fragmentPersonal;
    }

    /**
     * @因为onPause后，FragmentManager会对组件进行重绘，某些组件会回到原始的属性
     * @而如果在之间调用了隐藏动画，那么会造成冲突，视图UI组件会消失
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void getLayoutComponentsAttr() {
        mCollapsingHeight = mCollapsingToolbarLayout.getHeight();
        /* 锁定appBarLayout高度 */
        mAppBarLayout.getLayoutParams().height = mAppBarLayout.getHeight();
        if (ThemeUtil.Theme.bgImgSrc != null) {
            mAppBarLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_register));
        }
    }

    @Override
    public void getAndSetLayoutView() {
        setPaddingTopForStatusBarHeight(mAppBarLayout);
        mAppBarLayout.setBackgroundColor(ThemeUtil.Theme.afterGetResourcesColorId);
        /* 顶部条 */
        mSideMenu = mTopRetrieveBar.findViewById(R.id.ib_personal_side_menu);
        mTheme = mTopRetrieveBar.findViewById(R.id.ib_personal_theme);
        mSetting = mTopRetrieveBar.findViewById(R.id.ib_personal_setting);
        /* brief 展示界面 */
        username = mUserBrief.findViewById(R.id.tv_username);
        userBindInfo = mUserBrief.findViewById(R.id.tv_user_bind_info);
        mUserFriend = mUserBrief.findViewById(R.id.ll_friends);
        mUserProduct = mUserBrief.findViewById(R.id.ll_products);
        mUserTopic = mUserBrief.findViewById(R.id.ll_topics);
        userFriend = mUserFriend.findViewById(R.id.tv_user_friend);
        userProduct = mUserProduct.findViewById(R.id.tv_user_product);
        userTopic = mUserTopic.findViewById(R.id.tv_user_topic);
        /* Vector图片资源 */
        mSideMenu.setImageDrawable(VectorIllustrations.VI_MENU);
        mTheme.setImageDrawable(VectorIllustrations.VI_THEME);
        mSetting.setImageDrawable(VectorIllustrations.VI_SETTING);
        mMask.setImageDrawable(VectorIllustrations.VI_CIRCLE);
    }

    @Override
    public void loadData() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void registerViewComponentsAffairs() {
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float offset = mCollapsingHeight + verticalOffset;
            float ratio = offset / mCollapsingHeight;
            mCollapsingToolbarLayout.setAlpha(ratio);
        });
        mAppBarLayout.setOnClickListener(v -> {
            MessageDialog messageDialog = KZDialogUtil.IOS_LIGHT_VER_THREE_BUTTON_MESSAGE(mContext,
                    "更换背景",
                    "选择一种方式更换背景",
                    "系统默认",
                    "使用头像",
                    "相册");
            messageDialog.show();
            CustomTarget<Drawable> customTarget = new CustomTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    mAppBarLayout.setBackground(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            };
            messageDialog.setOnCancelButtonClickListener((baseDialog, v1) -> {
                Glide.with(this).load(R.drawable.bg_register).apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3))).into(customTarget);
                return false;
            });
        });
        mSetting.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                startActivity(new Intent(requireActivity(), UserProfileActivity.class));
            }
        });
        mUserAvatar.setOnClickListener(v -> {
            MessageDialog messageDialog = KZDialogUtil.IOS_LIGHT_VER_TWO_BUTTON_MESSAGE(mContext, "更换头像", "选择一种方式更换头像", "拍照", "相册");
            messageDialog.show();
            messageDialog.setOnCancelButtonClickListener((baseDialog, v1) -> {
                return false;
            });
        });
        mTheme.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                startActivity(new Intent(requireActivity(), ThemeActivity.class));
            }
        });
        mNSPersonalMain.setOnTouchListener(this::touchCheck);
        mSideMenu.setOnClickListener(v -> {
            mDLParentView.openDrawer(GravityCompat.START);
        });
        username.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        userBindInfo.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                startActivity(new Intent(requireActivity(), UserProfileActivity.class));
            }
        });
        mUserFriend.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {

            }
        });
        mUserProduct.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {

            }
        });
        mUserTopic.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {

            }
        });
        mUserGender.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                MessageDialog messageDialog = KZDialogUtil.IOS_LIGHT_VER_TWO_BUTTON_MESSAGE(mContext, "性别", "您还未设置性别哦", "老子晓得了！", "马上设置");
                messageDialog.setButtonOrientation(LinearLayout.HORIZONTAL);
                messageDialog.show();
            }
        });
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }

}
