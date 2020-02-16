package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Enquete;

public class EnqueteSharedViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public final AppCompatImageView delete;
    public TextView mLibelleView;
    public TextView mDescriptionView;
    public Enquete mItem;

    public EnqueteSharedViewHolder(View itemView) {
        super(itemView);
        mLibelleView = itemView.findViewById(R.id.libelle);
        mDescriptionView = itemView.findViewById(R.id.description);

        delete = itemView.findViewById(R.id.delete);
        mView = itemView;
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mLibelleView.getText() + "'";
    }
}