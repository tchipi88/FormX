package com.appli.nyx.formx.ui.fragment.business.cluster;

import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.ClusterViewModel;

public class ClusterStatsFragment extends ViewModelFragment<ClusterViewModel> {

    @Override
    protected Class<ClusterViewModel> getViewModel() {
        return ClusterViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return 0;
    }
}
