package com.appli.nyx.formx.ui.fields;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.BooleanQuestion;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

public class BooleanFieldGenerator implements  IFieldGenerator<BooleanQuestion>{

    @Override
    public View generateLayout(Context context, BooleanQuestion field) {
        if (field == null || context == null) {
            // Aucun champ à générer
            return null;
        }


        // Génération de la vue du champ affichant du texte à partir du layout
        LayoutInflater inflater = LayoutInflater.from(context);
        final View fieldView = inflater.inflate(R.layout.viewholder_question_boolean, null);
        field.setFieldView(fieldView);

        final SwitchMaterial switchCompat = fieldView.findViewById(R.id.booleanfield_swithcompat);

        //LIBELLE
        if (!TextUtils.isEmpty(field.getLibelle())) {
            switchCompat.setText(field.getLibelle());
        } else {
            // Supprimer une éventuelle information sur le champ
            switchCompat.setText("");
        }

        //description
        if (!TextUtils.isEmpty(field.getDescription())) {
            ((MaterialTextView) fieldView.findViewById(R.id.description)).setText(field.getDescription());
        }


        return fieldView;
    }

    @Override
    public void loadValues(BooleanQuestion field) {

    }

    @Override
    public void generateError(BooleanQuestion field) {

    }
}
