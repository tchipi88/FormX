package com.appli.nyx.formx.ui.fragment.business.enquete;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.BuildConfig;
import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Enquete;
import com.appli.nyx.formx.model.firebase.EnqueteForm;
import com.appli.nyx.formx.model.firebase.enumeration.EnqueteVisibility;
import com.appli.nyx.formx.ui.components.EmptyRecyclerView;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.fragment.account.ProfilFragment;
import com.appli.nyx.formx.ui.viewholder.EnqueteFormViewHolder;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.appli.nyx.formx.ui.viewmodel.SelectFormViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.FileCompressor;
import com.appli.nyx.formx.utils.ImageUtils;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindDrawable;
import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;
import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PATH;
import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PHOTO;

public class EnqueteFragment extends ViewModelFragment<EnqueteViewModel> {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    static final int ALL_PERMISSIONS_RESULT = 100;


    @BindDrawable(R.drawable.ic_assignment_white_128dp)
    Drawable ic_assignment_black_128dp;

    @BindView(R.id.enquete_photo)
    AppCompatImageView enquete_photo;
    @BindView(R.id.enquete_name)
    TextView enquete_name;
    @BindView(R.id.enquete_des)
    TextView enquete_des;

    @BindView(R.id.enquete_visibility)
    TextView enquete_visibility;


    @BindView(R.id.forms)
    EmptyRecyclerView recyclerViewForm;
    @BindView(R.id.emptyView)
    View emptyViewForm;
    private FormAdapter formAdapter;

    StorageReference storageRef;
    File mPhotoFile;
    FileCompressor mCompressor;

    Enquete enquete;

    SelectFormViewModel selectFormViewModel;

    @Override
    protected Class<EnqueteViewModel> getViewModel() {
        return EnqueteViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_enquete;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageRef = firebaseStorage.getReference();
        mCompressor = new FileCompressor(getContext());

        selectFormViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(SelectFormViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        enquete = viewModel.getEnqueteMutableLiveData().getValue();

        recyclerViewForm.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewForm.setItemAnimator(new DefaultItemAnimator());

        formAdapter = new FormAdapter(enquete.getForms());
        recyclerViewForm.setAdapter(formAdapter);

        recyclerViewForm.setEmptyView(emptyViewForm);

        enquete_name.setText(enquete.getLibelle());
        if (TextUtils.isEmpty(enquete.getDescription())) {
            rootView.findViewById(R.id.card_des).setVisibility(View.GONE);
        } else {
            enquete_des.setText(enquete.getDescription());
        }


        if (enquete.getEnqueteVisibility() != null)
            enquete_visibility.setText(enquete.getEnqueteVisibility().name());

        ImageUtils.displayRoundImageFromStorageReference(getContext(), storageRef.child(enquete.getId()), ENQUETE_PHOTO, enquete_photo, ic_assignment_black_128dp);


        enquete_photo.setOnClickListener(v -> {
            selectImage();
        });

        rootView.findViewById(R.id.add_form).setOnClickListener(v -> {
            NavHostFragment.findNavController(EnqueteFragment.this).navigate(R.id.action_enqueteFragment_to_selectFormFragment);
        });
        rootView.findViewById(R.id.card_visibility).setOnClickListener(v -> {
            if (EnqueteVisibility.PUBLIC.equals(enquete.getEnqueteVisibility()))
                NavHostFragment.findNavController(EnqueteFragment.this).navigate(R.id.action_enqueteFragment_to_enqueteVisibilityDialog);
        });

        selectFormViewModel.getFormsMutableLiveData().observe(getViewLifecycleOwner(), forms -> {
            if (!forms.isEmpty()) {

                Map<String, Object> updatedObject = new HashMap<>();
                updatedObject.put("forms", forms);
                updatedObject.put("enqueteVisibility", EnqueteVisibility.PUBLIC);

                FirebaseFirestore.getInstance().collection(ENQUETE_PATH)
                        .document(viewModel.getEnqueteMutableLiveData().getValue().getId())
                        .update(updatedObject)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                formAdapter.addForms(forms);
                                Toast.makeText(getContext(), R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
                            } else {
                                AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                            }
                        });
            }
        });

        return rootView;
    }


    private void addToCloudStorage(File f) {
        if (!TextUtils.isEmpty(enquete.getId())) {
            Uri picUri = Uri.fromFile(f);

            StorageReference uploadeRef = storageRef.child(enquete.getId()).child(ENQUETE_PHOTO);

            uploadeRef.putFile(picUri).addOnFailureListener(exception -> Log.e("Profil Fragment", "Failed to upload picture to cloud storage"));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                    ImageUtils.displayRoundImageFromUrl(getContext(), mPhotoFile.getAbsolutePath(), enquete_photo);
                    addToCloudStorage(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(ImageUtils.getRealPathFromUri(getContext(), selectedImage)));
                    ImageUtils.displayRoundImageFromUrl(getContext(), mPhotoFile.getAbsolutePath(), enquete_photo);
                    addToCloudStorage(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * Requesting multiple permissions (storage and camera) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission(boolean isCamera) {
        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            if (isCamera) {
                dispatchTakePictureIntent();
            } else {
                dispatchGalleryIntent();
            }
        } else {
            if (EasyPermissions.somePermissionDenied(this, Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showSettingsDialog();
            } else {
                // Ask for one permission
                EasyPermissions.requestPermissions(
                        this,
                        getString(R.string.rationale_permissions_profil),
                        ALL_PERMISSIONS_RESULT,
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }

        }
    }


    /**
     * Alert dialog for capture or select from galley
     */
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Remove Photo",
                "Cancel"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                requestStoragePermission(true);
            } else if (items[item].equals("Choose from Library")) {
                requestStoragePermission(false);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            } else if (items[item].equals("Remove Photo")) {
                removeImage();
            }
        });
        builder.show();
    }

    private void removeImage() {
        enquete_photo.setBackgroundDrawable(ic_assignment_black_128dp);

        if (storageRef.child(enquete.getId()) == null) return;

        StorageReference uploadeRef = storageRef.child(enquete.getId()).child(ENQUETE_PHOTO);

        uploadeRef.delete().addOnSuccessListener(aVoid -> {
            Toast.makeText(getContext(), R.string.operation_completes_successfully, Toast.LENGTH_SHORT).show();
            // File deleted successfully
            Log.d(ProfilFragment.class.getSimpleName(), "onSuccess: deleted file");
        }).addOnFailureListener(exception -> {
            // Uh-oh, an error occurred!
            Log.d(ProfilFragment.class.getSimpleName(), "onFailure: did not delete file");
            AlertDialogUtils.showErrorDialog(getContext(), exception.getMessage());
        });
    }


    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Need Permissions");
        builder.setMessage(
                "This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }


    /**
     * Capture image from camera
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = ImageUtils.getPictureFile(getContext(), "enquete_photo");
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     * Select image fro gallery
     */
    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    private class FormAdapter extends RecyclerView.Adapter<EnqueteFormViewHolder> {
        private List<EnqueteForm> forms;

        public FormAdapter(List<EnqueteForm> forms) {
            this.forms = forms != null ? forms : new ArrayList<>();
        }


        @NonNull
        @Override
        public EnqueteFormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_form_enquete, parent, false);
            return new EnqueteFormViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EnqueteFormViewHolder holder, int position) {
            EnqueteForm enqueteForm = forms.get(position);
            holder.mLibelleView.setText(enqueteForm.getLibelle());
            holder.mDescriptionView.setText(enqueteForm.getDescription());

            holder.delete.setOnClickListener(v -> {
                FirebaseFirestore.getInstance().collection(ENQUETE_PATH)
                        .document(viewModel.getEnqueteMutableLiveData().getValue().getId()).update("forms", FieldValue.arrayRemove(enqueteForm))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                removeForm(enqueteForm);
                                Toast.makeText(getContext(), R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
                            } else {
                                AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                            }
                        });
            });
        }

        @Override
        public int getItemCount() {
            return forms != null ? forms.size() : 0;
        }

        public void addForms(List<EnqueteForm> enqueteForms) {
            this.forms.addAll(enqueteForms);
            notifyDataSetChanged();
        }

        public void removeForm(EnqueteForm enqueteForm) {
            this.forms.remove(enqueteForm);
            notifyDataSetChanged();
        }

    }

    ;

}
