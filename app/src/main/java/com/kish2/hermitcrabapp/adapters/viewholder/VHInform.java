package com.kish2.hermitcrabapp.adapters.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.kish2.hermitcrabapp.R;

import org.jetbrains.annotations.NotNull;

public class VHInform extends BaseViewHolder {

    public TextView weekDay;
    public TextView date;
    public ImageView img;

    public VHInform(@NotNull View view) {
        super(view);
        this.weekDay = itemView.findViewById(R.id.tv_inform_day);
        this.date = itemView.findViewById(R.id.tv_inform_date);
        this.img = itemView.findViewById(R.id.iv_inform_img);
    }
}
