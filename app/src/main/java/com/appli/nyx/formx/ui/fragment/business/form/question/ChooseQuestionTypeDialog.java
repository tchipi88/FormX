package com.appli.nyx.formx.ui.fragment.business.form.question;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;

import butterknife.BindView;

public class ChooseQuestionTypeDialog extends BaseDialogFragment<FormViewModel> {

    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_choose_question_type;
    }

    @BindView(R.id.question_type)
    RadioGroup question_type;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        getDialog().setTitle(R.string.question_type);

       question_type.setOnCheckedChangeListener((group, checkedId) -> {
           viewModel.setQuestionCreationMode(true);

           switch (checkedId) {
               case R.id.question_text:
                   NavHostFragment.findNavController(ChooseQuestionTypeDialog.this).navigate(R.id.action_global_textDialog);
                   break;
               case R.id.question_number:
                   NavHostFragment.findNavController(ChooseQuestionTypeDialog.this).navigate(R.id.action_global_numberDialog);
                   break;
               case R.id.question_boolean:
                   NavHostFragment.findNavController(ChooseQuestionTypeDialog.this).navigate(R.id.action_global_booleanDialog);
                   break;
               case R.id.question_spinner:
                   NavHostFragment.findNavController(ChooseQuestionTypeDialog.this).navigate(R.id.action_global_spinnerDialog);
                   break;
               case R.id.question_date:
                   NavHostFragment.findNavController(ChooseQuestionTypeDialog.this).navigate(R.id.action_global_dateDialog);
                   break;
               case R.id.question_time:
                   NavHostFragment.findNavController(ChooseQuestionTypeDialog.this).navigate(R.id.action_global_timeDialog);
                   break;
               default:
           }

       });


        return view;
    }
}
