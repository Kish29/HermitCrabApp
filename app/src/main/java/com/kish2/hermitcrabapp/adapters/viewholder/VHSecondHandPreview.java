package com.kish2.hermitcrabapp.adapters.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.kish2.hermitcrabapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

public class VHSecondHandPreview extends BaseViewHolder {

    public ImageView productImg;

    public TextView productTag;

    public RoundedImageView venderImg;

    public TextView venderName;

    public TextView productPrice;

    public TextView desirePeople;

    public TextView productTitle;


    public VHSecondHandPreview(@NotNull View view) {
        super(view);
        productImg = view.findViewById(R.id.iv_product_img);
        productTag = view.findViewById(R.id.tv_product_tag);
        venderImg = view.findViewById(R.id.riv_vender_avatar);
        venderName = view.findViewById(R.id.tv_vender_name);
        productPrice = view.findViewById(R.id.tv_product_price);
        desirePeople = view.findViewById(R.id.tv_desire_people);
        productTitle = view.findViewById(R.id.tv_product_title);
    }
}
