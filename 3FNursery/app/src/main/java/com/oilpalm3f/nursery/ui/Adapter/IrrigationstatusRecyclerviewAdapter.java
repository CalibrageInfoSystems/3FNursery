package com.oilpalm3f.nursery.ui.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.MutipleData;
import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;
import com.oilpalm3f.nursery.dbmodels.NurseryIrrigationLog;
import com.oilpalm3f.nursery.dbmodels.NurseryIrrigationLogXref;
import com.oilpalm3f.nursery.dbmodels.SaplingActivity;
import com.oilpalm3f.nursery.ui.ActivityTask;
import com.oilpalm3f.nursery.ui.MultipleEntryScreen;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class IrrigationstatusRecyclerviewAdapter extends RecyclerView.Adapter<IrrigationstatusRecyclerviewAdapter.ViewHolder> {

    public Context context;

    List<NurseryIrrigationLog> IrrigationlogList;
    private List<MutipleData> multiplelist;
    private DataAccessHandler dataAccessHandler;
    private List<SaplingActivity> saplingActivitiesList = new ArrayList<>();
    String ConsignmentCode;
    String Irrigation_code;
    IrrigationlogxrefAdapter subItemAdapter;
    int row_index = -1;
    private  List<NurseryIrrigationLogXref> IrrigationLogXreflist =new ArrayList<>();
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    public IrrigationstatusRecyclerviewAdapter(Context context, List<NurseryIrrigationLog> IrrigationlogList) {
        this.context = context;
        this.IrrigationlogList = IrrigationlogList;

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

        final NurseryIrrigationLog  irrigation = IrrigationlogList.get(position);

        dataAccessHandler = new DataAccessHandler(context);

        holder.irrigationcode.setText(IrrigationlogList.get(position).getIrrigationCode());
        String beforeDate =  IrrigationlogList.get(position).getLogDate();
        String finalDate =   CommonUtils.formateDateFromstring("yyyy-MM-dd", "dd-MM-yyyy", beforeDate);
        holder.logdate.setText(finalDate);
        holder.regularlabourmale.setText(IrrigationlogList.get(position).getRegularMale()+"");

        holder.regularlabourfemale.setText(IrrigationlogList.get(position).getRegularFemale()+"");

        holder.contractlabourmale.setText(IrrigationlogList.get(position).getContractMale()+"");

        holder.contractlabourfemale.setText(IrrigationlogList.get(position).getContractFemale()+"");

        holder.txtSatusText.setText(IrrigationlogList.get(position).getDesc());


//        if(IrrigationlogList.size()== 0){
//            holder.rvSubItem.setVisibility(View.GONE);
//
//        }
//        else{
//            holder.rvSubItem.setVisibility(View.VISIBLE);
//
//        }

      //  List<NurseryIrrigationLogXref> IrrigationLogXreflist =new ArrayList<>();
        Irrigation_code =IrrigationlogList.get(position).getIrrigationCode();

        Log.e("======>Irrigation_code",Irrigation_code);

        IrrigationLogXreflist = dataAccessHandler.getirigationlogxref(Queries.getInstance().getIrrigationlogxref(Irrigation_code));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean expanded = irrigation.isExpanded();
                irrigation.setExpanded(!expanded);
                //  notifyIte`35w2qs3mChanged(position);
                int oldindex=  row_index;
                row_index = position;
                notifyItemChanged(oldindex);
                notifyItemChanged(position);





            }
        });


        if (row_index == position) {
            holder.sublinear.setVisibility(View.VISIBLE);

            holder.image_more.setVisibility(View.GONE);
            holder.image_less.setVisibility(View.VISIBLE);
            holder.bind(irrigation);
            // holder.createdDateTextView.setVisibility(View.VISIBLE);

        } else {
            holder.sublinear.setVisibility(View.GONE);
            holder.image_more.setVisibility(View.VISIBLE);
            holder.image_less.setVisibility(View.GONE);
            //  holder.createdDateTextView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return IrrigationlogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView irrigationcode, regularlabourmale, regularlabourfemale, contractlabourmale, contractlabourfemale, txtSatusText, logdate;
        public LinearLayout mainlyt,sublinear;
        public ImageView imgStatus, imgNurStatus, imgShStatus;
        private RecyclerView rvSubItem;
        public ImageView image_less, image_more;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.irrigationcode = (TextView) itemView.findViewById(R.id.irrigationcode);
            this.regularlabourmale = (TextView) itemView.findViewById(R.id.regularlabourmale);
            this.regularlabourfemale = (TextView) itemView.findViewById(R.id.regularlabourfemale);
            this.logdate = (TextView) itemView.findViewById(R.id.logdate);
            this.txtSatusText = (TextView) itemView.findViewById(R.id.txtSatusText);
            this.mainlyt = (LinearLayout) itemView.findViewById(R.id.mainlyt);
            this.contractlabourmale = itemView.findViewById(R.id.contractlabourmale);
            this.contractlabourfemale = itemView.findViewById(R.id.contractlabourfemale);
            this.imgNurStatus = itemView.findViewById(R.id.imgNurStatus);
            this.rvSubItem = itemView.findViewById(R.id.rv_sub_item);
            this.sublinear = itemView.findViewById(R.id.sublinearlayout);
            this.image_less = itemView.findViewById(R.id.image_less);
            this.image_more = itemView.findViewById(R.id.image_more);
        }


        public void bind(NurseryIrrigationLog irrigation) {

            boolean expanded = irrigation.isExpanded();

            sublinear.setVisibility(expanded ? View.VISIBLE : View.GONE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    rvSubItem.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            if (IrrigationLogXreflist.size() != 0) {
                sublinear.setVisibility(View.VISIBLE);

                subItemAdapter = new IrrigationlogxrefAdapter(context, IrrigationLogXreflist);

                ((SimpleItemAnimator) rvSubItem.getItemAnimator()).setSupportsChangeAnimations(false);
                rvSubItem.setLayoutManager(layoutManager);
                rvSubItem.setAdapter(subItemAdapter);
                rvSubItem.setRecycledViewPool(viewPool);
            }


            if( sublinear.getVisibility() == View.VISIBLE) {
                image_less.setVisibility(View.VISIBLE);
                image_more.setVisibility(View.GONE);
            }
            else {
                image_less.setVisibility(View.GONE);
                image_more.setVisibility(View.VISIBLE);
            }
        }
    }
}
