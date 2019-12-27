package com.appli.nyx.formx.ui.fragment.business.form;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

public class SectionFragment  extends ViewModelFragment<FormViewModel> {
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_section;
    }

    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }


    SimpleItemRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.questions);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));
        adapter = new SimpleItemRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);


        viewModel.loadQuestionBySection().observe(this, questions -> {
            adapter.addAll(questions);
        });

        view.findViewById(R.id.add_question).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_sectionFragment_to_questionAddDialog);
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
		if (item.getItemId() == R.id.action_edit) {
			NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_sectionFragment_to_sectionEditDialog);
			return true;
		}
		return super.onOptionsItemSelected(item);
    }

    private class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        Context context;
        private List<AbstractQuestion> mValues;


        public SimpleItemRecyclerViewAdapter() {
            mValues = new ArrayList<>();
        }

        @Override
        public SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.viewholder_question
                            , parent, false);
            return new SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mLibelleView.setText(holder.mItem.getLibelle());
			holder.mView.setOnClickListener(v -> {
				viewModel.setQuestion(holder.mItem);
                switch ((holder.mItem.getQuestionType())) {
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

			});
			holder.deleteButton.setOnClickListener(v -> {

			});
			holder.duplicateButton.setOnClickListener(v -> {

            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public void addAll(List<AbstractQuestion> fields) {
            mValues.addAll(fields);
            notifyDataSetChanged();

        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mLibelleView;
			public final ImageButton duplicateButton;
			public final ImageButton deleteButton;

            public AbstractQuestion mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mLibelleView = view.findViewById(R.id.libelle);
				duplicateButton = view.findViewById(R.id.duplicate_button);
				deleteButton = view.findViewById(R.id.delete_button);

            }

            @Override
            public String toString() {
                return super.toString() + " '" + mLibelleView.getText() + "'";
            }
        }
    }

}
