package com.kish2.hermitcrabapp.view.fragments.personal;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.palette.graphics.Palette;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kish2.hermitcrabapp.HermitCrabApp;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.bean.HermitCrabVectorIllustrations;
import com.kish2.hermitcrabapp.custom.listener.OnClickMayTriggerFastRepeatListener;
import com.kish2.hermitcrabapp.custom.view.CustomTipDialog;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.presenter.fragments.PersonalPresenter;
import com.kish2.hermitcrabapp.utils.dev.ApplicationConfigUtil;
import com.kish2.hermitcrabapp.utils.dev.FileStorageManager;
import com.kish2.hermitcrabapp.utils.dev.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.dev.SysInteractUtil;
import com.kish2.hermitcrabapp.utils.view.BitMapAndDrawableUtil;
import com.kish2.hermitcrabapp.utils.view.KZDialogUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.view.BaseFragment;
import com.kish2.hermitcrabapp.view.activities.LoginActivity;
import com.kish2.hermitcrabapp.view.activities.MainActivity;
import com.kish2.hermitcrabapp.view.activities.ThemeActivity;
import com.kish2.hermitcrabapp.view.activities.UserProfileActivity;
import com.kongzue.dialog.v3.MessageDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yalantis.ucrop.UCrop;

import java.io.IOException;

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


    @BindView(R.id.lav_heart_purchase)
    LottieAnimationView mHeartPurchase;
    @BindView(R.id.btn_ani_start)
    Button aniStart;
    @BindView(R.id.btn_ani_reverse)
    Button aniReverse;
    @BindView(R.id.btn_ani_reset)
    Button aniReset;


    /* 发布中心*/
    @BindView(R.id.ll_old_publish)
    View mOldPublish;
    /* 快速入口*/
    /* 学生服务*/
    private PersonalPresenter mPresenter;

    /* 记录上一次的取样值 */
    private int curSampleRadius = 0;
    private int curSampleVal = 0;

    /* 这三个方法必须重写 */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();

        curSampleRadius = ApplicationConfigUtil.sample_radius;
        curSampleVal = ApplicationConfigUtil.sample_value;

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
    protected void themeChanged() {
        mMask.setImageDrawable(HermitCrabVectorIllustrations.VI_CIRCLE);
        if (!ApplicationConfigUtil.BANNER_DEFAULT && ApplicationConfigUtil.HAS_BANNER_BKG) {
            return;
        }
        mAppBarLayout.setBackgroundColor(ThemeUtil.Theme.afterGetResourcesColorId);
        /* Vector图片资源 */
        mSideMenu.setImageDrawable(HermitCrabVectorIllustrations.VI_MENU);
        mTheme.setImageDrawable(HermitCrabVectorIllustrations.VI_THEME);
        mSetting.setImageDrawable(HermitCrabVectorIllustrations.VI_SETTING);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void initHandler() {
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
    }

    @Override
    public void getLayoutComponentsAttr() {
        mCollapsingHeight = mCollapsingToolbarLayout.getHeight();
        /* 必须先锁定appBarLayout高度 */
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
        if (HermitCrabApp.IS_USER_LOG_IN && HermitCrabApp.LOAD_USER_SUCCESS) {
            username.setText(HermitCrabApp.USER.getUsername());
            if (!HermitCrabApp.USER.isInfoBind())
                userBindInfo.setText("(未绑定学生信息)");
            else userBindInfo.setText(HermitCrabApp.USER.getDepartment());
        }
        /* 因为存在图片会使appLayout扩张，所以在appLayout固定高度之后，在refreshData中进行设置 */
        if (!ApplicationConfigUtil.BANNER_DEFAULT) {
            if (ApplicationConfigUtil.HAS_BANNER_BKG && ApplicationConfigUtil.USER_BANNER_BKG != null) {
                Palette.from(ApplicationConfigUtil.USER_BANNER_BKG).generate(palette -> {
                    setTextAndIconColor(ThemeUtil.isLightTone(palette, 0xa));
                });
                mAppBarLayout.setBackground(new BitmapDrawable(getResources(), ApplicationConfigUtil.USER_BANNER_BKG));
                /* 保存图片 */
                if (curSampleVal != ApplicationConfigUtil.sample_value || curSampleRadius != ApplicationConfigUtil.sample_radius) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                FileStorageManager.storeBitmapAsPng(ApplicationConfigUtil.USER_BANNER_BKG, FileStorageManager.banner_bkg_file_name, FileStorageManager.DIR_TYPE.USER_BANNER_BACKGROUND);
                                curSampleRadius = ApplicationConfigUtil.sample_radius;
                                curSampleVal = ApplicationConfigUtil.sample_value;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            }
        }
        if (ApplicationConfigUtil.HAS_AVATAR && ApplicationConfigUtil.USER_AVATAR != null) {
            mUserAvatar.setImageBitmap(ApplicationConfigUtil.USER_AVATAR);
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
            CustomTarget<Bitmap> customTarget = new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    onBannerBackgroundUploadSuccess(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            };
            /* 头像 */
            messageDialog.setOnCancelButtonClickListener((baseDialog, v1) -> {
                if (ApplicationConfigUtil.HAS_AVATAR && ApplicationConfigUtil.USER_AVATAR != null) {
                    Glide.with(this).asBitmap().load(ApplicationConfigUtil.USER_AVATAR).into(customTarget);
                }
                return false;
            });
            /* 相册 */
            messageDialog.setOnOtherButtonClickListener((baseDialog, v1) -> {
                UCrop.Options options = new UCrop.Options();
                int ratio = mAppBarLayout.getWidth() / mAppBarLayout.getHeight();
                options.withAspectRatio(ratio, 1);
                ((MainActivity) requireActivity()).setSysInteractArgs(
                        SysInteractUtil.FILE_OPERATE_PURPOSE.BANNER_BKG,
                        FileStorageManager.banner_bkg_file_name,
                        FileStorageManager.DIR_TYPE.USER_BANNER_BACKGROUND,
                        SysInteractUtil.request_gallery_activity_crop, options);
                ((MainActivity) requireActivity()).uploadPic();
                return false;
            });
            /* 系统默认 */
            messageDialog.setOnOkButtonClickListener((baseDialog, v1) -> {
                mAppBarLayout.setBackgroundColor(themeColorId);
                setTextAndIconColor(false);
                ApplicationConfigUtil.BANNER_DEFAULT = true;
                new Thread() {
                    @Override
                    public void run() {
                        ApplicationConfigUtil.storeAppConfig();
                    }
                }.start();
                return false;
            });
        });
        mSetting.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                if (!HermitCrabApp.IS_USER_LOG_IN)
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
                UCrop.Options options = new UCrop.Options();
                options.withAspectRatio(1, 1);
                options.setCircleDimmedLayer(true);
                ((MainActivity) requireActivity()).setSysInteractArgs(
                        SysInteractUtil.FILE_OPERATE_PURPOSE.USER_AVATAR,
                        FileStorageManager.user_avatar_file_name,
                        FileStorageManager.DIR_TYPE.USER_AVATAR,
                        SysInteractUtil.request_gallery_activity_crop, options);
                ((MainActivity) requireActivity()).uploadPic();
                return false;
            });
            /* 拍照 */
            messageDialog.setOnOkButtonClickListener((baseDialog, v1) -> {
                UCrop.Options options = new UCrop.Options();
                options.withAspectRatio(1, 1);
                options.setCircleDimmedLayer(true);
                ((MainActivity) requireActivity()).setSysInteractArgs(
                        SysInteractUtil.FILE_OPERATE_PURPOSE.USER_AVATAR,
                        FileStorageManager.user_avatar_file_name,
                        FileStorageManager.DIR_TYPE.USER_AVATAR,
                        SysInteractUtil.request_camera_activity_crop, options);
                ((MainActivity) requireActivity()).uploadPic();
                return false;
            });
        });
        mTheme.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                Intent intent = new Intent(requireActivity(), ThemeActivity.class);
                intent.putExtra("appBarLayoutHeight", mAppBarLayout.getHeight());
                startActivity(intent);
            }
        });
        mNSPersonalMain.setOnTouchListener(this::touchCheck);
        mSideMenu.setOnClickListener(v -> {
            mDLParentView.openDrawer(GravityCompat.START);
        });
        username.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                if (!HermitCrabApp.IS_USER_LOG_IN) {
                    startActivity(new Intent(requireActivity(), LoginActivity.class));
                } else startActivity(new Intent(requireActivity(), UserProfileActivity.class));
            }
        });
        userBindInfo.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                if (!HermitCrabApp.IS_USER_LOG_IN)
                    startActivity(new Intent(requireActivity(), LoginActivity.class));
                else startActivity(new Intent(requireActivity(), UserProfileActivity.class));
            }
        });
        mUserFriend.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                if (!HermitCrabApp.IS_USER_LOG_IN) {

                }
            }
        });
        mUserProduct.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                if (!HermitCrabApp.IS_USER_LOG_IN) {

                }
            }
        });
        mUserTopic.setOnClickListener(new OnClickMayTriggerFastRepeatListener() {
            @Override
            public void onMayTriggerFastRepeatClick(View v) {
                if (!HermitCrabApp.IS_USER_LOG_IN) {

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

        aniStart.setOnClickListener(v -> {
            mHeartPurchase.setSpeed(1);
            mHeartPurchase.playAnimation();
        });
        aniReverse.setOnClickListener(v -> {
            mHeartPurchase.setSpeed(-1);
            mHeartPurchase.playAnimation();
        });
        aniReset.setOnClickListener(v -> {
            mHeartPurchase.setFrame(1);
            mHeartPurchase.cancelAnimation();
        });
    }

    @Override
    public void attachPresenter() {
    }

    public void onUserAvatarUploadSuccess(Bitmap bitmap) {
        if (bitmap != null) {
            mUserAvatar.setImageBitmap(bitmap);
            ApplicationConfigUtil.HAS_AVATAR = true;
            ApplicationConfigUtil.USER_AVATAR = bitmap;
        }

    }

    @Override
    public void setTextAndIconColor(boolean isDarkTheme) {
        int colorId;
        if (isDarkTheme) {
            StatusBarUtil.setStatusBarDarkTheme(requireActivity(), true);
            HermitCrabVectorIllustrations.setAiColorBlack();
            colorId = HermitCrabVectorIllustrations.colorBlack;
        } else {
            StatusBarUtil.setStatusBarDarkTheme(requireActivity(), false);
            HermitCrabVectorIllustrations.setAIColorWhite();
            colorId = HermitCrabVectorIllustrations.colorWhite;
        }
        username.setTextColor(colorId);
        userBindInfo.setTextColor(colorId);
        userTopic.setTextColor(colorId);
        userProduct.setTextColor(colorId);
        userFriend.setTextColor(colorId);
        friendText.setTextColor(colorId);
        productText.setTextColor(colorId);
        topicText.setTextColor(colorId);
    }

    public void onBannerBackgroundUploadSuccess(Bitmap bitmap) {
        if (bitmap != null) {
            /* 裁剪 */
            bitmap = BitMapAndDrawableUtil.centerCropBitmapToFitView(bitmap, mAppBarLayout);
            Palette.from(bitmap).generate(palette -> {
                setTextAndIconColor(ThemeUtil.isLightTone(palette, 0xa));
            });
            Bitmap copyBitmap = bitmap;
            CustomTarget<Bitmap> customTarget = new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    mAppBarLayout.setBackground(new BitmapDrawable(getResources(), resource));
                    saveBannerBkg(copyBitmap, resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            };
            // 不要使用缓存机制
            Glide.with(this)
                    .asBitmap()
                    .load(bitmap)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .apply(RequestOptions.bitmapTransform(
                            new BlurTransformation(ApplicationConfigUtil.sample_radius, ApplicationConfigUtil.sample_value)))
                    .into(customTarget);

        }
    }

    /* 存两张图，一张原图，另一张是高斯模糊之后的图 */
    private void saveBannerBkg(Bitmap origin, Bitmap after) {
        /* 将bitmap存入手机 */
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    /* 创建文件 */
                    ApplicationConfigUtil.ORIGIN_BANNER_BKG_URI = FileStorageManager.storeBitmapAsPng(origin, FileStorageManager.origin_banner_bkg_file_name, FileStorageManager.DIR_TYPE.USER_BANNER_BACKGROUND);
                    FileStorageManager.storeBitmapAsPng(after, FileStorageManager.banner_bkg_file_name, FileStorageManager.DIR_TYPE.USER_BANNER_BACKGROUND);
                    /* 默认值 */
                    ApplicationConfigUtil.HAS_BANNER_BKG = true;
                    ApplicationConfigUtil.BANNER_DEFAULT = false;
                    ApplicationConfigUtil.USER_BANNER_BKG = after;
                    /* 异步保存 */
                    ApplicationConfigUtil.storeAppConfig();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
