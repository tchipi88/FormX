package com.appli.nyx.formx.ui.fragment.business.form.dialog;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;

public class TimeDialog extends BaseDialogFragment<FormViewModel> {

	@Override
	protected Class<FormViewModel> getViewModel() {
		return FormViewModel.class;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.dialog_form_add;
	}

}
