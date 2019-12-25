package com.appli.nyx.formx.ui.fields;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.BooleanQuestion;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class BooleanFieldGenerator implements  IFieldGenerator<BooleanQuestion>{

    @Override
    public View generateLayout(Context context, BooleanQuestion field) {
        if (field == null || context == null) {
            // Aucun champ à générer
            return null;
        }


        // Génération de la vue du champ affichant du texte à partir du layout
        LayoutInflater inflater = LayoutInflater.from(context);
        final View fieldView = inflater.inflate(R.layout.viewholder_booleanfield, null);
        field.setFieldView(fieldView);

        final SwitchMaterial switchCompat = fieldView.findViewById(R.id.booleanfield_swithcompat);

       /* if (!TextUtils.isEmpty(field.get)) {
            switchCompat.setText(hintAccessor.getValue());
        }

        loadValues(checkboxField);

        addListener(checkboxField, fieldView.findViewById(R.id.checklist_swithcompat_swith_swithcompat));
*/

        return fieldView;
    }

    @Override
    public void loadValues(BooleanQuestion field) {

    }

    @Override
    public void generateError(BooleanQuestion field) {

    }
}
