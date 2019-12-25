package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Form;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FormAddDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_form_add, container, false);

        v.findViewById(R.id.btn_save).setOnClickListener(view ->{
            String libelle=((TextInputEditText)v.findViewById(R.id.libelle_tiet)).getText().toString();
            if(TextUtils.isEmpty(libelle)){
                ((TextInputLayout)v.findViewById(R.id.libelle_til)).setError(getResources().getText(R.string.error_field_required));
            }else{
                Form form=new Form();
                form.libelle=libelle;
            }
        });

        return v;
    }

}
