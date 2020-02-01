package com.appli.nyx.formx.ui.fragment.business.form;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.enumeration.QuestionType;
import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;
import com.appli.nyx.formx.model.firebase.fields.BooleanQuestion;
import com.appli.nyx.formx.model.firebase.fields.DateQuestion;
import com.appli.nyx.formx.model.firebase.fields.NumberQuestion;
import com.appli.nyx.formx.model.firebase.fields.SpinnerQuestion;
import com.appli.nyx.formx.model.firebase.fields.TextQuestion;
import com.appli.nyx.formx.model.firebase.fields.TimeQuestion;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.QuestionViewHolder;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static com.appli.nyx.formx.utils.MyConstant.DATA;
import static com.appli.nyx.formx.utils.MyConstant.FIELDS_PATH;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;
import static com.appli.nyx.formx.utils.MyConstant.SECTION_PATH;

public class SectionFragment extends ViewModelFragment<FormViewModel> {
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_section;
    }

    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }


    FirestoreRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.questions);
        emptyView = view.findViewById(R.id.emptyView);

        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance()
                .collection(FORM_PATH)
                .document(SessionUtils.getUserUid())
                .collection(DATA)
                .document(viewModel.getFormMutableLiveData().getValue().getId())
                .collection(SECTION_PATH)
                .document(viewModel.getSectionMutableLiveData().getValue().getId())
                .collection(FIELDS_PATH);

        FirestoreRecyclerOptions<AbstractQuestion> options = new FirestoreRecyclerOptions.Builder<AbstractQuestion>().setQuery(query, snapshot -> {
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
            if (question != null)
                question.setId(snapshot.getId());
            return question;
        }).build();


        adapter = new FirestoreRecyclerAdapter<AbstractQuestion, QuestionViewHolder>(options) {

            @NonNull
            @Override
            public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.viewholder_question
                                , parent, false);
                return new QuestionViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull QuestionViewHolder holder, int position, @NonNull AbstractQuestion model) {
                holder.mItem = getItem(position);
                holder.mLibelleView.setText(holder.mItem.getLibelle());

                Drawable drawable = getContext().getResources().getDrawable(getQuestionDrawable(holder.mItem.getQuestionType()), null);
                holder.mLibelleView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                holder.mView.setOnClickListener(v -> {
                    viewModel.setQuestion(holder.mItem);
                    viewModel.setQuestionCreationMode(false);
                    switch ((holder.mItem.getQuestionType())) {
                        case TEXT:
                            NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_global_textDialog);
                            break;
                        case NUMBER:
                            NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_global_numberDialog);
                            break;
                        case BOOLEAN:
                            NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_global_booleanDialog);
                            break;
                        case SPINNER:
                            NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_global_spinnerDialog);
                            break;
                        case DATE_PICKER:
                            NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_global_dateDialog);
                            break;
                        case TIME_PICKER:
                            NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_global_timeDialog);
                            break;
                        default:
                    }
                });

                holder.delete.setOnClickListener(v -> {

                });

                holder.duplicate.setOnClickListener(v -> {

                    FirebaseFirestore.getInstance()
                            .collection(FORM_PATH)
                            .document(SessionUtils.getUserUid())
                            .collection(DATA)
                            .document(viewModel.getFormMutableLiveData().getValue().getId())
                            .collection(SECTION_PATH)
                            .document(viewModel.getSectionMutableLiveData().getValue().getId())
                            .collection(FIELDS_PATH)
                            .add(holder.mItem).addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
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

        viewModel.getSectionMutableLiveData().observe(getViewLifecycleOwner(), section -> {
            NavHostFragment.findNavController(SectionFragment.this).getCurrentDestination().setLabel(section.libelle);
        });


        view.findViewById(R.id.add_question).setOnClickListener(v -> {
            NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_sectionFragment_to_questionAddDialog);
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.section, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.action_edit) {
            NavHostFragment.findNavController(SectionFragment.this).navigate(R.id.action_sectionFragment_to_sectionEditDialog);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private int getQuestionDrawable(QuestionType questionType) {
        switch (questionType) {
            case TEXT:
                return R.drawable.ic_short_text_black_24dp;
            case NUMBER:
                return R.drawable.ic_plus_one_black_24dp;
            case BOOLEAN:
                return R.drawable.ic_check_box_black_24dp;
            case SPINNER:
                return R.drawable.ic_arrow_drop_down_circle_black_24dp;
            case DATE_PICKER:
                return R.drawable.ic_event_black_24dp;
            case TIME_PICKER:
                return R.drawable.ic_access_time_black_24dp;
            default:
                return 0;
        }
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
