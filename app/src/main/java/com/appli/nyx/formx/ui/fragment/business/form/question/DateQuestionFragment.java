package com.appli.nyx.formx.ui.fragment.business.form.question;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.DateQuestion;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.google.android.material.switchmaterial.SwitchMaterial;

import butterknife.BindView;
import butterknife.OnClick;

public class DateQuestionFragment extends CommonQuestionFragment {

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_question_date;
	}

    DateQuestion question;

    @BindView(R.id.mandatory)
    SwitchMaterial mandatory;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);

        viewModel.getQuestionCreationMode().observe(getViewLifecycleOwner(), aBoolean -> {
            if (Boolean.FALSE.equals(aBoolean))
                viewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), abstractQuestion -> {
                    question = (DateQuestion) abstractQuestion;
                    libelle_tiet.setText(question.getLibelle());
                    description_tiet.setText(question.getDescription());
                    mandatory.setChecked(question.isMandatory());

                    NavHostFragment.findNavController(DateQuestionFragment.this).getCurrentDestination().setLabel(question.getLibelle());
                });
        });


		return view;
	}

	@OnClick(R.id.save)
	public void save(View view) {
		if (!validate()) {
			return;
		}

        question = new DateQuestion();
        question.setLibelle(libelle_tiet.getText().toString());
        question.setDescription(description_tiet.getText().toString());
        question.setMandatory(mandatory.isChecked());

        if (Boolean.TRUE.equals(viewModel.getQuestionCreationMode().getValue())) {
            fieldsRef.add(question).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
                    NavHostFragment.findNavController(DateQuestionFragment.this).navigateUp();
                } else {
                    AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                }
            });

        } else {
            fieldsRef.document(viewModel.getQuestionMutableLiveData().getValue().getId())
                    .set(question).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
                    NavHostFragment.findNavController(DateQuestionFragment.this).navigateUp();
                } else {
                    AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                }
            });

        }

    }



}