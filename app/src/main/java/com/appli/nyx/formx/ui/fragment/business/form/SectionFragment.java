package com.appli.nyx.formx.ui.fragment.business.form;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;
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
        adapter = new SimpleItemRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getSectionMutableLiveData().observe(this, section -> {
            NavHostFragment.findNavController(SectionFragment.this).getCurrentDestination().setLabel(section.libelle);
        });

        viewModel.loadQuestionBySection().observe(this, questions -> {
            adapter.addAll(questions);
        });

        view.findViewById(R.id.add_question).setOnClickListener(v -> {
            NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_sectionFragment_to_questionAddDialog);
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
        inflater.inflate(R.menu.section, menu);
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

            Drawable drawable = getContext().getResources().getDrawable( getQuestionDrawable(holder.mItem.getQuestionType()) ,null);
            holder.mLibelleView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, getContext().getResources().getDrawable(R.drawable.ic_chevron_right_black_24dp), null);
			holder.mView.setOnClickListener(v -> {
				viewModel.setQuestion(holder.mItem);
                switch ((holder.mItem.getQuestionType())) {
                    case TEXT:
                         NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_global_textDialog);
                        break;
                    case NUMBER:
                         NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_global_numberDialog);
                        break;
                    case BOOLEAN:
                         NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_global_booleanDialog);
                        break;
                    case SPINNER:
                         NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_global_spinnerDialog);
                        break;
                    case DATE_PICKER:
                         NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_global_dateDialog);
                        break;
                    case TIME_PICKER:
                         NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_global_timeDialog);
                        break;
                    default:
                }

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


            public AbstractQuestion mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mLibelleView = view.findViewById(R.id.libelle);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mLibelleView.getText() + "'";
            }
        }
    }

    private int getQuestionDrawable(QuestionType questionType) {
        switch (questionType){
            case TEXT:
                 return  R.drawable.ic_short_text_black_24dp;
            case NUMBER:
                return  R.drawable.ic_plus_one_black_24dp;
            case BOOLEAN:
                return  R.drawable.ic_check_box_black_24dp;
            case SPINNER:
                return  R.drawable.ic_arrow_drop_down_circle_black_24dp;
            case DATE_PICKER:
                return  R.drawable.ic_event_black_24dp;
            case TIME_PICKER:
                return  R.drawable.ic_access_time_black_24dp;
                default:return 0;
        }
    }

}
