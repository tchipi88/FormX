package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;
import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;
import com.appli.nyx.formx.model.firebase.fields.BooleanQuestion;
import com.appli.nyx.formx.model.firebase.fields.DateQuestion;
import com.appli.nyx.formx.model.firebase.fields.NumberQuestion;
import com.appli.nyx.formx.model.firebase.fields.SpinnerQuestion;
import com.appli.nyx.formx.model.firebase.fields.TextQuestion;
import com.appli.nyx.formx.model.firebase.fields.TimeQuestion;
import com.appli.nyx.formx.ui.fields.FieldsGenerator;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.appli.nyx.formx.utils.SessionUtils;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.appli.nyx.formx.utils.MyConstant.DATA;
import static com.appli.nyx.formx.utils.MyConstant.FIELDS_PATH;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;
import static com.appli.nyx.formx.utils.MyConstant.SECTION_PATH;

public class FormViewFragment extends ViewModelFragment<FormViewModel> {
    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_form_view;
    }

    @BindView(R.id.fields_container)
    LinearLayout fieldsContainer;

    @BindView(R.id.btn_next)
    MaterialButton btn_next;

    List<SectionView> sections = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        viewModel.getFormMutableLiveData().observe(getViewLifecycleOwner(), form -> {
            NavHostFragment.findNavController(FormViewFragment.this).getCurrentDestination().setLabel(form.getLibelle());

            //get all sections
            FirebaseFirestore.getInstance().collection(FORM_PATH)
                    .document(SessionUtils.getUserUid())
                    .collection(DATA)
                    .document(form.getId())
                    .collection(SECTION_PATH).get().addOnCompleteListener(sectiontask -> {
                if (sectiontask.isSuccessful()) {

                    for (DocumentSnapshot sectionSnapshot : sectiontask.getResult().getDocuments()) {

                        SectionView sectionView = new SectionView();
                        sectionView.id = sectionSnapshot.getId();
                        sectionView.libelle = (String) sectionSnapshot.get("libelle");
                        sectionView.description = (String) sectionSnapshot.get("description");

                        sections.add(sectionView);
                    }

                    if (!sections.isEmpty())
                        generateLayoutSection(sections.get(0));


                }
            });

            //TODO like quizz
        });


        return view;
    }

    private void generateLayoutSection(SectionView sectionView) {
        //get all fields for section
        FirebaseFirestore.getInstance().collection(FORM_PATH)
                .document(SessionUtils.getUserUid())
                .collection(DATA)
                .document(viewModel.getFormMutableLiveData().getValue().getId())
                .collection(SECTION_PATH)
                .document(sectionView.id)
                .collection(FIELDS_PATH).get().addOnCompleteListener(fieldsTask -> {
            if (fieldsTask.isSuccessful()) {
                for (DocumentSnapshot fieldsSnapshot : fieldsTask.getResult().getDocuments()) {
                    AbstractQuestion question = null;
                    QuestionType questionType = fieldsSnapshot.get("questionType", QuestionType.class);
                    switch (questionType) {
                        case TIME_PICKER:
                            question = fieldsSnapshot.toObject(TimeQuestion.class);
                            break;
                        case DATE_PICKER:
                            question = fieldsSnapshot.toObject(DateQuestion.class);
                            break;
                        case SPINNER:
                            question = fieldsSnapshot.toObject(SpinnerQuestion.class);
                            break;
                        case BOOLEAN:
                            question = fieldsSnapshot.toObject(BooleanQuestion.class);
                            break;
                        case NUMBER:
                            question = fieldsSnapshot.toObject(NumberQuestion.class);
                            break;
                        case TEXT:
                            question = fieldsSnapshot.toObject(TextQuestion.class);
                            break;
                    }

                    sectionView.questions.add(question);
                }

                FieldsGenerator.generateLayoutField(getContext(), fieldsContainer, sectionView.questions);
            }
        });
    }


    private class SectionView implements Serializable {
        public String id;
        public String libelle;
        public String description;

        public List<AbstractQuestion> questions = new ArrayList<>();
    }
}
