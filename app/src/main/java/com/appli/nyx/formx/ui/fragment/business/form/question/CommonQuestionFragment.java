package com.appli.nyx.formx.ui.fragment.business.form.question;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;

import static com.appli.nyx.formx.utils.MyConstant.DATA;
import static com.appli.nyx.formx.utils.MyConstant.FIELDS_PATH;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;
import static com.appli.nyx.formx.utils.MyConstant.SECTION_PATH;

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


	protected CollectionReference fieldsRef;


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		fieldsRef = FirebaseFirestore.getInstance()
				.collection(FORM_PATH)
				.document(SessionUtils.getUserUid())
				.collection(DATA)
				.document(viewModel.getFormMutableLiveData().getValue().getId())
				.collection(SECTION_PATH)
				.document(viewModel.getSectionMutableLiveData().getValue().getId())
				.collection(FIELDS_PATH);
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
