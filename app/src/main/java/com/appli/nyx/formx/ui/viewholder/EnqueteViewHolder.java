package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Enquete;
import com.leodroidcoder.genericadapter.BaseViewHolder;
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener;

public class EnqueteViewHolder extends BaseViewHolder<Enquete, OnRecyclerItemClickListener> {

	private TextView mLibelleView;

	public EnqueteViewHolder(View itemView, OnRecyclerItemClickListener listener) {
		super(itemView, listener);
		// initialize view and set click listener
		mLibelleView = itemView.findViewById(R.id.libelle);
		if (listener != null) {
			itemView.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
		}
	}

	@Override
	public void onBind(Enquete item) {
		// bind data to the views
		mLibelleView.setText(item.libelle);
	}
}