package com.appli.nyx.formx.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.appli.nyx.formx.model.firebase.Enquete;
import com.appli.nyx.formx.model.firebase.Section;
import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class EnqueteViewModel extends AndroidViewModel {

    private MutableLiveData<Enquete> enqueteMutableLiveData = new MutableLiveData<>();

    //Form View
    private MutableLiveData<Integer> sectionViewIndex = new MutableLiveData<>();
    private MutableLiveData<List<AbstractQuestion>> questionsListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Section>> sectionsListMutableLiveData = new MutableLiveData<>();

    @Inject
    public EnqueteViewModel(@NonNull Application application) {
        super(application);
        sectionViewIndex.setValue(0);
        questionsListMutableLiveData.setValue(new ArrayList<>());
        sectionsListMutableLiveData.setValue(new ArrayList<>());
    }


    public void setEnquete(Enquete enquete) {
        enqueteMutableLiveData.setValue(enquete);
    }

    public MutableLiveData<Enquete> getEnqueteMutableLiveData() {
        return enqueteMutableLiveData;
    }

    public LiveData<Integer> getsectionViewIndex() {
        return sectionViewIndex;
    }

    public void setsectionViewIndex() {
        sectionViewIndex.setValue(sectionViewIndex.getValue() + 1);
    }

    public void setsectionViewIndex(int questionIndex) {
        this.sectionViewIndex.setValue(questionIndex);
    }

    public void clearQuestionList() {
        questionsListMutableLiveData.getValue().clear();
    }

    public MutableLiveData<List<AbstractQuestion>> getQuestionsListMutableLiveData() {
        return questionsListMutableLiveData;
    }

    public void addQuestion(AbstractQuestion question) {
        questionsListMutableLiveData.getValue().add(question);
    }

    public void clearSectionList() {
        sectionsListMutableLiveData.getValue().clear();
    }
}
