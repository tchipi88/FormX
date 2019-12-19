package com.appli.nyx.formx.ui.viewmodel;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NetworkErrorViewModel extends ViewModel {

	final MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

	@Inject
	public NetworkErrorViewModel() {
		// In this example, the user is always unAuthenticated when MainActivity is launched
		networkState.setValue(NetworkState.CONNECTED);
	}

	public void connected() {
		networkState.setValue(NetworkState.CONNECTED);
	}

	public void unconnected() {
		networkState.setValue(NetworkState.UNCONNECTED);
	}

	public MutableLiveData<NetworkState> getNetworkState() {
		return networkState;
	}

	public enum NetworkState {
		CONNECTED, UNCONNECTED
	}
}
