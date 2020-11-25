package com.kish2.hermitcrabapp.adapters;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.adapters.viewholder.VHInform;
import com.kish2.hermitcrabapp.bean.Inform;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InformAdapter extends BaseQuickAdapter<Inform, VHInform> implements LoadMoreModule {

    private Context mContext;

    public InformAdapter(Context context, @Nullable List<Inform> data) {
        super(R.layout.view_item_inform_preview, data);
        this.mContext = context;
    }

    public InformAdapter(Context context) {
        super(R.layout.view_item_inform_preview);
        this.mContext = context;
    }

    @Override
    protected void convert(@NotNull VHInform vhInform, Inform inform) {
        vhInform.weekDay.setText(inform.getTitle());
        vhInform.date.setText(inform.getDate().toString());
        Glide.with(mContext)
                .load("http://cipp.ustb.edu.cn/attachment/0/20201120/AF93B12399914663BEC44B2A49FFEFCD_mmexport1605834669317.jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(vhInform.img);
    }
}
