package com.appli.nyx.formx.ui.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Enquete;

public class EnqueteJoinedViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public final Button reply;
    public final Button quit;
    public TextView mLibelleView;
    public TextView mDescriptionView;

    public Enquete mItem;

    public EnqueteJoinedViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mLibelleView = itemView.findViewById(R.id.libelle);
        mDescriptionView = itemView.findViewById(R.id.description);

        reply = itemView.findViewById(R.id.reply);
        quit = itemView.findViewById(R.id.quit);

    }

    @Override
    public String toString() {
        return super.toString() + " '" + mLibelleView.getText() + "'";
    }
}