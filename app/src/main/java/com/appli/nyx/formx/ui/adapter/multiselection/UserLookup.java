package com.appli.nyx.formx.ui.adapter.multiselection;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.ui.fragment.business.SelectUserFragment;

public class UserLookup extends ItemDetailsLookup {

	private final RecyclerView recyclerView;

	public UserLookup(RecyclerView recyclerView) {
		this.recyclerView = recyclerView;
	}

	@Nullable
	@Override
	public ItemDetails getItemDetails(@NonNull MotionEvent e) {
		View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
		if (view != null) {
			RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder instanceof SelectUserFragment.UserFirebaseAdapter.UserViewHolder) {
                return ((SelectUserFragment.UserFirebaseAdapter.UserViewHolder) viewHolder).getItemDetails();
			}
		}

		return null;
	}
}
