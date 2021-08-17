package com.oilpalm3f.nursery.ui.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.MutipleData;
import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;
import com.oilpalm3f.nursery.dbmodels.SaplingActivity;
import com.oilpalm3f.nursery.ui.ActivityTask;
import com.oilpalm3f.nursery.ui.MultipleEntryScreen;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class IrrigationstatusRecyclerviewAdapter extends RecyclerView.Adapter<IrrigationstatusRecyclerviewAdapter.ViewHolder> {

    public Context context;

    List<NurseryAcitivity> mActivitiesList;
    private List<MutipleData> multiplelist;
    private DataAccessHandler dataAccessHandler;
    private List<SaplingActivity> saplingActivitiesList = new ArrayList<>();
    String ConsignmentCode;
    String targetDatedata;

    public IrrigationstatusRecyclerviewAdapter(Context context, List<NurseryAcitivity> mActivitiesList, String ConsignmentCode, String targetDate) {
        this.context = context;
        this.mActivitiesList = mActivitiesList;
        this.ConsignmentCode = ConsignmentCode;
        this.targetDatedata = targetDate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.irrigationstatuslayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        dataAccessHandler = new DataAccessHandler(context);



    }

    @Override
    public int getItemCount() {
        return mActivitiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView irrigationcode, regularlabourmale, regularlabourfemale, contractlabourmale, contractlabourfemale,txtSatusText,logdate;
        public LinearLayout mainlyt;
        public ImageView imgStatus, imgNurStatus, imgShStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.irrigationcode = (TextView) itemView.findViewById(R.id.irrigationcode);
            this.regularlabourmale = (TextView) itemView.findViewById(R.id.regularlabourmale);
            this.regularlabourfemale = (TextView) itemView.findViewById(R.id.regularlabourfemale);
            this.logdate = (TextView) itemView.findViewById(R.id.logdate);
//            this.saplingExpecteddate = (TextView )itemView.findViewById(R.id.saplingExpecteddate);
            this.mainlyt = (LinearLayout) itemView.findViewById(R.id.mainlyt);
            this.contractlabourmale = itemView.findViewById(R.id.contractlabourmale);
            this.contractlabourfemale = itemView.findViewById(R.id.contractlabourfemale);
            this.txtSatusText = itemView.findViewById(R.id.txtSatusText);
        }
    }



}
