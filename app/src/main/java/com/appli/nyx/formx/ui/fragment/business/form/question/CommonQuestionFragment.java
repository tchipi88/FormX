package com.appli.nyx.formx.ui.fragment.business.form.question;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.fragment.business.form.FormFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;

public abstract class CommonQuestionFragment extends ViewModelFragment<FormViewModel> {

	@Override
	protected Class<FormViewModel> getViewModel() {
		return FormViewModel.class;
	}

	@BindView(R.id.libelle_tiet)
	TextInputEditText libelle_tiet;
	@BindView(R.id.libelle_til)
	TextInputLayout libelle_til;

	@BindView(R.id.description_tiet)
	TextInputEditText description_tiet;
	@BindView(R.id.description_til)
	TextInputLayout description_til;


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.question, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_duplicate:
				return true;
			case R.id.action_delete:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}


	public boolean validate() {
		boolean valid = true;

		libelle_til.setError(null);
		description_til.setError(null);

		if (libelle_tiet.getText().toString().isEmpty()) {
			libelle_til.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		}


		return valid;
	}
}
