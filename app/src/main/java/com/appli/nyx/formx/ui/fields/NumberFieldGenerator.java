package com.appli.nyx.formx.ui.fields;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.NumberQuestion;
import com.appli.nyx.formx.ui.components.EditTextUnitDrawable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class NumberFieldGenerator implements  IFieldGenerator<NumberQuestion>{

    @Override
    public View generateLayout(Context context, NumberQuestion field) {
        if (field == null || context == null) {
            // Aucun champ à générer
            return null;
        }

        // Génération de la vue du champ de saisie à partir du layout
        LayoutInflater inflater = LayoutInflater.from(context);
		final View fieldView = inflater.inflate(R.layout.viewholder_question_number, null);
        field.setFieldView(fieldView);

		final TextInputLayout tilInput = fieldView.findViewById(R.id.numberfield_til);
		final TextInputEditText edtInput = fieldView.findViewById(R.id.numberfield_tiet);

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


        edtInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

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

        return fieldView;
    }

    @Override
    public String getValue(Context context, NumberQuestion field) {
        final View fieldView = field.getFieldView();
        final TextInputEditText edtInput = fieldView.findViewById(R.id.numberfield_tiet);
        return edtInput.getText().toString();
    }

    @Override
    public boolean generateError(Context context, NumberQuestion field) {
        if (field == null || field.getFieldView() == null) {
            return false;
        }
        final TextInputLayout tilInput = field.getFieldView().findViewById(R.id.numberfield_til);
        final TextInputEditText edtInput = field.getFieldView().findViewById(R.id.numberfield_tiet);
        tilInput.setError(null);
        if (field.mandatory) {
            if (TextUtils.isEmpty(edtInput.getText().toString())) {
                tilInput.setError(context.getResources().getText(R.string.error_field_required));
                return false;
            }
        }

        return true;
    }
}
