package com.appli.nyx.formx.ui.fragment.account;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appli.nyx.formx.BuildConfig;
import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.UserViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.FileCompressor;
import com.appli.nyx.formx.utils.ImageUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindDrawable;
import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class ProfilFragment extends ViewModelFragment<UserViewModel> {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    static final int ALL_PERMISSIONS_RESULT = 100;
    @BindView(R.id.menu_changepwd)
    TextView menu_changepwd;
    @BindView(R.id.titleName)
    TextView user_profile_name;
    @BindView(R.id.titlePhone)
    TextView user_profile_phone;
    @BindView(R.id.titleBirthDay)
    TextView user_profile_birthday;

    @BindView(R.id.titleTown)
    TextView user_profile_town;
    @BindView(R.id.titleMail)
    TextView user_profile_email;
    @BindView(R.id.profil_photo)
    AppCompatImageView profil_photo;
    FileCompressor mCompressor;

    @BindDrawable(R.drawable.ic_account_circle_white_128dp)
    Drawable ic_account_circle_white_128dp;



    StorageReference storageRef;

    File mPhotoFile;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_profil;
    }

    @Override
    protected Class<UserViewModel> getViewModel() {
        return UserViewModel.class;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        viewModel.getObservableUser().observe(getViewLifecycleOwner(), user -> {

            user_profile_name.setText(user.name);
            user_profile_email.setText(user.email);

            if (TextUtils.isEmpty(user.telephone)) {
                rootView.findViewById(R.id.phoneLayout).setVisibility(View.GONE);
            } else {
                user_profile_phone.setText(user.telephone);
            }
            if (TextUtils.isEmpty(user.birthDay)) {
                rootView.findViewById(R.id.birthdayLayout).setVisibility(View.GONE);
            } else {
                user_profile_birthday.setText(user.birthDay);
            }

            if (TextUtils.isEmpty(user.town)) {
                rootView.findViewById(R.id.townLayout).setVisibility(View.GONE);
            } else {
                user_profile_town.setText(user.town);
            }

            ImageUtils.displayRoundImageFromStorageReference(getContext(), storageRef.child(SessionUtils.getUserUid()).child("profil_photo.jpg"), profil_photo, ic_account_circle_white_128dp);

        });

        menu_changepwd.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_profilFragment_to_changePasswordFragment));
        rootView.findViewById(R.id.menu_signout).setOnClickListener(v -> {

            FirebaseUser firebaseUser = SessionUtils.getUserSigned();
            if (firebaseUser != null) {
                // if you want to clear cache of specific user when logout, you can do like this.
                FirebaseAuth.getInstance().signOut();
            }
            prefsManager.clearSessionPrefs();
            signInViewModel.unAuthenticated();

        });

        rootView.findViewById(R.id.menu_profilComplete).setVisibility(prefsManager.isProfilComplete() ? View.GONE : View.VISIBLE);

        rootView.findViewById(R.id.menu_profilComplete).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profilFragment_to_profilEditFragment);
        });

        profil_photo.setOnClickListener(v -> {
            selectImage();
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        storageRef = firebaseStorage.getReference();
        mCompressor = new FileCompressor(getContext());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit:
                NavHostFragment.findNavController(ProfilFragment.this).navigate(R.id.action_profilFragment_to_profilEditFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private File getPictureFile() throws IOException {
        String pictureFile = "profil_photo";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile, ".jpg", storageDir);
        return image;
    }

    /**
     * Get real file path from URI
     */
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private void addToCloudStorage(File f) {
        Uri picUri = Uri.fromFile(f);

        StorageReference uploadeRef = storageRef.child(SessionUtils.getUserUid()).child("profil_photo.jpg");

		uploadeRef.putFile(picUri).addOnFailureListener(exception -> {
			AlertDialogUtils.showErrorDialog(getContext(), exception.getMessage());
			Log.e("Profil Fragment", "Failed to upload picture to cloud storage");
		});
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                    ImageUtils.displayRoundImageFromUrl(getContext(), mPhotoFile.getAbsolutePath(), profil_photo);
                    addToCloudStorage(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    ImageUtils.displayRoundImageFromUrl(getContext(), mPhotoFile.getAbsolutePath(), profil_photo);
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
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Remove Photo",
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
		profil_photo.setBackgroundDrawable(ic_account_circle_white_128dp);

		StorageReference uploadeRef = storageRef.child(SessionUtils.getUserUid()).child("profil_photo.jpg");

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
                photoFile = getPictureFile();
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


}
