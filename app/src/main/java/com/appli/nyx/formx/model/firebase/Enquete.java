package com.appli.nyx.formx.model.firebase;

import com.appli.nyx.formx.model.firebase.enumeration.EnqueteVisibility;

public class Enquete extends MyDocument {

	private String libelle;
	private String description;

	private EnqueteVisibility enqueteVisibility;

    private Form form;

	private String formPath;

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

	public EnqueteVisibility getEnqueteVisibility() {
		return enqueteVisibility;
	}

	public void setEnqueteVisibility(EnqueteVisibility enqueteVisibility) {
		this.enqueteVisibility = enqueteVisibility;
	}

	public String getFormPath() {
		return formPath;
	}

	public void setFormPath(String formPath) {
		this.formPath = formPath;
	}
}
