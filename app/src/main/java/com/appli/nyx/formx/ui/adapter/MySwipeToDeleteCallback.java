package com.appli.nyx.formx.ui.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public abstract class MySwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

	private final Drawable ic_delete;
	private final ColorDrawable background;

	public MySwipeToDeleteCallback(Drawable ic_delete) {
		super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
		this.ic_delete = ic_delete;
		this.background = new ColorDrawable(Color.RED);
		;
	}

	@Override
	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
		// used for up and down movements
		return false;
	}

	@Override
	public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
		super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

		View itemView = viewHolder.itemView;
		int backgroundCornerOffset = 20; //so background is behind the rounded corners of itemView

		int iconMargin = (itemView.getHeight() - ic_delete.getIntrinsicHeight()) / 2;
		int iconTop = itemView.getTop() + (itemView.getHeight() - ic_delete.getIntrinsicHeight()) / 2;
		int iconBottom = iconTop + ic_delete.getIntrinsicHeight();

		if (dX > 0) { // Swiping to the right
			int iconLeft = itemView.getLeft() + iconMargin + ic_delete.getIntrinsicWidth();
			int iconRight = itemView.getLeft() + iconMargin;
			ic_delete.setBounds(iconLeft, iconTop, iconRight, iconBottom);

			background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
		} else if (dX < 0) { // Swiping to the left
			int iconLeft = itemView.getRight() - iconMargin - ic_delete.getIntrinsicWidth();
			int iconRight = itemView.getRight() - iconMargin;
			ic_delete.setBounds(iconLeft, iconTop, iconRight, iconBottom);

			background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset, itemView.getTop(), itemView.getRight(), itemView.getBottom());
		} else { // view is unSwiped
			background.setBounds(0, 0, 0, 0);
		}

		background.draw(c);
		ic_delete.draw(c);
	}

}
