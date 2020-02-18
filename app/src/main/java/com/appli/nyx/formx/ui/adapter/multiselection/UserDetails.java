package com.appli.nyx.formx.ui.adapter.multiselection;

import com.appli.nyx.formx.model.firebase.User;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

public class UserDetails extends ItemDetailsLookup.ItemDetails {

	private final int adapterPosition;
	private final User selectionKey;

	public UserDetails(int adapterPosition, User selectionKey) {
		this.adapterPosition = adapterPosition;
		this.selectionKey = selectionKey;
	}

	@Override
	public int getPosition() {
		return adapterPosition;
	}

	@Nullable
	@Override
	public Object getSelectionKey() {
		return selectionKey;
	}
}