package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Form;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewholder.SimpleViewHolder;
import com.appli.nyx.formx.ui.viewmodel.SelectFormViewModel;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static com.appli.nyx.formx.utils.MyConstant.FORM_DATA;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;

public class SelectFormDialog extends BaseDialogFragment<SelectFormViewModel> {

    FirestoreRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyView;

    @Override
    protected Class<SelectFormViewModel> getViewModel() {
        return SelectFormViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.recyclerview_simple;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.items);
        emptyView = view.findViewById(R.id.emptyView);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance().collection(FORM_PATH).document(SessionUtils.getUserUid()).collection(FORM_DATA).orderBy("libelle");

        FirestoreRecyclerOptions<Form> options = new FirestoreRecyclerOptions.Builder<Form>().setQuery(query, snapshot -> {
            Form form = snapshot.toObject(Form.class);
            form.setId(snapshot.getId());
            return form;
        }).build();
        adapter = new FirestoreRecyclerAdapter<Form, SimpleViewHolder>(options) {

            @NonNull
            @Override
            public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_simple, parent, false);
                return new SimpleViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull SimpleViewHolder holder, int position, @NonNull Form model) {
                holder.mLibelleView.setText(model.getLibelle());
                holder.mView.setOnClickListener(v -> {
                    viewModel.setForm(model);
                    int destination = getArguments().getInt("destination");
                    if (destination == 0) {
                        NavHostFragment.findNavController(SelectFormDialog.this).navigateUp();
                    } else {
                        NavHostFragment.findNavController(SelectFormDialog.this).navigate(destination);
                    }

                });
            }

            @Override
            public void onDataChanged() {
                if (getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

        };

        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle(getResources().getString(R.string.select_form));
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
