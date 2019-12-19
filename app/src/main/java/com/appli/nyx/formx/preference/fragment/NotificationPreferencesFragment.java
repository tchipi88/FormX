package com.appli.nyx.formx.preference.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.appli.nyx.formx.R;

import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

public class NotificationPreferencesFragment extends MyPreferencesFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		setPreferencesFromResource(R.xml.pref_notification, rootKey);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Bind the summaries of EditText/List/Dialog/Ringtone preferences
		// to their values. When their values change, their summaries are
		// updated to reflect the new value, per the Android Design
		// guidelines.

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		sharedPref.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onDetach() {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		sharedPref.unregisterOnSharedPreferenceChangeListener(this);
		super.onDetach();
	}

	@Override
	public void onDisplayPreferenceDialog(Preference preference) {

	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

	}
}
