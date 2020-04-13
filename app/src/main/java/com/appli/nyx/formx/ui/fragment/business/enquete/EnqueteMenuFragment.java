package com.appli.nyx.formx.ui.fragment.business.enquete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;

public class EnqueteMenuFragment extends ViewModelFragment<EnqueteViewModel> {

    @Override
    protected Class<EnqueteViewModel> getViewModel() {
        return EnqueteViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_enquete_menu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        view.findViewById(R.id.card_myenquetes).setOnClickListener(v -> NavHostFragment.findNavController(EnqueteMenuFragment.this).navigate(R.id.action_global_enqueteListFragment));
        view.findViewById(R.id.card_joinenquete).setOnClickListener(v -> NavHostFragment.findNavController(EnqueteMenuFragment.this).navigate(R.id.action_global_joinEnqueteFragment));
        view.findViewById(R.id.card_newenquete).setOnClickListener(v -> NavHostFragment.findNavController(EnqueteMenuFragment.this).navigate(R.id.action_global_enqueteAddDialog));
        view.findViewById(R.id.card_myinvitations).setOnClickListener(v -> NavHostFragment.findNavController(EnqueteMenuFragment.this).navigate(R.id.action_enqueteMenuFragment_to_enqueteSharedFragment));

        return view;
    }
}
