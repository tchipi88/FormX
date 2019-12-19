package com.appli.nyx.formx.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.viewmodel.NetworkErrorViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import static com.appli.nyx.formx.ui.viewmodel.NetworkErrorViewModel.NetworkState.UNCONNECTED;

public abstract class NetworkFragment extends BaseFragment {

	protected NetworkErrorViewModel networkErrorViewModel;

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		networkErrorViewModel = ViewModelProviders.of(requireActivity()).get(NetworkErrorViewModel.class);

		final NavController navController = Navigation.findNavController(view);

		networkErrorViewModel.getNetworkState().observe(getViewLifecycleOwner(), networkState -> {
			if (UNCONNECTED.equals(networkState)) {
				navController.navigate(R.id.action_global_networkErrorFragment);
			}
		});

	}
}
