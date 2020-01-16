package com.appli.nyx.formx.ui.fragment.business.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.adapter.MySwipeToDeleteCallback;
import com.appli.nyx.formx.ui.adapter.ReportAdapter;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.ReportViewModel;
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.LinearLayout.VERTICAL;

public class ReportsListFragment extends ViewModelFragment<ReportViewModel> implements OnRecyclerItemClickListener {

    ReportAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected Class<ReportViewModel> getViewModel() {
        return ReportViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_report_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.reports);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));

        adapter = new ReportAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);


        viewModel.loadReportByUser().observe(getViewLifecycleOwner(), reports -> {
            adapter.addAll(reports);
        });

        view.findViewById(R.id.add_report).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_reportsListFragment_to_reportsFragment);
        });

        return view;
    }

    @Override
    public void onItemClick(int position) {
        viewModel.setReport(adapter.getItem(position));
        NavHostFragment.findNavController(ReportsListFragment.this).navigate(R.id.action_reportsListFragment_to_reportsFragment);
    }

    private class SwipeToDeleteCallback extends MySwipeToDeleteCallback {

        ReportAdapter adapter;

        public SwipeToDeleteCallback(ReportAdapter adapter) {
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
