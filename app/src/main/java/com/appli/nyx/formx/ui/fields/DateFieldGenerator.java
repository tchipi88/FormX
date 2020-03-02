package com.appli.nyx.formx.ui.fields;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.DateQuestion;
import com.appli.nyx.formx.utils.DateUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.joda.time.LocalDate;

public class DateFieldGenerator implements IFieldGenerator<DateQuestion> {

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

        final TextInputLayout tilInput = fieldView.findViewById(R.id.datefield_til);
        final TextInputEditText edtInput = fieldView.findViewById(R.id.datefield_tiet);

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
    public void loadValues(DateQuestion field) {

    }

    @Override
    public boolean generateError(Context context, DateQuestion field) {
        if (field == null || field.getFieldView() == null) {
            return false;
        }

        final View fieldView = field.getFieldView();
        final TextInputLayout textfield_til = fieldView.findViewById(R.id.datefield_til);
        final TextInputEditText textfield_tiet = fieldView.findViewById(R.id.datefield_tiet);

        textfield_til.setError(null);

        if (TextUtils.isEmpty(textfield_tiet.getText().toString())) {
            if (field.isMandatory()) {
                textfield_til.setError(context.getResources().getText(R.string.error_field_required));
                return false;
            }
        } else {

            try {
                LocalDate dt = DateUtils.getLocalDate(textfield_tiet.getText().toString());
            } catch (Exception e) {
                textfield_til.setError("Not Valid Date");
                return false;
            }

        }

        return true;
    }
}
