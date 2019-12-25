package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.DialogFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;
import com.tiper.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class QuestionAddDialog extends DialogFragment {

    //@BindView(R.id.question_type)
    MaterialSpinner question_type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_question_add, container, false);

        List<String> optons=new ArrayList<>();
        for(QuestionType questionType :QuestionType.values()){
            optons.add(questionType.name());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                optons
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question_type.setAdapter(spinnerAdapter);


        v.findViewById(R.id.btn_save).setOnClickListener(view ->{
            if(TextUtils.isEmpty(question_type.getPrompt())){
                question_type.setError(getResources().getText(R.string.error_field_required));
            }else{

            }
        });

        return v;
    }
}
