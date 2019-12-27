package com.appli.nyx.formx.ui.fragment.business.form.question;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.tiper.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.Navigation;
import butterknife.BindView;

public class QuestionAddDialog extends BaseDialogFragment<FormViewModel> {

    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_question_add;
    }

    @BindView(R.id.question_type)
    MaterialSpinner question_type;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        List<String> options = new ArrayList<>();
        for(QuestionType questionType :QuestionType.values()){
            options.add(questionType.name());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item, options
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question_type.setAdapter(spinnerAdapter);

        view.findViewById(R.id.btn_save).setOnClickListener(v -> {
            if (TextUtils.isEmpty((String) question_type.getSelectedItem())) {
                question_type.setError(getResources().getText(R.string.error_field_required));
            }else{
                switch (QuestionType.valueOf((String) question_type.getSelectedItem())) {
                    case TEXT:
                        Navigation.findNavController(v).navigate(R.id.action_questionAddDialogFragment_to_textDialog);
                        break;
                    case NUMBER:
                        Navigation.findNavController(v).navigate(R.id.action_questionAddDialogFragment_to_numberDialog);
                        break;
                    case BOOLEAN:
                        Navigation.findNavController(v).navigate(R.id.action_questionAddDialogFragment_to_booleanDialog);
                        break;
                    case SPINNER:
                        Navigation.findNavController(v).navigate(R.id.action_questionAddDialogFragment_to_spinnerDialog);
                        break;
                    case DATE_PICKER:
                        Navigation.findNavController(v).navigate(R.id.action_questionAddDialogFragment_to_dateDialog);
                        break;
                    case TIME_PICKER:
                        Navigation.findNavController(v).navigate(R.id.action_questionAddDialogFragment_to_timeDialog);
                        break;
                    default:
                }
            }
        });

        return view;
    }
}
