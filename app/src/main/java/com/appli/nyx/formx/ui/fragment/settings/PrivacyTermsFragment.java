package com.appli.nyx.formx.ui.fragment.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class PrivacyTermsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_privacy_terms, container, false);
		view.findViewById(R.id.action_button_1).setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_global_privacyFragment));
		view.findViewById(R.id.action_button_2).setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_global_termsFragment));
		return view;
	}

}
