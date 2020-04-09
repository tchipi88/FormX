package com.appli.nyx.formx.ui.fragment.business.enquete;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Enquete;
import com.appli.nyx.formx.model.firebase.enumeration.EnqueteVisibility;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.OnClick;

import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_DATA;
import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PATH;

public class EnqueteAddDialog extends BaseDialogFragment<EnqueteViewModel> {
    @BindView(R.id.libelle_tiet)
    TextInputEditText libelle_tiet;
    @BindView(R.id.libelle_til)
    TextInputLayout libelle_til;
    @BindView(R.id.description_tiet)
    TextInputEditText description_tiet;
    @BindView(R.id.description_til)
    TextInputLayout description_til;

    @Override
    protected Class<EnqueteViewModel> getViewModel() {
        return EnqueteViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_enquete_add;
    }

    @OnClick(R.id.save)
    public void save(View view) {
        if (!validate()) {
            return;
        }
        String libelle = libelle_tiet.getText().toString();
        Enquete enquete = new Enquete();
        enquete.setLibelle(libelle);
        enquete.setDescription(description_tiet.getText().toString());
        enquete.setEnqueteVisibility(EnqueteVisibility.DRAFT);
        enquete.setAuthorId(SessionUtils.getUserUid());

        FirebaseFirestore.getInstance()
                .collection(ENQUETE_PATH)
                .document(SessionUtils.getUserUid())
                .collection(ENQUETE_DATA)
                .add(enquete).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                NavHostFragment.findNavController(EnqueteAddDialog.this).navigate(R.id.action_global_enqueteListFragment);
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
        getDialog().setTitle(getResources().getString(R.string.add_enquete));
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
