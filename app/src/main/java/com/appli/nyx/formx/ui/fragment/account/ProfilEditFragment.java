package com.appli.nyx.formx.ui.fragment.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.navigation.Navigation;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.User;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.UserViewModel;
import com.appli.nyx.formx.utils.DateUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.appli.nyx.formx.utils.MyConstant.USER_PATH;

public class ProfilEditFragment extends ViewModelFragment<UserViewModel> {

	@BindView(R.id.surname)
	EditText surname;
	@BindView(R.id.surnameLayout)
	TextInputLayout surnameLayout;
	@BindView(R.id.name)
	EditText name;
	@BindView(R.id.nameLayout)
	TextInputLayout nameLayout;
	@BindView(R.id.birthday)
	EditText birthday;
	@BindView(R.id.birthdayLayout)
	TextInputLayout birthdayLayout;

    @BindView(R.id.phone)
	EditText tel;
	@BindView(R.id.phoneLayout)
	TextInputLayout phoneLayout;

	@BindView(R.id.town)
	EditText town;



    @Override
	protected Class<UserViewModel> getViewModel() {
		return UserViewModel.class;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_profil_edit;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = super.onCreateView(inflater, container, savedInstanceState);

		viewModel.getObservableUser().observe(getViewLifecycleOwner(), user -> {
			name.setText(user.name);
			surname.setText(user.firstName);
			birthday.setText(user.birthDay);
			tel.setText(user.telephone);
			town.setText(user.town);


        });
		return rootView;
	}

	@OnClick(R.id.save)
	public void save(View view) {
		if (!validate()) {
			return;
		}

		User user = viewModel.getObservableUser().getValue();

		user.name = (name.getText().toString());
		user.firstName = (surname.getText().toString());
		user.birthDay = (birthday.getText().toString());
		user.telephone = (tel.getText().toString());
		user.town = (town.getText().toString());

		viewModel.setUser(user);

		showLoading(true);

		DocumentReference UserRef = mFirestore.collection(USER_PATH).document(SessionUtils.getUserUid());

		Map<String, Object> UserNewValues = new HashMap<>();
		UserNewValues.put("name", user.name);
		UserNewValues.put("firstName", user.firstName);
		UserNewValues.put("birthDay", user.birthDay);
		UserNewValues.put("telephone", user.telephone);
		UserNewValues.put("town", user.town);

		UserRef.update(UserNewValues).addOnSuccessListener(aVoid -> {
			showLoading(false);
			prefsManager.setProfilComplete(true);
			Navigation.findNavController(view).navigate(R.id.action_global_profilFragment);
		}).addOnFailureListener(e -> Log.w("Profil Edit", "Transaction failure.", e));
	}

	public boolean validate() {
		boolean valid = true;
		nameLayout.setError(null);
		birthdayLayout.setError(null);
		phoneLayout.setError(null);


        if (name.getText().toString().isEmpty()) {
			nameLayout.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		}
		if (birthday.getText().toString().isEmpty()) {
			birthdayLayout.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		} else {
			try {
				LocalDate dt = DateUtils.getLocalDate(birthday.getText().toString());
				if (Years.yearsBetween(dt, LocalDate.now()).getYears() < 18) {
					birthdayLayout.setError("User too young");
					valid = false;
				}
				if (Years.yearsBetween(dt, LocalDate.now()).getYears() > 100) {
					birthdayLayout.setError(getResources().getText(R.string.max_exceeded));
					valid = false;
				}
			} catch (Exception e) {
				birthdayLayout.setError("Not Valid Date");
				valid = false;
			}
		}
		if (tel.getText().toString().isEmpty()) {
			phoneLayout.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		}

		return valid;
	}

}


