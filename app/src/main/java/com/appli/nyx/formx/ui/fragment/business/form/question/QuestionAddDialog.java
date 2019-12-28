package com.appli.nyx.formx.ui.fragment.business.form.question;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.tiper.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

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
    RadioGroup question_type;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        getDialog().setTitle(R.string.question_type);

       question_type.setOnCheckedChangeListener((group, checkedId) -> {

           switch (checkedId) {
               case R.id.question_text:
                   NavHostFragment.findNavController(QuestionAddDialog.this).navigate(R.id.action_global_textDialog);
                   break;
               case R.id.question_number:
                   NavHostFragment.findNavController(QuestionAddDialog.this).navigate(R.id.action_global_numberDialog);
                   break;
               case R.id.question_boolean:
                   NavHostFragment.findNavController(QuestionAddDialog.this).navigate(R.id.action_global_booleanDialog);
                   break;
               case R.id.question_spinner:
                   NavHostFragment.findNavController(QuestionAddDialog.this).navigate(R.id.action_global_spinnerDialog);
                   break;
               case R.id.question_date:
                   NavHostFragment.findNavController(QuestionAddDialog.this).navigate(R.id.action_global_dateDialog);
                   break;
               case R.id.question_time:
                   NavHostFragment.findNavController(QuestionAddDialog.this).navigate(R.id.action_global_timeDialog);
                   break;
               default:
           }

       });


        return view;
    }
}
