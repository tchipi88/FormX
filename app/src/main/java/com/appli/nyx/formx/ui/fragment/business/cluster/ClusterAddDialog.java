package com.appli.nyx.formx.ui.fragment.business.cluster;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.ClusterFils;
import com.appli.nyx.formx.model.firebase.enumeration.TypeClusterFils;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.ClusterViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.OnClick;

import static com.appli.nyx.formx.utils.MyConstant.CLUSTER_DATA;
import static com.appli.nyx.formx.utils.MyConstant.CLUSTER_PATH;

public class ClusterAddDialog extends BaseDialogFragment<ClusterViewModel> {

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

	@OnClick(R.id.save)
	public void save(View view) {
		if (!validate()) {
			return;
		}
		String libelle = libelle_tiet.getText().toString();
		ClusterFils cluster = new ClusterFils(TypeClusterFils.CLUSTER);
		cluster.setLibelle(libelle);
		cluster.setDescription(description_tiet.getText().toString());
		//TODO
		FirebaddseFirestore.getInstance().collection(CLUSTER_PATH).document(SessionUtils.getUserUid()).collection(CLUSTER_DATA).add(cluster).addOnCompleteListener(task -> {
			if (task.isSuccessful()) {
				NavHostFragment.findNavController(ClusterAddDialog.this).navigate(R.id.action_global_clusterListFragment);
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
		getDialog().setTitle(getResources().getString(R.string.add_cluster));
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
