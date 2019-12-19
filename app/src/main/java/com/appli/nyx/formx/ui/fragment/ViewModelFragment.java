package com.appli.nyx.formx.ui.fragment;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public abstract class ViewModelFragment<V extends ViewModel> extends LoggedFragment {

	@Inject
	protected ViewModelProvider.Factory viewModelFactory;

	protected V viewModel;

	protected abstract Class<V> getViewModel();

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(getViewModel());
	}

}