package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;

public class FormSelectViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public final TextView mLibelleView;
    public final TextView mDescriptionView;

    public FormSelectViewHolder(@NonNull View view) {
        super(view);
        mView = view;
        mLibelleView = view.findViewById(R.id.libelle);
        mDescriptionView = view.findViewById(R.id.description);
    }
}
