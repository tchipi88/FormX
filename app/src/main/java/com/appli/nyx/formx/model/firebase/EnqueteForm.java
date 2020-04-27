package com.appli.nyx.formx.model.firebase;

import java.io.Serializable;

public class EnqueteForm implements Serializable {

    private String id;
    private String libelle;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public EnqueteForm() {
    }

    public EnqueteForm(String id, String libelle, String description) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
