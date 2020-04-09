package com.appli.nyx.formx.ui.viewholder;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.appli.nyx.formx.R;

public class FormViewHolder extends FormSelectViewHolder {



	public final AppCompatImageView delete;
	public final AppCompatImageView voir;
	public final AppCompatImageView edit;
	public final AppCompatImageView duplicate;


	public FormViewHolder(View view) {
		super(view);

		delete = view.findViewById(R.id.delete);
		voir = view.findViewById(R.id.voir);
		edit = view.findViewById(R.id.edit);
		duplicate = view.findViewById(R.id.duplicate);

	}

	@Override
	public String toString() {
		return super.toString() + " '" + mLibelleView.getText() + "'";
	}
}