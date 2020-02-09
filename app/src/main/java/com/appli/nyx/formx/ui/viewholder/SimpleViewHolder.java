package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;

public class SimpleViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public final TextView mLibelleView;

    public SimpleViewHolder(View view) {
        super(view);
        mView = view;
        mLibelleView = view.findViewById(R.id.libelle);


    }

    @Override
    public String toString() {
        return super.toString() + " '" + mLibelleView.getText() + "'";
    }
}