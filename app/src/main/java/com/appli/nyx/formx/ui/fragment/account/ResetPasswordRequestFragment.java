package com.appli.nyx.formx.ui.fragment.account;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.NetworkFragment;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class ResetPasswordRequestFragment extends NetworkFragment {

	EditText email;
	TextInputLayout emailLayout;
	TextView signUp;

	private FirebaseAuth mAuth;

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_reset_password_request;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = super.onCreateView(inflater, container, savedInstanceState);
		mAuth = FirebaseAuth.getInstance();

		email = rootView.findViewById(R.id.email);
		emailLayout = rootView.findViewById(R.id.emailLayout);
		signUp = rootView.findViewById(R.id.sign_up);

		signUp.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_global_signinFragment));
		signUp.setText(Html.fromHtml(getString(R.string.don_t_have_account_sign_up_now, "#7824DD")));

		rootView.findViewById(R.id.btn_reset).setOnClickListener(view -> {
			if (!validate()) {
				return;
			}

			showLoading(true);

			mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(task -> {
				showLoading(false);
				if (task.isSuccessful()) {
					new MaterialAlertDialogBuilder(getActivity()).setIcon(R.drawable.ic_info_black_24dp).setTitle(getString(R.string.reset_succesful)).setMessage(getString(R.string.msg_code_reset_password)).setPositiveButton("OK", (dialog, which) -> {
						NavHostFragment.findNavController(ResetPasswordRequestFragment.this).navigate(R.id.action_global_signinFragment);
					}).setCancelable(false).show();

				} else {
					AlertDialogUtils.showErrorDialog(getActivity(), task.getException().getMessage());
				}
			});

		});

		return rootView;
	}

	public boolean validate() {

		boolean valid = true;
		emailLayout.setError(null);

		if (email.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
			emailLayout.setError(getString(R.string.enter_a_valid_email));
			email.requestFocus();
			valid = false;
		}

		return valid;
	}

}