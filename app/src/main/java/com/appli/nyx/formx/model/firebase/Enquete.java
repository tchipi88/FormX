package com.appli.nyx.formx.model.firebase;

import java.io.Serializable;
import java.util.List;

public class Enquete  implements Serializable {

    public String libelle;
    public String description;

    public String userId;

    public List<Form> forms;

    public Enquete(String libelle) {
        this.libelle = libelle;
    }

    public Enquete() {
    }
}
