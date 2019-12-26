package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Form;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;

public class FormAddDialog extends BaseDialogFragment<FormViewModel> {

    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_form_add;
    }

    @BindView(R.id.libelle_tiet)
    TextInputEditText libelleTiet;
    @BindView(R.id.libelle_til)
    TextInputLayout libelleTil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        v.findViewById(R.id.btn_save).setOnClickListener(view ->{
            String libelle = libelleTiet.getText().toString();
            if(TextUtils.isEmpty(libelle)){
                libelleTil.setError(getResources().getText(R.string.error_field_required));
            }else{
                Form form=new Form();
                form.libelle=libelle;
            }
        });

        return v;
    }

}
