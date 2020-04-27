package com.appli.nyx.formx.ui.fragment.business.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Report;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.ReportViewHolder;
import com.appli.nyx.formx.ui.viewmodel.ReportViewModel;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

import static android.widget.LinearLayout.VERTICAL;
import static com.appli.nyx.formx.utils.MyConstant.AUTHOR_ID;
import static com.appli.nyx.formx.utils.MyConstant.REPORTS_PATH;

public class ReportsListFragment extends ViewModelFragment<ReportViewModel> {

	FirestoreRecyclerAdapter adapter;
    private ShimmerRecyclerViewX recyclerView;
    private View emptyView;

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
        emptyView = view.findViewById(R.id.emptyView);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));


		// Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance().collection(REPORTS_PATH).whereEqualTo(AUTHOR_ID, SessionUtils.getUserUid()).orderBy("libelle");

		FirestoreRecyclerOptions<Report> options = new FirestoreRecyclerOptions.Builder<Report>().setQuery(query, Report.class).build();

		adapter = new FirestoreRecyclerAdapter<Report, ReportViewHolder>(options) {

			@NonNull
			@Override
			public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
				View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_report, parent, false);
				return new ReportViewHolder(view);
			}

			@Override
			protected void onBindViewHolder(@NonNull ReportViewHolder holder, int position, @NonNull Report model) {
				holder.mItem = getItem(position);
				holder.mLibelleView.setText(model.getLibelle());

				holder.mView.setOnClickListener(v -> {
					viewModel.setReport(holder.mItem);
					NavHostFragment.findNavController(ReportsListFragment.this).navigate(R.id.action_reportsListFragment_to_reportsFragment);
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
