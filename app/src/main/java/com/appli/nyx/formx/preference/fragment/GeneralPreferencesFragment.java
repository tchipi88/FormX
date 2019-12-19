package com.appli.nyx.formx.preference.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.appli.nyx.formx.R;

import androidx.preference.PreferenceFragmentCompat;

public class GeneralPreferencesFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		setPreferencesFromResource(R.xml.pref_general, rootKey);

	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

	}
}
