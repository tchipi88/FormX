package com.appli.nyx.formx.model.firebase.fields;

import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;

public class TimeQuestion extends AbstractQuestion {

    public boolean mandatory;

    public TimeQuestion(String libelle) {
        super(QuestionType.TIME_PICKER);
        this.libelle=libelle;
    }

    public TimeQuestion() {
        super(QuestionType.TIME_PICKER);
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
}
