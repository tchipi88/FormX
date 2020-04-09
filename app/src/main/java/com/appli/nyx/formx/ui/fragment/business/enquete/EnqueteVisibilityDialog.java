package com.appli.nyx.formx.ui.fragment.business.enquete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.enumeration.EnqueteVisibility;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PATH;

public class EnqueteVisibilityDialog extends BaseDialogFragment<EnqueteViewModel> {

    @BindView(R.id.enquete_visibility)
    RadioGroup enquete_visibility;

    @Override
    protected Class<EnqueteViewModel> getViewModel() {
        return EnqueteViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_enquete_visibility;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getDialog().setTitle(getResources().getString(R.string.enquete_visibility));
        enquete_visibility.setOnCheckedChangeListener((group, checkedId) -> {
            Map<String, Object> updatedObject = new HashMap<>();
            switch (checkedId) {
                case R.id.enquete_visibility_draft:
                    updatedObject.put("enqueteVisibility", EnqueteVisibility.DRAFT);
                    break;
                case R.id.enquete_visibility_public:
                    updatedObject.put("enqueteVisibility", EnqueteVisibility.PUBLIC);
                    break;
            }

            FirebaseFirestore.getInstance().collection(ENQUETE_PATH)
                    .document(viewModel.getEnqueteMutableLiveData().getValue().getId())
                    .update(updatedObject)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(view.getContext(), R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
                            NavHostFragment.findNavController(EnqueteVisibilityDialog.this).navigateUp();
                        } else {
                            AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                        }
                    });

        });

        return view;
    }
}
