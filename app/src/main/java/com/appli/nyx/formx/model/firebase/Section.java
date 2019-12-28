package com.appli.nyx.formx.model.firebase;

import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;

import java.io.Serializable;
import java.util.List;

public class Section implements Serializable {

    public String libelle;
    public String description;
    public List<AbstractQuestion> fields;

    public Section(String libelle) {
        this.libelle = libelle;
    }

    public Section() {
    }
}
