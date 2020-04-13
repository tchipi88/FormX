package com.appli.nyx.formx.ui.fragment.business.enquete;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.MainActivity;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.appli.nyx.formx.utils.ImageUtils;
import com.google.firebase.storage.StorageReference;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PHOTO;

public class EnqueteReplyIntroFragment extends ViewModelFragment<EnqueteViewModel> {

    @BindDrawable(R.drawable.ic_assignment_black_128dp)
    Drawable ic_assignment_black_128dp;

    StorageReference storageRef;

    @BindView(R.id.img)
    AppCompatImageView enquete_photo;
    @BindView(R.id.libelle)
    TextView enquete_name;
    @BindView(R.id.description)
    TextView enquete_des;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageRef = firebaseStorage.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        viewModel.getEnqueteMutableLiveData().observe(getViewLifecycleOwner(), enquete -> {
            ((MainActivity) requireActivity()).getSupportActionBar().setTitle(enquete.getLibelle());
            enquete_name.setText(enquete.getLibelle());
            enquete_des.setText(enquete.getDescription());
            ImageUtils.displayRoundImageFromStorageReference(getContext(), storageRef.child(enquete.getId()), ENQUETE_PHOTO, enquete_photo, ic_assignment_black_128dp);
        });
        return rootView;
    }

    @Override
    protected Class<EnqueteViewModel> getViewModel() {
        return EnqueteViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_enquete_reply_intro;
    }

    @OnClick(R.id.start)
    public void start(View view) {
        NavHostFragment.findNavController(EnqueteReplyIntroFragment.this).navigate(R.id.action_enqueteReplyIntroFragment_to_enqueteReplyFragment);
    }
}
