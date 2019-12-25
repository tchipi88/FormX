package com.appli.nyx.formx.ui.fields;

import android.content.Context;
import android.view.View;

import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;
import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;
import com.appli.nyx.formx.model.firebase.fields.BooleanQuestion;
import com.appli.nyx.formx.model.firebase.fields.DateQuestion;
import com.appli.nyx.formx.model.firebase.fields.NumberQuestion;
import com.appli.nyx.formx.model.firebase.fields.SpinnerQuestion;
import com.appli.nyx.formx.model.firebase.fields.TextQuestion;
import com.appli.nyx.formx.model.firebase.fields.TimeQuestion;

public class FieldsGenerator {

    private static View generateLayoutField(Context context, AbstractQuestion field){
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
                fieldGenerator=new DateFieldGenerator();
                return fieldGenerator.generateLayout(context, dateField);
            case TIME_PICKER:
                TimeQuestion timeField = (TimeQuestion) field;
                fieldGenerator=new TimeFieldGenerator();
                return fieldGenerator.generateLayout(context, timeField);

        }
        return null;
    }
}
