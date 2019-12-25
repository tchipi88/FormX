package com.appli.nyx.formx.model.firebase.fields;

import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;

public class NumberQuestion extends AbstractQuestion {

    public int max;
    public int min;
    public boolean mandatory;

    private String unit;


    public NumberQuestion(QuestionType questionType) {
        super(QuestionType.NUMBER);
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
