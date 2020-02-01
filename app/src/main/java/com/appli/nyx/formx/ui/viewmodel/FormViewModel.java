package com.appli.nyx.formx.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.appli.nyx.formx.model.firebase.Form;
import com.appli.nyx.formx.model.firebase.Section;
import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;
import com.appli.nyx.formx.model.firebase.fields.BooleanQuestion;
import com.appli.nyx.formx.model.firebase.fields.DateQuestion;
import com.appli.nyx.formx.model.firebase.fields.NumberQuestion;
import com.appli.nyx.formx.model.firebase.fields.SpinnerQuestion;
import com.appli.nyx.formx.model.firebase.fields.TextQuestion;
import com.appli.nyx.formx.model.firebase.fields.TimeQuestion;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class FormViewModel extends AndroidViewModel {

    private MutableLiveData<Form> formMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> questionCreationMode = new MutableLiveData<>();
    private MutableLiveData<Section> sectionMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<AbstractQuestion> questionMutableLiveData = new MutableLiveData<>();

    @Inject
    public FormViewModel(@NonNull Application application) {
        super(application);
    }


    public void setForm(Form form) {
        formMutableLiveData.setValue(form);
    }

    public void setSection(Section section) {
        sectionMutableLiveData.setValue(section);
    }

    public LiveData<List<AbstractQuestion>> loadQuestionBySection() {
        List<AbstractQuestion> questions= Arrays.asList(
                new TextQuestion("TextQuestion"),
                new BooleanQuestion("BooleanQuestion"),
                new DateQuestion("DateQuestion"),
                new TimeQuestion("TimeQueston"),
                new SpinnerQuestion("SpinnerQuestion"),
                new NumberQuestion("NumberQuestion")
        );
        return  new MutableLiveData<>(questions);
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
