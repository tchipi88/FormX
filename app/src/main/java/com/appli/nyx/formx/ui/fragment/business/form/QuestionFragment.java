package com.appli.nyx.formx.ui.fragment.business.form;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;

public class QuestionFragment extends ViewModelFragment<FormViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_question;
    }

    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }
}
