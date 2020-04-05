package com.appli.nyx.formx.ui.fragment.business.cluster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.ClusterViewModel;

import butterknife.BindView;

public class ChooseClusterTypeDialog extends BaseDialogFragment<ClusterViewModel> {

    @BindView(R.id.clusterfils_type)
    RadioGroup clusterfils_type;

    @Override
    protected Class<ClusterViewModel> getViewModel() {
        return ClusterViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_choose_cluster_type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getDialog().setTitle(getResources().getString(R.string.add_cluster));
        clusterfils_type.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.clusterfils_type_cluster:
                    NavHostFragment.findNavController(ChooseClusterTypeDialog.this).navigate(R.id.action_global_clusterAddDialog);
                    break;
                case R.id.clusterfils_type_enquete:
                    NavHostFragment.findNavController(ChooseClusterTypeDialog.this).navigate(R.id.action_chooseClusterTypeDialog_to_clusterEnqueteFragment);
                    break;
                case R.id.clusterfils_type_stat:
                    // tODO NavHostFragment.findNavController(ChooseClusterTypeDialog.this).navigate(R.id.action_clusterFilsAddDialog_to_clusterAddDialog);
                    break;
            }

        });

        return view;
    }
}
