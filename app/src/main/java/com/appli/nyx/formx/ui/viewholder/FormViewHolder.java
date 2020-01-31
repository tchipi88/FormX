package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Form;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class FormViewHolder extends RecyclerView.ViewHolder {

	public final View mView;
	public final TextView mLibelleView;
	public final TextView mDescriptionView;

	public final AppCompatImageView delete;
	public final AppCompatImageView voir;
	public final AppCompatImageView edit;

	public Form mItem;

	public FormViewHolder(View view) {
		super(view);
		mView = view;
		mLibelleView = view.findViewById(R.id.libelle);
		mDescriptionView = view.findViewById(R.id.description);

		delete = view.findViewById(R.id.delete);
		voir = view.findViewById(R.id.voir);
		edit = view.findViewById(R.id.edit);

	}

	@Override
	public String toString() {
		return super.toString() + " '" + mLibelleView.getText() + "'";
	}
}