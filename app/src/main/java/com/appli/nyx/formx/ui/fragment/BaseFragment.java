package com.appli.nyx.formx.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.preference.PrefsManager;
import com.appli.nyx.formx.preference.PrefsManager_;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindDrawable;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseFragment extends Fragment {

	protected FirebaseFirestore mFirestore;
	protected ProgressBar progressBar;

	protected PrefsManager prefsManager;

	@BindDrawable(R.drawable.ic_delete_black_24dp)
	protected Drawable ic_delete;

	@LayoutRes
	protected abstract int getLayoutRes();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Enable Firestore logging
		FirebaseFirestore.setLoggingEnabled(true);
		mFirestore = FirebaseFirestore.getInstance();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutRes(), container, false);
		ButterKnife.bind(this, view);
		prefsManager = PrefsManager_.getInstance_(getActivity());
		progressBar = new ProgressBar(getContext());
		return view;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		// Forward results to EasyPermissions
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	public void showLoading(boolean visibility) {
		getActivity().findViewById(R.id.llProgressBar).setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
	}

}
