package com.appli.nyx.formx.ui.fragment.business.cluster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Cluster;
import com.appli.nyx.formx.ui.MainActivity;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.ClusterViewHolder;
import com.appli.nyx.formx.ui.viewmodel.ClusterViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.ClusterUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

public class ClusterFragment extends ViewModelFragment<ClusterViewModel> {

    FirestoreRecyclerAdapter adapter;
    private ShimmerRecyclerViewX recyclerView;
    private View emptyView;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_cluster;
    }

    @Override
    protected Class<ClusterViewModel> getViewModel() {
        return ClusterViewModel.class;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (ClusterUtils.isParentIsROOT(ClusterUtils.getParentPath(viewModel.getClusterCollectionPathMutableLiveData().getValue()))) {
                    NavHostFragment.findNavController(ClusterFragment.this).navigate(R.id.action_global_clusterListFragment);
                } else {
                    viewModel.setClusterCollectionPathMutableLiveData(ClusterUtils.getParentPath(viewModel.getClusterCollectionPathMutableLiveData().getValue()));
                    NavHostFragment.findNavController(ClusterFragment.this).navigate(R.id.action_clusterFragment_self);
                }

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        recyclerView = view.findViewById(R.id.clusters_fils);
        emptyView = view.findViewById(R.id.emptyView);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        CollectionReference collectionReferenceParent = FirebaseFirestore.getInstance().collection(viewModel.getClusterCollectionPathMutableLiveData().getValue());
        Query query = collectionReferenceParent.orderBy("type");

        collectionReferenceParent.getParent().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ((MainActivity) requireActivity()).getSupportActionBar().setTitle("Cluster: " + (String) task.getResult().get("libelle"));
            }
        });

        FirestoreRecyclerOptions<Cluster> options = new FirestoreRecyclerOptions.Builder<Cluster>().setQuery(query, snapshot -> {
            Cluster cluster = snapshot.toObject(Cluster.class);
            cluster.setId(snapshot.getId());
            return cluster;
        }).build();

        adapter = new FirestoreRecyclerAdapter<Cluster, RecyclerView.ViewHolder>(options) {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view;
                switch (viewType) {
                    case 1: //enquete
                        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cluster_enquete, parent, false);
                        return new ClusterViewHolder(view);

                    case 2: // STATISTIQUE
                        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cluster_stat, parent, false);
                        return new ClusterViewHolder(view);
                    default:
                        //cluster
                        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cluster, parent, false);
                        return new ClusterViewHolder(view);
                }
            }

            @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull Cluster cluster) {
                switch (getItemViewType(position)) {
                    case 0: //cluster
                        ((ClusterViewHolder) holder).setOnViewClickListener(v -> {
                            viewModel.setClusterCollectionPathMutableLiveData(ClusterUtils.getCollectionPath(collectionReferenceParent.getPath(), cluster.getId()));
                            NavHostFragment.findNavController(ClusterFragment.this).navigate(R.id.action_clusterFragment_self);
                        });
                        ((ClusterViewHolder) holder).setOnDeleteClickListener(v -> {
                            AlertDialogUtils.showConfirmDeleteDialog(getContext(), ClusterUtils.getDocumentPath(collectionReferenceParent.getPath(), cluster.getId()));
                        });
                        ((ClusterViewHolder) holder).setOnEditClickListener(v -> {
                            viewModel.setCluster(cluster);
                            viewModel.setClusterCollectionPathMutableLiveData(ClusterUtils.getDocumentPath(collectionReferenceParent.getPath(), cluster.getId()));
                            NavHostFragment.findNavController(ClusterFragment.this).navigate(R.id.action_global_clusterEditDialog);
                        });
                        ((ClusterViewHolder) holder).bind(cluster);
                        break;


                    default: // STATISTIQUE  //ENQUETE
                        ((ClusterViewHolder) holder).setOnDeleteClickListener(v -> {
                            AlertDialogUtils.showConfirmDeleteDialog(getContext(), ClusterUtils.getDocumentPath(collectionReferenceParent.getPath(), cluster.getId()));
                        });
                        ((ClusterViewHolder) holder).bind(cluster);
                        break;

                }

            }

            // Determines the appropriate ViewType according to the sender of the message.
            @Override
            public int getItemViewType(int position) {
                Cluster cluster = getItem(position);
                return cluster.getType().ordinal();
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

        view.findViewById(R.id.clusterfils_type_cluster).setOnClickListener(v -> {
            NavHostFragment.findNavController(ClusterFragment.this).navigate(R.id.action_global_clusterAddDialog);
        });

        view.findViewById(R.id.clusterfils_type_enquete).setOnClickListener(v -> {
            NavHostFragment.findNavController(ClusterFragment.this).navigate(R.id.action_cclusterFragment_to_clusterEnqueteFragment);
        });

        view.findViewById(R.id.clusterfils_type_stat).setOnClickListener(v -> {

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
