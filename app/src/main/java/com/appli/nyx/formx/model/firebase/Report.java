package com.appli.nyx.formx.model.firebase;

import java.io.Serializable;

public class Report implements Serializable {

    public String libelle;
    public String description;

    public String userId;

    public Report(String libelle) {
        this.libelle = libelle;
    }

    public Report() {
    }
}
