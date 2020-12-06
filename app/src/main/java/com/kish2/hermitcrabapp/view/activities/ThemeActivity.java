package com.kish2.hermitcrabapp.view.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.kish2.hermitcrabapp.HermitCrabApp;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.bean.HermitCrabVectorIllustrations;
import com.kish2.hermitcrabapp.custom.view.StatusFixedToolBar;
import com.kish2.hermitcrabapp.model.handler.MessageForHandler;
import com.kish2.hermitcrabapp.utils.dev.ApplicationConfigUtil;
import com.kish2.hermitcrabapp.utils.dev.StatusBarUtil;
import com.kish2.hermitcrabapp.utils.view.BitMapAndDrawableUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

@SuppressLint("NonConstantResourceId")
public class ThemeActivity extends BaseActivity {

    @BindView(R.id.sft_toolbar_top)
    StatusFixedToolBar mToolBar;

    @BindView(R.id.isb_sample_radius)
    SeekBar mSampleRadius;
    @BindView(R.id.tv_radius)
    TextView mRadius;
    @BindView(R.id.isb_sampling_value)
    SeekBar mSamplingValue;
    @BindView(R.id.tv_sampling)
    TextView mSampling;
    @BindView(R.id.riv_theme_banner)
    RoundedImageView mBannerImage;

    ViewGroup[] mThemeList;
    ImageView[] mThemeListChecks;
    View[] mColorBlocks;
    String[] theme_name;

    private int appBarLayoutHeight = 0;
    private CustomTarget<Bitmap> customTarget;

    /* 压缩后的banner <= 50 kb
     * 用于展示 */
    private Bitmap compressedBanner;

    @Override
    protected void themeChanged() {
        StatusBarUtil.setSinkStatusBar(this, false, ThemeUtil.Theme.afterGetResourcesColorId);
        mToolBar.bindAndSetThisToolbar(this, true, "主题颜色");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* banner的高度 */
        appBarLayoutHeight = getIntent().getIntExtra("appBarLayoutHeight", 0);
        customTarget = new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                mBannerImage.setImageBitmap(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        };
        setContentView(R.layout.activity_theme);
        ButterKnife.bind(this);
        getAndSetLayoutView();
        registerViewComponentsAffairs();
        HermitCrabApp.APP_THREAD_POOL.execute(this::loadData);
    }


    @Override
    public void getAndSetLayoutView() {
        StatusBarUtil.setSinkStatusBar(this, false, ThemeUtil.Theme.afterGetResourcesColorId);
        mToolBar.bindAndSetThisToolbar(this, true, "主题颜色");
        theme_name = getResources().getStringArray(R.array.theme_name);
        mThemeList = new ViewGroup[9];
        mThemeListChecks = new ImageView[9];
        mColorBlocks = new View[9];
        mThemeList[0] = findViewById(R.id.theme_1);
        mThemeList[1] = findViewById(R.id.theme_2);
        mThemeList[2] = findViewById(R.id.theme_3);
        mThemeList[3] = findViewById(R.id.theme_4);
        mThemeList[4] = findViewById(R.id.theme_5);
        mThemeList[5] = findViewById(R.id.theme_6);
        mThemeList[6] = findViewById(R.id.theme_7);
        mThemeList[7] = findViewById(R.id.theme_8);
        mThemeList[8] = findViewById(R.id.theme_9);
        for (int i = 0; i < 9; i++) {
            mThemeList[i].setTag(i);
            mThemeListChecks[i] = mThemeList[i].findViewById(R.id.iv_theme_list_check);
            ((TextView) mThemeList[i].findViewById(R.id.tv_theme_item_title)).setText(theme_name[i]);
            mColorBlocks[i] = mThemeList[i].findViewById(R.id.v_color_block);
            mColorBlocks[i].setBackground(BitMapAndDrawableUtil.getRoundRectangleDrawable(ContextCompat.getColor(this, ThemeUtil.AppTheme[i]), -1, -1, 8));
        }
        mThemeListChecks[ThemeUtil.THEME_ID].setImageDrawable(HermitCrabVectorIllustrations.VI_CHECK_THEME);

        BitMapAndDrawableUtil.setSeekBarColor(mSampleRadius, themeColorId);
        BitMapAndDrawableUtil.setSeekBarColor(mSamplingValue, themeColorId);
        mRadius.setTextColor(themeColorId);
        mSampling.setTextColor(themeColorId);
        mRadius.setText(String.valueOf(ApplicationConfigUtil.sample_radius));
        mSampleRadius.setProgress(ApplicationConfigUtil.sample_radius * 4);
        mSampling.setText(String.valueOf(ApplicationConfigUtil.sample_value));
        mSamplingValue.setProgress(ApplicationConfigUtil.sample_value * 33 + 1);
        if (!ApplicationConfigUtil.BANNER_DEFAULT && ApplicationConfigUtil.HAS_BANNER_BKG) {
            mBannerImage.getLayoutParams().height = appBarLayoutHeight;
            compressedBanner = BitmapFactory.decodeFile(ApplicationConfigUtil.LOCAL_BANNER_URI);
            /* 压缩到50KB以内 */
            compressedBanner = BitMapAndDrawableUtil.compressImageToSize(compressedBanner, 50);
            Glide.with(this)
                    .load(compressedBanner)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(ApplicationConfigUtil.sample_radius, ApplicationConfigUtil.sample_value)))
                    .into(mBannerImage);
        }
    }


    @Override
    public void loadData() {
    }

    @Override
    public void refreshData() {
    }

    @Override
    public void registerViewComponentsAffairs() {
        mToolBar.setNavigationOnClickListener(v -> {
            ApplicationConfigUtil.storeAppConfig();
            finish();
        });
        for (ViewGroup theme : mThemeList) {
            theme.setOnClickListener(v -> {
                int cur = (int) v.getTag();
                ThemeUtil.changeTheme(cur);
                StatusBarUtil.setSinkStatusBar(this, false, ThemeUtil.Theme.afterGetResourcesColorId);
                mToolBar.bindAndSetThisToolbar(this, true, "主题颜色");
                mThemeList = new ViewGroup[9];
                BitMapAndDrawableUtil.setSeekBarColor(mSampleRadius, ThemeUtil.Theme.afterGetResourcesColorId);
                BitMapAndDrawableUtil.setSeekBarColor(mSamplingValue, ThemeUtil.Theme.afterGetResourcesColorId);
                mRadius.setTextColor(ThemeUtil.Theme.afterGetResourcesColorId);
                mSampling.setTextColor(ThemeUtil.Theme.afterGetResourcesColorId);
                /* 双向指针 */
                int k = 0, j = mThemeList.length - 1;
                while (k <= j) {
                    if (k != cur)
                        mThemeListChecks[k].setImageDrawable(null);
                    if (j != cur)
                        mThemeListChecks[j].setImageDrawable(null);
                    k++;
                    j--;
                }
                mThemeListChecks[cur].setImageDrawable(HermitCrabVectorIllustrations.VI_CHECK_THEME);
                HermitCrabApp.APP_THREAD_POOL.execute(ApplicationConfigUtil::storeAppConfig);
            });
        }
        mSampleRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int val = progress / 4;
                if (val == 0) val = 1;
                /* 如果改变了 */
                if (val != ApplicationConfigUtil.sample_radius) {
                    ApplicationConfigUtil.sample_radius = val;
                    updatePreview(val, ApplicationConfigUtil.sample_value);
                    mRadius.setText(String.valueOf(val));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSamplingValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int val = progress / 33 + 1;
                if (val != ApplicationConfigUtil.sample_value) {
                    ApplicationConfigUtil.sample_value = val;
                    mSampling.setText(String.valueOf(val));
                    updatePreview(ApplicationConfigUtil.sample_radius, val);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updatePreview(int radius, int val) {
        /* 利用签名机制，更新图片 */
        RequestOptions signature = new RequestOptions().signature(new ObjectKey(System.currentTimeMillis()));
        Glide.with(ThemeActivity.this)
                .asBitmap()
                .apply(signature)
                .load(compressedBanner)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(radius, val)))
                .into(customTarget);
    }

    @Override
    public void getLayoutComponentsAttr() {
    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void initHandler() {
        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MessageForHandler.LOCAL_DATA_LOADED:
                    case MessageForHandler.SYSTEM_FAILURE:
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        ApplicationConfigUtil.storeAppConfig();
        super.onBackPressed();
    }
}
