package com.oilpalm3f.nursery.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;

import java.util.List;

public class ActivitiesRecyclerviewAdapter extends RecyclerView.Adapter<ActivitiesRecyclerviewAdapter.ViewHolder> {

    Context context;
    List<NurseryAcitivity> mActivitiesList;


    public ActivitiesRecyclerviewAdapter(Context context, List<NurseryAcitivity> mActivitiesList) {
        this.context = context;
        this.mActivitiesList = mActivitiesList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.activitieslayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.activityName = mActivitiesList.get(position).getName();

    }

    @Override
    public int getItemCount() {
        return mActivitiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView activityName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.activityName = (TextView )itemView.findViewById(R.id.activityName);
        }
    }
}
