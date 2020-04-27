package com.appli.nyx.formx.ui.fragment.business;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.User;
import com.appli.nyx.formx.ui.adapter.ActionModeController;
import com.appli.nyx.formx.ui.adapter.multiselection.MyKeyProvider;
import com.appli.nyx.formx.ui.adapter.multiselection.MyLookup;
import com.appli.nyx.formx.ui.adapter.multiselection.UserDetails;
import com.appli.nyx.formx.ui.adapter.multiselection.ViewHolderWithDetails;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.appli.nyx.formx.utils.AlertDialogUtils;
import com.appli.nyx.formx.utils.ImageUtils;
import com.appli.nyx.formx.utils.MyConstant;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindDrawable;

import static android.widget.LinearLayout.VERTICAL;
import static com.appli.nyx.formx.utils.MyConstant.ENQUETE_PATH;
import static com.appli.nyx.formx.utils.MyConstant.SHARE_USER_ID;

public class SelectUserFragment extends ViewModelFragment<EnqueteViewModel> implements SearchView.OnQueryTextListener {

    private static final String TAG = "SelectUserFragment";

    UserFirebaseAdapter adapter;
    ShimmerRecyclerViewX recyclerView;
    View emptyView;



    @BindDrawable(R.drawable.ic_account_circle_black_24dp)
    Drawable ic_account_circle_black_24dp;

    StorageReference storageRef;

    SelectionTracker selectionTracker;
    MenuItem selectedUserCount;
    MenuItem action_clear;
    MenuItem action_share;

    private ActionMode actionMode;

    @Override
    protected Class<EnqueteViewModel> getViewModel() {
        return EnqueteViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        storageRef = firebaseStorage.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.users);
        emptyView = view.findViewById(R.id.emptyView);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));



        // Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance().collection(MyConstant.USER_PATH).orderBy("name");

        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>().setQuery(query, snapshot -> {
            User user = snapshot.toObject(User.class);
            user.setId(snapshot.getId());
            return user;
        }).build();

        adapter = new UserFirebaseAdapter(options);
        recyclerView.setAdapter(adapter);
        recyclerView.showShimmerAdapter();

        selectionTracker = new SelectionTracker.Builder<>("my-user-id",
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
        selectedUserCount.setTitle("" + selectedItemSize);

        selectedUserCount.setVisible(selectedItemSize != 0);
        action_clear.setVisible(selectedItemSize != 0);
        action_share.setVisible(selectedItemSize != 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                selectionTracker.clearSelection();
                return true;
            case R.id.action_share:
                Iterator<Long> selectedIds = selectionTracker.getSelection().iterator();
                List<User> result = new ArrayList<>();
                while (selectedIds.hasNext()) {
                    User user = adapter.getItem(selectedIds.next().intValue());
                    result.add(user);
                }
                FirebaseFirestore.getInstance().collection(ENQUETE_PATH)
                        .document(viewModel.getEnqueteMutableLiveData().getValue().getId()).update(SHARE_USER_ID, FieldValue.arrayUnion(result))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                //TODO sent notification

                                Toast.makeText(getContext(), R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
                                NavHostFragment.findNavController(SelectUserFragment.this).navigateUp();
                            } else {
                                AlertDialogUtils.showErrorDialog(getContext(), task.getException().getMessage());
                            }
                        });

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.user_list, menu);
        selectedUserCount = menu.findItem(R.id.action_user_count);
        action_clear = menu.findItem(R.id.action_clear);
        action_share = menu.findItem(R.id.action_share);
    }


    public class UserFirebaseAdapter extends FirestoreRecyclerAdapter<User, UserFirebaseAdapter.UserViewHolder> {

        private SelectionTracker selectionTracker;

        /**
         * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
         *
         * @param options
         */
        public UserFirebaseAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
            super(options);
            setHasStableIds(true);
        }

        public SelectionTracker getSelectionTracker() {
            return selectionTracker;
        }

        public void setSelectionTracker(SelectionTracker selectionTracker) {
            this.selectionTracker = selectionTracker;
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_user, parent, false);
            return new UserViewHolder(view);
        }

        @Override
        protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User user) {
            holder.user_name.setText(user.name);
            holder.user_firstname.setText(user.firstName);

            ImageUtils.displayRoundImageFromStorageReference(getContext(), storageRef.child(user.getId()), "profil_photo.jpg", holder.user_img, ic_account_circle_black_24dp);

            holder.mView.setActivated(selectionTracker.isSelected(position));
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


        public class UserViewHolder extends RecyclerView.ViewHolder implements ViewHolderWithDetails {

            public final View mView;
            public TextView user_name;
            public TextView user_firstname;
            public AppCompatImageView user_img;


            public UserViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                user_firstname = itemView.findViewById(R.id.user_firstname);
                user_name = itemView.findViewById(R.id.user_name);
                user_img = itemView.findViewById(R.id.user_img);

            }

            @Override
            public String toString() {
                return super.toString() + " '" + user_name.getText() + "'";
            }


            @Override
            public ItemDetailsLookup.ItemDetails getItemDetails() {
                return new UserDetails(getAdapterPosition(), getItemId());
            }
        }
    }
}
