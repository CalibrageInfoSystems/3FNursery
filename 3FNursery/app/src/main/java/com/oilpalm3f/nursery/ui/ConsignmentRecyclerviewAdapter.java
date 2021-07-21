package com.oilpalm3f.nursery.ui;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
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
import com.oilpalm3f.nursery.dbmodels.ConsignmentData;
import com.oilpalm3f.nursery.dbmodels.NurseryData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsignmentRecyclerviewAdapter extends RecyclerView.Adapter<ConsignmentRecyclerviewAdapter.ViewHolder>  {

    public Context context;

    List<ConsignmentData> consignmentList = new ArrayList<>();

    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
    String convertedcreatedDate;

    public ConsignmentRecyclerviewAdapter(Context context,  List<ConsignmentData> consignmentList) {
        this.context = context;
        this.consignmentList = consignmentList;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.consignmentlayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConsignmentRecyclerviewAdapter.ViewHolder holder, int position) {
        try {
            Date oneWayTripDate = input.parse(consignmentList.get(position).getCreatedDate());

            convertedcreatedDate = output.format(oneWayTripDate);
            //datetimevalute.setText(output.format(oneWayTripDate));

            Log.e("===============", "======currentData======" + output.format(oneWayTripDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.consignmentcode.setText(":  " +consignmentList.get(position).getConsignmentCode());
        holder.originname.setText(":  " +consignmentList.get(position).getOriginname());
        holder.vendorname.setText(":  " +consignmentList.get(position).getVendorname());
        holder.varietyname.setText(":  " +consignmentList.get(position).getVarietyname());

        holder.estimatedqty.setText(":  " +consignmentList.get(position).getEstimatedQuantity() + "");
        holder.ordereddate.setText(":  " +convertedcreatedDate);
        Log.d("ArrivedDate", consignmentList.get(position).getArrivedDate());

        if (consignmentList.get(position).getArrivedDate().equalsIgnoreCase("null")   || consignmentList.get(position).getArrivedDate() == "null" || TextUtils.isEmpty(consignmentList.get(position).getArrivedDate())){

            holder.arrivaldate.setText(":  " +"");
        }else{
            holder.arrivaldate.setText(":  " +consignmentList.get(position).getArrivedDate());

        }

        if (consignmentList.get(position).getArrivedQuantity() == 0 || TextUtils.isEmpty(consignmentList.get(position).getArrivedQuantity()+"")){

            holder.arrivedqty.setText(":  " +"");
        }else{
            holder.arrivedqty.setText(":  " +consignmentList.get(position).getArrivedQuantity() + "");

        }

        holder.mainlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Activities.class);
                CommonConstants.ConsignmentCode = consignmentList.get(position).getConsignmentCode();
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return consignmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView consignmentcode,originname,vendorname, varietyname, estimatedqty, ordereddate,arrivaldate,arrivedqty;
        LinearLayout mainlyt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.consignmentcode = (TextView )itemView.findViewById(R.id.consignmentcode);
            this.originname = (TextView )itemView.findViewById(R.id.originname);
            this.vendorname = (TextView )itemView.findViewById(R.id.vendorName);
            this.varietyname = (TextView )itemView.findViewById(R.id.vareityName);

            this.estimatedqty = (TextView )itemView.findViewById(R.id.estimatedQty);
            this.ordereddate = (TextView )itemView.findViewById(R.id.ordereddate);
            this.arrivaldate = (TextView )itemView.findViewById(R.id.arrivaldate);
            this.arrivedqty = (TextView )itemView.findViewById(R.id.arrivedqty);

            mainlyt = (LinearLayout ) itemView.findViewById(R.id.mainnlyt);

        }
    }
}
