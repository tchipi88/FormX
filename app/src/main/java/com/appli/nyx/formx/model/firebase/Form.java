package com.appli.nyx.formx.model.firebase;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Form implements Serializable {

	private String libelle;
	private String description;
	@ServerTimestamp
	private Date mTimestamp;

	private List<Section> sections;

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

	public Date getmTimestamp() {
		return mTimestamp;
	}

	public void setmTimestamp(Date mTimestamp) {
		this.mTimestamp = mTimestamp;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
}
