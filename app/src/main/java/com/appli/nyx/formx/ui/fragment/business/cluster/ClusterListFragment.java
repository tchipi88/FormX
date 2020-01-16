package com.appli.nyx.formx.ui.fragment.business.cluster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.adapter.ClusterAdapter;
import com.appli.nyx.formx.ui.adapter.MySwipeToDeleteCallback;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.ClusterViewModel;
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.LinearLayout.VERTICAL;

public class ClusterListFragment extends ViewModelFragment<ClusterViewModel> implements OnRecyclerItemClickListener {

    ClusterAdapter adapter;
    private RecyclerView recyclerView;


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
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));

        adapter = new ClusterAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);


        viewModel.loadClusterByUser().observe(getViewLifecycleOwner(), clusters -> {
            adapter.addAll(clusters);
        });

        view.findViewById(R.id.add_cluster).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_clusterListFragment_to_clusterFragment);
        });

        return view;
    }

    @Override
    public void onItemClick(int position) {
        viewModel.setCluster(adapter.getItem(position));
        NavHostFragment.findNavController(ClusterListFragment.this).navigate(R.id.action_clusterListFragment_to_clusterFragment);
    }

    private class SwipeToDeleteCallback extends MySwipeToDeleteCallback {

        ClusterAdapter adapter;

        public SwipeToDeleteCallback(ClusterAdapter adapter) {
            super(ic_delete);
            this.adapter = adapter;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            adapter.remove(adapter.getItem(position));
        }

    }



}
