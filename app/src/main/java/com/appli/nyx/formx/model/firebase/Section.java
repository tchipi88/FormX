package com.appli.nyx.formx.model.firebase;

public class Section extends MyDocument {

    public String libelle;
    public String description;

    public Section(String libelle) {
        this.libelle = libelle;
    }

    public Section() {
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
