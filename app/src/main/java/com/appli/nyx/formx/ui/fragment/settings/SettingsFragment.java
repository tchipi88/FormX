package com.appli.nyx.formx.ui.fragment.settings;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.navigation.Navigation;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.LoggedFragment;
import com.appli.nyx.formx.utils.ImageUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.appli.nyx.formx.utils.ShareUtils;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

import butterknife.BindDrawable;
import butterknife.BindView;

public class SettingsFragment extends LoggedFragment {

	@BindView(R.id.menu_feedback)
	TextView menu_feedback;
	@BindView(R.id.menu_share)
	TextView menu_share;

	@BindView(R.id.profil)
	TextView profil;

	@BindView(R.id.menu_about)
	TextView menu_about;
	@BindView(R.id.menu_terms)
	TextView menu_terms;
	@BindView(R.id.menu_privacy)
	TextView menu_privacy;

	@BindView(R.id.menu_licences)
	TextView menu_licences;

	StorageReference storageRef;

	@BindDrawable(R.drawable.ic_account_circle_black_24dp)
	Drawable ic_account_circle_black_24dp;

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_settings;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = super.onCreateView(inflater, container, savedInstanceState);

		rootView.findViewById(R.id.settings_general).setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_settingsFragment_to_generalPreferencesFragment));
		rootView.findViewById(R.id.settings_notifications).setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_settingsFragment_to_notificationPreferencesFragment));

		menu_feedback.setOnClickListener(v -> startActivity(ShareUtils.sendFeedback(getContext())));
		menu_share.setOnClickListener(v -> startActivity(ShareUtils.share()));

		menu_about.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_settingsFragment_to_aboutFragment));
		menu_terms.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_global_termsFragment));
		menu_privacy.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_global_privacyFragment));
		menu_licences.setOnClickListener(v -> ShareUtils.openLicencesPage(getContext()));

		rootView.findViewById(R.id.profil_layout).setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_settingsFragment_to_profilFragment));
		FirebaseUser firebaseUser = SessionUtils.getUserSigned();
		if (firebaseUser != null) {
			profil.setText(prefsManager.getCurrentUserName());
			((TextView) rootView.findViewById(R.id.profil_subtitle)).setText(prefsManager.getCurrentUserEmail());
			if (firebaseUser.getPhotoUrl() != null) {
				((AppCompatImageView) rootView.findViewById(R.id.profil_img)).setImageURI(firebaseUser.getPhotoUrl());
			}

			ImageUtils.displayRoundImageFromStorageReference(getContext(), storageRef.child(SessionUtils.getUserUid()), "profil_photo.jpg", ((AppCompatImageView) rootView.findViewById(R.id.profil_img)), ic_account_circle_black_24dp);
		}
		return rootView;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		storageRef = firebaseStorage.getReference();
	}
}
