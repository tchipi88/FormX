package com.appli.nyx.formx.ui.fragment.business.form.question.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.TextQuestion;

import butterknife.OnClick;

public class TextQuestionDialog extends CommonQuestionDialog {

	@Override
	protected int getLayoutRes() {
		return R.layout.dialog_question_text;
	}

	TextQuestion textQuestion;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);

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
