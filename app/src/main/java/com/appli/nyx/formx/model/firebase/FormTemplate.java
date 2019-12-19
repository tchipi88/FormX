package com.appli.nyx.formx.model.firebase;

import java.io.Serializable;
import java.util.List;

public class FormTemplate implements Serializable {

    public String libelle;

    public int color;

    public List<Section> sections;

    private class Section {
        public List<Question> sections;
    }
}
