package com.appli.nyx.formx.ui.fragment.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.viewmodel.NetworkErrorViewModel;
import com.appli.nyx.formx.utils.NetworkUtils;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

public class NetworkErrorFragment extends Fragment {

	@Inject
	protected ViewModelProvider.Factory viewModelFactory;

	NetworkErrorViewModel viewModel;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(NetworkErrorViewModel.class);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_network_error, container, false);
		view.findViewById(R.id.retry).setOnClickListener(v -> {

			if (NetworkUtils.isConnected(getContext())) {
				viewModel.connected();
			} else {
				viewModel.unconnected();
			}
		});

		viewModel.getNetworkState().observe(getViewLifecycleOwner(), networkState -> {
			if (NetworkErrorViewModel.NetworkState.CONNECTED.equals(networkState)) {
				NavHostFragment.findNavController(NetworkErrorFragment.this).popBackStack();
			}

		});

		return view;
	}
}
