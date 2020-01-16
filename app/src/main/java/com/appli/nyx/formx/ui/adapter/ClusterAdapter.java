package com.appli.nyx.formx.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Cluster;
import com.appli.nyx.formx.ui.viewholder.ClusterViewHolder;
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter;
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener;

public class ClusterAdapter extends GenericRecyclerViewAdapter<Cluster, OnRecyclerItemClickListener, ClusterViewHolder> {

	public ClusterAdapter(Context context, OnRecyclerItemClickListener listener) {
		super(context, listener);
	}

	@Override
	public ClusterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ClusterViewHolder(inflate(R.layout.viewholder_cluster, parent), getListener());
	}

}