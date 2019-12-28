package com.appli.nyx.formx.ui.fragment.business.form;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Form;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

public class FormListFragment extends ViewModelFragment<FormViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_form_list;
    }


    SimpleItemRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.forms);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SimpleItemRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);


        viewModel.loadFormByUser().observe(this, forms -> {
            adapter.addAll(forms);
        });

        view.findViewById(R.id.add_form).setOnClickListener(v -> {
             NavHostFragment.findNavController(FormListFragment.this).navigate(R.id.action_formListFragment_to_formAddDialog);
        });

        return view;
    }

    private class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        Context context;
        private List<Form> mValues;


        public SimpleItemRecyclerViewAdapter() {
            mValues = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.viewholder_form
                            , parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mLibelleView.setText(holder.mItem.libelle);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.setForm(holder.mItem);
                    NavHostFragment.findNavController(FormListFragment.this).navigate(R.id.action_formListFragment_to_formFragment);

                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public void addAll(List<Form> forms) {
            mValues.addAll(forms);
            notifyDataSetChanged();

        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mLibelleView;

            public Form mItem;

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
