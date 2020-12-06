package com.kish2.hermitcrabapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapters.viewholder.VHSecondHandPreview;
import com.kish2.hermitcrabapp.bean.SecondHandPreview;
import com.kish2.hermitcrabapp.utils.view.BitMapAndDrawableUtil;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SecondHandListAdapter extends BaseQuickAdapter<SecondHandPreview, VHSecondHandPreview> implements LoadMoreModule {

    private final Context mContext;

    public SecondHandListAdapter(@Nullable List<SecondHandPreview> data, Context mContext) {
        super(R.layout.view_item_secondhand_preview, data);
        this.mContext = mContext;
    }

    public SecondHandListAdapter(Context mContext) {
        super(R.layout.view_item_secondhand_preview);
        this.mContext = mContext;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(@NotNull VHSecondHandPreview vhSecondHandPreview, SecondHandPreview secondHandPreview) {
        vhSecondHandPreview.productTitle.setText(secondHandPreview.getProductTitle());
        vhSecondHandPreview.desirePeople.setText(secondHandPreview.getDesirePeople() + "人想要");
        vhSecondHandPreview.productPrice.setText("￥" + secondHandPreview.getProductPrice());
        vhSecondHandPreview.productPrice.setTextColor(ThemeUtil.Theme.afterGetResourcesColorId);
        vhSecondHandPreview.productTag.setText(secondHandPreview.getProductTag());
        vhSecondHandPreview.productTag.setBackground(BitMapAndDrawableUtil.getGradientCircleDrawable(mContext, 5));
    }
}
