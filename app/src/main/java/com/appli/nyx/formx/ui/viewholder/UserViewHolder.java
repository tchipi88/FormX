package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public TextView user_name;
    public TextView user_firstname;
    public AppCompatImageView user_img;

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
}