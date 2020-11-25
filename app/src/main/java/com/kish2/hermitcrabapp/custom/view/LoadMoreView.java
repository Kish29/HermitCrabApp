package com.kish2.hermitcrabapp.custom.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.loadmore.BaseLoadMoreView;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.ybq.android.spinkit.SpinKitView;
import com.kish2.hermitcrabapp.R;
import com.kish2.hermitcrabapp.utils.view.ThemeUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LoadMoreView extends BaseLoadMoreView {
    @NotNull
    @Override
    public View getLoadComplete(@NotNull BaseViewHolder baseViewHolder) {
        // 布局中 “当前一页加载完成”的View
        return Objects.requireNonNull(baseViewHolder.findView(R.id.tv_nmsl));
    }

    @NotNull
    @Override
    public View getLoadEndView(@NotNull BaseViewHolder baseViewHolder) {
        // 布局中 “全部加载结束，没有数据”的View
        return Objects.requireNonNull(baseViewHolder.findView(R.id.tv_nmsl));
    }

    @NotNull
    @Override
    public View getLoadFailView(@NotNull BaseViewHolder baseViewHolder) {
        return Objects.requireNonNull(baseViewHolder.findView(R.id.tv_nmsl));
    }

    @NotNull
    @Override
    public View getLoadingView(@NotNull BaseViewHolder baseViewHolder) {
        SpinKitView view = baseViewHolder.findView(R.id.skv_load_more);
        assert view != null;
        view.setColor(ThemeUtil.Theme.afterGetResourcesColorId);
        return view;
    }

    @NotNull
    @Override
    public View getRootView(@NotNull ViewGroup viewGroup) {
        // 整个 LoadMore 布局
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_load_more, viewGroup, false);
    }
}
