package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Cluster;

public class ClusterViewHolder extends RecyclerView.ViewHolder {

	public final View mView;
	public TextView mLibelleView;
	public final AppCompatImageButton delete;
	public final AppCompatImageButton edit;
	public TextView mDescriptionView;


	public ClusterViewHolder(View itemView) {
		super(itemView);
		mLibelleView = itemView.findViewById(R.id.libelle);
		mDescriptionView = itemView.findViewById(R.id.description);

		delete = itemView.findViewById(R.id.delete);
		edit = itemView.findViewById(R.id.edit);
		mView = itemView;
	}

	@Override
	public String toString() {
		return super.toString() + " '" + mLibelleView.getText() + "'";
	}

	public void bind(Cluster model) {

		mLibelleView.setText(model.getLibelle());
		mDescriptionView.setText(model.getDescription());

	}

	public void setOnViewClickListener(View.OnClickListener onClickListener) {
		mView.setOnClickListener(onClickListener);
	}

	public void setOnDeleteClickListener(View.OnClickListener onClickListener) {
		delete.setOnClickListener(onClickListener);
	}

	public void setOnEditClickListener(View.OnClickListener onClickListener) {
		edit.setOnClickListener(onClickListener);
	}
}