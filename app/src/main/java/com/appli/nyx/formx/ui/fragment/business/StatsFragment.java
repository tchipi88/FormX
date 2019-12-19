package com.appli.nyx.formx.ui.fragment.business;

import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.UserViewModel;

public class StatsFragment extends ViewModelFragment<UserViewModel> {

	@Override
	protected Class<UserViewModel> getViewModel() {
		return UserViewModel.class;
	}

	@Override
	protected int getLayoutRes() {
		return 0;
	}
}
