package com.appli.nyx.formx.ui.fields;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.DateQuestion;

public class DateFieldGenerator implements  IFieldGenerator<DateQuestion>{

    @Override
    public View generateLayout(Context context, DateQuestion field) {
        if (field == null || context == null) {
            // Aucun champ à générer
            return null;
        }


        // Génération de la vue du champ affichant du texte à partir du layout
        LayoutInflater inflater = LayoutInflater.from(context);
        final View fieldView = inflater.inflate(R.layout.viewholder_question_date, null);
        field.setFieldView(fieldView);

        return fieldView;
    }

    @Override
    public void loadValues(DateQuestion field) {

    }

    @Override
    public void generateError(DateQuestion field) {

    }
}
