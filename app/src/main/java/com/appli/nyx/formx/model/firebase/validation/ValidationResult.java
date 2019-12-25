package com.appli.nyx.formx.model.firebase.validation;

import java.util.List;

public class ValidationResult {


    private List<Integer> sectionWithError;


    private int numError;


    private boolean preventSending;

    public boolean hasError() {
        return sectionWithError != null && !sectionWithError.isEmpty();
    }
}
