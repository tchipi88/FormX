package com.appli.nyx.formx.ui.adapter.multiselection;

import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

public class UserDetails extends ItemDetailsLookup.ItemDetails<Long> {

	private final int adapterPosition;
	private final Long selectionKey;

	public UserDetails(int adapterPosition, Long selectionKey) {
		this.adapterPosition = adapterPosition;
		this.selectionKey = selectionKey;
	}

	@Override
	public int getPosition() {
		return adapterPosition;
	}

	@Nullable
	@Override
	public Long getSelectionKey() {
		return selectionKey;
	}

	public boolean inSelectionHotspot(@NonNull MotionEvent e) {
		return true;
	}
}