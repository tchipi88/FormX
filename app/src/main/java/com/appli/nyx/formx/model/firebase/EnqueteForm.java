package com.appli.nyx.formx.model.firebase;

import java.io.Serializable;

public class EnqueteForm implements Serializable {

    private String id;
    private String libelle;

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
}
