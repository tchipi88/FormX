package com.appli.nyx.formx.ui.fragment.business.form;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.EnqueteForm;
import com.appli.nyx.formx.model.firebase.Form;
import com.appli.nyx.formx.ui.MainActivity;
import com.appli.nyx.formx.ui.adapter.ActionModeController;
import com.appli.nyx.formx.ui.adapter.multiselection.MyKeyProvider;
import com.appli.nyx.formx.ui.adapter.multiselection.MyLookup;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.SelectFormViewHolder;
import com.appli.nyx.formx.ui.viewmodel.SelectFormViewModel;
import com.appli.nyx.formx.utils.SessionUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.widget.LinearLayout.VERTICAL;
import static com.appli.nyx.formx.utils.MyConstant.AUTHOR_ID;
import static com.appli.nyx.formx.utils.MyConstant.FORM_PATH;

public class SelectFormFragment extends ViewModelFragment<SelectFormViewModel> {

    private static final String TAG = "SelectFormFragment";

    FormFirebaseAdapter adapter;
    private ShimmerRecyclerViewX recyclerView;
    private View emptyView;

    SelectionTracker selectionTracker;
    MenuItem selectedFormCount;
    MenuItem action_clear;
    MenuItem action_select;

    private ActionMode actionMode;

    @Override
    protected Class<SelectFormViewModel> getViewModel() {
        return SelectFormViewModel.class;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_form_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        ((MainActivity) requireActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.select_form));

        recyclerView = view.findViewById(R.id.forms);
        emptyView = view.findViewById(R.id.emptyView);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));

        view.findViewById(R.id.add_form).setVisibility(View.GONE);


        // Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance().collection(FORM_PATH).whereEqualTo(AUTHOR_ID, SessionUtils.getUserUid()).orderBy("libelle");

        FirestoreRecyclerOptions<Form> options = new FirestoreRecyclerOptions.Builder<Form>().setQuery(query, snapshot -> {
            Form form = snapshot.toObject(Form.class);
            form.setId(snapshot.getId());
            return form;
        }).build();
        adapter = new FormFirebaseAdapter(options);

        recyclerView.setAdapter(adapter);
        recyclerView.showShimmerAdapter();

        selectionTracker = new SelectionTracker.Builder<>("my-form-id",
                recyclerView,
                new MyKeyProvider(recyclerView),
                new MyLookup(recyclerView),
                StorageStrategy.createLongStorage())
                .withOnItemActivatedListener((item, e) -> true).withOnDragInitiatedListener(e -> {
                    Log.d(TAG, "onDragInitiated");
                    return true;
                }).build();

        if (savedInstanceState != null) {
            selectionTracker.onRestoreInstanceState(savedInstanceState);
        }

        adapter.setSelectionTracker(selectionTracker);

        selectionTracker.addObserver(new SelectionTracker.SelectionObserver<Long>() {

            @Override
            public void onItemStateChanged(@NonNull Long key, boolean selected) {
                super.onItemStateChanged(key, selected);
            }

            @Override
            public void onSelectionRefresh() {
                super.onSelectionRefresh();
            }

            @Override
            public void onSelectionChanged() {
                super.onSelectionChanged();
                if (selectionTracker.hasSelection() && actionMode == null) {
                    actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new ActionModeController(getContext(), selectionTracker));
                    setMenuItemTitle(selectionTracker.getSelection().size());
                } else if (!selectionTracker.hasSelection() && actionMode != null) {
                    actionMode.finish();
                    actionMode = null;
                } else {
                    setMenuItemTitle(selectionTracker.getSelection().size());
                }
            }

            @Override
            public void onSelectionRestored() {
                super.onSelectionRestored();
            }
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

    public void setMenuItemTitle(int selectedItemSize) {
        selectedFormCount.setTitle("" + selectedItemSize);

        selectedFormCount.setVisible(selectedItemSize != 0);
        action_clear.setVisible(selectedItemSize != 0);
        action_select.setVisible(selectedItemSize != 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                selectionTracker.clearSelection();
                return true;
            case R.id.action_select:
                Iterator<Long> selectedIds = selectionTracker.getSelection().iterator();
                List<EnqueteForm> result = new ArrayList<>();

                while (selectedIds.hasNext()) {
                    Form form = adapter.getItem(selectedIds.next().intValue());
                    result.add(new EnqueteForm(form.getId(), form.getLibelle(), form.getDescription()));
                }
                viewModel.setForms(result);
                NavHostFragment.findNavController(SelectFormFragment.this).navigateUp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.form_select, menu);
        selectedFormCount = menu.findItem(R.id.action_form_count);
        action_clear = menu.findItem(R.id.action_clear);
        action_select = menu.findItem(R.id.action_select);
    }

    public class FormFirebaseAdapter extends FirestoreRecyclerAdapter<Form, SelectFormViewHolder> {

        private SelectionTracker selectionTracker;


        public FormFirebaseAdapter(@NonNull FirestoreRecyclerOptions<Form> options) {
            super(options);
            setHasStableIds(true);
        }

        public SelectionTracker getSelectionTracker() {
            return selectionTracker;
        }

        public void setSelectionTracker(SelectionTracker selectionTracker) {
            this.selectionTracker = selectionTracker;
        }

        @Override
        protected void onBindViewHolder(@NonNull SelectFormViewHolder holder, int position, @NonNull Form form) {
            holder.mLibelleView.setText(form.getLibelle());
            holder.mDescriptionView.setText(form.getDescription());
            holder.mView.setActivated(selectionTracker.isSelected(position));
        }

        @NonNull
        @Override
        public SelectFormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_form_select, parent, false);
            return new SelectFormViewHolder(view);
        }

        @Override
        public void onDataChanged() {
            recyclerView.hideShimmerAdapter();
            if (getItemCount() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }
}
