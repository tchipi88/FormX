package com.appli.nyx.formx.ui.viewholder;

import android.view.View;

import com.appli.nyx.formx.R;

public class FormViewHolder extends SelectFormViewHolder {


	public final androidx.appcompat.widget.AppCompatImageButton delete;
	public final androidx.appcompat.widget.AppCompatImageButton voir;
	public final androidx.appcompat.widget.AppCompatImageButton edit;
	public final androidx.appcompat.widget.AppCompatImageButton duplicate;


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