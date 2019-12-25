package com.appli.nyx.formx.model.firebase.fields;

import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;

public class DateQuestion extends AbstractQuestion {

    public boolean mandatory;

    public DateQuestion() {
        super(QuestionType.DATE_PICKER);
    }
}
