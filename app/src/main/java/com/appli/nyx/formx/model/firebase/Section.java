package com.appli.nyx.formx.model.firebase;

import com.appli.nyx.formx.utils.SessionUtils;

public class Section extends MyDocument {

    public String libelle;
    public String description;
    public String path;
    public String authorId;

    public Section(String libelle) {
        this.authorId = SessionUtils.getUserUid();
        this.libelle = libelle;
    }

    public Section() {
        this.authorId = SessionUtils.getUserUid();
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
