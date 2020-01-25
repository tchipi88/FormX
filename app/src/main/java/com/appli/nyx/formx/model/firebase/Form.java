package com.appli.nyx.formx.model.firebase;

import java.io.Serializable;
import java.util.List;

public class Form implements Serializable {

    public String libelle;
    public String description;

    public String userId;

    public List<Section> sections;

    public Form(String libelle) {
        this.libelle = libelle;
    }

    public Form() {
    }
}