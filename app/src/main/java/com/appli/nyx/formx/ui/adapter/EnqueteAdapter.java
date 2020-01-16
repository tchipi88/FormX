package com.appli.nyx.formx.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Enquete;
import com.appli.nyx.formx.ui.viewholder.EnqueteViewHolder;
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter;
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener;

public class EnqueteAdapter extends GenericRecyclerViewAdapter<Enquete, OnRecyclerItemClickListener, EnqueteViewHolder> {

	public EnqueteAdapter(Context context, OnRecyclerItemClickListener listener) {
		super(context, listener);
	}

	@Override
	public EnqueteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new EnqueteViewHolder(inflate(R.layout.viewholder_enquete, parent), getListener());
	}
}