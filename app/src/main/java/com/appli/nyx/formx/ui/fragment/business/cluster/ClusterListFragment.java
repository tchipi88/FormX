package com.appli.nyx.formx.ui.fragment.business.cluster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Cluster;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.ClusterViewHolder;
import com.appli.nyx.formx.ui.viewmodel.ClusterViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.LinearLayout.VERTICAL;
import static com.appli.nyx.formx.utils.MyConstant.CLUSTER_DATA;
import static com.appli.nyx.formx.utils.MyConstant.CLUSTER_PATH;

public class ClusterListFragment extends ViewModelFragment<ClusterViewModel> {

    FirestoreRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyView;


    @Override
    protected Class<ClusterViewModel> getViewModel() {
        return ClusterViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_cluster_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.clusters);
        emptyView = view.findViewById(R.id.emptyView);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));

		// Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance().collection(CLUSTER_PATH).document(SessionUtils.getUserUid()).collection(CLUSTER_DATA).orderBy("libelle");

		FirestoreRecyclerOptions<Cluster> options = new FirestoreRecyclerOptions.Builder<Cluster>().setQuery(query, snapshot -> {
			Cluster cluster = snapshot.toObject(Cluster.class);
			cluster.setId(snapshot.getId());
			return cluster;
		}).build();

		adapter = new FirestoreRecyclerAdapter<Cluster, ClusterViewHolder>(options) {

			@NonNull
			@Override
			public ClusterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
				View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cluster, parent, false);
				return new ClusterViewHolder(view);
			}

			@Override
			protected void onBindViewHolder(@NonNull ClusterViewHolder holder, int position, @NonNull Cluster model) {
				holder.mLibelleView.setText(model.getLibelle());
				holder.mDescriptionView.setText(model.getDescription());

				holder.mView.setOnClickListener(v -> {
					viewModel.setCluster(model);
					NavHostFragment.findNavController(ClusterListFragment.this).navigate(R.id.action_clusterListFragment_to_clusterFragment);
				});

				holder.edit.setOnClickListener(v -> {
					viewModel.setCluster(model);
					NavHostFragment.findNavController(ClusterListFragment.this).navigate(R.id.action_clusterListFragment_to_clusterEditDialog);
				});

				holder.delete.setOnClickListener(v -> {

					AlertDialogUtils.showConfirmDeleteDialog(getContext(), (dialog, which) -> {
						FirebaseFirestore.getInstance().collection(CLUSTER_PATH).document(SessionUtils.getUserUid()).collection(CLUSTER_DATA).document(model.getId()).delete().addOnCompleteListener(task -> {
							if (task.isSuccessful()) {
								Toast.makeText(getContext(), R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
							} else {
								AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
							}
						});
					});
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

        view.findViewById(R.id.add_cluster).setOnClickListener(v -> {
			NavHostFragment.findNavController(ClusterListFragment.this).navigate(R.id.action_global_clusterAddDialog);
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
