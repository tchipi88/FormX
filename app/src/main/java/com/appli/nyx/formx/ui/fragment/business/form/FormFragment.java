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
import com.appli.nyx.formx.model.firebase.Form;
import com.appli.nyx.formx.model.firebase.Section;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.FormViewHolder;
import com.appli.nyx.formx.ui.viewholder.SectionViewHolder;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.appli.nyx.formx.utils.MyConstant.DATA;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;

public class FormFragment extends ViewModelFragment<FormViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_form;
    }


    FirestoreRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyView;


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
        emptyView = view.findViewById(R.id.emptyView);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance().collection(FORM_PATH)
                .document(SessionUtils.getUserUid())
                .collection(DATA)
                .document(viewModel.getFormMutableLiveData().getValue().getId())
                .collection("sections");

        FirestoreRecyclerOptions<Section> options = new FirestoreRecyclerOptions.Builder<Section>().setQuery(query, snapshot -> {
            Section section = snapshot.toObject(Section.class);
            section.setId(snapshot.getId());
            return section;
        }).build();

        adapter = new FirestoreRecyclerAdapter<Section, SectionViewHolder>(options) {

            @NonNull
            @Override
            public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_section, parent, false);
                return new SectionViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull SectionViewHolder holder, int position, @NonNull Section model) {
                holder.mItem = getItem(position);
                holder.mLibelleView.setText(model.getLibelle());

                holder.mView.setOnClickListener(v -> {
                    viewModel.setSection(holder.mItem);
                    NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_formFragment_to_sectionFragment);

                });


                holder.delete.setOnClickListener(v -> {

                });

                holder.duplicate.setOnClickListener(v -> {
                    viewModel.setSection(holder.mItem);
                });

                holder.edit.setOnClickListener(v -> {
                    viewModel.setSection(holder.mItem);
                    NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_formFragment_to_sectionEditDialog);
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

        viewModel.getFormMutableLiveData().observe(getViewLifecycleOwner(), form -> {
            NavHostFragment.findNavController(FormFragment.this).getCurrentDestination().setLabel(form.getLibelle());
        });


        view.findViewById(R.id.add_section).setOnClickListener(v -> {
            NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_formFragment_to_sectionAddDialog);
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
