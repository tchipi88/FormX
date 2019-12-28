package com.appli.nyx.formx.model.firebase.fields;

import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;

import java.util.List;

public class SpinnerQuestion extends AbstractQuestion {

    public boolean mandatory;
    public List<String> options;

    public SpinnerQuestion(String libelle) {
        super(QuestionType.SPINNER);
        this.libelle=libelle;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
