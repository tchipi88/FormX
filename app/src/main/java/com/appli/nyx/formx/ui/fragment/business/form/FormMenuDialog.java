package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Section;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;

import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;
import static com.appli.nyx.formx.utils.MyConstant.SECTION_PATH;

public class FormMenuDialog extends BaseDialogFragment<FormViewModel> {

    @BindView(R.id.form_menu)
    RadioGroup form_menu;

    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_menu_form;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getDialog().setTitle("Menu");
        form_menu.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.add_section:
                    NavHostFragment.findNavController(FormMenuDialog.this).navigate(R.id.action_formMenuDialog_to_sectionAddDialog);
                    break;
                case R.id.add_question:

                    Section section = new Section();
                    section.libelle = "DEFAULT";
                    section.setAuthorId(SessionUtils.getUserUid());

                    FirebaseFirestore.getInstance()
                            .collection(FORM_PATH)
                            .document(viewModel.getFormMutableLiveData().getValue().getId())
                            .collection(SECTION_PATH)
                            .add(section).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            section.setId(task.getResult().getId());
                            viewModel.setSection(section);
                            NavHostFragment.findNavController(FormMenuDialog.this).navigate(R.id.action_formMenuDialog_to_questionAddDialog);
                        } else {
                            AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                        }
                    });

                    break;
                case R.id.import_section:
                    NavHostFragment.findNavController(FormMenuDialog.this).navigate(R.id.action_formMenuDialog_to_importSectionFragment);
                    break;
            }

        });

        return view;
    }
}
