package com.appli.nyx.formx.ui.fragment.business.form.question.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.BooleanQuestion;

import butterknife.OnClick;

public class BooleanQuestionDialog extends CommonQuestionDialog {

	@Override
	protected int getLayoutRes() {
		return R.layout.dialog_question_boolean;
	}

	BooleanQuestion booleanQuestion;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);

		getDialog().setTitle(R.string.question_boolean);

		return view;
	}

	@OnClick(R.id.save)
	public void save(View view) {
		if (!validate()) {
			return;
		}

	}

	public boolean validate() {
		boolean valid = super.validate();

		return valid;
	}

}