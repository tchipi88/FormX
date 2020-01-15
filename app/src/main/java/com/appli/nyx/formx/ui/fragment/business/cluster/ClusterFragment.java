package com.appli.nyx.formx.ui.fragment.business.cluster;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.UserViewModel;

public class ClusterFragment extends ViewModelFragment<UserViewModel> {

	@Override
	protected Class<UserViewModel> getViewModel() {
		return UserViewModel.class;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_cluster;
	}
}
