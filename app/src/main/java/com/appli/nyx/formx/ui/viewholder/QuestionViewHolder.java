package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;

public class QuestionViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mLibelleView;

    public final AppCompatImageView delete;
    public final AppCompatImageView duplicate;

    public AbstractQuestion mItem;

    public QuestionViewHolder(View view) {
        super(view);
        mView = view;
        mLibelleView = view.findViewById(R.id.libelle);

        delete = view.findViewById(R.id.delete);
        duplicate = view.findViewById(R.id.duplicate);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mLibelleView.getText() + "'";
    }

}