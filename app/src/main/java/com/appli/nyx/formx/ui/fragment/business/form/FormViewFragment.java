package com.appli.nyx.formx.ui.fragment.business.form;

import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;

public class FormViewFragment extends ViewModelFragment<FormViewModel> {
    @Override
    protected int getLayoutRes() {
        return 0;
    }

	@Override
	protected Class<FormViewModel> getViewModel() {
		return FormViewModel.class;
	}
}
