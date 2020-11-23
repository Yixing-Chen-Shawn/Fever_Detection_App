package com.CIS400.fever_detection_app.adapters;

import android.graphics.Typeface;
import android.graphics.fonts.FontStyle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CIS400.fever_detection_app.R;

import java.lang.reflect.Type;
import java.util.List;

public class SymptomRecyclerViewAdapter extends RecyclerView.Adapter<SymptomRecyclerViewAdapter.ViewHolder> {
    private List<List<String>> values;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtDate;
        public TextView txtRating;
        public TextView txtDescription;
        private View layout;

        public ViewHolder(View v){
            super(v);
            layout = v;
            txtDate = (TextView) v.findViewById(R.id.firstLine_symptom);
            txtRating = (TextView) v.findViewById(R.id.secondLine_symptom);
            txtDescription = (TextView) v.findViewById(R.id.thirdLine_symptom);
        }
    }

    public void add(int position, List<String> item){
        values.add(position, item);
        notifyItemChanged(position);
    }

    public void remove(int position){
        values.remove(position);
        notifyItemRemoved(position);
    }

    public SymptomRecyclerViewAdapter(List<List<String>> myDataSet){
        values = myDataSet;
    }

    @NonNull
    @Override
    public SymptomRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.activity_symptom_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomRecyclerViewAdapter.ViewHolder holder, int position) {
        final List<String> value = values.get(position);
        holder.txtDate.setText("Date: " + value.get(0));
        holder.txtRating.setText("Rating: " + value.get(1));
        holder.txtDescription.setText("Description: " + value.get(2));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
