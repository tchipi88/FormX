package com.appli.nyx.formx.ui.fragment.business.form.dialog;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;

public abstract class CommonDialog extends BaseDialogFragment<FormViewModel> {

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

	public boolean validate() {
		boolean valid = true;

		libelle_til.setError(null);
		description_til.setError(null);

		if (libelle_tiet.getText().toString().isEmpty()) {
			libelle_til.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		}

		if (description_tiet.getText().toString().isEmpty()) {
			description_til.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		}

		return valid;
	}
}
