package com.appli.nyx.formx.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.appli.nyx.formx.model.firebase.Form;
import com.appli.nyx.formx.model.firebase.Section;
import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;

import java.util.List;

import javax.inject.Inject;

public class FormViewModel extends AndroidViewModel {

    private MutableLiveData<Form> formMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Section> sectionMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<AbstractQuestion> questionMutableLiveData = new MutableLiveData<>();

    @Inject
    public FormViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Form>> loadForm() {
        return  null;
    }

    public void setForm(Form form) {
        formMutableLiveData.setValue(form);
    }

    public LiveData<List<Section>> loadSectionByForm() {
        return null;
    }

    public void setSection(Section section) {
        sectionMutableLiveData.setValue(section);
    }

    public LiveData<List<AbstractQuestion>> loadQuestionBySection() {
        return  null;
    }

    public void setQuestion(AbstractQuestion field) {
        questionMutableLiveData.setValue(field);
    }
}
