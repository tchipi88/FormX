package com.appli.nyx.formx.ui.adapter;

import android.content.Context;
import android.graphics.Path;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.model.firebase.fields.Option;

import java.util.ArrayList;

public class SpinnerOptionAdapter extends RecyclerView.Adapter<SpinnerOptionAdapter.MyViewHolder> {

    public static ArrayList<Option> options;
    private LayoutInflater inflater;


    public SpinnerOptionAdapter(Context ctx, ArrayList<Option> editModelArrayList) {

        inflater = LayoutInflater.from(ctx);
        this.options = editModelArrayList;
    }

    @Override
    public SpinnerOptionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.viewholder_question_spinner_option, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final SpinnerOptionAdapter.MyViewHolder holder, final int position) {


        holder.editText.setText(options.get(position).getEditTextValue());
        Log.d("print", "yes");

    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public void add() {
        options.add(new Option());
        notifyItemInserted(options.size() - 1);
    }

    public void remove(Option item) {
        int position = options.indexOf(item);
        if (position > -1) {
            options.remove(position);
            notifyItemRemoved(position);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        protected EditText editText;
        protected AppCompatImageView delete;

        public MyViewHolder(View itemView) {
            super(itemView);

            editText = itemView.findViewById(R.id.option);
            delete = itemView.findViewById(R.id.delete);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    options.get(getAdapterPosition()).setEditTextValue(editText.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            delete.setOnClickListener(v -> remove(options.get(getAdapterPosition())));

        }

    }

}
