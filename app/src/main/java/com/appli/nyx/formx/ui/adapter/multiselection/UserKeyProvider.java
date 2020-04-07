package com.appli.nyx.formx.ui.adapter.multiselection;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

import com.appli.nyx.formx.ui.fragment.business.SelectUserFragment;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class UserKeyProvider extends ItemKeyProvider {

	private final List<DocumentSnapshot> itemList;

	@SuppressLint("WrongConstant")
	public UserKeyProvider(SelectUserFragment.UserFirebaseAdapter adapter) {
        super(1);
		itemList = adapter.getCurrentList();
	}

	@Nullable
	@Override
    public Object getKey(int position) {
        return itemList.get(position);
	}

	@Override
    public int getPosition(@NonNull Object key) {
		return itemList.indexOf(key);
	}
}