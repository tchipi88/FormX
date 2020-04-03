package com.appli.nyx.formx.ui.fragment.business.cluster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.ClusterViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

public class ClusterFilsAddDialog extends BaseDialogFragment<ClusterViewModel> {

	@Override
	protected Class<ClusterViewModel> getViewModel() {
		return ClusterViewModel.class;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.dialog_add_cluster_fils;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		getDialog().setTitle(getResources().getString(R.string.add_cluster));
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		view.findViewById(R.id.card_cluster).setOnClickListener(v -> {
			NavHostFragment.findNavController(ClusterFilsAddDialog.this).navigate(R.id.action_global_clusterListFragment);
		});
		view.findViewById(R.id.card_stats).setOnClickListener(v -> {
			NavHostFragment.findNavController(ClusterFilsAddDialog.this).navigate(R.id.action_global_clusterListFragment);
		});
		view.findViewById(R.id.card_enquetes).setOnClickListener(v -> {
			NavHostFragment.findNavController(ClusterFilsAddDialog.this).navigate(R.id.action_global_clusterListFragment);
		});
		return view;
	}
}
