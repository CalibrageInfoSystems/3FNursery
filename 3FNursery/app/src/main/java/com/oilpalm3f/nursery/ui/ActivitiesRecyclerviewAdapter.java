package com.oilpalm3f.nursery.ui;

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

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesRecyclerviewAdapter extends RecyclerView.Adapter<ActivitiesRecyclerviewAdapter.ViewHolder> {

    public Context context;

    List<NurseryAcitivity> mActivitiesList;
    private List<MutipleData> multiplelist;
    private DataAccessHandler dataAccessHandler;
    private List<SaplingActivity> saplingActivitiesList = new ArrayList<>();
    String ConsignmentCode;
    String targetDatedata;

    public ActivitiesRecyclerviewAdapter(Context context, List<NurseryAcitivity> mActivitiesList, String ConsignmentCode, String targetDate) {
        this.context = context;
        this.mActivitiesList = mActivitiesList;
        this.ConsignmentCode = ConsignmentCode;
        this.targetDatedata = targetDate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activitieslayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        dataAccessHandler = new DataAccessHandler(context);

        holder.activityName.setText(mActivitiesList.get(position).getName());
        String status = "";
        if (mActivitiesList.get(position).getDesc() == null || mActivitiesList.get(position).getDesc().isEmpty() || mActivitiesList.get(position).toString() == "") {
            holder.txtStatusTxt.setText("");
        } else {
            holder.txtStatusTxt.setText(mActivitiesList.get(position).getDesc());
        }
        holder.imgStatus.setImageDrawable(null);
        holder.imgNurStatus.setImageDrawable(null);
        holder.imgShStatus.setImageDrawable(null);
        holder.txtDoneDate.setText("");

        if (!StringUtils.isEmpty(mActivitiesList.get(position).getUpdatedDate())) {
            Log.d("###################", mActivitiesList.get(position).getUpdatedDate());
            holder.txtDoneDate.setText(CommonUtils.getProperComplaintsDate(mActivitiesList.get(position).getUpdatedDate()));
        }

        if (mActivitiesList.get(position).getStatusTypeId() == 346) {
            holder.imgStatus.setImageResource(R.drawable.done);
            holder.imgNurStatus.setImageResource(R.drawable.inprogress);
            holder.imgShStatus.setImageResource(R.drawable.inprogress);
        } else if (mActivitiesList.get(position).getStatusTypeId() == 347) {
            holder.imgStatus.setImageResource(R.drawable.done);
            holder.imgNurStatus.setImageResource(R.drawable.done);
            holder.imgShStatus.setImageResource(R.drawable.inprogress);
        } else if (mActivitiesList.get(position).getStatusTypeId() == 348) {
            holder.imgStatus.setImageResource(R.drawable.done);
            holder.imgNurStatus.setImageResource(R.drawable.done);
            holder.imgShStatus.setImageResource(R.drawable.done);
        } else if (mActivitiesList.get(position).getStatusTypeId() == 349) {
            holder.imgStatus.setImageResource(R.drawable.done);
            holder.imgNurStatus.setImageResource(R.drawable.rejected);
            holder.imgShStatus.setImageResource(R.drawable.rejected);
        } else if (mActivitiesList.get(position).getStatusTypeId() == 352) {
            holder.imgStatus.setImageResource(R.drawable.inprogress);
            holder.imgNurStatus.setImageResource(R.drawable.inprogress);
            holder.imgShStatus.setImageResource(R.drawable.inprogress);
        }
String beforeDate =  dataAccessHandler.getSingleValue(Queries.addDaysToSapling(mActivitiesList.get(position).getTargetDays(), ConsignmentCode));
  String finalDate =   CommonUtils.formateDateFromstring("yyyy-MM-dd", "dd-MM-yyyy", beforeDate);
        holder.expecteddate.setText(finalDate);
//        holder.expecteddate.setText(( CommonUtils.getTargetDate2(targetDatedata,mActivitiesList.get(position).getTargetDays())));
//        holder.expecteddate.setText( ( "( "+mActivitiesList.get(position).getTargetDays()+" Days )   ") + CommonUtils.getTargetDate2("2021-11-25T05:25:35.643",mActivitiesList.get(position).getTargetDays()));
//        holder.expecteddate.setText( ( CommonUtils.getTargetDate2("2021-08-05 17:14:11",mActivitiesList.get(position).getTargetDays())));


        holder.mainlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saplingActivitiesList = dataAccessHandler.getSaplingActivityData(Queries.getInstance().getSaplingActivityCounttQuery(ConsignmentCode, mActivitiesList.get(position).getId() + ""));
                Log.d("saplingActivitiesListSize", saplingActivitiesList.size() + "");

                if (mActivitiesList.get(position).getIsMultipleEntries().equalsIgnoreCase("true") && saplingActivitiesList.size() != 0) {

                    Intent met = new Intent(context, MultipleEntryScreen.class);
                    met.putExtra("consignmentcode", ConsignmentCode);
                    met.putExtra("ActivityTypeId1", mActivitiesList.get(position).getId() + "");
                    met.putExtra("ActivityName1", mActivitiesList.get(position).getName() + "");
                    met.putExtra("Ismultipleentry1", mActivitiesList.get(position).getIsMultipleEntries());
                    met.putExtra("status", mActivitiesList.get(position).getDesc());
                    met.putExtra("statusId", mActivitiesList.get(position).getStatusTypeId());
                    met.putExtra("addActivity", false);

                    Log.d("consignmentcode1", ConsignmentCode);
                    context.startActivity(met);

                } else {
                    // Todo Check ALready hava data or not.
                    // TODO Check Data Exisitng Or Not
                   if( !StringUtils.isEmpty(mActivitiesList.get(position).getUpdatedDate()))
                   {
                       // EXISTING UI
                       Boolean enableEditing = false;
                       if (mActivitiesList.get(position).getStatusTypeId() == 349) {
                           enableEditing = true;
                       }
                       Intent at = new Intent(context, ActivityTask.class);
                       at.putExtra("multipleEntry", mActivitiesList.get(position).getIsMultipleEntries());
                       at.putExtra("consignmentcode", ConsignmentCode);
                       at.putExtra("ActivityTypeId", mActivitiesList.get(position).getId() + "");
                       at.putExtra("ActivityName", mActivitiesList.get(position).getName() + "");
                       at.putExtra("enableEditing", enableEditing);
                       at.putExtra(CommonConstants.SCREEN_CAME_FROM, CommonConstants.FROM_SINGLE_ENTRY_EDITDATA);
                       context.startActivity(at);
                   }else{
//                      NEW ACTIVITY
                       Intent at = new Intent(context, ActivityTask.class);
                       at.putExtra("multipleEntry", mActivitiesList.get(position).getIsMultipleEntries());
                       at.putExtra("consignmentcode", ConsignmentCode);
                       at.putExtra("ActivityTypeId", mActivitiesList.get(position).getId() + "");
                       at.putExtra("ActivityName", mActivitiesList.get(position).getName() + "");
                       at.putExtra("enableEditing", true);
                       at.putExtra(CommonConstants.SCREEN_CAME_FROM, CommonConstants.FROM_SINGLE_ENTRY_EDITDATA);
                       context.startActivity(at);
                   }

                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return mActivitiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView activityName, expecteddate, saplingExpecteddate, txtStatusTxt, txtDoneDate;
        public LinearLayout mainlyt;
        public ImageView imgStatus, imgNurStatus, imgShStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.activityName = (TextView) itemView.findViewById(R.id.activityName);
            this.expecteddate = (TextView) itemView.findViewById(R.id.expecteddate);
            this.txtStatusTxt = (TextView) itemView.findViewById(R.id.txtStatusTxt);
            this.txtDoneDate = (TextView) itemView.findViewById(R.id.txtDoneDate);
//            this.saplingExpecteddate = (TextView )itemView.findViewById(R.id.saplingExpecteddate);
            this.mainlyt = (LinearLayout) itemView.findViewById(R.id.mainlyt);
            this.imgStatus = itemView.findViewById(R.id.imgStatus);
            this.imgNurStatus = itemView.findViewById(R.id.imgNurStatus);
            this.imgShStatus = itemView.findViewById(R.id.imgShStatus);
        }
    }

    public void showDialog(Activity activity) {

        final Dialog dialog = new Dialog(activity, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.questionsdialouge);


        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 500);
    }

}
