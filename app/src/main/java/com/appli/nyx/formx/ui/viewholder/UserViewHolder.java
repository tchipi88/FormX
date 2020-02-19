package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.User;
import com.appli.nyx.formx.ui.adapter.multiselection.UserDetails;
import com.appli.nyx.formx.ui.adapter.multiselection.ViewHolderWithDetails;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder implements ViewHolderWithDetails {

    public final View mView;
    public TextView user_name;
    public TextView user_firstname;
    public AppCompatImageView user_img;

    public User user;

    public UserViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        user_firstname = itemView.findViewById(R.id.user_firstname);
        user_name = itemView.findViewById(R.id.user_name);
        user_img = itemView.findViewById(R.id.user_img);

    }

    @Override
    public String toString() {
        return super.toString() + " '" + user_name.getText() + "'";
    }

    @Override
    public ItemDetailsLookup.ItemDetails getItemDetails() {
        return new UserDetails(getAdapterPosition(), user.getId());
    }
}