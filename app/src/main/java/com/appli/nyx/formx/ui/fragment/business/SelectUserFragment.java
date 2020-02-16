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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.User;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.viewholder.UserViewHolder;
import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.appli.nyx.formx.utils.ImageUtils;
import com.appli.nyx.formx.utils.MyConstant;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;

import butterknife.BindDrawable;

public class SelectUserFragment extends ViewModelFragment<EnqueteViewModel> implements SearchView.OnQueryTextListener {

    FirestorePagingAdapter adapter;
    RecyclerView recyclerView;
    View emptyView;
    SwipeRefreshLayout mSwipeRefreshLayout;


    PagedList.Config config;

    @BindDrawable(R.drawable.ic_account_circle_black_24dp)
    Drawable ic_account_circle_black_24dp;

    StorageReference storageRef;

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

        // This configuration comes from the Paging Support Library
        // https://developer.android.com/reference/android/arch/paging/PagedList.Config.html
        config = new PagedList.Config.Builder().setEnablePlaceholders(false).setPrefetchDistance(10).setPageSize(20).build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.users);
        emptyView = view.findViewById(R.id.emptyView);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Refresh Action on Swipe Refresh Layout
        mSwipeRefreshLayout.setOnRefreshListener(() -> adapter.refresh());
        // Create the query and the FirestoreRecyclerOptions
        Query query = FirebaseFirestore.getInstance().collection(MyConstant.USER_PATH).orderBy("name");

        FirestorePagingOptions<User> options = new FirestorePagingOptions.Builder<User>().setQuery(query, config, snapshot -> {
            User user = snapshot.toObject(User.class);
            user.setId(snapshot.getId());
            return user;
        }).build();

        adapter = new FirestorePagingAdapter<User, UserViewHolder>(options) {

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_user, parent, false);
                return new UserViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {
                holder.user_name.setText(model.name);
                holder.user_firstname.setText(model.firstName);

                ImageUtils.displayRoundImageFromStorageReference(getContext(), storageRef.child(model.getId()).child("profil_photo.jpg"),
                        holder.user_img, ic_account_circle_black_24dp);

                holder.mView.setOnClickListener(v -> {

                });
            }

            @Override
            protected void onError(@NonNull Exception e) {
                super.onError(e);
                Log.e(SelectUserFragment.class.getSimpleName(), e.getMessage());
            }

            @Override
            protected void onLoadingStateChanged(@NonNull LoadingState state) {
                switch (state) {
                    case LOADING_INITIAL:
                        // The initial load has begun
                    case LOADING_MORE:
                        // The adapter has started to load an additional page
                        // ...
                        mSwipeRefreshLayout.setRefreshing(true);
                        break;
                    case LOADED:

                    case FINISHED:
                        // The previous load (either initial or additional) completed
                        // ...
                        mSwipeRefreshLayout.setRefreshing(false);
                        break;
                    case ERROR:
                        // The previous load (either initial or additional) failed. Call
                        // the retry() method in order to retry the load operation.
                        // ...

                        Toast.makeText(getContext(), "Error Occurred!", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                        break;
                }

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
        MenuItem searchViewItem = menu.findItem(R.id.action_search);

        final SearchView searchViewAndroidActionBar = (SearchView) searchViewItem.getActionView();
        searchViewAndroidActionBar.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);

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
