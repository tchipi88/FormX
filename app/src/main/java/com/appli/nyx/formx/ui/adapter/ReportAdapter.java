package com.appli.nyx.formx.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Report;
import com.appli.nyx.formx.ui.viewholder.ReportViewHolder;
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter;
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener;

public class ReportAdapter extends GenericRecyclerViewAdapter<Report, OnRecyclerItemClickListener, ReportViewHolder> {

	public ReportAdapter(Context context, OnRecyclerItemClickListener listener) {
		super(context, listener);
	}

	@Override
	public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ReportViewHolder(inflate(R.layout.viewholder_report, parent), getListener());
	}
}