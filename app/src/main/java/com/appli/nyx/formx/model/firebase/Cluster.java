package com.appli.nyx.formx.model.firebase;

import java.io.Serializable;

public class Cluster implements Serializable {

    public String libelle;
    public String description;

    public String userId;

    public Cluster(String libelle) {
        this.libelle = libelle;
    }

    public Cluster() {
    }
}
