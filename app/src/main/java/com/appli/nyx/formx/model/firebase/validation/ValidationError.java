package com.appli.nyx.formx.model.firebase.validation;

import java.util.LinkedHashSet;
import java.util.Set;

public class ValidationError {

    private Set<String> errorMessages;
    /**
     * Messages d'alerte.
     */
    private Set<String> warningMessages;

    public boolean hasError() {
        return this.errorMessages != null && !this.errorMessages.isEmpty();
    }

    public boolean hasWarning() {
        return this.warningMessages != null && !this.warningMessages.isEmpty();
    }

    public boolean isEmpty() {
        return !hasError() && !hasWarning();
    }

    public void addErrorMessage(String errorMessage) {
        if (this.errorMessages == null) {
            this.errorMessages = new LinkedHashSet<>();
        }

        this.errorMessages.add(errorMessage);
    }

    public void addWarningMessage(String warningMessage) {
        if (this.warningMessages == null) {
            this.warningMessages = new LinkedHashSet<>();
        }

        this.warningMessages.add(warningMessage);
    }

    public Set<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Set<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Set<String> getWarningMessages() {
        return warningMessages;
    }

    public void setWarningMessages(Set<String> warningMessages) {
        this.warningMessages = warningMessages;
    }
}
