package com.appli.nyx.formx.ui.adapter.multiselection;

import com.appli.nyx.formx.model.firebase.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

public class UserKeyProvider extends ItemKeyProvider {

	private final List<User> itemList;

	public UserKeyProvider(int scope, List<User> itemList) {
		super(scope);
		this.itemList = itemList;
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