package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Form;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.FormViewHolder;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.appli.nyx.formx.utils.MyConstant.DATA;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;

public class FormListFragment extends ViewModelFragment<FormViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_form_list;
    }

    FirestoreRecyclerAdapter adapter;
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

        // Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance().collection(FORM_PATH).document(SessionUtils.getUserUid()).collection(DATA).orderBy("libelle");

        FirestoreRecyclerOptions<Form> options = new FirestoreRecyclerOptions.Builder<Form>().setQuery(query, Form.class).build();
        adapter = new FirestoreRecyclerAdapter<Form, FormViewHolder>(options) {

            @NonNull
            @Override
            public FormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_form, parent, false);
                return new FormViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FormViewHolder holder, int position, @NonNull Form model) {
                holder.mItem = getItem(position);
                holder.mLibelleView.setText(model.getLibelle());
                holder.mDescriptionView.setText(model.getDescription());

                holder.mView.setOnClickListener(v -> {
                    viewModel.setForm(holder.mItem);
                    NavHostFragment.findNavController(FormListFragment.this).navigate(R.id.action_formListFragment_to_formFragment);
                });

                holder.delete.setOnClickListener(v -> {

                });

                holder.voir.setOnClickListener(v -> {
                    viewModel.setForm(holder.mItem);
                    NavHostFragment.findNavController(FormListFragment.this).navigate(R.id.action_global_formViewFragment);
                });

                holder.edit.setOnClickListener(v -> {
                    viewModel.setForm(holder.mItem);
                    NavHostFragment.findNavController(FormListFragment.this).navigate(R.id.action_global_formEditDialog);
                });
            }
        };
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.add_form).setOnClickListener(v -> {
            NavHostFragment.findNavController(FormListFragment.this).navigate(R.id.action_formListFragment_to_formAddDialog);
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
