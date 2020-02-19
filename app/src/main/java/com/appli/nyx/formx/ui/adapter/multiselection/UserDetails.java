package com.appli.nyx.formx.ui.adapter.multiselection;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

public class UserDetails extends ItemDetailsLookup.ItemDetails<String> {

	private final int adapterPosition;
	private final String selectionKey;

	public UserDetails(int adapterPosition, String selectionKey) {
		this.adapterPosition = adapterPosition;
		this.selectionKey = selectionKey;
	}

	@Override
	public int getPosition() {
		return adapterPosition;
	}

	@Nullable
	@Override
	public String getSelectionKey() {
		return selectionKey;
	}
}