package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Report;

import androidx.recyclerview.widget.RecyclerView;

public class ReportViewHolder extends RecyclerView.ViewHolder {

	public final View mView;
	public TextView mLibelleView;

	public Report mItem;

	public ReportViewHolder(View itemView) {
		super(itemView);
		mLibelleView = itemView.findViewById(R.id.libelle);
		mView = itemView;
	}

	@Override
	public String toString() {
		return super.toString() + " '" + mLibelleView.getText() + "'";
	}
}