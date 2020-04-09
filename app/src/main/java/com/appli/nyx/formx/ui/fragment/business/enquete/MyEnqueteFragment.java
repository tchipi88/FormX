package com.appli.nyx.formx.ui.fragment.business.enquete;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Enquete;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.EnqueteViewHolder;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.ImageUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;

import butterknife.BindDrawable;

import static com.appli.nyx.formx.utils.MyConstant.AUTHOR_ID;
import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PATH;
import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PHOTO;

public class MyEnqueteFragment extends ViewModelFragment<EnqueteViewModel> {

    FirestoreRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyView;

    StorageReference storageRef;

    @BindDrawable(R.drawable.ic_assignment_black_24dp)
    Drawable ic_assignment_black_24dp;

    @Override
    protected Class<EnqueteViewModel> getViewModel() {
        return EnqueteViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_enquete_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageRef = firebaseStorage.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.enquetes);
        emptyView = view.findViewById(R.id.emptyView);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance().collection(ENQUETE_PATH).whereEqualTo(AUTHOR_ID, SessionUtils.getUserUid()).orderBy("libelle");

        FirestoreRecyclerOptions<Enquete> options = new FirestoreRecyclerOptions.Builder<Enquete>().setQuery(query, snapshot -> {
            Enquete enquete = snapshot.toObject(Enquete.class);
            enquete.setId(snapshot.getId());
            return enquete;
        }).build();

        adapter = new FirestoreRecyclerAdapter<Enquete, EnqueteViewHolder>(options) {

            @NonNull
            @Override
            public EnqueteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_enquete, parent, false);
                return new EnqueteViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull EnqueteViewHolder holder, int position, @NonNull Enquete enquete) {
                holder.mItem = getItem(position);
                holder.mLibelleView.setText(enquete.getLibelle());
                holder.mDescriptionView.setText(enquete.getDescription());

                ImageUtils.displayRoundImageFromStorageReference(getContext(), storageRef.child(enquete.getId()).child(ENQUETE_PHOTO), holder.img, ic_assignment_black_24dp);

                holder.mView.setOnClickListener(v -> {
                    viewModel.setEnquete(holder.mItem);
                    NavHostFragment.findNavController(MyEnqueteFragment.this).navigate(R.id.action_enqueteListFragment_to_enqueteFragment);
                });

                holder.delete.setOnClickListener(v -> {

                    AlertDialogUtils.showConfirmDeleteDialog(getContext(), (dialog, which) -> {
                        FirebaseFirestore.getInstance().collection(ENQUETE_PATH).document(enquete.getId()).delete().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
                            } else {
                                AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                            }
                        });
                    });

                });

                holder.share.setOnClickListener(v -> {
                    viewModel.setEnquete(holder.mItem);
                    NavHostFragment.findNavController(MyEnqueteFragment.this).navigate(R.id.action_enqueteListFragment_to_selectUserFragment);
                });

                holder.edit.setOnClickListener(v -> {
                    viewModel.setEnquete(holder.mItem);
                    NavHostFragment.findNavController(MyEnqueteFragment.this).navigate(R.id.action_enqueteListFragment_to_enqueteEditDialog);
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

        view.findViewById(R.id.add_enquete).setOnClickListener(v -> {
            NavHostFragment.findNavController(MyEnqueteFragment.this).navigate(R.id.action_global_enqueteAddDialog);
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

