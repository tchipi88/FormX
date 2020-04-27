package com.appli.nyx.formx.ui.viewholder;

import android.view.View;

import com.appli.nyx.formx.R;

public class EnqueteFormViewHolder extends SelectFormViewHolder {


    public final androidx.appcompat.widget.AppCompatImageButton delete;


    public EnqueteFormViewHolder(View view) {
        super(view);

        delete = view.findViewById(R.id.delete);

    }

    @Override
    public String toString() {
        return super.toString() + " '" + mLibelleView.getText() + "'";
    }
}