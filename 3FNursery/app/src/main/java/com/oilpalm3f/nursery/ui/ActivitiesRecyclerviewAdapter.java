package com.oilpalm3f.nursery.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;

import java.util.List;

public class ActivitiesRecyclerviewAdapter extends RecyclerView.Adapter {


    public ActivitiesRecyclerviewAdapter(Activities activities, List<NurseryAcitivity> mActivitiesList) {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
