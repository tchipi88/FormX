package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fields.FieldsGenerator;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;

import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;

public class FormViewFragment extends ViewModelFragment<FormViewModel> {
    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_form_view;
    }

    @BindView(R.id.fields_container)
    LinearLayout fieldsContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        viewModel.getFormMutableLiveData().observe(getViewLifecycleOwner(), form -> {
			NavHostFragment.findNavController(FormViewFragment.this).getCurrentDestination().setLabel(form.getLibelle());
        });

        viewModel.loadQuestionBySection().observe(getViewLifecycleOwner(), questions -> {
            FieldsGenerator.generateLayoutField(getContext(), fieldsContainer, questions);
        });

        return view;
    }
}
