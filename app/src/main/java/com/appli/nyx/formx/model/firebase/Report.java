package com.appli.nyx.formx.model.firebase;

import java.io.Serializable;

public class Report extends MyDocument {

    public String libelle;
    public String description;


    public Report(String libelle) {
        this.libelle = libelle;
    }

    public Report() {
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
