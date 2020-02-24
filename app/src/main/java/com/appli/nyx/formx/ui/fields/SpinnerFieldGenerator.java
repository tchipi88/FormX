package com.appli.nyx.formx.ui.fields;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.SpinnerQuestion;
import com.appli.nyx.formx.model.firebase.validation.ValidationError;
import com.tiper.MaterialSpinner;

public class SpinnerFieldGenerator implements  IFieldGenerator<SpinnerQuestion>{

    @Override
    public View generateLayout(Context context, SpinnerQuestion field) {
        if (field == null || context == null) {
            // Aucun champ à générer
            return null;
        }

        // Génération de la vue du champ de saisie à partir du layout
        LayoutInflater inflater = LayoutInflater.from(context);
        final View fieldView = inflater.inflate(R.layout.viewholder_question_spinner, null);
        field.setFieldView(fieldView);

        final MaterialSpinner materialSpinner = fieldView.findViewById(R.id.spinnerfield_spi);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                field.options
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materialSpinner.setAdapter(spinnerAdapter);

        //LIBELLE
        if (!TextUtils.isEmpty(field.getLibelle())) {
            materialSpinner.setHint(field.getLibelle());
        } else {
            // Supprimer une éventuelle information sur le champ
            materialSpinner.setHint("");
        }

        //HELPER TEXT
        if (!TextUtils.isEmpty(field.getDescription())) {
            materialSpinner.setHelperText(field.getDescription());
        } else {
            // Supprimer une éventuelle information sur le champ
            materialSpinner.setHelperText("");
        }

        return fieldView;
    }

    @Override
    public void loadValues(SpinnerQuestion field) {

    }

    @Override
    public void generateError(SpinnerQuestion field) {
        if (field == null || field.getFieldView() == null ) {
            return;
        }

        final View fieldView = field.getFieldView();
        final MaterialSpinner spiSpinner = fieldView.findViewById(R.id.spinnerfield_spi);


        final ValidationError validationError = field.getValidationError();
        if (validationError != null && !validationError.isEmpty()) {
            if (validationError.hasError()) {
                // Le champ ne respecte pas certaines règles obligatoires
                spiSpinner.setError("");
            }
        } else {
            // Aucune erreur à afficher, afficher le champ de façon classique
            spiSpinner.setError(null);
        }
    }
}
