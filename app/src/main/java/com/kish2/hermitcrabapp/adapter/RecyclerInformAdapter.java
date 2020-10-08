package com.kish2.hermitcrabapp.adapter;

import android.content.Context;
import android.icu.text.IDNA;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.bean.Inform;

import java.util.List;

public class RecyclerInformAdapter extends RecyclerView.Adapter<RecyclerInformAdapter.ItemHolder> {

    private List<Inform> mInformList;
    private Context mContext;

    public RecyclerInformAdapter(List<Inform> informList, Context context) {
        this.mInformList = informList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerInformAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 找到一个xml布局，但是是隐藏的
        View view = LayoutInflater.from(mContext).inflate(R.layout.ly_inform, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerInformAdapter.ItemHolder holder, int position) {
        Inform inform = mInformList.get(position);
        holder.title.setText(inform.getTitle());
        holder.date.setText(inform.getDate().toString());
        holder.imgSrc.setText(inform.getImgSrc());
    }

    @Override
    public int getItemCount() {
        return mInformList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;
        TextView imgSrc;

        private ItemHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tv_inform_title);
            this.date = itemView.findViewById(R.id.tv_inform_date);
            this.imgSrc = itemView.findViewById(R.id.tv_inform_img);
        }
    }
}
