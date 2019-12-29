package com.appli.nyx.formx.ui.fields;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.TextQuestion;
import com.appli.nyx.formx.ui.components.EditTextUnitDrawable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TextFieldGenerator implements  IFieldGenerator<TextQuestion>{

    @Override
    public View generateLayout(Context context, TextQuestion field) {
        if (field == null || context == null) {
            // Aucun champ à générer
            return null;
        }

        // Génération de la vue du champ de saisie à partir du layout
        LayoutInflater inflater = LayoutInflater.from(context);
        final View fieldView = inflater.inflate(R.layout.viewholder_question_text, null);
        field.setFieldView(fieldView);

        final TextInputLayout tilInput = fieldView.findViewById(R.id.textfield_til);
        final TextInputEditText edtInput = fieldView.findViewById(R.id.textfield_tiet);

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

        //UNITE
        if (!TextUtils.isEmpty(field.getUnit())){
            // Ajouter une unité à la fin du champ
            EditTextUnitDrawable unitDrawable = new EditTextUnitDrawable(fieldView.getContext(), field.getUnit());
            edtInput.setCompoundDrawablesWithIntrinsicBounds(null, null, unitDrawable, null);
            edtInput.setCompoundDrawablePadding(unitDrawable.getWidth() + fieldView.getContext().getResources().getDimensionPixelSize(R.dimen.unit_margin_left));
        } else {
            // Supprimer une éventuelle unité à la fin du champ
            edtInput.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }

        edtInput.setInputType(InputType.TYPE_CLASS_TEXT);

        return fieldView;
    }

    @Override
    public void loadValues(TextQuestion field) {
      /*  if (field == null || field.getFieldView() == null) {
            // Aucun champ à charger
            throw new EmptyValueException("Le champ de saisie de texte est null ou aucune vue ne lui est associée.");
        }

        final View fieldView = field.getFieldView();


        final TextInputEditText edtInput = fieldView.findViewById(R.id.textfield_tiet);


        // Action de validation au clic sur le bouton DONE sur le clavier
        edtInput.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                field.updateValue(!TextUtils.isEmpty(view.getText()) ? view.getText().toString().trim() : null, field.getId());
            }

            return false;
        });

        final String value = field.getFormattedValue();
        if (!TextUtils.isEmpty(value)) {
            // S'il existe une valeur pour le champ, l'associer à celui-ci
            edtInput.setText(value);
        } else {
            // S'il n'existe pas de valeur pour le champ, l'associer quand même à celui-ci pour déclencher le TextWatcher
            edtInput.setText("");
        }*/
    }

    @Override
    public void generateError(TextQuestion field) {
       /* if (field == null || field.getFieldView() == null ) {
            return;
        }

        final View fieldView = field.getFieldView();
        final Context context = fieldView.getContext();
        final TextInputEditText edtInput = fieldView.findViewById(R.id.textfield_tiet);

        if (edtInput == null) {
            // Aucun champ de saisie affiché donc aucune information à mettre en erreur
            return;
        }

        final ValidationError validationError = field.getValidationError();
        if (validationError != null && !validationError.isEmpty()) {
            if (validationError.hasError()) {
                // Le champ ne respecte pas certaines règles obligatoires
                edtInput.setBackground(ContextCompat.getDrawable(context, R.drawable.edittext_error_background));
            } else if (validationError.hasWarning()) {
                // Le champ ne respecte pas certaines règles
                edtInput.setBackground(ContextCompat.getDrawable(context, R.drawable.edittext_warning_background));
            }
        } else {
            // Aucune erreur à afficher, afficher le champ de façon classique
            edtInput.setBackground(ContextCompat.getDrawable(context, R.drawable.edittext_normal_background));
        }*/
    }
}
