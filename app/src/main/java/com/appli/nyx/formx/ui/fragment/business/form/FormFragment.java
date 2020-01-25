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

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Section;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FormFragment extends ViewModelFragment<FormViewModel> {

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
        inflater.inflate(R.menu.form, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit:
                NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_global_formEditDialog);
                return true;
            case R.id.action_visibility:
                NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_global_formViewFragment);
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
        adapter = new SimpleItemRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getFormMutableLiveData().observe(getViewLifecycleOwner(), form -> {
            NavHostFragment.findNavController(FormFragment.this).getCurrentDestination().setLabel(form.libelle);
        });

        viewModel.loadSectionByForm().observe(getViewLifecycleOwner(), sections -> {
            adapter.addAll(sections);
        });

        view.findViewById(R.id.add_section).setOnClickListener(v -> {
            NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_formFragment_to_sectionAddDialog);
        });

        return view;
    }

    private class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.SectionViewHolder> {

        Context context;
        private List<Section> mValues;


        public SimpleItemRecyclerViewAdapter() {
            mValues = new ArrayList<>();
        }

        @Override
        public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.viewholder_section
                            , parent, false);
            return new SectionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SectionViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mLibelleView.setText(holder.mItem.libelle);
            holder.mView.setOnClickListener(v -> {
                viewModel.setSection(holder.mItem);
                NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_formFragment_to_sectionFragment);

            });


            holder.delete.setOnClickListener(v -> {

            });

            holder.duplicate.setOnClickListener(v -> {
                viewModel.setSection(holder.mItem);
                NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_global_formViewFragment);
            });

            holder.edit.setOnClickListener(v -> {
                viewModel.setSection(holder.mItem);
                NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_global_formEditDialog);
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


        public class SectionViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mLibelleView;

            public final AppCompatImageView delete;
            public final AppCompatImageView duplicate;
            public final AppCompatImageView edit;

            public Section mItem;

            public SectionViewHolder(View view) {
                super(view);
                mView = view;
                mLibelleView = view.findViewById(R.id.libelle);

                delete = view.findViewById(R.id.delete);
                duplicate = view.findViewById(R.id.duplicate);
                edit = view.findViewById(R.id.edit);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mLibelleView.getText() + "'";
            }
        }
    }

}
