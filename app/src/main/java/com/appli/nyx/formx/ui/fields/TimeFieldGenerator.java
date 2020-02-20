package com.appli.nyx.formx.ui.fields;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.TimeQuestion;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TimeFieldGenerator  implements  IFieldGenerator<TimeQuestion>{



    @Override
    public View generateLayout(Context context, TimeQuestion field) {
        if (field == null || context == null) {
            // Aucun champ à générer
            return null;
        }


        // Génération de la vue du champ affichant du texte à partir du layout
        LayoutInflater inflater = LayoutInflater.from(context);
        final View fieldView = inflater.inflate(R.layout.viewholder_question_time, null);
        field.setFieldView(fieldView);

        final TextInputLayout tilInput = fieldView.findViewById(R.id.timefield_til);
        final TextInputEditText edtInput = fieldView.findViewById(R.id.timefield_tiet);

        //LIBELLE
        if (!TextUtils.isEmpty(field.getLibelle())) {
            tilInput.setHint(field.getLibelle());
        } else {
            // Supprimer une éventuelle information sur le champ
            tilInput.setHint("");
        }

        //HELPER TEXT
        if (!TextUtils.isEmpty(field.getDescription())) {
            tilInput.setHelperText(field.getDescription());
        } else {
            // Supprimer une éventuelle information sur le champ
            tilInput.setHelperText("");
        }


        return fieldView;
    }

    @Override
    public void loadValues(TimeQuestion field) {

    }

    @Override
    public void generateError(TimeQuestion field) {

    }
}
