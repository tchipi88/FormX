package com.appli.nyx.formx.model.firebase;

public class Section extends MyDocument {

    public String libelle;
    public String description;
    public String path;
    public String userID;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
