package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Section;
import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;
import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;
import com.appli.nyx.formx.model.firebase.fields.BooleanQuestion;
import com.appli.nyx.formx.model.firebase.fields.DateQuestion;
import com.appli.nyx.formx.model.firebase.fields.NumberQuestion;
import com.appli.nyx.formx.model.firebase.fields.SpinnerQuestion;
import com.appli.nyx.formx.model.firebase.fields.TextQuestion;
import com.appli.nyx.formx.model.firebase.fields.TimeQuestion;
import com.appli.nyx.formx.ui.fragment.BaseDialogFragment;
import com.appli.nyx.formx.ui.viewholder.SimpleViewHolder;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.appli.nyx.formx.ui.viewmodel.SelectFormViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static com.appli.nyx.formx.utils.MyConstant.FIELDS_PATH;
import static com.appli.nyx.formx.utils.MyConstant.FORM_DATA;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;
import static com.appli.nyx.formx.utils.MyConstant.SECTION_PATH;

public class ImportSectionDialog extends BaseDialogFragment<SelectFormViewModel> {

    FirestoreRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyView;

    @Override
    protected Class<SelectFormViewModel> getViewModel() {
        return SelectFormViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.recyclerview_simple;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.items);
        emptyView = view.findViewById(R.id.emptyView);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getDialog().setTitle(getResources().getString(R.string.select_section));

        // Create the query and the FirestoreRecyclerOptions
        //TODO restrict to own sections
        Query query = FirebaseFirestore.getInstance().collectionGroup(SECTION_PATH);

        FirestoreRecyclerOptions<Section> options = new FirestoreRecyclerOptions.Builder<Section>().setQuery(query, snapshot -> {
            Section section = snapshot.toObject(Section.class);
            section.setId(snapshot.getId());
            return section;
        }).build();

        adapter = new FirestoreRecyclerAdapter<Section, SimpleViewHolder>(options) {

            @NonNull
            @Override
            public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_simple, parent, false);
                return new SimpleViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull SimpleViewHolder holder, int position, @NonNull Section model) {
                holder.mLibelleView.setText(model.getLibelle());

                holder.itemView.setOnClickListener(v -> {
                    FormViewModel formViewModel = ViewModelProviders.of(requireActivity()).get(FormViewModel.class);

                    FirebaseFirestore.getInstance()
                            .collection(FORM_PATH)
                            .document(SessionUtils.getUserUid())
                            .collection(FORM_DATA)
                            .document(formViewModel.getFormMutableLiveData().getValue().getId())
                            .collection(SECTION_PATH)
                            .add(model).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            //add fields
                            FirebaseFirestore.getInstance().collection(FORM_PATH)
                                    .document(SessionUtils.getUserUid())
                                    .collection(FORM_DATA)
                                    .document(viewModel.getFormMutableLiveData().getValue().getId())
                                    .collection(SECTION_PATH)
                                    .document(model.getId())
                                    .collection(FIELDS_PATH)
                                    .get()
                                    .addOnCompleteListener(fieldstask -> {
                                        if (fieldstask.isSuccessful()) {
                                            for (DocumentSnapshot snapshot : fieldstask.getResult().getDocuments()) {
                                                AbstractQuestion question = null;
                                                QuestionType questionType = snapshot.get("questionType", QuestionType.class);
                                                switch (questionType) {
                                                    case TIME_PICKER:
                                                        question = snapshot.toObject(TimeQuestion.class);
                                                        break;
                                                    case DATE_PICKER:
                                                        question = snapshot.toObject(DateQuestion.class);
                                                        break;
                                                    case SPINNER:
                                                        question = snapshot.toObject(SpinnerQuestion.class);
                                                        break;
                                                    case BOOLEAN:
                                                        question = snapshot.toObject(BooleanQuestion.class);
                                                        break;
                                                    case NUMBER:
                                                        question = snapshot.toObject(NumberQuestion.class);
                                                        break;
                                                    case TEXT:
                                                        question = snapshot.toObject(TextQuestion.class);
                                                        break;
                                                }


                                                FirebaseFirestore.getInstance()
                                                        .collection(FORM_PATH)
                                                        .document(SessionUtils.getUserUid())
                                                        .collection(FORM_DATA)
                                                        .document(formViewModel.getFormMutableLiveData().getValue().getId())
                                                        .collection(SECTION_PATH)
                                                        .document(task.getResult().getId())
                                                        .collection(FIELDS_PATH)
                                                        .add(question).addOnCompleteListener(task1 -> {

                                                });
                                            }

                                            Toast.makeText(getContext(), R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
                                            NavHostFragment.findNavController(ImportSectionDialog.this).navigateUp();
                                        }
                                    });


                        } else {
                            AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
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