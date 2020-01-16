package com.appli.nyx.formx.ui.fragment.business.enquete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.adapter.EnqueteAdapter;
import com.appli.nyx.formx.ui.adapter.MySwipeToDeleteCallback;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.LinearLayout.VERTICAL;

public class EnqueteListFragment extends ViewModelFragment<EnqueteViewModel> implements OnRecyclerItemClickListener {

	@Override
	protected Class<EnqueteViewModel> getViewModel() {
		return EnqueteViewModel.class;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_enquete_list;
	}

	EnqueteAdapter adapter;
	private RecyclerView recyclerView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);


		recyclerView = view.findViewById(R.id.enquetes);
		assert recyclerView != null;
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));

		adapter = new EnqueteAdapter(getContext(), this);
		recyclerView.setAdapter(adapter);

		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
		itemTouchHelper.attachToRecyclerView(recyclerView);



		viewModel.loadEnqueteByUser().observe(getViewLifecycleOwner(), enquetes -> {
			adapter.addAll(enquetes);
		});

		view.findViewById(R.id.add_enquete).setOnClickListener(v -> {
			NavHostFragment.findNavController(EnqueteListFragment.this).navigate(R.id.action_enqueteListFragment_to_enqueteFragment);
		});

		return view;
	}

	@Override
	public void onItemClick(int position) {
		viewModel.setEnquete(adapter.getItem(position));
		NavHostFragment.findNavController(EnqueteListFragment.this).navigate(R.id.action_enqueteListFragment_to_enqueteFragment);
	}

	private class SwipeToDeleteCallback extends MySwipeToDeleteCallback {

		EnqueteAdapter adapter;

		public SwipeToDeleteCallback(EnqueteAdapter adapter) {
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
