package com.appli.nyx.formx.model.firebase;

import java.io.Serializable;

public class Cluster implements Serializable {

    public String libelle;
    public String description;

    public Cluster(String libelle) {
        this.libelle = libelle;
    }

    public Cluster() {
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
