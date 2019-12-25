package com.appli.nyx.formx.ui.fragment.business.form;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Section;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

public class FormFragment  extends ViewModelFragment<FormViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_form;
    }


    SimpleItemRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;


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
        switch (item.getItemId()) {
            case R.id.action_edit:
                NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_formFragment_to_formEditDialog);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.sections);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));
        adapter = new SimpleItemRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);


        viewModel.loadSectionByForm().observe(this, sections -> {
            adapter.addAll(sections);
        });

        view.findViewById(R.id.add_section).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_formFragment_to_sectionAddDialog);
        });

        return view;
    }

    private class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        Context context;
        private List<Section> mValues;


        public SimpleItemRecyclerViewAdapter() {
            mValues = new ArrayList<>();
        }

        @Override
        public SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.viewholder_section
                            , parent, false);
            return new SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mLibelleView.setText(holder.mItem.libelle);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.setSection(holder.mItem);
                   Navigation.findNavController(v).navigate(R.id.action_formFragment_to_sectionFragment);

                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public void addAll(List<Section> sections) {
            mValues.addAll(sections);
            notifyDataSetChanged();

        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mLibelleView;

            public Section mItem;

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

}
