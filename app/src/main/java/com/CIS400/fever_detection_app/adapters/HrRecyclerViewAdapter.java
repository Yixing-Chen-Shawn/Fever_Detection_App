package com.CIS400.fever_detection_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CIS400.fever_detection_app.R;

import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.util.List;

public class HrRecyclerViewAdapter extends RecyclerView.Adapter<HrRecyclerViewAdapter.ViewHolder> {
    private List<List<String>> values;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDate;
        public TextView txtHeartRate;
        public TextView txtContacts;
        public TextView txtBodyTemp;
        public TextView txtBlood;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtDate = (TextView) v.findViewById(R.id.firstLine);
            txtHeartRate = (TextView) v.findViewById(R.id.secondLine);
            txtContacts = (TextView) v.findViewById(R.id.thirdLine);
            txtBodyTemp = (TextView) v.findViewById(R.id.fourthLine);
            txtBlood = (TextView) v.findViewById(R.id.fifthLine);
        }
    }

    public void add(int position, List<String> item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HrRecyclerViewAdapter(List<List<String>> myDataset) {
        values = myDataset;
    }

    @Override
    public HrRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.activity_heart_rate_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final List<String> value = values.get(position);
        holder.txtDate.setText("Date: " + value.get(0));
        holder.txtHeartRate.setText("Heart Rate: " + value.get(1));
        holder.txtContacts.setText("Daily Contacts: " + value.get(2));
        holder.txtBodyTemp.setText("Body Temperature: " + value.get(3));
        holder.txtBlood.setText("Blood Pressure: " + value.get(4));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
