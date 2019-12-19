package com.appli.nyx.formx.ui.fragment.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.appli.nyx.formx.R;

import androidx.fragment.app.Fragment;

public class TermsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_terms, container, false);

		WebView web_view = view.findViewById(R.id.web_view);
		web_view.requestFocus();
		web_view.getSettings().setJavaScriptEnabled(true);
		web_view.getSettings().setGeolocationEnabled(true);
		web_view.setSoundEffectsEnabled(true);
		web_view.loadUrl("file:///android_asset/terms_and_conditions.html");

		return view;
	}

}
