package com.oilpalm3f.nursery.ui.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.ConsignmentSelectionScreen;
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
import org.joda.time.DateTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActivitiesRecyclerviewAdapter extends RecyclerView.Adapter<ActivitiesRecyclerviewAdapter.ViewHolder> {

    public Context context;

    List<NurseryAcitivity> mActivitiesList;
    private List<MutipleData> multiplelist;
    private DataAccessHandler dataAccessHandler;
    private List<SaplingActivity> saplingActivitiesList = new ArrayList<>();
    String ConsignmentCode;
    String targetDatedata;
    String  finaldate,targetdate;
    String dependencyname,  dependecyCode;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        dataAccessHandler = new DataAccessHandler(context);

        holder.activityName.setText(mActivitiesList.get(position).getName());
        String status = "";
        if (mActivitiesList.get(position).getDesc() == null || mActivitiesList.get(position).getDesc().isEmpty() || mActivitiesList.get(position).toString() == "") {
            holder.txtStatusTxt.setText("");
        } else {
            holder.txtStatusTxt.setText(mActivitiesList.get(position).getDesc());
        //    holder.txtStatusTxt.setTextColor(context.getColor(R.color.green));
        }
        holder.imgStatus.setImageDrawable(null);
        holder.imgNurStatus.setImageDrawable(null);
        holder.imgShStatus.setImageDrawable(null);
        holder.txtDoneDate.setText("");
        String beforeDate =  dataAccessHandler.getSingleValue(Queries.addDaysToSapling(mActivitiesList.get(position).getTargetDays(), ConsignmentCode));
        finaldate =   CommonUtils.formateDateFromstring("yyyy-MM-dd", "dd-MM-yyyy hh:mm a", beforeDate);
      String   expecteddate =   CommonUtils.formateDateFromstring("yyyy-MM-dd", "dd-MM-yyyy", beforeDate);
        holder.expecteddate.setText(expecteddate);
        if (!StringUtils.isEmpty(mActivitiesList.get(position).getUpdatedDate())) {

            holder.txtDoneDate.setText(CommonUtils.getProperComplaintsDate(mActivitiesList.get(position).getUpdatedDate()));

            targetdate = CommonUtils.getProperComplaintsDate(mActivitiesList.get(position).getUpdatedDate());

            Log.d("###################", finaldate  + "=== "+targetdate);
            Date date1;
            Date date2;
            SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

            try {
                date1 = dates.parse(finaldate);
                date2 = dates.parse(targetdate);
                long difference = date1.getTime() - date2.getTime();
                long  differenceDates = difference / (24 * 60 * 60 * 1000);
                int dayDifference = (int) differenceDates;
                Log.i("day diff","The difference between two dates is " + dayDifference + " days");

                if (dayDifference < -1)
                {

                    holder.activityName.setTextColor(context.getColor(R.color.red));
                }
                else if (dayDifference == -1 )
                {
                    holder.activityName.setTextColor(context.getColor(R.color.yellow));

                }
                  else if (dayDifference >= 0)
                {

                    holder.activityName.setTextColor(context.getColor(R.color.green));
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }



        }
        else
        {
            holder.activityName.setTextColor(context.getColor(R.color.black));
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







//        holder.expecteddate.setText(( CommonUtils.getTargetDate2(targetDatedata,mActivitiesList.get(position).getTargetDays())));
//        holder.expecteddate.setText( ( "( "+mActivitiesList.get(position).getTargetDays()+" Days )   ") + CommonUtils.getTargetDate2("2021-11-25T05:25:35.643",mActivitiesList.get(position).getTargetDays()));
//        holder.expecteddate.setText( ( CommonUtils.getTargetDate2("2021-08-05 17:14:11",mActivitiesList.get(position).getTargetDays())));


        holder.mainlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saplingActivitiesList = dataAccessHandler.getSaplingActivityData(Queries.getInstance().getSaplingActivityCounttQuery(ConsignmentCode, mActivitiesList.get(position).getId() + ""));
                Log.d("saplingActivitiesListSize", saplingActivitiesList.size() + "");

                if (validations(position )) {
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
                        if (!StringUtils.isEmpty(mActivitiesList.get(position).getUpdatedDate())) {
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
                            at.putExtra("Code", mActivitiesList.get(position).getCode());
                            at.putExtra("DependentActivityCode", mActivitiesList.get(position).getDependentActivityCode() + "");
                            at.putExtra("enableEditing", enableEditing);
                            at.putExtra(CommonConstants.SCREEN_CAME_FROM, CommonConstants.FROM_SINGLE_ENTRY_EDITDATA);
                            context.startActivity(at);
                        } else {
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
                else{

                     dependencyname = dataAccessHandler.getSingleValue(Queries.dependencyname(dependecyCode));
                    Log.d("dependencyname=============",dependencyname);
                    Toast.makeText(context, "   Please Complete  "+dependencyname + "   Activity " , Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    private boolean validations(int position) {
      boolean issuces = false;
       dependecyCode =  mActivitiesList.get(position).getDependentActivityCode();

      Log.d("dependecyCode=============",dependecyCode);
      if(StringUtils.isEmpty(dependecyCode) || dependecyCode == null || dependecyCode.equalsIgnoreCase("null") || dependecyCode ==""){
          issuces  =  true;
      }else {

      Integer statuscode =  dataAccessHandler.getSingleIntValue(Queries.dependencystatus(ConsignmentCode,dependecyCode));

        if(statuscode != null){
            issuces = true;
        }
      }

//        if(mActivitiesList.get(position).getCode().equalsIgnoreCase("A012") && StringUtils.isEmpty(mActivitiesList.get(position).getUpdatedDate())){
//
//            Toast.makeText(context, mActivitiesList.get(position).getDependentActivityCode() +"dependency " + mActivitiesList.get(position).getName() , Toast.LENGTH_LONG).show();
//            return false;
//        }
//        if(mActivitiesList.get(position).getCode().equalsIgnoreCase("A013") && StringUtils.isEmpty(mActivitiesList.get(position).getUpdatedDate())){
//
//
//            Toast.makeText(context, mActivitiesList.get(position).getDependentActivityCode() + " dependency" + mActivitiesList.get(position).getName(), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if(mActivitiesList.get(position).getCode().equalsIgnoreCase("A014") && StringUtils.isEmpty(mActivitiesList.get(position).getUpdatedDate())){
//            dependencyname = dataAccessHandler.getSingleValue(Queries.dependencyvalue(ConsignmentCode,mActivitiesList.get(position).getDependentActivityCode()));
//            dependencyname = dataAccessHandler.getSingleValue(Queries.dependencyvalue(ConsignmentCode,mActivitiesList.get(position).getDependentActivityCode()));
//
//            if(dependencyname!=null){
//                Toast.makeText(context, mActivitiesList.get(position).getDependentActivityCode() +"dependency " + mActivitiesList.get(position).getName() , Toast.LENGTH_LONG).show();
//                return true;
//            }
//
//            Toast.makeText(context, "", Toast.LENGTH_LONG).show();
//
//            return false;
//        }
//        if(mActivitiesList.get(position).getCode().equalsIgnoreCase("A015") && StringUtils.isEmpty(mActivitiesList.get(position).getUpdatedDate())){
//
//
//            Toast.makeText(context, mActivitiesList.get(position).getDependentActivityCode() + " dependency" + mActivitiesList.get(position).getName(), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
        return issuces;
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

    public void showDialog(Context activity) {

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
