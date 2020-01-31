package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Form;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import butterknife.BindView;
import butterknife.OnClick;

import static com.appli.nyx.formx.utils.MyConstant.DATA;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;

public class FormEditDialog extends BaseDialogFragment<FormViewModel> {

    @BindView(R.id.libelle_tiet)
    TextInputEditText libelle_tiet;
    @BindView(R.id.libelle_til)
    TextInputLayout libelle_til;
    @BindView(R.id.description_tiet)
    TextInputEditText description_tiet;
    @BindView(R.id.description_til)
    TextInputLayout description_til;

    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_form_add;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        viewModel.getFormMutableLiveData().observe(getViewLifecycleOwner(), form -> {
            libelle_tiet.setText(form.getLibelle());
            description_tiet.setText(form.getDescription());
        });


        return view;
    }

    @OnClick(R.id.save)
    public void save(View view) {
        if (!validate()) {
            return;
        }

        String libelle = libelle_tiet.getText().toString();
        Form form = new Form();
        form.setLibelle(libelle);
        form.setDescription(description_tiet.getText().toString());

        FirebaseFirestore.getInstance()
                .collection(FORM_PATH)
                .document(SessionUtils.getUserUid()).collection(DATA)
                .document(viewModel.getFormMutableLiveData().getValue().getId())
                .set(form).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                NavHostFragment.findNavController(FormEditDialog.this).navigateUp();
            } else {
                AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
            }
        });


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        libelle_tiet.requestFocus();

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle(getResources().getString(R.string.edit_form));
    }

    public boolean validate() {
        boolean valid = true;

        libelle_til.setError(null);

        if (libelle_tiet.getText().toString().isEmpty()) {
            libelle_til.setError(getResources().getText(R.string.error_field_required));
            valid = false;
        }


        return valid;
    }

}
