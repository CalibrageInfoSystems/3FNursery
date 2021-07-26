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

import java.util.ArrayList;
import java.util.List;

public class ActivitiesRecyclerviewAdapter extends RecyclerView.Adapter<ActivitiesRecyclerviewAdapter.ViewHolder> {

    public Context context;

    List<NurseryAcitivity> mActivitiesList;
    private List<MutipleData> multiplelist;
    private DataAccessHandler dataAccessHandler;
    private List<SaplingActivity> saplingActivitiesList = new ArrayList<>();
    String ConsignmentCode;



    public ActivitiesRecyclerviewAdapter(Context context, List<NurseryAcitivity> mActivitiesList, String ConsignmentCode) {
        this.context = context;
        this.mActivitiesList = mActivitiesList;
        this.ConsignmentCode = ConsignmentCode;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        dataAccessHandler = new DataAccessHandler(context);



        holder.activityName.setText(mActivitiesList.get(position).getName());

        holder.expecteddate.setText( ( "( "+mActivitiesList.get(position).getTargetDays()+" Days )   ") + CommonUtils.getTargetDate("2021-11-25T05:25:35.643",mActivitiesList.get(position).getTargetDays()));

        holder.mainlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saplingActivitiesList = dataAccessHandler.getSaplingActivityData(Queries.getInstance().getSaplingActivityCounttQuery(ConsignmentCode, mActivitiesList.get(position).getId() + ""));
                Log.d("saplingActivitiesListSize", saplingActivitiesList.size() + "");

                if (mActivitiesList.get(position).getIsMultipleEntries().equalsIgnoreCase("true") && saplingActivitiesList.size() != 0){

                    Intent met = new Intent(context, MultipleEntryScreen.class);
                    met.putExtra("consignmentcode", ConsignmentCode);
                    met.putExtra("ActivityTypeId1", mActivitiesList.get(position).getId() + "");
                    met.putExtra("ActivityName1", mActivitiesList.get(position).getName() + "");
                    met.putExtra("Ismultipleentry1",mActivitiesList.get(position).getIsMultipleEntries());
                    met.putExtra("addActivity",false );
                    Log.d("consignmentcode1", ConsignmentCode);
                    context.startActivity(met);

                }else{
                    // Todo Check ALready hava data or not.
                    Intent at = new Intent(context, ActivityTask.class);
                    at.putExtra("consignmentcode", ConsignmentCode);
                    at.putExtra("isSingleEntry",true);
                    at.putExtra("ActivityTypeId", mActivitiesList.get(position).getId() + "");
                    at.putExtra("ActivityName", mActivitiesList.get(position).getName() + "");
                    at.putExtra("Ismultipleentry",mActivitiesList.get(position).getIsMultipleEntries() );
                    at.putExtra("addActivity",false );
                    Log.d("consignmentcode2", ConsignmentCode);

                    context.startActivity(at);
               }


            }
        });

    }

    @Override
    public int getItemCount() {
        return mActivitiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView activityName,expecteddate,saplingExpecteddate;
        public LinearLayout mainlyt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.activityName = (TextView )itemView.findViewById(R.id.activityName);
            this.expecteddate = (TextView )itemView.findViewById(R.id.expecteddate);
//            this.saplingExpecteddate = (TextView )itemView.findViewById(R.id.saplingExpecteddate);
            this.mainlyt = (LinearLayout) itemView.findViewById(R.id.mainlyt);
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
