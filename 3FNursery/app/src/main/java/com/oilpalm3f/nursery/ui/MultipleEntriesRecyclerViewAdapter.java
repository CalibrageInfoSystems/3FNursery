package com.oilpalm3f.nursery.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.LandlevellingFields;
import com.oilpalm3f.nursery.dbmodels.MutipleData;
import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MultipleEntriesRecyclerViewAdapter extends RecyclerView.Adapter<MultipleEntriesRecyclerViewAdapter.ViewHolder>{

    public Context context;
    String convertedDate;
    private List<MutipleData> multiplelist;
    private List<LandlevellingFields> fieldslist;
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");

    public MultipleEntriesRecyclerViewAdapter(Context context, List<MutipleData> multiplelist,List<LandlevellingFields> fieldslist) {
        this.context = context;
        this.multiplelist = multiplelist;
        this.fieldslist = fieldslist;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.multiplelayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleEntriesRecyclerViewAdapter.ViewHolder holder, int position) {


        try {
            Date oneWayTripDate = input.parse(multiplelist.get(position).getCreatedDate());

            convertedDate = output.format(oneWayTripDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (multiplelist.get(position).getServerUpdatedStatus() == 1){

            holder.editicon.setVisibility(View.GONE);
        }else{
            holder.editicon.setVisibility(View.VISIBLE);
        }

        holder.transactionId.setText(":  "+multiplelist.get(position).getTransactionId());
        holder.consignmentcode.setText(":  "+multiplelist.get(position).getConsignmentCode());
        holder.status.setText(":  "+multiplelist.get(position).getDesc());
        holder.comment.setText(":  "+multiplelist.get(position).getComment());
        holder.createddate.setText(":  "+convertedDate);

        holder.infoicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(context);
            }
        });


    }

    @Override
    public int getItemCount() {
        return multiplelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView transactionId,consignmentcode,status, comment, createddate;
        ImageView editicon, infoicon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.transactionId = (TextView )itemView.findViewById(R.id.transactionid);
            this.consignmentcode = (TextView )itemView.findViewById(R.id.conscode);
            this.status = (TextView )itemView.findViewById(R.id.status);
            this.comment = (TextView )itemView.findViewById(R.id.comment);
            editicon = itemView.findViewById(R.id.editicon);
            infoicon = itemView.findViewById(R.id.infoicon);
            this.createddate = (TextView )itemView.findViewById(R.id.createddate);
        }
    }

    public void showDialog(Context context) {
        final Dialog dialog = new Dialog(context, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.infodialog);

        TextView maleregulartxt,maleregular,femaleregulartxt,femaleregular,maleoutsidetxt,maleoutside,femaleoutsidetxt,femaleoutside,tractorhirechargestxt,tractorhirecharges,diselchargestxt,
                diselcharges,completedtxt,completed;

        maleregulartxt = dialog.findViewById(R.id.maleregulartxt);
        maleregular = dialog.findViewById(R.id.maleregular);
        femaleregulartxt = dialog.findViewById(R.id.femaleregulartxt);
        femaleregular = dialog.findViewById(R.id.femaleregular);
        maleoutsidetxt = dialog.findViewById(R.id.maleoutsidetxt);
        maleoutside = dialog.findViewById(R.id.maleoutside);
        femaleoutsidetxt = dialog.findViewById(R.id.femaleoutsidetxt);
        femaleoutside = dialog.findViewById(R.id.femaleoutside);
        tractorhirechargestxt = dialog.findViewById(R.id.tractorhirechargestxt);
        tractorhirecharges = dialog.findViewById(R.id.tractorhirecharges);
        diselchargestxt = dialog.findViewById(R.id.diselchargestxt);
        diselcharges = dialog.findViewById(R.id.diselcharges);
        completedtxt = dialog.findViewById(R.id.completedtxt);
        completed = dialog.findViewById(R.id.completed);

        maleregulartxt.setText(fieldslist.get(0).getField());
        femaleregulartxt.setText(fieldslist.get(1).getField());
        maleoutsidetxt.setText(fieldslist.get(2).getField());
        femaleoutsidetxt.setText(fieldslist.get(3).getField());
        tractorhirechargestxt.setText(fieldslist.get(4).getField());
        diselchargestxt.setText(fieldslist.get(5).getField());
        completedtxt.setText(fieldslist.get(6).getField());

        String value;

        if (fieldslist.get(6).getValue() == 0){
            value = "true";
        }else{
            value = "false";
        }

        maleregular.setText(":  " +fieldslist.get(0).getValue() +"");
        femaleregular.setText(":  " +fieldslist.get(1).getValue()+"");
        maleoutside.setText(":  " +fieldslist.get(2).getValue()+"");
        femaleoutside.setText(":  " +fieldslist.get(3).getValue()+"");
        tractorhirecharges.setText(":  " +fieldslist.get(4).getValue()+"");
        diselcharges.setText(":  " +fieldslist.get(5).getValue()+"");
        completed.setText(":  " +value);


        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 500);
    }
}
