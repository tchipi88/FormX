package com.appli.nyx.formx.model.firebase;

import com.appli.nyx.formx.model.firebase.enumeration.EnqueteVisibility;

import java.util.ArrayList;
import java.util.List;

public class Enquete extends MyDocument {

	private String libelle;
	private String description;

	private EnqueteVisibility enqueteVisibility;

    public String authorId;

    private List<EnqueteForm> forms;

    private List<String> joinUserId;
    private List<String> shareUserId;

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

	public EnqueteVisibility getEnqueteVisibility() {
		return enqueteVisibility;
	}

	public void setEnqueteVisibility(EnqueteVisibility enqueteVisibility) {
		this.enqueteVisibility = enqueteVisibility;
	}

    public List<EnqueteForm> getForms() {
        return forms;
    }

    public void setForms(List<EnqueteForm> forms) {
        this.forms = forms;
    }

    public void addForm(EnqueteForm enqueteForm) {
        if (this.forms == null) this.forms = new ArrayList<>();
        this.forms.add(enqueteForm);
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public List<String> getJoinUserId() {
        return joinUserId;
    }

    public void setJoinUserId(List<String> joinUserId) {
        this.joinUserId = joinUserId;
    }

    public List<String> getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(List<String> shareUserId) {
        this.shareUserId = shareUserId;
    }
}
