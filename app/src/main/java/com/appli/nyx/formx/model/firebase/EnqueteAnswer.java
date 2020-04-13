package com.appli.nyx.formx.model.firebase;

import com.appli.nyx.formx.utils.SessionUtils;

import java.util.ArrayList;
import java.util.List;

public class EnqueteAnswer extends MyDocument {

    private String authorId;

    private List<EnqueteAnswerSection> sections;

    public EnqueteAnswer() {
        this.authorId = SessionUtils.getUserUid();
        this.sections = new ArrayList<>();
    }


    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public List<EnqueteAnswerSection> getSections() {
        return sections;
    }

    public void setSections(List<EnqueteAnswerSection> sections) {
        this.sections = sections;
    }

    public void addSection(EnqueteAnswerSection section) {
        this.sections.add(section);
    }
}
