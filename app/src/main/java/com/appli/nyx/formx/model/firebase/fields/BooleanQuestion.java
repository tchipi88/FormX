package com.appli.nyx.formx.model.firebase.fields;

import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;

public class BooleanQuestion extends AbstractQuestion {

    public BooleanQuestion(String libelle) {
        super(QuestionType.BOOLEAN);
        this.libelle=libelle;
    }
}
