package com.appli.nyx.formx.model.firebase;

import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;

import java.io.Serializable;

public class Question implements Serializable {

    public QuestionType questionType;

    public String libelle;
    public String helperText;


}
