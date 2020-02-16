package com.appli.nyx.formx.model.firebase;

public class Enquete extends MyDocument {

	private String libelle;
	private String description;

    private Form form;

    public Enquete(String libelle) {
        this.libelle = libelle;
    }

    public Enquete() {
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

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
	}
}
