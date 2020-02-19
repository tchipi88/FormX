package com.appli.nyx.formx.ui.adapter.multiselection;

import android.annotation.SuppressLint;

import com.appli.nyx.formx.ui.fragment.business.SelectUserFragment;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

public class UserKeyProvider extends ItemKeyProvider<String> {

	private final List<DocumentSnapshot> itemList;

	@SuppressLint("WrongConstant")
	public UserKeyProvider(SelectUserFragment.UserFirebaseAdapter adapter) {
		super(0);
		itemList = adapter.getCurrentList();
	}

	@Nullable
	@Override
	public String getKey(int position) {
		return itemList.get(position).getId();
	}

	@Override
	public int getPosition(@NonNull String key) {
		return itemList.indexOf(key);
	}
}