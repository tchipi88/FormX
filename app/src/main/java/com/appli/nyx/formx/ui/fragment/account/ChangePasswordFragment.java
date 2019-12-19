package com.appli.nyx.formx.ui.fragment.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.LoggedFragment;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;

import androidx.navigation.Navigation;

public class ChangePasswordFragment extends LoggedFragment {

	EditText password;
	TextInputLayout passwordLayout;
	EditText confirmPassword;
	TextInputLayout confirmPasswordLayout;

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_change_password;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = super.onCreateView(inflater, container, savedInstanceState);

		password = rootView.findViewById(R.id.password);
		passwordLayout = rootView.findViewById(R.id.passwordLayout);
		confirmPassword = rootView.findViewById(R.id.confirmPassword);
		confirmPasswordLayout = rootView.findViewById(R.id.confirmPasswordLayout);

		rootView.findViewById(R.id.btn_save).setOnClickListener(v -> save(v));

		return rootView;
	}

	public void save(View view) {
		if (!validate()) {
			return;
		}

		showLoading(true);

		FirebaseUser firebaseUser = SessionUtils.getUserSigned();
		firebaseUser.reauthenticate(EmailAuthProvider.getCredential(firebaseUser.getEmail(), password.getText().toString())).addOnSuccessListener(aVoid -> firebaseUser.updatePassword(confirmPassword.getText().toString()).addOnSuccessListener(aVoid1 -> {
			showLoading(false);
			new MaterialAlertDialogBuilder(getActivity()).setIcon(R.drawable.ic_info_black_24dp).setTitle("Notifications").setMessage(getString(R.string.your_password_has_been_reset)).setPositiveButton("OK", (dialog, which) -> {
				Navigation.findNavController(view).navigate(R.id.action_global_mainFragment);
			}).setCancelable(false).show();
		}).addOnFailureListener(e -> {
			showLoading(false);
			AlertDialogUtils.showErrorDialog(getActivity(), e.getMessage());
		})).addOnFailureListener(e -> {
			showLoading(false);
			AlertDialogUtils.showErrorDialog(getActivity(), e.getMessage());
		});

	}

	public boolean validate() {
		boolean valid = true;
		if (password.getText().toString().isEmpty()) {
			passwordLayout.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		} else {
			if (password.getText().toString().length() < 4 || password.getText().toString().length() > 10) {
				passwordLayout.setError(getResources().getText(R.string.password_error));
				password.requestFocus();
				valid = false;
			}
		}
		if (confirmPassword.getText().toString().isEmpty()) {
			confirmPasswordLayout.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		}

		if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
			confirmPasswordLayout.setError(getString(R.string.password_dont_match));
			valid = false;
		}
		return valid;
	}
}