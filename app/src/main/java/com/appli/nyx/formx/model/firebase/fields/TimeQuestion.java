package com.appli.nyx.formx.model.firebase.fields;

import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;

public class TimeQuestion extends AbstractQuestion {

    public boolean mandatory;

    public TimeQuestion(String libelle) {
        super(QuestionType.TIME_PICKER);
        this.libelle=libelle;
    }
}
