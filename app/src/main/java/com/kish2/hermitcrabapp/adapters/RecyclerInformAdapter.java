package com.kish2.hermitcrabapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.bean.Inform;

import java.util.Date;
import java.util.List;

public class RecyclerInformAdapter extends RecyclerView.Adapter<RecyclerInformAdapter.ItemHolder> {

    private List<Inform> mInformList;
    private Context mContext;

    public RecyclerInformAdapter(List<Inform> informList, Context context) {
        this.mInformList = informList;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public RecyclerInformAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 找到一个xml布局，但是是隐藏的
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_inform, parent, false);
//        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = 0;
        return new ItemHolder(view);
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerInformAdapter.ItemHolder holder, int position) {
        Inform inform = mInformList.get(position);
        holder.title.setText("星期日");
        holder.date.setText(new SimpleDateFormat("yyyy年MM月dd日").format(new Date().getTime()));
        Glide.with(mContext)
                .load("http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgSrc);
//        holder.imgSrc.setImageResource(img[inform.getPicInt()]);
//        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        /*layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;*/
//        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return mInformList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;
        ImageView imgSrc;

        private ItemHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tv_inform_day);
            this.date = itemView.findViewById(R.id.tv_inform_date);
            this.imgSrc = itemView.findViewById(R.id.tv_inform_img);
        }
    }
}
