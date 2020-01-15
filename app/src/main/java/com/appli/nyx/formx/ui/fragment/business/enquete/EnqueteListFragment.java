package com.appli.nyx.formx.ui.fragment.business.enquete;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Enquete;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.fragment.business.form.FormListFragment;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.appli.nyx.formx.ui.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class EnqueteListFragment extends ViewModelFragment<EnqueteViewModel> {

	@Override
	protected Class<EnqueteViewModel> getViewModel() {
		return EnqueteViewModel.class;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_enquete_list;
	}

	SimpleItemRecyclerViewAdapter adapter;
	private RecyclerView recyclerView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);


		recyclerView = view.findViewById(R.id.enquetes);
		assert recyclerView != null;
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		adapter = new SimpleItemRecyclerViewAdapter();
		recyclerView.setAdapter(adapter);


		viewModel.loadEnqueteByUser().observe(getViewLifecycleOwner(), enquetes -> {
			adapter.addAll(enquetes);
		});

		view.findViewById(R.id.add_enquete).setOnClickListener(v -> {
			NavHostFragment.findNavController(EnqueteListFragment.this).navigate(R.id.action_enqueteListFragment_to_enqueteFragment);
		});

		return view;
	}

	private class SimpleItemRecyclerViewAdapter
			extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

		Context context;
		private List<Enquete> mValues;


		public SimpleItemRecyclerViewAdapter() {
			mValues = new ArrayList<>();
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			context = parent.getContext();
			View view = LayoutInflater.from(context)
					.inflate(R.layout.viewholder_enquete
							, parent, false);
			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(final ViewHolder holder, int position) {
			holder.mItem = mValues.get(position);
			holder.mLibelleView.setText(holder.mItem.libelle);
			holder.mView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					viewModel.setEnquete(holder.mItem);
					NavHostFragment.findNavController(EnqueteListFragment.this).navigate(R.id.action_enqueteListFragment_to_enqueteFragment);

				}
			});
		}

		@Override
		public int getItemCount() {
			return mValues.size();
		}

		public void addAll(List<Enquete> enquetes) {
			mValues.addAll(enquetes);
			notifyDataSetChanged();

		}


		public class ViewHolder extends RecyclerView.ViewHolder {
			public final View mView;
			public final TextView mLibelleView;

			public Enquete mItem;

			public ViewHolder(View view) {
				super(view);
				mView = view;
				mLibelleView = view.findViewById(R.id.libelle);

			}

			@Override
			public String toString() {
				return super.toString() + " '" + mLibelleView.getText() + "'";
			}
		}
	}
}
