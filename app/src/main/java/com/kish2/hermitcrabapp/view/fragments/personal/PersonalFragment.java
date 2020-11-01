package com.kish2.hermitcrabapp.view.fragments.personal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.bean.HermitCrabVectorIllustrations;
import com.kish2.hermitcrabapp.custom.view.CustomTipDialog;
import com.kish2.hermitcrabapp.custom.listener.OnClickMayTriggerFastRepeatListener;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.fragments.PersonalPresenter;
import com.kish2.hermitcrabapp.utils.App;
import com.kish2.hermitcrabapp.utils.dev.FileStorageManager;
import com.kish2.hermitcrabapp.utils.dev.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.dev.SysInteractUtil;
import com.kish2.hermitcrabapp.utils.view.BitMapAndDrawableUtil;
import com.kish2.hermitcrabapp.utils.view.KZDialogUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.kish2.hermitcrabapp.view.activities.LoginActivity;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.view.activities.ThemeActivity;
import com.kish2.hermitcrabapp.view.activities.UserProfileActivity;
import com.kongzue.dialog.v3.MessageDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yalantis.ucrop.UCrop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
    @BindView(R.id.iv_banner_bkg)
    ImageView mBannerBkg;
    private static float mCollapsingHeight = 0;

    // 左上角菜单
    ImageButton mSideMenu;
    ImageButton mTheme;
    ImageButton mSetting;


    // 用户头像部分
    @BindView(R.id.riv_avatar)
    RoundedImageView mUserAvatar;
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
    TextView friendText;
    LinearLayout mUserProduct;
    TextView userProduct;
    TextView productText;
    LinearLayout mUserTopic;
    TextView userTopic;
    TextView topicText;


    /* 发布中心*/
    @BindView(R.id.ll_old_publish)
    View mOldPublish;
    /* 快速入口*/
    /* 学生服务*/
    private Context mContext;
    private PersonalPresenter mPresenter;

    /* 这三个方法必须重写 */
    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
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
        registerViewComponentsAffairs();
        mAppBarLayout.getViewTreeObserver().addOnGlobalLayoutListener(this::getLayoutComponentsAttr);
        return fragmentPersonal;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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
        friendText = mUserFriend.findViewById(R.id.tv_friend_text);
        userProduct = mUserProduct.findViewById(R.id.tv_user_product);
        productText = mUserProduct.findViewById(R.id.tv_product_text);
        userTopic = mUserTopic.findViewById(R.id.tv_user_topic);
        topicText = mUserTopic.findViewById(R.id.tv_topic_text);
        /* Vector图片资源 */
        mSideMenu.setImageDrawable(HermitCrabVectorIllustrations.VI_MENU);
        mTheme.setImageDrawable(HermitCrabVectorIllustrations.VI_THEME);
        mSetting.setImageDrawable(HermitCrabVectorIllustrations.VI_SETTING);
        mMask.setImageDrawable(HermitCrabVectorIllustrations.VI_CIRCLE);
    }

    @Override
    public void loadData() {
    }

    @Override
    public void refreshData() {
        if (App.IS_USER_LOG_IN && App.LOAD_USER_SUCCESS) {
            username.setText(App.USER.getUsername());
            if (!App.USER.isInfoBind())
                userBindInfo.setText("(未绑定学生信息)");
            else userBindInfo.setText(App.USER.getDepartment());
        }
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
                if (!App.IS_USER_LOG_IN)
                    startActivity(new Intent(requireActivity(), LoginActivity.class));
                else startActivity(new Intent(requireActivity(), UserProfileActivity.class));
            }
        });
        mUserAvatar.setOnClickListener(v -> {
            MessageDialog messageDialog = KZDialogUtil.IOS_LIGHT_VER_THREE_BUTTON_MESSAGE(mContext,
                    "更换头像",
                    "选择一种方式更换头像",
                    "拍照",
                    "相册",
                    "查看头像");
            messageDialog.show();
            /* 相册 */
            messageDialog.setOnCancelButtonClickListener((baseDialog, v1) -> {
                ArrayList<String> list = new ArrayList<>();
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                UCrop.Options cropOptions = new UCrop.Options();
                cropOptions.withAspectRatio(1, 1);
                cropOptions.setCircleDimmedLayer(true);
                ((MainActivity) requireActivity()).setCropOption(cropOptions);
                ((MainActivity) requireActivity()).setFileOperatePurpose(BaseActivity.FILE_OPERATE_PURPOSE.USER_AVATAR);
                if (SysInteractUtil.checkAndRequestPermissions(requireActivity(), list, SysInteractUtil.request_file_pick_permission)) {
                    try {
                        SysInteractUtil.uploadPicture(requireActivity(), FileStorageManager.user_avatar_file_name, FileStorageManager.DIR_TYPE.USER_AVATAR);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            });
            /* 拍照 */
            messageDialog.setOnOkButtonClickListener((baseDialog, v1) -> {
                ArrayList<String> list = new ArrayList<>();
                list.add(Manifest.permission.CAMERA);
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                UCrop.Options cropOptions = new UCrop.Options();
                cropOptions.withAspectRatio(1, 1);
                cropOptions.setCircleDimmedLayer(true);
                ((MainActivity) requireActivity()).setCropOption(cropOptions);
                ((MainActivity) requireActivity()).setFileOperatePurpose(BaseActivity.FILE_OPERATE_PURPOSE.USER_AVATAR);
                if (SysInteractUtil.checkAndRequestPermissions(requireActivity(), list, SysInteractUtil.request_camera_permission)) {
                    try {
                        SysInteractUtil.takePhoto(requireActivity(), FileStorageManager.user_avatar_file_name, FileStorageManager.DIR_TYPE.USER_AVATAR);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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
                if (!App.IS_USER_LOG_IN) {
                    startActivity(new Intent(requireActivity(), LoginActivity.class));
                } else startActivity(new Intent(requireActivity(), UserProfileActivity.class));
            }
        });
        userBindInfo.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                if (!App.IS_USER_LOG_IN)
                    startActivity(new Intent(requireActivity(), LoginActivity.class));
                else startActivity(new Intent(requireActivity(), UserProfileActivity.class));
            }
        });
        mUserFriend.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                if (!App.IS_USER_LOG_IN) {

                }
            }
        });
        mUserProduct.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                if (!App.IS_USER_LOG_IN) {

                }
            }
        });
        mUserTopic.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                if (!App.IS_USER_LOG_IN) {

                }
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

    public void onUserAvatarUploadSuccess(Bitmap bitmap) {
        if (bitmap != null)
            mUserAvatar.setImageBitmap(bitmap);

    }

    @Override
    public void setDarkThemeColor() {
        StatusBarUtil.setStatusBarDarkTheme(requireActivity(), true);
        username.setTextColor(HermitCrabVectorIllustrations.colorBlack);
        userBindInfo.setTextColor(HermitCrabVectorIllustrations.colorBlack);
        userTopic.setTextColor(HermitCrabVectorIllustrations.colorBlack);
        userProduct.setTextColor(HermitCrabVectorIllustrations.colorBlack);
        userFriend.setTextColor(HermitCrabVectorIllustrations.colorBlack);
        HermitCrabVectorIllustrations.setAiColorBlack();
        friendText.setTextColor(HermitCrabVectorIllustrations.colorBlack);
        productText.setTextColor(HermitCrabVectorIllustrations.colorBlack);
        topicText.setTextColor(HermitCrabVectorIllustrations.colorBlack);
    }

    @Override
    public void setLightThemeColor() {
        StatusBarUtil.setStatusBarDarkTheme(requireActivity(), false);
        username.setTextColor(HermitCrabVectorIllustrations.colorWhite);
        userBindInfo.setTextColor(HermitCrabVectorIllustrations.colorWhite);
        userTopic.setTextColor(HermitCrabVectorIllustrations.colorWhite);
        userProduct.setTextColor(HermitCrabVectorIllustrations.colorWhite);
        userFriend.setTextColor(HermitCrabVectorIllustrations.colorWhite);
        HermitCrabVectorIllustrations.setAIColorWhite();
        friendText.setTextColor(HermitCrabVectorIllustrations.colorWhite);
        productText.setTextColor(HermitCrabVectorIllustrations.colorWhite);
        topicText.setTextColor(HermitCrabVectorIllustrations.colorWhite);
    }

    public void onBannerBackgroundUploadSuccess(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap = BitMapAndDrawableUtil.centerCropBitmapToFitView(bitmap, mAppBarLayout);
            Palette.from(bitmap).generate(palette -> {
                if (ThemeUtil.isLightTone(palette, 0xb)) {
                    setDarkThemeColor();
                } else setLightThemeColor();
            });
            mAppBarLayout.setBackground(new BitmapDrawable(getResources(), bitmap));
        }
    }


}
