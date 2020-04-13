package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Form;
import com.appli.nyx.formx.ui.MainActivity;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.FormSelectViewHolder;
import com.appli.nyx.formx.ui.viewmodel.SelectFormViewModel;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static android.widget.LinearLayout.VERTICAL;
import static com.appli.nyx.formx.utils.MyConstant.AUTHOR_ID;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;

public class SelectFormFragment extends ViewModelFragment<SelectFormViewModel> {

    FirestoreRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyView;

    @Override
    protected Class<SelectFormViewModel> getViewModel() {
        return SelectFormViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_form_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        ((MainActivity) requireActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.select_form));

        recyclerView = view.findViewById(R.id.forms);
        emptyView = view.findViewById(R.id.emptyView);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));

        // Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance().collection(FORM_PATH).whereEqualTo(AUTHOR_ID, SessionUtils.getUserUid()).orderBy("libelle");

        FirestoreRecyclerOptions<Form> options = new FirestoreRecyclerOptions.Builder<Form>().setQuery(query, snapshot -> {
            Form form = snapshot.toObject(Form.class);
            form.setId(snapshot.getId());
            return form;
        }).build();
        adapter = new FirestoreRecyclerAdapter<Form, FormSelectViewHolder>(options) {

            @NonNull
            @Override
            public FormSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_form_select, parent, false);
                return new FormSelectViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FormSelectViewHolder holder, int position, @NonNull Form form) {
                holder.mLibelleView.setText(form.getLibelle());
                holder.mDescriptionView.setText(form.getDescription());

                holder.mView.setOnClickListener(v -> {
                    viewModel.setForm(form);
                    NavHostFragment.findNavController(SelectFormFragment.this).navigateUp();
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
