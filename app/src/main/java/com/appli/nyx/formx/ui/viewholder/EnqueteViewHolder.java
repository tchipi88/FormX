package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Enquete;

public class EnqueteViewHolder extends RecyclerView.ViewHolder {

	public final View mView;
	public TextView mLibelleView;
	public final AppCompatImageView delete;
	public final AppCompatImageView share;
	public final AppCompatImageView edit;
	public TextView mDescriptionView;

	public Enquete mItem;

	public EnqueteViewHolder(View itemView) {
		super(itemView);
		mView = itemView;
		mLibelleView = itemView.findViewById(R.id.libelle);
		mDescriptionView = itemView.findViewById(R.id.description);

		delete = itemView.findViewById(R.id.delete);
		share = itemView.findViewById(R.id.share);
		edit = itemView.findViewById(R.id.edit);

	}

	@Override
	public String toString() {
		return super.toString() + " '" + mLibelleView.getText() + "'";
	}
}