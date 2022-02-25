package com.oilpalm3f.nursery.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.oilpalm3f.nursery.ConsignmentSelectionScreen;
import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.dbmodels.NurseryRMActivity;
import com.oilpalm3f.nursery.ui.NurseryrmActivities;
import com.oilpalm3f.nursery.ui.NurseryrmTransactionsScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NurseryrmActivitiesAdapter extends RecyclerView.Adapter<NurseryrmActivitiesAdapter.MyViewHolder> {

    public Context mContext;
    private List<NurseryRMActivity> RMActivitylist;
   // private RequestAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;
        public ImageView thumbnail;
        RelativeLayout mainlyt;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.textView);

            mainlyt = (RelativeLayout ) itemView.findViewById(R.id.relativeLayout);
        }
    }


    public NurseryrmActivitiesAdapter(Context context, List<NurseryRMActivity> RMActivitylist) {
        this.mContext = context;
//        this.listener = listener;
        this.RMActivitylist = RMActivitylist;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rm_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
      final NurseryRMActivity rmactivitylist = RMActivitylist.get(position);

        holder.name.setText(rmactivitylist.getActivityName());


        holder.mainlyt.setOnClickListener(new View.OnClickListener() {    // Intent  Consignment Selection Screen
            @Override
            public void onClick(View view) {




                    Intent intent = new Intent(mContext, NurseryrmTransactionsScreen.class);
                    intent.putExtra("RmActivityname",RMActivitylist.get(position).getActivityName());

                mContext.startActivity(intent);



            }
        });


        //     Picasso.with(context).load(settings.getImage()).error(R.drawable.ic_user).transform(new CircleTransform()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return RMActivitylist.size();
    }



//
//    public interface RequestAdapterListener {
//        void onContactSelected(GetServicesByStateCode.ListResult list);
//    }
}
