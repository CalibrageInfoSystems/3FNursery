package com.oilpalm3f.nursery.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.dbmodels.ConsignmentData;
import com.oilpalm3f.nursery.ui.irrigation.IrrigationActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ConsignmentRecyclerviewAdapter extends RecyclerView.Adapter<ConsignmentRecyclerviewAdapter.ViewHolder> {

    public Context context;

    List<ConsignmentData> consignmentList = new ArrayList<>();

    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
    String convertedcreatedDate, nurceryId;

    public ConsignmentRecyclerviewAdapter(Context context, List<ConsignmentData> consignmentList, String nurceryId) {
        this.context = context;
        this.consignmentList = consignmentList;
        this.nurceryId = nurceryId;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.consignmentlayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConsignmentRecyclerviewAdapter.ViewHolder holder, int position) {


        //final ConsignmentData model = consignmentList.get(position);

        holder.consignmentcode.setText(":  " + consignmentList.get(position).getConsignmentCode());
        holder.originname.setText(":  " + consignmentList.get(position).getOriginname());
        holder.vendorname.setText(":  " + consignmentList.get(position).getVendorname());
        holder.varietyname.setText(":  " + consignmentList.get(position).getVarietyname());

        holder.estimatedqty.setText(":  " + consignmentList.get(position).getEstimatedQuantity() + "");
        holder.ordereddate.setText(":  " + CommonUtils.getProperComplaintsDate(consignmentList.get(position).getCreatedDate()));
//        Log.d("ArrivedDate", consignmentList.get(position).getArrivedDate());

        holder.lytarrivaldate.setVisibility(View.GONE);
        holder.lytarrivedqty.setVisibility(View.GONE);
        if (consignmentList.get(position).getArrivedDate() == null || consignmentList.get(position).getArrivedDate().equalsIgnoreCase("null") || consignmentList.get(position).getArrivedDate() == "null" || TextUtils.isEmpty(consignmentList.get(position).getArrivedDate())) {
            holder.lytarrivaldate.setVisibility(View.GONE);
            holder.arrivaldate.setText(":  " + "");

        } else {
            holder.lytarrivaldate.setVisibility(View.VISIBLE);
            holder.arrivaldate.setText(":  " + consignmentList.get(position).getArrivedDate());

        }

        if (consignmentList.get(position).getArrivedQuantity() == 0 || TextUtils.isEmpty(consignmentList.get(position).getArrivedQuantity() + "")) {
            holder.lytarrivedqty.setVisibility(View.GONE);
            holder.arrivedqty.setText(":  " + "");
        } else {
            holder.lytarrivedqty.setVisibility(View.VISIBLE);
            holder.arrivedqty.setText(":  " + consignmentList.get(position).getArrivedQuantity() + "");

        }

        holder.mainlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CommonConstants.COMMINGFROM != 1){

                    Intent intent = new Intent(context, Activities.class);
                    intent.putExtra("nurceryId", nurceryId);
                    intent.putExtra("ConsignmentCode", consignmentList.get(position).getConsignmentCode());
                    context.startActivity(intent);

                } else {
//                   model.setSelected(!model.isSelected());
//                    holder.mainlyt.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);

                    Intent intent = new Intent(context, IrrigationActivity.class);
                    CommonConstants.ConsignmentID = consignmentList.get(position).getId();
                    CommonConstants.ConsignmentCode = consignmentList.get(position).getConsignmentCode();
                    context.startActivity(intent);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return consignmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView consignmentcode, originname, vendorname, varietyname, estimatedqty, ordereddate, arrivaldate, arrivedqty;
        LinearLayout mainlyt, lytarrivaldate, lytarrivedqty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.consignmentcode = (TextView) itemView.findViewById(R.id.consignmentcode);
            this.originname = (TextView) itemView.findViewById(R.id.originname);
            this.vendorname = (TextView) itemView.findViewById(R.id.vendorName);
            this.varietyname = (TextView) itemView.findViewById(R.id.vareityName);

            this.estimatedqty = (TextView) itemView.findViewById(R.id.estimatedQty);
            this.ordereddate = (TextView) itemView.findViewById(R.id.ordereddate);
            this.arrivaldate = (TextView) itemView.findViewById(R.id.arrivaldate);
            this.arrivedqty = (TextView) itemView.findViewById(R.id.arrivedqty);
            this.lytarrivaldate = itemView.findViewById(R.id.lytarrivaldate);
            this.lytarrivedqty = itemView.findViewById(R.id.lytarrivedqty);

            mainlyt = (LinearLayout) itemView.findViewById(R.id.mainnlyt);

        }
    }
}
