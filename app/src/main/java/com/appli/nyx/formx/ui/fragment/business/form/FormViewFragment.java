package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;

import static com.appli.nyx.formx.utils.MyConstant.DATA;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;
import static com.appli.nyx.formx.utils.MyConstant.SECTION_PATH;

public class FormViewFragment extends ViewModelFragment<FormViewModel> {
    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_form_view;
    }

    @BindView(R.id.fields_container)
    LinearLayout fieldsContainer;

    @BindView(R.id.btn_next)
    MaterialButton btn_next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        viewModel.getFormMutableLiveData().observe(getViewLifecycleOwner(), form -> {
            NavHostFragment.findNavController(FormViewFragment.this).getCurrentDestination().setLabel(form.getLibelle());

            //get all sections
            FirebaseFirestore.getInstance().collection(FORM_PATH)
                    .document(SessionUtils.getUserUid())
                    .collection(DATA)
                    .document(form.getId())
                    .collection(SECTION_PATH).get().addOnCompleteListener(task -> {
//TODO

            });

        });

        return view;
    }
}
