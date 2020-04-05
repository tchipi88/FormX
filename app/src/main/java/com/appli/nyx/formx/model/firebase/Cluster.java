package com.appli.nyx.formx.model.firebase;

import com.appli.nyx.formx.model.firebase.enumeration.TypeCluster;

public class Cluster extends MyDocument {

    public String libelle;
    public String description;

    public TypeCluster type;

    public String path;

    public String libelleParent;

    public Cluster(String libelle) {
        this.libelle = libelle;
    }

    public Cluster() {
        this.type = TypeCluster.CLUSTER;
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

    public TypeCluster getType() {
        return type;
    }

    public void setType(TypeCluster type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLibelleParent() {
        return libelleParent;
    }

    public void setLibelleParent(String libelleParent) {
        this.libelleParent = libelleParent;
    }
}
