package com.appli.nyx.formx.model.firebase;

public class Form extends MyDocument {

	private String libelle;
	private String description;

    public String authorId;

    public Form(String libelle) {
        this.libelle = libelle;
    }

    public Form() {
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
