package com.appli.nyx.formx.ui.fragment.business.form.question;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.Option;
import com.appli.nyx.formx.model.firebase.fields.SpinnerQuestion;
import com.appli.nyx.formx.ui.MainActivity;
import com.appli.nyx.formx.ui.adapter.SpinnerOptionAdapter;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static android.widget.LinearLayout.VERTICAL;

public class SpinnerQuestionFragment extends CommonQuestionFragment {

    public ArrayList<Option> editModelArrayList;
    SpinnerQuestion question;

    @BindView(R.id.mandatory)
    SwitchMaterial mandatory;
    private RecyclerView recyclerView;
    private SpinnerOptionAdapter customAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_question_spinner;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler);

        editModelArrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));


        customAdapter = new SpinnerOptionAdapter(getActivity(), editModelArrayList);
        recyclerView.setAdapter(customAdapter);


        view.findViewById(R.id.add_new).setOnClickListener(v -> {
            customAdapter.add();
        });


        viewModel.getQuestionCreationMode().observe(getViewLifecycleOwner(), aBoolean -> {
            if (Boolean.FALSE.equals(aBoolean))
                viewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), abstractQuestion -> {
                    question = (SpinnerQuestion) abstractQuestion;
                    libelle_tiet.setText(question.getLibelle());
                    description_tiet.setText(question.getDescription());
                    mandatory.setChecked(question.isMandatory());

                    customAdapter.addAll(question.options);

                    ((MainActivity) requireActivity()).getSupportActionBar().setTitle(question.getLibelle());
                });
        });


        return view;
    }


    @OnClick(R.id.save)
    public void save(View view) {
        if (!validate()) {
            return;
        }

        question = new SpinnerQuestion();
        question.setLibelle(libelle_tiet.getText().toString());
        question.setDescription(description_tiet.getText().toString());
        question.setMandatory(mandatory.isChecked());

        question.setOptions(customAdapter.getOptionsValues());


        if (Boolean.TRUE.equals(viewModel.getQuestionCreationMode().getValue())) {
            fieldsRef.add(question).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
                    NavHostFragment.findNavController(SpinnerQuestionFragment.this).navigateUp();
                } else {
                    AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                }
            });

        } else {
            fieldsRef.document(viewModel.getQuestionMutableLiveData().getValue().getId())
                    .set(question).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
                    NavHostFragment.findNavController(SpinnerQuestionFragment.this).navigateUp();
                } else {
                    AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                }
            });

        }


    }

    public boolean validate() {
        boolean valid = super.validate();

        if (customAdapter.getOptionsValues().isEmpty())
            return false;

        return valid;
    }

}