package com.appli.nyx.formx.ui.fragment.account;

import android.content.Intent;
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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.User;
import com.appli.nyx.formx.ui.fragment.NetworkFragment;
import com.appli.nyx.formx.ui.viewmodel.SignInViewModel;
import com.appli.nyx.formx.ui.viewmodel.UserViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

import static com.appli.nyx.formx.utils.MyConstant.USER_PATH;

public class SignInFragment extends NetworkFragment {

    private static final int RC_SIGN_IN_GOOGLE = 9001;

    protected static final String TAG = "SignIn_Fragment";
    public Button btnLogin;
    protected SignInViewModel signInViewModel;
    protected FirebaseAuth mAuth;
    EditText email;
    EditText password;
    TextInputLayout emailLayout;
    TextInputLayout passwordLayout;
    @BindView(R.id.google)
    public Button google;
    @BindView(R.id.sign_in_button)
    public SignInButton login_button_google;
    GoogleSignInClient googleSignInClient;

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

        login_button_google.setOnClickListener(v -> signInWithGoogle());

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        login_button_google.setSize(SignInButton.SIZE_STANDARD);

        google.setOnClickListener(v -> signInWithGoogle());

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

        UserViewModel userViewModel = ViewModelProviders.of(requireActivity()).get(UserViewModel.class);

        mFirestore.collection(USER_PATH).document(firebaseUser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                User user = documentSnapshot.toObject(User.class);
                userViewModel.setUser(user);
                prefsManager.setCurrentUserName(user.name);
            }
        });

        NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_global_mainFragment);
    }

    @OnClick(R.id.link_forgetpassword)
    public void forgetPassword(View v) {
        NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_loginFragment_to_resetPasswordRequestFragment);
    }

    @OnClick(R.id.sign_up)
    public void signUpFragment(View v) {
        NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_global_signUpFragment);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN_GOOGLE) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                AlertDialogUtils.showErrorDialog(getContext(), e.getMessage());

            }
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgress(true);
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");

                        boolean newuser = task.getResult().getAdditionalUserInfo().isNewUser();
                        if (newuser) {

                            User user1 = new User();
                            user1.name = acct.getFamilyName();
                            user1.firstName = acct.getGivenName();
                            user1.email = acct.getEmail();
                            user1.firebaseToken = prefsManager.getFirebaseToken();

                            mFirestore.collection(USER_PATH).document(SessionUtils.getUserUid()).set(user1).addOnCompleteListener(task1 -> {
                                if (!task1.isSuccessful()) {
                                    Log.w(TAG, "Error adding User", task1.getException());
                                } else {
                                    prefsManager.clearSessionPrefs();
                                    signInViewModel.authenticated();
                                    prefsManager.setCurrentUserEmail(task.getResult().getUser().getEmail());
                                    prefsManager.setCurrentUserName(task.getResult().getUser().getDisplayName());

                                    UserViewModel userViewModel = ViewModelProviders.of(requireActivity()).get(UserViewModel.class);
                                    userViewModel.setUser(user1);

                                    NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_global_mainFragment);
                                }
                            });

                        } else {
                            setCurrentUser(task.getResult().getUser());
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                        signInViewModel.invalidAuthenticated();
                    }

                    // [START_EXCLUDE]
                    showProgress(false);
                    // [END_EXCLUDE]
                });
    }
    // [END auth_with_google]

}

