package com.appli.nyx.formx.ui.fragment.business.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Report;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.ReportViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.OnClick;

import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PATH;

public class ReportEditDialog extends BaseDialogFragment<ReportViewModel> {

    @BindView(R.id.libelle_tiet)
    TextInputEditText libelle_tiet;
    @BindView(R.id.libelle_til)
    TextInputLayout libelle_til;
    @BindView(R.id.description_tiet)
    TextInputEditText description_tiet;
    @BindView(R.id.description_til)
    TextInputLayout description_til;

    @Override
    protected Class<ReportViewModel> getViewModel() {
        return ReportViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_enquete_add;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        viewModel.getReportMutableLiveData().observe(getViewLifecycleOwner(), form -> {
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
        Report report = new Report();
        report.setLibelle(libelle);
        report.setDescription(description_tiet.getText().toString());
        report.setAuthorId(SessionUtils.getUserUid());

        FirebaseFirestore.getInstance().collection(ENQUETE_PATH)
                .document(viewModel.getReportMutableLiveData().getValue().getId()).set(report).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                NavHostFragment.findNavController(ReportEditDialog.this).navigateUp();
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
        getDialog().setTitle(getResources().getString(R.string.edit_report));
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
