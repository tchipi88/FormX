package com.appli.nyx.formx.ui.fragment.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.User;
import com.appli.nyx.formx.model.firebase.enumeration.Gender;
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

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.OnClick;

import static com.appli.nyx.formx.utils.FirestoreConstant.USER_PATH;

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
	@BindView(R.id.birthplace)
	EditText birthplace;
	@BindView(R.id.birthplaceLayout)
	TextInputLayout birthplaceLayout;
	@BindView(R.id.phone)
	EditText tel;
	@BindView(R.id.phoneLayout)
	TextInputLayout phoneLayout;

	@BindView(R.id.gender)
	RadioGroup gender;
	@BindView(R.id.gender_female)
	RadioButton gender_female;
	@BindView(R.id.gender_male)
	RadioButton gender_male;

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

        viewModel.getObservableUser().observe(getViewLifecycleOwner(), User -> {
			name.setText(User.name);
			surname.setText(User.firstName);
			birthplace.setText(User.birthPlace);
			birthday.setText(User.birthDay);
			tel.setText(User.telephone);

            if (Gender.F.equals(User.gender)) {
                gender.check(R.id.gender_female);
            }
            if (Gender.M.equals(User.gender)) {
                gender.check(R.id.gender_male);
            }
		});
		return rootView;
	}

	@OnClick(R.id.save)
	public void save(View view) {
		if (!validate()) {
			return;
		}

		User User = viewModel.getObservableUser().getValue();

		User.name = (name.getText().toString());
		User.firstName = (surname.getText().toString());
		User.birthPlace = (birthplace.getText().toString());
		User.birthDay = (birthday.getText().toString());
		User.telephone = (tel.getText().toString());

		if (gender.getCheckedRadioButtonId() == R.id.gender_female) {
			User.gender = Gender.F;
		}
		if (gender.getCheckedRadioButtonId() == R.id.gender_male) {
			User.gender = (Gender.M);
		}

		viewModel.setUser(User);

		showLoading(true);

		DocumentReference UserRef = mFirestore.collection(USER_PATH).document(SessionUtils.getUserUid());

		Map<String, Object> UserNewValues = new HashMap<>();
		UserNewValues.put("name", User.name);
		UserNewValues.put("firstName", User.firstName);
		UserNewValues.put("birthPlace", User.birthPlace);
		UserNewValues.put("birthDay", User.birthDay);
		UserNewValues.put("telephone", User.telephone);

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
		birthplaceLayout.setError(null);
		phoneLayout.setError(null);

		if (gender.getCheckedRadioButtonId() == -1) {
			gender_female.setError("Please setUser Gender");
			valid = false;
		}
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
		} else {
			if (tel.getText().toString().length() != 9) {
				phoneLayout.setError("Not Valid Phone Number");
				valid = false;
			}
		}

		if (birthplace.getText().toString().isEmpty()) {
			birthplaceLayout.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		}

		return valid;
	}

}


