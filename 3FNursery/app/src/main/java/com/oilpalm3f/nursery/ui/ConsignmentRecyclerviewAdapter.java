package com.oilpalm3f.nursery.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.dbmodels.ConsignmentData;
import com.oilpalm3f.nursery.dbmodels.NurseryData;

import java.util.ArrayList;
import java.util.List;

public class ConsignmentRecyclerviewAdapter extends RecyclerView.Adapter<ConsignmentRecyclerviewAdapter.ViewHolder>  {

    public Context context;

    List<ConsignmentData> consignmentList = new ArrayList<>();

    public ConsignmentRecyclerviewAdapter(Context context,  List<ConsignmentData> consignmentList) {
        this.context = context;
        this.consignmentList = consignmentList;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.consignmentlayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull ConsignmentRecyclerviewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
