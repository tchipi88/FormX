package com.appli.nyx.formx.ui.viewmodel;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignInViewModel extends ViewModel {

	final MutableLiveData<AuthenticationState> authenticationState = new MutableLiveData<>();

	@Inject
	public SignInViewModel() {
		// In this example, the user is always unAuthenticated when AbstractMainActivity is launched
		authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
	}

	public void unAuthenticated() {
		authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
	}

	public void invalidAuthenticated() {
		authenticationState.setValue(AuthenticationState.INVALID_AUTHENTICATION);
	}

	public void authenticated() {
		authenticationState.setValue(AuthenticationState.AUTHENTICATED);
	}

	public MutableLiveData<AuthenticationState> getAuthenticationState() {
		return authenticationState;
	}

	public enum AuthenticationState {
		UNAUTHENTICATED,        // Initial state, the user needs to authenticate
		AUTHENTICATED,          // The user has authenticated successfully
		INVALID_AUTHENTICATION  // Authentication failed
	}

}
