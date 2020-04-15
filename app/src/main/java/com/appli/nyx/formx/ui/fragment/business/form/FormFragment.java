package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.appli.nyx.formx.ui.MainActivity;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.SectionViewHolder;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

import static com.appli.nyx.formx.utils.MyConstant.FIELDS_PATH;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;
import static com.appli.nyx.formx.utils.MyConstant.SECTION_PATH;

public class FormFragment extends ViewModelFragment<FormViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_form;
    }


    FirestoreRecyclerAdapter adapter;
    private ShimmerRecyclerViewX recyclerView;
    private View emptyView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.form, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit:
                NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_global_formEditDialog);
                return true;
            case R.id.action_visibility:
                NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_global_formViewFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Class<FormViewModel> getViewModel() {
        return FormViewModel.class;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.sections);
        emptyView = view.findViewById(R.id.emptyView);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.showShimmerAdapter();


        // Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance().collection(FORM_PATH)
                .document(viewModel.getFormMutableLiveData().getValue().getId())
                .collection(SECTION_PATH);

        FirestoreRecyclerOptions<Section> options = new FirestoreRecyclerOptions.Builder<Section>().setQuery(query, snapshot -> {
            Section section = snapshot.toObject(Section.class);
            section.setId(snapshot.getId());
            return section;
        }).build();

        adapter = new FirestoreRecyclerAdapter<Section, SectionViewHolder>(options) {

            @NonNull
            @Override
            public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_section, parent, false);
                return new SectionViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull SectionViewHolder holder, int position, @NonNull Section model) {
                holder.mItem = getItem(position);
                holder.mLibelleView.setText(model.getLibelle());

                CollectionReference sectionCollectionRef = FirebaseFirestore.getInstance().collection(FORM_PATH)
                        .document(viewModel.getFormMutableLiveData().getValue().getId()).collection(SECTION_PATH);

                holder.mView.setOnClickListener(v -> {
                    viewModel.setSection(holder.mItem);
                    NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_formFragment_to_sectionFragment);

                });


                holder.delete.setOnClickListener(v -> {
                    AlertDialogUtils.showConfirmDeleteDialog(getContext(), (dialog, which) -> {
                        sectionCollectionRef.document(holder.mItem.getId()).delete().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
                            } else {
                                AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                            }
                        });
                    });
                });

                holder.duplicate.setOnClickListener(v -> {
                    Section duplicatedSection = new Section();
                    duplicatedSection.setLibelle(holder.mItem.getLibelle() + "__Copy");
                    duplicatedSection.setDescription(holder.mItem.getDescription());
                    sectionCollectionRef.add(duplicatedSection).addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                        } else {
                            //Ajout des champs
                            sectionCollectionRef
                                    .document(holder.mItem.getId())
                                    .collection(FIELDS_PATH).get().addOnCompleteListener(fieldstask -> {
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

                                        sectionCollectionRef.document(task.getResult().getId()).collection(FIELDS_PATH).add(question).addOnCompleteListener(null);
                                    }
                                }

                            });

                        }
                    });

                });

                holder.edit.setOnClickListener(v -> {
                    viewModel.setSection(holder.mItem);
                    NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_formFragment_to_sectionEditDialog);
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

        viewModel.getFormMutableLiveData().observe(getViewLifecycleOwner(), form -> {
            ((MainActivity) requireActivity()).getSupportActionBar().setTitle(form.getLibelle());
        });


        view.findViewById(R.id.add_section).setOnClickListener(v -> {
            NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_formFragment_to_sectionAddDialog);
        });

        view.findViewById(R.id.import_section).setOnClickListener(v -> {
            NavHostFragment.findNavController(FormFragment.this).navigate(R.id.action_formFragment_to_importSectionFragment);
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
