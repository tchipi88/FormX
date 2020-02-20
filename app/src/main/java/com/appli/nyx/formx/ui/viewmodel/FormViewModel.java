package com.appli.nyx.formx.ui.viewmodel;

import android.app.Application;

import com.appli.nyx.formx.model.firebase.Form;
import com.appli.nyx.formx.model.firebase.Section;
import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class FormViewModel extends AndroidViewModel {

    private MutableLiveData<Form> formMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> questionCreationMode = new MutableLiveData<>();
    private MutableLiveData<Section> sectionMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<AbstractQuestion> questionMutableLiveData = new MutableLiveData<>();

	private MutableLiveData<List<AbstractQuestion>> questionsListMutableLiveData = new MutableLiveData<>();
	private MutableLiveData<List<Section>> sectionsListMutableLiveData = new MutableLiveData<>();

	//Form View
	private MutableLiveData<Integer> sectionViewIndex = new MutableLiveData<>();

    @Inject
    public FormViewModel(@NonNull Application application) {
        super(application);
		sectionViewIndex.setValue(0);
		questionsListMutableLiveData.setValue(new ArrayList<>());
		sectionsListMutableLiveData.setValue(new ArrayList<>());
    }

	public LiveData<Integer> getsectionViewIndex() {
		return sectionViewIndex;
	}

	public void addQuestion(AbstractQuestion question) {
		questionsListMutableLiveData.getValue().add(question);
	}

	public void clearQuestionList() {
		questionsListMutableLiveData.getValue().clear();
	}

	public MutableLiveData<List<AbstractQuestion>> getQuestionsListMutableLiveData() {
		return questionsListMutableLiveData;
	}

	public void addSection(Section section) {
		sectionsListMutableLiveData.getValue().add(section);
	}

	public void clearSectionList() {
		sectionsListMutableLiveData.getValue().clear();
	}

	public MutableLiveData<List<Section>> getSectionsListMutableLiveData() {
		return sectionsListMutableLiveData;
	}


	public void setsectionViewIndex() {
		sectionViewIndex.setValue(sectionViewIndex.getValue() + 1);
	}

	public void setsectionViewIndex(int questionIndex) {
		this.sectionViewIndex.setValue(questionIndex);
	}

	public void setForm(Form form) {
        formMutableLiveData.setValue(form);
    }

    public void setSection(Section section) {
        sectionMutableLiveData.setValue(section);
    }


    public void setQuestion(AbstractQuestion field) {
        questionMutableLiveData.setValue(field);
    }

	public MutableLiveData<Form> getFormMutableLiveData() {
		return formMutableLiveData;
	}

	public MutableLiveData<Section> getSectionMutableLiveData() {
		return sectionMutableLiveData;
	}

	public MutableLiveData<AbstractQuestion> getQuestionMutableLiveData() {
		return questionMutableLiveData;
	}

    public MutableLiveData<Boolean> getQuestionCreationMode() {
        return questionCreationMode;
    }

    public void setQuestionCreationMode(Boolean value) {
        this.questionCreationMode.setValue(value);
    }
}
