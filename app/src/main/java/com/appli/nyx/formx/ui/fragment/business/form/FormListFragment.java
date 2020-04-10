package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Form;
import com.appli.nyx.formx.model.firebase.Section;
import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;
import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;
import com.appli.nyx.formx.model.firebase.fields.BooleanQuestion;
import com.appli.nyx.formx.model.firebase.fields.DateQuestion;
import com.appli.nyx.formx.model.firebase.fields.NumberQuestion;
import com.appli.nyx.formx.model.firebase.fields.SpinnerQuestion;
import com.appli.nyx.formx.model.firebase.fields.TextQuestion;
import com.appli.nyx.formx.model.firebase.fields.TimeQuestion;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.FormViewHolder;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static com.appli.nyx.formx.utils.MyConstant.AUTHOR_ID;
import static com.appli.nyx.formx.utils.MyConstant.FIELDS_PATH;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;
import static com.appli.nyx.formx.utils.MyConstant.SECTION_PATH;

public class FormListFragment extends ViewModelFragment<FormViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_form_list;
    }

    FirestoreRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyView;

    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.forms);
        emptyView = view.findViewById(R.id.emptyView);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance().collection(FORM_PATH).whereEqualTo(AUTHOR_ID, SessionUtils.getUserUid()).orderBy("libelle");

        FirestoreRecyclerOptions<Form> options = new FirestoreRecyclerOptions.Builder<Form>().setQuery(query, snapshot -> {
            Form form = snapshot.toObject(Form.class);
            form.setId(snapshot.getId());
            return form;
        }).build();
        adapter = new FirestoreRecyclerAdapter<Form, FormViewHolder>(options) {

            @NonNull
            @Override
            public FormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_form, parent, false);
                return new FormViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FormViewHolder holder, int position, @NonNull Form form) {
                holder.mLibelleView.setText(form.getLibelle());
                holder.mDescriptionView.setText(form.getDescription());

                holder.mView.setOnClickListener(v -> {
                    viewModel.setForm(form);
                    NavHostFragment.findNavController(FormListFragment.this).navigate(R.id.action_formListFragment_to_formFragment);
                });

                holder.delete.setOnClickListener(v -> {
                    AlertDialogUtils.showConfirmDeleteDialog(getContext(), (dialog, which) -> {
                        FirebaseFirestore.getInstance().collection(FORM_PATH).document(form.getId()).delete().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
                            } else {
                                AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                            }
                        });
                    });
                });

                holder.voir.setOnClickListener(v -> {
                    viewModel.setForm(form);
                    NavHostFragment.findNavController(FormListFragment.this).navigate(R.id.action_global_formViewFragment);
                });

                holder.edit.setOnClickListener(v -> {
                    viewModel.setForm(form);
                    NavHostFragment.findNavController(FormListFragment.this).navigate(R.id.action_global_formEditDialog);
                });

                holder.duplicate.setOnClickListener(v -> {
                    CollectionReference formCollectionRef = FirebaseFirestore.getInstance().collection(FORM_PATH);

                    Form duplicateform = new Form();
                    duplicateform.setLibelle(form.getLibelle() + "__Copy");
                    duplicateform.setDescription(form.getDescription());

                    formCollectionRef.add(duplicateform).addOnCompleteListener(addformTask -> {
                        if (!addformTask.isSuccessful()) {
                            AlertDialogUtils.showErrorDialog(getContext(), addformTask.getException().getMessage());
                        } else {
                            //Ajout des sections
                            formCollectionRef.document(form.getId()).collection(SECTION_PATH).get().addOnCompleteListener(getOldsectionTask -> {
                                if (!getOldsectionTask.isSuccessful()) {
                                    AlertDialogUtils.showErrorDialog(getContext(), addformTask.getException().getMessage());
                                } else {
                                    for (DocumentSnapshot sectionSnapshot : getOldsectionTask.getResult().getDocuments()) {
                                        Section section = sectionSnapshot.toObject(Section.class);
                                        formCollectionRef.document(addformTask.getResult().getId()).collection(SECTION_PATH).add(section).addOnCompleteListener(addSectionTask -> {
                                            if (!addSectionTask.isSuccessful()) {
                                                AlertDialogUtils.showErrorDialog(getContext(), addformTask.getException().getMessage());
                                            } else {
                                                formCollectionRef.document(form.getId()).collection(SECTION_PATH).document(sectionSnapshot.getId()).collection(FIELDS_PATH).get().addOnCompleteListener(getOldFieldsTask -> {
                                                    if (!getOldFieldsTask.isSuccessful()) {
                                                        AlertDialogUtils.showErrorDialog(getContext(), addformTask.getException().getMessage());
                                                    } else {
                                                        for (DocumentSnapshot fielsSnapshot : getOldFieldsTask.getResult().getDocuments()) {
                                                            AbstractQuestion question = null;
                                                            QuestionType questionType = fielsSnapshot.get("questionType", QuestionType.class);
                                                            switch (questionType) {
                                                                case TIME_PICKER:
                                                                    question = fielsSnapshot.toObject(TimeQuestion.class);
                                                                    break;
                                                                case DATE_PICKER:
                                                                    question = fielsSnapshot.toObject(DateQuestion.class);
                                                                    break;
                                                                case SPINNER:
                                                                    question = fielsSnapshot.toObject(SpinnerQuestion.class);
                                                                    break;
                                                                case BOOLEAN:
                                                                    question = fielsSnapshot.toObject(BooleanQuestion.class);
                                                                    break;
                                                                case NUMBER:
                                                                    question = fielsSnapshot.toObject(NumberQuestion.class);
                                                                    break;
                                                                case TEXT:
                                                                    question = fielsSnapshot.toObject(TextQuestion.class);
                                                                    break;
                                                            }
                                                            formCollectionRef.document(addformTask.getResult().getId()).collection(SECTION_PATH).document(addSectionTask.getResult().getId()).collection(FIELDS_PATH).add(question).addOnCompleteListener(null);
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            });

                        }
                    });
                });
            }

            @Override
            public void onDataChanged() {
                if (getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        };
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.add_form).setOnClickListener(v -> {
            NavHostFragment.findNavController(FormListFragment.this).navigate(R.id.action_formListFragment_to_formAddDialog);
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
