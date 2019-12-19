package com.appli.nyx.formx.ui.fragment.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.UserViewModel;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;

public class ProfilFragment extends ViewModelFragment<UserViewModel> {

	@BindView(R.id.menu_changepwd)
	TextView menu_changepwd;

	@BindView(R.id.titleName)
	TextView user_profile_name;

	@BindView(R.id.titlePhone)
	TextView user_profile_phone;

	@BindView(R.id.titleGender)
	TextView user_profile_gender;

	@BindView(R.id.titleBirthDay)
	TextView user_profile_birthday;

	@BindView(R.id.titleMail)
	TextView user_profile_email;

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_profil;
	}

	@Override
	protected Class<UserViewModel> getViewModel() {
		return UserViewModel.class;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = super.onCreateView(inflater, container, savedInstanceState);

		viewModel.getObservableUser().observe(this, user -> {

			user_profile_name.setText(user.name);
			user_profile_email.setText(user.email);
			user_profile_phone.setText(user.telephone);
			if (user.gender != null) {
				user_profile_gender.setText(user.gender.name());
			}
			user_profile_birthday.setText(user.birthDay);
			((TextView) rootView.findViewById(R.id.titleBirthPlace)).setText(user.birthPlace);

		});

		menu_changepwd.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_profilFragment_to_changePasswordFragment));
		rootView.findViewById(R.id.menu_signout).setOnClickListener(v -> {

			FirebaseUser firebaseUser = SessionUtils.getUserSigned();
			if (firebaseUser != null) {
				String userId = SessionUtils.getUserUid();
				// if you want to clear cache of specific user when logout, you can do like this.
				FirebaseAuth.getInstance().signOut();
			}
			prefsManager.clearSessionPrefs();
			signInViewModel.unAuthenticated();

		});

		rootView.findViewById(R.id.menu_profilComplete).setVisibility(prefsManager.isProfilComplete() ? View.GONE : View.VISIBLE);

		rootView.findViewById(R.id.menu_profilComplete).setOnClickListener(v -> {
			Navigation.findNavController(v).navigate(R.id.action_profilFragment_to_profilEditFragment);
		});

		return rootView;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.profil, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_edit:
				NavHostFragment.findNavController(ProfilFragment.this).navigate(R.id.action_profilFragment_to_profilEditFragment);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
