package com.appli.nyx.formx.ui.fragment.account;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.User;
import com.appli.nyx.formx.ui.fragment.NetworkFragment;
import com.appli.nyx.formx.ui.viewmodel.SignInViewModel;
import com.appli.nyx.formx.ui.viewmodel.UserViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

import static com.appli.nyx.formx.utils.FirestoreConstant.USER_PATH;

public class SignUpFragment extends NetworkFragment {

	private static final String TAG = "EmailPassword";

	@BindView(R.id.link_terms)
	TextView link_terms;

	@BindView(R.id.email)
	EditText email;
	@BindView(R.id.emailLayout)
	TextInputLayout emailLayout;

	@BindView(R.id.name)
	EditText name;
	@BindView(R.id.nameLayout)
	TextInputLayout nameLayout;

	@BindView(R.id.phone)
	EditText tel;
	@BindView(R.id.phoneLayout)
	TextInputLayout phoneLayout;

	@BindView(R.id.surname)
	EditText surname;

	@BindView(R.id.password)
	EditText password;
	@BindView(R.id.passwordLayout)
	TextInputLayout passwordLayout;

	@BindView(R.id.confirmPassword)
	EditText confirmPassword;
	@BindView(R.id.confirmPasswordLayout)
	TextInputLayout confirmPasswordLayout;

	@BindView(R.id.sign_up)
	Button sign_up;

	private FirebaseAuth mAuth;

	String specialiteSelected;

	UserViewModel userViewModel;
	SignInViewModel signInViewModel;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		AndroidSupportInjection.inject(this);
		super.onCreate(savedInstanceState);
		userViewModel = ViewModelProviders.of(requireActivity()).get(UserViewModel.class);
		signInViewModel = ViewModelProviders.of(requireActivity()).get(SignInViewModel.class);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_signup;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = super.onCreateView(inflater, container, savedInstanceState);

		link_terms.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_global_privacyTermsFragment));
		link_terms.setText(Html.fromHtml(getString(R.string.by_creating_an_account_you_agree_to_our_terms_of_services_and_privacy_policy, "#7824DD")));

		mAuth = FirebaseAuth.getInstance();
		return rootView;
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	private void showProgress(boolean visible) {
		if (visible) {
			showLoading(true);
		} else {
			showLoading(false);
		}
		email.setEnabled(!visible);
		password.setEnabled(!visible);
		sign_up.setEnabled(!visible);
		if (visible) {
			email.clearFocus();
			password.clearFocus();
		}
	}

	@OnClick(R.id.sign_up)
	public void save(View view) {
		if (!validate()) {
			return;
		}
		showProgress(true);

		// [START create_user_with_email]
		mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(getActivity(), task -> {
			if (task.isSuccessful()) {
				// Sign in success, update UI with the signed-in user's information
				Log.d(TAG, "createUserWithEmail:success");

				User user1 = new User();
				user1.name = name.getText().toString();
				user1.firstName = surname.getText().toString();
				user1.email = email.getText().toString();

				mFirestore.collection(USER_PATH).document(SessionUtils.getUserUid()).set(user1).addOnSuccessListener(aVoid -> {
					prefsManager.clearSessionPrefs();
					signInViewModel.authenticated();
					prefsManager.setCurrentUserEmail(email.getText().toString());
					prefsManager.setCurrentUserName(name.getText().toString());

					Navigation.findNavController(view).navigate(R.id.action_global_mainFragment);
				}).addOnFailureListener(e -> Log.w(TAG, "Error adding Patient", e));

			} else {
				// If sign in fails, display a message to the user.
				Log.w(TAG, "createUserWithEmail:failure", task.getException());
				AlertDialogUtils.showErrorDialog(getActivity(), task.getException().getMessage());
			}

			// [START_EXCLUDE]
			showProgress(false);
			// [END_EXCLUDE]
		});
		// [END create_user_with_email]

	}

	public boolean validate() {
		boolean valid = true;

		emailLayout.setError(null);
		nameLayout.setError(null);
		passwordLayout.setError(null);
		confirmPasswordLayout.setError(null);
		phoneLayout.setError(null);

		if (tel.getText().toString().isEmpty()) {
			phoneLayout.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		} else {
			if (tel.getText().toString().length() != 9) {
				phoneLayout.setError("Not Valid Phone Number");
				valid = false;
			}
		}

		if (email.getText().toString().isEmpty()) {
			emailLayout.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		} else {
			// Regex for a valid email address
			String emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
			// Compare the regex with the email address
			Pattern pattern = Pattern.compile(emailRegEx);
			Matcher matcher = pattern.matcher(email.getText().toString());
			if (!matcher.find()) {
				emailLayout.setError(getResources().getText(R.string.enter_a_valid_email));
				valid = false;
			}
		}

		if (password.getText().toString().isEmpty()) {
			passwordLayout.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		} else {
			if (password.getText().toString().length() < 4 || password.getText().toString().length() > 10) {
				passwordLayout.setError("between 4 and 10 alphanumeric characters");
				password.requestFocus();
				valid = false;
			}
		}
		if (confirmPassword.getText().toString().isEmpty()) {
			confirmPasswordLayout.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		}
		if (name.getText().toString().isEmpty()) {
			nameLayout.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		}

		if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
			confirmPasswordLayout.setError(getString(R.string.password_dont_match));
			valid = false;
		}
		return valid;
	}

}


