package com.appli.nyx.formx.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.preference.PrefsManager;
import com.appli.nyx.formx.preference.PrefsManager_;
import com.appli.nyx.formx.ui.viewmodel.NetworkErrorViewModel;
import com.appli.nyx.formx.ui.viewmodel.SignInViewModel;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import pub.devrel.easypermissions.EasyPermissions;

import static com.appli.nyx.formx.ui.viewmodel.NetworkErrorViewModel.NetworkState.UNCONNECTED;

public abstract class BaseDialogFragment<V extends ViewModel> extends DialogFragment {

	protected FirebaseFirestore mFirestore;

	protected PrefsManager prefsManager;

	@Inject
	protected ViewModelProvider.Factory viewModelFactory;

	protected V viewModel;

	protected abstract Class<V> getViewModel();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		AndroidSupportInjection.inject(this);
		super.onCreate(savedInstanceState);

		viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(getViewModel());

		signInViewModel = ViewModelProviders.of(requireActivity()).get(SignInViewModel.class);
		if (SessionUtils.isUserSigned()) {
			signInViewModel.authenticated();
		} else {
			signInViewModel.unAuthenticated();
		}
		// Enable Firestore logging
		FirebaseFirestore.setLoggingEnabled(true);
		mFirestore = FirebaseFirestore.getInstance();
	}

	@LayoutRes
	protected abstract int getLayoutRes();

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutRes(), container, false);
		ButterKnife.bind(this, view);
		prefsManager = PrefsManager_.getInstance_(getActivity());
		return view;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		// Forward results to EasyPermissions
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	protected NetworkErrorViewModel networkErrorViewModel;
	protected SignInViewModel signInViewModel;

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		networkErrorViewModel = ViewModelProviders.of(requireActivity()).get(NetworkErrorViewModel.class);

		final NavController navController = NavHostFragment.findNavController(this);

		networkErrorViewModel.getNetworkState().observe(getViewLifecycleOwner(), networkState -> {
			if (UNCONNECTED.equals(networkState)) {
				navController.navigate(R.id.action_global_networkErrorFragment);
			}
		});

		signInViewModel.getAuthenticationState().observe(getViewLifecycleOwner(), authenticationState -> {
			if (SignInViewModel.AuthenticationState.UNAUTHENTICATED.equals(authenticationState)) {
				navController.navigate(R.id.action_global_signinFragment);
			}
		});

	}
}
