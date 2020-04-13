package com.appli.nyx.formx.ui.fields;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;
import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;
import com.appli.nyx.formx.model.firebase.fields.BooleanQuestion;
import com.appli.nyx.formx.model.firebase.fields.DateQuestion;
import com.appli.nyx.formx.model.firebase.fields.NumberQuestion;
import com.appli.nyx.formx.model.firebase.fields.SpinnerQuestion;
import com.appli.nyx.formx.model.firebase.fields.TextQuestion;
import com.appli.nyx.formx.model.firebase.fields.TimeQuestion;

public class FieldsGenerator {

    public static View generateLayoutField(FragmentManager fragmentManager, Context context, AbstractQuestion field) {
        QuestionType questionType = field.getQuestionType();
        IFieldGenerator fieldGenerator;
        switch (questionType) {
            case TEXT:
                TextQuestion textField = (TextQuestion) field;
                fieldGenerator=new TextFieldGenerator();
                return fieldGenerator.generateLayout(context, textField);
            case NUMBER:
                NumberQuestion numberField = (NumberQuestion) field;
                fieldGenerator=new NumberFieldGenerator();
                return fieldGenerator.generateLayout(context, numberField);
            case BOOLEAN:
                BooleanQuestion booleanField = (BooleanQuestion) field;
                fieldGenerator=new BooleanFieldGenerator();
                return fieldGenerator.generateLayout(context, booleanField);
            case SPINNER:
                SpinnerQuestion spinnerField = (SpinnerQuestion) field;
                fieldGenerator=new SpinnerFieldGenerator();
                return fieldGenerator.generateLayout(context, spinnerField);
            case DATE_PICKER:
                DateQuestion dateField = (DateQuestion) field;
                DateFieldGenerator dateFieldGenerator = new DateFieldGenerator();
                return dateFieldGenerator.generateLayout(fragmentManager, context, dateField);
            case TIME_PICKER:
                TimeQuestion timeField = (TimeQuestion) field;
                TimeFieldGenerator timeFieldGenerator = new TimeFieldGenerator();
                return timeFieldGenerator.generateLayout(fragmentManager, context, timeField);

        }
        return null;
    }

    public static boolean validate(Context context, AbstractQuestion field) {
        QuestionType questionType = field.getQuestionType();
        IFieldGenerator fieldGenerator;
        boolean valid = true;
        switch (questionType) {
            case TEXT:
                TextQuestion textField = (TextQuestion) field;
                fieldGenerator = new TextFieldGenerator();
                valid = fieldGenerator.generateError(context, textField);
                break;
            case NUMBER:
                NumberQuestion numberField = (NumberQuestion) field;
                fieldGenerator = new NumberFieldGenerator();
                valid = fieldGenerator.generateError(context, numberField);
                break;
            case BOOLEAN:
                BooleanQuestion booleanField = (BooleanQuestion) field;
                fieldGenerator = new BooleanFieldGenerator();
                valid = fieldGenerator.generateError(context, booleanField);
                break;
            case SPINNER:
                SpinnerQuestion spinnerField = (SpinnerQuestion) field;
                fieldGenerator = new SpinnerFieldGenerator();
                valid = fieldGenerator.generateError(context, spinnerField);
                break;
            case DATE_PICKER:
                DateQuestion dateField = (DateQuestion) field;
                fieldGenerator = new DateFieldGenerator();
                valid = fieldGenerator.generateError(context, dateField);
                break;
            case TIME_PICKER:
                TimeQuestion timeField = (TimeQuestion) field;
                fieldGenerator = new TimeFieldGenerator();
                valid = fieldGenerator.generateError(context, timeField);
                break;

        }

        return valid;

    }

}
