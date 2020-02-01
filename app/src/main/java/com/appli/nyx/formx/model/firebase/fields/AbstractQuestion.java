package com.appli.nyx.formx.model.firebase.fields;

import android.view.View;

import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;
import com.appli.nyx.formx.model.firebase.validation.ValidationError;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class AbstractQuestion implements Serializable {

    @DocumentId
    protected String id;

    protected final QuestionType questionType;

    protected String libelle;
    protected String description;

    @Exclude
    private View fieldView;

    @Exclude
    private ValidationError validationError;

    public AbstractQuestion(QuestionType questionType) {
        this.questionType = questionType;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }


    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public View getFieldView() {
        return fieldView;
    }

    public void setFieldView(View fieldView) {
        this.fieldView = fieldView;
    }

    public ValidationError getValidationError() {
        return validationError;
    }

    public void setValidationError(ValidationError validationError) {
        this.validationError = validationError;
    }
}
