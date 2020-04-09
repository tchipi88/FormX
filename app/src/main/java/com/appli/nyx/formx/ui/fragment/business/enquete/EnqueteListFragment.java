package com.appli.nyx.formx.ui.fragment.business.enquete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.adapter.ViewPagerAdapter;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class EnqueteListFragment extends ViewModelFragment<EnqueteViewModel> {

	@Override
	protected Class<EnqueteViewModel> getViewModel() {
		return EnqueteViewModel.class;
	}

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

	@Override
	protected int getLayoutRes() {
        return R.layout.fragment_myenquete;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new EnqueteJoinedFragment(), getString(R.string.enquete_joined));
        adapter.addFragment(new MyEnqueteFragment(), getString(R.string.created_by));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        return rootView;
    }

}
