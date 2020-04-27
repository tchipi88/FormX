package com.appli.nyx.formx.ui.adapter.multiselection;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.widget.RecyclerView;

public class MyKeyProvider extends ItemKeyProvider<Long> {

    private final RecyclerView mRecyclerView;

    @SuppressLint("WrongConstant")
    public MyKeyProvider(@NonNull RecyclerView recyclerView) {
        super(SCOPE_MAPPED);
        mRecyclerView = recyclerView;
    }

    @Nullable
    @Override
    public Long getKey(int position) {
        return mRecyclerView.getAdapter().getItemId(position);
    }

    @Override
    public int getPosition(@NonNull Long key) {
        RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForItemId(key);
        return holder != null ? holder.getLayoutPosition() : RecyclerView.NO_POSITION;
    }
}