package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Report;
import com.leodroidcoder.genericadapter.BaseViewHolder;
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener;

public class ReportViewHolder extends BaseViewHolder<Report, OnRecyclerItemClickListener> {

	private TextView mLibelleView;

	public ReportViewHolder(View itemView, OnRecyclerItemClickListener listener) {
		super(itemView, listener);
		// initialize view and set click listener
		mLibelleView = itemView.findViewById(R.id.libelle);
		if (listener != null) {
			itemView.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
		}
	}

	@Override
	public void onBind(Report item) {
		// bind data to the views
		mLibelleView.setText(item.libelle);
	}
}