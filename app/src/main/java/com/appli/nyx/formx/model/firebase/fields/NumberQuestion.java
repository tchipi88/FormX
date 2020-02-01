package com.appli.nyx.formx.model.firebase.fields;

import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;

public class NumberQuestion extends AbstractQuestion {

    public int max;
    public int min;
    public boolean mandatory;

    private String unit;


    public NumberQuestion(String libelle) {
        super(QuestionType.NUMBER);
        this.libelle = libelle;
    }

    public NumberQuestion() {
        super(QuestionType.NUMBER);
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
}
