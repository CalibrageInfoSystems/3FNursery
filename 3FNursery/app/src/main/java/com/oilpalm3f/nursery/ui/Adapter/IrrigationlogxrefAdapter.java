package com.oilpalm3f.nursery.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.ConsignmentSelectionScreen;
import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.dbmodels.NurseryData;
import com.oilpalm3f.nursery.dbmodels.NurseryIrrigationLogXref;

import java.util.ArrayList;
import java.util.List;

public class IrrigationlogxrefAdapter extends RecyclerView.Adapter<IrrigationlogxrefAdapter.ViewHolder> {

    public Context context;

    List<NurseryIrrigationLogXref> irrigationxref_list = new ArrayList<>();


    public IrrigationlogxrefAdapter(Context context, List<NurseryIrrigationLogXref> irrigationxref_list) {
        this.context = context;
        this.irrigationxref_list = irrigationxref_list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.irrigationxreflayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.subirrigationcode.setText(irrigationxref_list.get(position).getIrrigationCode());
        holder.consignmentcode.setText(irrigationxref_list.get(position).getConsignmentCode());
        holder.consignmentstatus.setText(irrigationxref_list.get(position).getDesc());






    }

    @Override
    public int getItemCount() {
        return irrigationxref_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subirrigationcode,consignmentcode,consignmentstatus, pincode;
        LinearLayout mainlyt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.subirrigationcode = (TextView )itemView.findViewById(R.id.subirrigationcode);
            this.consignmentcode = (TextView )itemView.findViewById(R.id.consignmentcode);
            this.consignmentstatus = (TextView )itemView.findViewById(R.id.consignmentstatus);

            mainlyt = (LinearLayout ) itemView.findViewById(R.id.mainlyt);
        }
    }
}
