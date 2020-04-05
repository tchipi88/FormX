package com.appli.nyx.formx.ui.fragment.business.cluster;

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
import com.appli.nyx.formx.model.firebase.Cluster;
import com.appli.nyx.formx.model.firebase.Enquete;
import com.appli.nyx.formx.model.firebase.enumeration.TypeCluster;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.EnqueteViewHolder;
import com.appli.nyx.formx.ui.viewmodel.ClusterViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_DATA;
import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PATH;

public class ClusterEnqueteFragment extends ViewModelFragment<ClusterViewModel> {

	FirestoreRecyclerAdapter adapter;
	private RecyclerView recyclerView;
	private View emptyView;

	@Override
	protected Class<ClusterViewModel> getViewModel() {
		return ClusterViewModel.class;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_enquete_list;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);

		recyclerView = view.findViewById(R.id.enquetes);
		emptyView = view.findViewById(R.id.emptyView);
		assert recyclerView != null;
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		CollectionReference enqueteCollectionRef = FirebaseFirestore.getInstance().collection(ENQUETE_PATH).document(SessionUtils.getUserUid()).collection(ENQUETE_DATA);

		Query query = enqueteCollectionRef.orderBy("libelle");

		FirestoreRecyclerOptions<Enquete> options = new FirestoreRecyclerOptions.Builder<Enquete>().setQuery(query, snapshot -> {
			Enquete enquete = snapshot.toObject(Enquete.class);
			enquete.setId(snapshot.getId());
			return enquete;
		}).build();

		adapter = new FirestoreRecyclerAdapter<Enquete, EnqueteViewHolder>(options) {

			@NonNull
			@Override
			public EnqueteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
				View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_enquete, parent, false);
				return new EnqueteViewHolder(view);
			}

			@Override
			protected void onBindViewHolder(@NonNull EnqueteViewHolder holder, int position, @NonNull Enquete enquete) {
				holder.mItem = getItem(position);
				holder.mLibelleView.setText(enquete.getLibelle());
				holder.mDescriptionView.setText(enquete.getDescription());

				holder.mView.setOnClickListener(v -> {
					Cluster cluster = new Cluster();
					cluster.setType(TypeCluster.ENQUETE);
					cluster.setLibelle(enquete.getLibelle());
					cluster.setDescription(enquete.getDescription());
					cluster.setPath(enqueteCollectionRef.document(enquete.getId()).getPath());

					FirebaseFirestore.getInstance().collection(viewModel.getClusterCollectionPathMutableLiveData().getValue()).add(cluster).addOnCompleteListener(task -> {
						if (task.isSuccessful()) {
							NavHostFragment.findNavController(ClusterEnqueteFragment.this).navigateUp();
						} else {
							AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
						}
					});

				});

				holder.delete.setVisibility(View.GONE);

				holder.share.setVisibility(View.GONE);
				holder.edit.setVisibility(View.GONE);
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

		view.findViewById(R.id.add_enquete).setVisibility(View.GONE);

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
