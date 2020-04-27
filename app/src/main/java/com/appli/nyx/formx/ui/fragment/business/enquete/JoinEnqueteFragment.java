package com.appli.nyx.formx.ui.fragment.business.enquete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Enquete;
import com.appli.nyx.formx.model.firebase.enumeration.EnqueteVisibility;
import com.appli.nyx.formx.ui.viewholder.EnqueteSimpleViewHolder;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.ImageUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static android.widget.LinearLayout.VERTICAL;
import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PATH;
import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PHOTO;
import static com.appli.nyx.formx.utils.MyConstant.JOIN_USER_ID;

public class JoinEnqueteFragment extends EnqueteListFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));

        // Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance().collection(ENQUETE_PATH)
                .whereEqualTo("enqueteVisibility", EnqueteVisibility.PUBLIC).orderBy("libelle");

        FirestoreRecyclerOptions<Enquete> options = new FirestoreRecyclerOptions.Builder<Enquete>().setQuery(query, snapshot -> {
            Enquete enquete = snapshot.toObject(Enquete.class);
            enquete.setId(snapshot.getId());
            return enquete;
        }).build();

        adapter = new FirestoreRecyclerAdapter<Enquete, EnqueteSimpleViewHolder>(options) {

            @NonNull
            @Override
            public EnqueteSimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_enquete_select, parent, false);
                return new EnqueteSimpleViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull EnqueteSimpleViewHolder holder, int position, @NonNull Enquete enquete) {
                holder.mItem = getItem(position);
                holder.mLibelleView.setText(enquete.getLibelle());
                holder.mDescriptionView.setText(enquete.getDescription());

                ImageUtils.displayRoundImageFromStorageReference(getContext(), storageRef.child(enquete.getId()), ENQUETE_PHOTO, holder.img, ic_assignment_black_24dp);

                holder.mView.setOnClickListener(v -> {
                    FirebaseFirestore.getInstance().collection(ENQUETE_PATH)
                            .document(enquete.getId()).update(JOIN_USER_ID, FieldValue.arrayUnion(SessionUtils.getUserUid()))
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    NavHostFragment.findNavController(JoinEnqueteFragment.this).navigate(R.id.action_global_enqueteListFragment);
                                } else {
                                    AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                                }
                            });

                });


            }

            @Override
            public void onDataChanged() {
                recyclerView.hideShimmerAdapter();
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

        recyclerView.showShimmerAdapter();


        view.findViewById(R.id.add_enquete).setVisibility(View.GONE);

        return view;
    }


}
