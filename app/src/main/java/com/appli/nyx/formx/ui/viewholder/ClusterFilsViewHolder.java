package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.ClusterFils;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class ClusterFilsViewHolder extends RecyclerView.ViewHolder {

	public final View mView;
	public final AppCompatImageView delete;
	public TextView mLibelleView;
	public TextView mDescriptionView;

	public ClusterFilsViewHolder(View itemView) {
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

	public void bind(ClusterFils model) {
		mLibelleView.setText(model.getLibelle());
		mDescriptionView.setText(model.getDescription());

		delete.setOnClickListener(v -> {
			Toast.makeText(mView.getContext(), "Not Yet Implemented", Toast.LENGTH_LONG).show();
		});
	}
}