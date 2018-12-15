package com.hyc.newsmallexcellent.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.widget.TextView;

import com.hyc.newsmallexcellent.R;

import java.util.ArrayList;
import java.util.List;

public class JobsClassificationAdapter extends RecyclerView.Adapter<JobsClassificationAdapter.ViewHolder> {

    private Context content;
    private List<String> stringList;

    public JobsClassificationAdapter(Context mContent , List<String> list) {
        this.content = mContent;
        this.stringList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(content).
                inflate(R.layout.item_classification,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.classification_id.setText(stringList.get(position));
        holder.classification_id.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public void appendDataToList(String data) {
        if (stringList == null) {
            stringList = new ArrayList<>();
        }
        stringList.add(data);
        notifyItemInserted(getItemCount() - 1);
    }

    public void removeData(int position) {
        stringList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView classification_id;

        public ViewHolder(View itemView) {
            super(itemView);
            classification_id = (TextView) itemView.findViewById(R.id.classification_id);
        }
    }
}
