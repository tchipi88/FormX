package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.ui.adapter.multiselection.ViewHolderWithDetails;

public class SelectFormViewHolder extends RecyclerView.ViewHolder implements ViewHolderWithDetails {

    public final View mView;
    public final TextView mLibelleView;
    public final TextView mDescriptionView;

    public SelectFormViewHolder(@NonNull View view) {
        super(view);
        mView = view;
        mLibelleView = view.findViewById(R.id.libelle);
        mDescriptionView = view.findViewById(R.id.description);
    }

    @Override
    public ItemDetailsLookup.ItemDetails getItemDetails() {
        return new ItemDetailsLookup.ItemDetails<Long>() {
            @Override
            public int getPosition() {
                return getAdapterPosition();
            }

            @Nullable
            @Override
            public Long getSelectionKey() {
                return getItemId();
            }
        };
    }
}
