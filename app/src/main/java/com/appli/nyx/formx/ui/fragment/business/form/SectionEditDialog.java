package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;

public class SectionEditDialog extends BaseDialogFragment<FormViewModel> {

    @Override
	protected Class<FormViewModel> getViewModel() {
		return FormViewModel.class;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.dialog_section_edit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);

        // Do all the stuff to initialize your custom view

        return v;
    }

}
