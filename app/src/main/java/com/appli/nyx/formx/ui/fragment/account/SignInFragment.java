package com.appli.nyx.formx.ui.fragment.account;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.NetworkFragment;
import com.appli.nyx.formx.ui.viewmodel.SignInViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class SignInFragment extends NetworkFragment {

	protected SignInViewModel signInViewModel;

	protected static final String TAG = "SignIn_Fragment";

	EditText email;
	EditText password;
	TextInputLayout emailLayout;
	TextInputLayout passwordLayout;
	public Button btnLogin;

	protected FirebaseAuth mAuth;

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_signin;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		AndroidSupportInjection.inject(this);
		super.onCreate(savedInstanceState);
		signInViewModel = ViewModelProviders.of(requireActivity()).get(SignInViewModel.class);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = super.onCreateView(inflater, container, savedInstanceState);
		email = rootView.findViewById(R.id.email);
		password = rootView.findViewById(R.id.password);
		emailLayout = rootView.findViewById(R.id.emailLayout);
		passwordLayout = rootView.findViewById(R.id.passwordLayout);
		btnLogin = rootView.findViewById(R.id.btn_login);

		((TextView) rootView.findViewById(R.id.sign_up)).setText(Html.fromHtml(getString(R.string.don_t_have_account_sign_up_now, "#7824DD")));

		password.setOnEditorActionListener((v, actionId, event) -> {
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				attemptLogin();
				return true;
			}
			return false;
		});

		mAuth = FirebaseAuth.getInstance();

		btnLogin.setOnClickListener(view -> attemptLogin());
		return rootView;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		final NavController navController = Navigation.findNavController(view);
		// If the user presses the back button, bring them back to the home screen
		requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {

			@Override
			public void handleOnBackPressed() {
				navController.popBackStack(R.id.clusterFragment, false);
			}
		});

		signInViewModel.getAuthenticationState().observe(getViewLifecycleOwner(), authenticationState -> {
			switch (authenticationState) {
				case AUTHENTICATED:
					navController.popBackStack();
					break;
				case INVALID_AUTHENTICATION:
					AlertDialogUtils.showErrorDialog(getActivity(), getResources().getString(R.string.invalid_credentials));

					break;
			}
		});
	}

	public void attemptLogin() {

		// Réinitialise les erreurs
		email.setError(null);
		password.setError(null);

		// Stocke les valeurs au moment de la tentative de connexion
		String username = email.getText().toString();
		String password = this.password.getText().toString();

		boolean cancel = false;
		View focusView = null;

		if (TextUtils.isEmpty(password)) {
			passwordLayout.setError(getString(R.string.login_txt_password_required));
			focusView = this.password;
			cancel = true;
		}

		if (TextUtils.isEmpty(username)) {
			emailLayout.setError(getString(R.string.login_txt_username_required));
			focusView = email;
			cancel = true;
		}

		if (cancel) {
			// Une erreur s'est produite ; on focus le premier champ du formulaire avec une erreur
			focusView.requestFocus();
		} else {
			// On affiche un indicateur de progression et on lance une tâche en arrière-plan pour effectuer la tentative de connexion
			showProgress(true);

			mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(getActivity(), task -> {
				if (task.isSuccessful()) {
					// Sign in success, update UI with the signed-in user's information
					Log.d(TAG, "signInWithEmail:success");
					FirebaseUser user = mAuth.getCurrentUser();
					setCurrentUser(user);
				} else {
					// If sign in fails, display a message to the user.
					Log.w(TAG, "signInWithEmail:failure", task.getException());
					AlertDialogUtils.showErrorDialog(getActivity(), task.getException().getMessage());

					signInViewModel.invalidAuthenticated();
				}

				showProgress(false);
			});

		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	public void showProgress(boolean visible) {
		if (visible) {
			showLoading(true);
		} else {
			showLoading(false);
		}
		email.setEnabled(!visible);
		password.setEnabled(!visible);
		btnLogin.setEnabled(!visible);
		if (visible) {
			email.clearFocus();
			password.clearFocus();
		}
	}

	public void setCurrentUser(FirebaseUser firebaseUser) {
		prefsManager.clearSessionPrefs();
		signInViewModel.authenticated();
		prefsManager.setCurrentUserEmail(firebaseUser.getEmail());
		prefsManager.setCurrentUserName(firebaseUser.getDisplayName());
	}

	@OnClick(R.id.link_forgetpassword)
	public void forgetPassword(View v) {
		NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_loginFragment_to_resetPasswordRequestFragment);
	}

	@OnClick(R.id.sign_up)
	public void signUpFragment(View v) {
		NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_global_signUpFragment);
	}

}

