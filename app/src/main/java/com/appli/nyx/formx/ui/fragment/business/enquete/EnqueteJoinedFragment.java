package com.appli.nyx.formx.ui.fragment.business.enquete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Enquete;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.EnqueteJoinedViewHolder;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_DATA;
import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PATH;

public class EnqueteJoinedFragment extends ViewModelFragment<EnqueteViewModel> {

    FirestoreRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyView;

    @Override
    protected Class<EnqueteViewModel> getViewModel() {
        return EnqueteViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_enquete_list;
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
        Query query = FirebaseFirestore.getInstance().collection(ENQUETE_PATH).document(SessionUtils.getUserUid()).collection(ENQUETE_DATA).orderBy("libelle");

        FirestoreRecyclerOptions<Enquete> options = new FirestoreRecyclerOptions.Builder<Enquete>().setQuery(query, snapshot -> {
            Enquete enquete = snapshot.toObject(Enquete.class);
            enquete.setId(snapshot.getId());
            return enquete;
        }).build();

        adapter = new FirestoreRecyclerAdapter<Enquete, EnqueteJoinedViewHolder>(options) {

            @NonNull
            @Override
            public EnqueteJoinedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_enquete_joined, parent, false);
                return new EnqueteJoinedViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull EnqueteJoinedViewHolder holder, int position, @NonNull Enquete enquete) {
                holder.mItem = getItem(position);
                holder.mLibelleView.setText(enquete.getLibelle());
                holder.mDescriptionView.setText(enquete.getDescription());

                holder.mView.setOnClickListener(v -> {
                    viewModel.setEnquete(holder.mItem);
                    NavHostFragment.findNavController(EnqueteJoinedFragment.this).navigate(R.id.action_enqueteListFragment_to_enqueteFragment);
                });

                holder.quit.setOnClickListener(v -> {


                });

                holder.reply.setOnClickListener(v -> {
                    viewModel.setEnquete(holder.mItem);

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
