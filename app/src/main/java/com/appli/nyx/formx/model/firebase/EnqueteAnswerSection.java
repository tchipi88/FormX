package com.appli.nyx.formx.model.firebase;

import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;

import java.util.ArrayList;
import java.util.List;

public class EnqueteAnswerSection {


    public String libelle;
    public String description;
    List<AbstractQuestion> questions;

    public EnqueteAnswerSection() {
        this.questions = new ArrayList<>();
    }

    public List<AbstractQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<AbstractQuestion> questions) {
        this.questions = questions;
    }

    public void addQuestion(AbstractQuestion question) {
        this.questions.add(question);
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
}
