package com.appli.nyx.formx.ui.fragment.business.form.question;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.Option;
import com.appli.nyx.formx.model.firebase.fields.SpinnerQuestion;
import com.appli.nyx.formx.ui.adapter.SpinnerOptionAdapter;

import java.util.ArrayList;

import butterknife.OnClick;

import static android.widget.LinearLayout.VERTICAL;

public class SpinnerQuestionFragment extends CommonQuestionFragment {

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_question_spinner;
	}

	SpinnerQuestion spinnerQuestion;

	public ArrayList<Option> editModelArrayList;
	private RecyclerView recyclerView;
	private SpinnerOptionAdapter customAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);

		recyclerView = view.findViewById(R.id.recycler);

		editModelArrayList = populateList();

		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));


		customAdapter = new SpinnerOptionAdapter(getActivity(), editModelArrayList);
		recyclerView.setAdapter(customAdapter);


		view.findViewById(R.id.add_new).setOnClickListener(v -> {
			customAdapter.add();
		});

		return view;
	}

	private ArrayList<Option> populateList() {

		ArrayList<Option> list = new ArrayList<>();

		for (int i = 0; i < 8; i++) {
			Option editModel = new Option();
			editModel.setEditTextValue(String.valueOf(i));
			list.add(editModel);
		}

		return list;
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