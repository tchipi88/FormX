package com.appli.nyx.formx.ui.fragment.business.enquete;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.appli.nyx.formx.ui.viewmodel.UserViewModel;

public class EnqueteFragment extends ViewModelFragment<EnqueteViewModel> {

	@Override
	protected Class<EnqueteViewModel> getViewModel() {
		return EnqueteViewModel.class;
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.fragment_enquete;
	}
}
