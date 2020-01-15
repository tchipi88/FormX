package com.appli.nyx.formx.ui.fragment.business.reports;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.Enquete;
import com.appli.nyx.formx.model.firebase.Report;
import com.appli.nyx.formx.ui.fragment.ViewModelFragment;
import com.appli.nyx.formx.ui.fragment.business.enquete.EnqueteListFragment;
import com.appli.nyx.formx.ui.viewmodel.ReportViewModel;
import com.appli.nyx.formx.ui.viewmodel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;

import static android.widget.LinearLayout.VERTICAL;

public class ReportsListFragment extends ViewModelFragment<ReportViewModel> {


    @BindDrawable(R.drawable.ic_delete_black_24dp)
    Drawable ic_delete;
    SimpleItemRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected Class<ReportViewModel> getViewModel() {
        return ReportViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_report_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        recyclerView = view.findViewById(R.id.reports);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL));

        adapter = new SimpleItemRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);


        viewModel.loadReportByUser().observe(getViewLifecycleOwner(), reports -> {
            adapter.addAll(reports);
        });

        view.findViewById(R.id.add_report).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_reportsListFragment_to_reportsFragment);
        });

        return view;
    }

    private class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

        private final ColorDrawable background;
        SimpleItemRecyclerViewAdapter adapter;

        public SwipeToDeleteCallback(SimpleItemRecyclerViewAdapter adapter) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.adapter = adapter;

            background = new ColorDrawable(Color.RED);
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            adapter.deleteItem(position);
        }


        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            // used for up and down movements
            return false;
        }


        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            View itemView = viewHolder.itemView;
            int backgroundCornerOffset = 20; //so background is behind the rounded corners of itemView

            int iconMargin = (itemView.getHeight() - ic_delete.getIntrinsicHeight()) / 2;
            int iconTop = itemView.getTop() + (itemView.getHeight() - ic_delete.getIntrinsicHeight()) / 2;
            int iconBottom = iconTop + ic_delete.getIntrinsicHeight();

            if (dX > 0) { // Swiping to the right
                int iconLeft = itemView.getLeft() + iconMargin + ic_delete.getIntrinsicWidth();
                int iconRight = itemView.getLeft() + iconMargin;
                ic_delete.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                background.setBounds(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
            } else if (dX < 0) { // Swiping to the left
                int iconLeft = itemView.getRight() - iconMargin - ic_delete.getIntrinsicWidth();
                int iconRight = itemView.getRight() - iconMargin;
                ic_delete.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                        itemView.getTop(), itemView.getRight(), itemView.getBottom());
            } else { // view is unSwiped
                background.setBounds(0, 0, 0, 0);
            }

            background.draw(c);
            ic_delete.draw(c);
        }
    }


    private class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        Context context;
        private List<Report> mValues;

        public SimpleItemRecyclerViewAdapter() {
            mValues = new ArrayList<>();
        }

        @Override
        public SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.viewholder_report
                            , parent, false);
            return new SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mLibelleView.setText(holder.mItem.libelle);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.setReport(holder.mItem);
                    NavHostFragment.findNavController(ReportsListFragment.this).navigate(R.id.action_reportsListFragment_to_reportsFragment);

                }
            });
        }

        public Context getContext() {
            return context;
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public void addAll(List<Report> reports) {
            mValues.addAll(reports);
            notifyDataSetChanged();

        }

        public void deleteItem(int position) {
            mValues.remove(position);
            notifyItemRemoved(position);

        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mLibelleView;

            public Report mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mLibelleView = view.findViewById(R.id.libelle);

            }

            @Override
            public String toString() {
                return super.toString() + " '" + mLibelleView.getText() + "'";
            }
        }
    }
}
