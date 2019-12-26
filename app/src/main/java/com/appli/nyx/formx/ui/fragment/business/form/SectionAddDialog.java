package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Section;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SectionAddDialog extends BaseDialogFragment<FormViewModel> {

    @Override
	protected Class<FormViewModel> getViewModel() {
		return FormViewModel.class;
    }

	@Override
	protected int getLayoutRes() {
		return R.layout.dialog_section_add;
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);

        v.findViewById(R.id.btn_save).setOnClickListener(view ->{
            String libelle=((TextInputEditText)v.findViewById(R.id.libelle_tiet)).getText().toString();
            if(TextUtils.isEmpty(libelle)){
                ((TextInputLayout)v.findViewById(R.id.libelle_til)).setError(getResources().getText(R.string.error_field_required));
            }else{
                Section section=new Section();
                section.libelle=libelle;
            }
        });

        return v;
    }

}
