package com.appli.nyx.formx.ui.adapter.multiselection;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

import com.google.firebase.firestore.DocumentSnapshot;

public class UserDetails extends ItemDetailsLookup.ItemDetails<DocumentSnapshot> {

	private final int adapterPosition;
    private final DocumentSnapshot selectionKey;

    public UserDetails(int adapterPosition, DocumentSnapshot selectionKey) {
		this.adapterPosition = adapterPosition;
		this.selectionKey = selectionKey;
	}

	@Override
	public int getPosition() {
		return adapterPosition;
	}

	@Nullable
	@Override
    public DocumentSnapshot getSelectionKey() {
		return selectionKey;
	}
}