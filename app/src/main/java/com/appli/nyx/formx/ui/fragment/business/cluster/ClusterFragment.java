package com.appli.nyx.formx.ui.fragment.business.cluster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.ClusterFils;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.ClusterFilsViewHolder;
import com.appli.nyx.formx.ui.viewholder.ClusterViewHolder;
import com.appli.nyx.formx.ui.viewmodel.ClusterViewModel;
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

public class ClusterFragment extends ViewModelFragment<ClusterViewModel> {

	FirestoreRecyclerAdapter adapter;

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_cluster;
	}

	private RecyclerView recyclerView;
	private View emptyView;

	@Override
	protected Class<ClusterViewModel> getViewModel() {
		return ClusterViewModel.class;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);

		recyclerView = view.findViewById(R.id.clusters_fils);
		emptyView = view.findViewById(R.id.emptyView);
		assert recyclerView != null;
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));

		String clusterId = viewModel.getClusterMutableLiveData().getValue().getId();

		Query query = FirebaseFirestore.getInstance().collection(CLUSTER_PATH).document(SessionUtils.getUserUid()).collection(CLUSTER_DATA).document(clusterId).collection(clusterId).orderBy("type");

		FirestoreRecyclerOptions<ClusterFils> options = new FirestoreRecyclerOptions.Builder<ClusterFils>().setQuery(query, snapshot -> {
			ClusterFils cluster = snapshot.toObject(ClusterFils.class);
			cluster.setId(snapshot.getId());
			return cluster;
		}).build();

		adapter = new FirestoreRecyclerAdapter<ClusterFils, RecyclerView.ViewHolder>(options) {

			@NonNull
			@Override
			public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
				View view;
				switch (viewType) {
					case 1: //enquete
						view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cluster_enquete, parent, false);
						return new ClusterFilsViewHolder(view);

					case 2: // STATISTIQUE
						view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cluster_stat, parent, false);
						return new ClusterFilsViewHolder(view);
					default:
						//cluster
						view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cluster, parent, false);
						return new ClusterViewHolder(view);
				}
			}

			@Override
			protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull ClusterFils model) {
				switch (getItemViewType(position)) {
					case 0: //cluster
						((ClusterViewHolder) holder).bind(model);
						break;
					case 1: //enquete
						((ClusterFilsViewHolder) holder).bind(model);
						break;

					case 2: // STATISTIQUE
						((ClusterFilsViewHolder) holder).bind(model);
						break;

				}

			}

			// Determines the appropriate ViewType according to the sender of the message.
			@Override
			public int getItemViewType(int position) {
				ClusterFils clusterFils = getItem(position);
				return clusterFils.type.ordinal();
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

		view.findViewById(R.id.add_cluster_fils).setOnClickListener(v -> {
			NavHostFragment.findNavController(ClusterFragment.this).navigate(R.id.action_clusterFragment_to_clusterFilsAddDialog);
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
