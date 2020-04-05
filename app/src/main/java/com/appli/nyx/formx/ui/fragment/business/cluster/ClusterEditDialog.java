package com.appli.nyx.formx.ui.fragment.business.cluster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Cluster;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.ClusterViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.OnClick;

public class ClusterEditDialog extends BaseDialogFragment<ClusterViewModel> {

	@BindView(R.id.libelle_tiet)
	TextInputEditText libelle_tiet;
	@BindView(R.id.libelle_til)
	TextInputLayout libelle_til;
	@BindView(R.id.description_tiet)
	TextInputEditText description_tiet;
	@BindView(R.id.description_til)
	TextInputLayout description_til;

	@Override
	protected Class<ClusterViewModel> getViewModel() {
		return ClusterViewModel.class;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.dialog_enquete_add;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

        viewModel.getClusterMutableLiveData().observe(getViewLifecycleOwner(), cluster -> {
            libelle_tiet.setText(cluster.getLibelle());
            description_tiet.setText(cluster.getDescription());
		});

		return view;
	}

	@OnClick(R.id.save)
	public void save(View view) {
		if (!validate()) {
			return;
		}

		String libelle = libelle_tiet.getText().toString();
        Cluster cluster = new Cluster();
		cluster.setLibelle(libelle);
		cluster.setDescription(description_tiet.getText().toString());

        FirebaseFirestore.getInstance().document(viewModel.getClusterCollectionPathMutableLiveData().getValue()).set(cluster).addOnCompleteListener(task -> {
			if (task.isSuccessful()) {
				NavHostFragment.findNavController(ClusterEditDialog.this).navigateUp();
			} else {
				AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
			}
		});

	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		libelle_tiet.requestFocus();

		getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		getDialog().setTitle(getResources().getString(R.string.edit_cluster));
	}

	public boolean validate() {
		boolean valid = true;

		libelle_til.setError(null);

		if (libelle_tiet.getText().toString().isEmpty()) {
			libelle_til.setError(getResources().getText(R.string.error_field_required));
			valid = false;
		}

		return valid;
	}

}
