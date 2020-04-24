package com.appli.nyx.formx.ui.fragment.business.enquete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.navigation.fragment.NavHostFragment;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Enquete;
import com.appli.nyx.formx.model.firebase.EnqueteAnswer;
import com.appli.nyx.formx.model.firebase.EnqueteAnswerSection;
import com.appli.nyx.formx.model.firebase.Section;
import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;
import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;
import com.appli.nyx.formx.model.firebase.fields.BooleanQuestion;
import com.appli.nyx.formx.model.firebase.fields.DateQuestion;
import com.appli.nyx.formx.model.firebase.fields.NumberQuestion;
import com.appli.nyx.formx.model.firebase.fields.SpinnerQuestion;
import com.appli.nyx.formx.model.firebase.fields.TextQuestion;
import com.appli.nyx.formx.model.firebase.fields.TimeQuestion;
import com.appli.nyx.formx.ui.MainActivity;
import com.appli.nyx.formx.ui.fields.FieldsGenerator;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.appli.nyx.formx.ui.fields.FieldsGenerator.generateLayoutField;
import static com.appli.nyx.formx.utils.MyConstant.DATE_CREATED;
import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_ANSWER;
import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PATH;
import static com.appli.nyx.formx.utils.MyConstant.FIELDS_PATH;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;
import static com.appli.nyx.formx.utils.MyConstant.SECTION_PATH;

public class EnqueteReplyFragment extends ViewModelFragment<EnqueteViewModel> {

    @BindView(R.id.fields_container)
    LinearLayout fieldsContainer;

    @BindView(R.id.btn_next)
    MaterialButton btn_next;

    List<Section> sections = new ArrayList<>();

    Enquete enquete;

    EnqueteAnswer enqueteAnswer;

    @Override
    protected Class<EnqueteViewModel> getViewModel() {
        return EnqueteViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_enquete_reply;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        enquete = viewModel.getEnqueteMutableLiveData().getValue();

        enqueteAnswer = new EnqueteAnswer();

        CollectionReference answerRef = FirebaseFirestore.getInstance().collection(ENQUETE_PATH)
                .document(enquete.getId()).collection(ENQUETE_ANSWER);

        ((MainActivity) requireActivity()).getSupportActionBar().setTitle(R.string.enquetes + ": " + enquete.getLibelle());

        //get all sections
        FirebaseFirestore.getInstance().collection(FORM_PATH)
                .document(enquete.getFormId())
                .collection(SECTION_PATH).orderBy(DATE_CREATED).get().addOnCompleteListener(sectiontask -> {
            if (sectiontask.isSuccessful()) {

                for (DocumentSnapshot sectionSnapshot : sectiontask.getResult().getDocuments()) {

                    Section section = new Section();
                    section.setId(sectionSnapshot.getId());
                    section.libelle = (String) sectionSnapshot.get("libelle");
                    section.description = (String) sectionSnapshot.get("description");

                    sections.add(section);
                }

                if (!sections.isEmpty()) {
                    generateLayoutSection(sections.get(viewModel.getsectionViewIndex().getValue()));
                    rootView.findViewById(R.id.no_section).setVisibility(View.GONE);
                } else {
                    btn_next.setVisibility(View.GONE);
                }


            }
        });

        btn_next.setOnClickListener(view1 -> {
            if (viewModel.getsectionViewIndex().getValue() + 1 < sections.size()) {

                //validate questions of section
                if (validateSection(sections.get(viewModel.getsectionViewIndex().getValue()))) {
                    saveSection(sections.get(viewModel.getsectionViewIndex().getValue()));
                    generateLayoutSection(sections.get(viewModel.getsectionViewIndex().getValue() + 1));
                    viewModel.setsectionViewIndex();
                }
                ;
            } else {
                if (validateSection(sections.get(viewModel.getsectionViewIndex().getValue()))) {
                    saveSection(sections.get(viewModel.getsectionViewIndex().getValue()));

                    answerRef.add(enqueteAnswer).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            new MaterialAlertDialogBuilder(getContext()).setIcon(R.drawable.ic_info_black_24dp)
                                    .setTitle("INFO").setMessage(getString(R.string.thanks_for_your_reply)).setPositiveButton("OK", (dialog, which) -> {
                                NavHostFragment.findNavController(EnqueteReplyFragment.this).navigateUp();
                            }).setCancelable(false).show();
                        } else {
                            AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                        }
                    });

                }

            }
        });

        viewModel.getsectionViewIndex().observe(getViewLifecycleOwner(), index -> {
            if (index + 1 < sections.size()) {
                btn_next.setText(R.string.finish);
            }
        });

        return rootView;
    }

    private boolean validateSection(Section section) {
        boolean valid = true;

        for (AbstractQuestion question : viewModel.getQuestionsListMutableLiveData().getValue()) {
            if (!FieldsGenerator.validate(getContext(), question)) {
                valid = false;
            }
        }
        return valid;
    }

    private void saveSection(Section section) {
        EnqueteAnswerSection enqueteAnswerSection = new EnqueteAnswerSection();
        enqueteAnswerSection.setLibelle(section.getLibelle());
        enqueteAnswerSection.setDescription(section.getDescription());

        for (AbstractQuestion question : viewModel.getQuestionsListMutableLiveData().getValue()) {
            question.setValue(FieldsGenerator.getValue(getContext(), question));
            enqueteAnswerSection.addQuestion(question);
        }
        enqueteAnswer.addSection(enqueteAnswerSection);
    }

    private void generateLayoutSection(Section section) {
        viewModel.clearQuestionList();
        fieldsContainer.removeAllViews();
        //get all fields for section
        FirebaseFirestore.getInstance().collection(FORM_PATH)
                .document(viewModel.getEnqueteMutableLiveData().getValue().getFormId())
                .collection(SECTION_PATH).document(section.getId())
                .collection(FIELDS_PATH).orderBy(DATE_CREATED).get().addOnCompleteListener(fieldsTask -> {
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

                    View fieldView = generateLayoutField(getFragmentManager(), getContext(), question);
                    question.setFieldView(fieldView);
                    fieldsContainer.addView(fieldView);
                    viewModel.addQuestion(question);
                }

            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.setsectionViewIndex(0);
        viewModel.clearQuestionList();
        viewModel.clearSectionList();
    }
}
