package com.appli.nyx.formx.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.viewmodel.SignInViewModel;
import com.appli.nyx.formx.utils.SessionUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import dagger.android.support.AndroidSupportInjection;

public abstract class LoggedFragment extends NetworkFragment {

	protected SignInViewModel signInViewModel;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		AndroidSupportInjection.inject(this);
		super.onCreate(savedInstanceState);
		signInViewModel = ViewModelProviders.of(requireActivity()).get(SignInViewModel.class);
		if (SessionUtils.isUserSigned()) {
			signInViewModel.authenticated();
		} else {
			signInViewModel.unAuthenticated();
		}
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final NavController navController = Navigation.findNavController(view);
		signInViewModel.getAuthenticationState().observe(getViewLifecycleOwner(), authenticationState -> {
			if (SignInViewModel.AuthenticationState.UNAUTHENTICATED.equals(authenticationState)) {
				navController.navigate(R.id.action_global_signinFragment);
			}
		});

	}
}
