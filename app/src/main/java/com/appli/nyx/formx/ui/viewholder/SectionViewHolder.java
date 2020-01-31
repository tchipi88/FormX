package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Section;

public class SectionViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mLibelleView;

    public final AppCompatImageView delete;
    public final AppCompatImageView duplicate;
    public final AppCompatImageView edit;

    public Section mItem;

    public SectionViewHolder(View view) {
        super(view);
        mView = view;
        mLibelleView = view.findViewById(R.id.libelle);

        delete = view.findViewById(R.id.delete);
        duplicate = view.findViewById(R.id.duplicate);
        edit = view.findViewById(R.id.edit);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mLibelleView.getText() + "'";
    }
}