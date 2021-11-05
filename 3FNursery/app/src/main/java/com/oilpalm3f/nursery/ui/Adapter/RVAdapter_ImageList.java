package com.oilpalm3f.nursery.ui.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.ApplicationThread;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.dbmodels.CullinglossFileRepository;
import com.oilpalm3f.nursery.dbmodels.Imagemodel;
import com.oilpalm3f.nursery.dbmodels.NurseryIrrigationLogXref;
import com.oilpalm3f.nursery.ui.Activities;
import com.oilpalm3f.nursery.ui.ActivityTask;
import com.oilpalm3f.nursery.ui.ImageClickListener;
import com.oilpalm3f.nursery.ui.irrigation.IrrigationActivity;
import com.oilpalm3f.nursery.uihelper.ProgressBar;
import com.oilpalm3f.nursery.utils.ImageUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.oilpalm3f.nursery.common.CommonUtils.REQUEST_CAM_PERMISSIONS;

public class RVAdapter_ImageList extends RecyclerView.Adapter<RVAdapter_ImageList.ViewHolder> {

    public Context context;
    private static final String LOG_TAG = RVAdapter_ImageList.class.getName();
    private DataAccessHandler dataAccessHandler;
    List<CullinglossFileRepository> imageRepolist = new ArrayList<>();
    ImageClickListener mItemListener;
    private Bitmap currentBitmap = null;
    private Bitmap currentBitmap2 = null;
    LayoutInflater mInflater;
    private PopupWindow pwindo1,pwindo2;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int CAMERA_REQUEST = 1888;
String imagepath ;


    public RVAdapter_ImageList(Context context,   List<CullinglossFileRepository> imageRepo, ImageClickListener listener) {

        this.context = context;
        this.imageRepolist = imageRepo;
        mItemListener = listener;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.addimagelayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        dataAccessHandler = new DataAccessHandler(context);
        imagepath= imageRepolist.get(position).getFileLocation();
        Log.v(LOG_TAG, "imagepath ============" + imagepath);
        if(imagepath!=null){

        Bitmap bitmap = BitmapFactory.decodeFile(imagepath);

        bitmap = ImageUtility.rotatePicture(90, bitmap);

        currentBitmap2 = bitmap;
        holder.image.setImageBitmap(bitmap);}

        else{
            holder.image.setVisibility(View.GONE);
        }
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onImageClicked(imageRepolist.get(position).getId());
               // listener.onContactSelected1(list.get(position).getRequestCode().toString());
//                dataAccessHandler.deleteRow("CullingLossFileRepository", "id", imageRepolist.get(position).getId()+"", true, new ApplicationThread.OnComplete<String>() {
//                    @Override
//                    public void execute(boolean success, String result, String msg) {
//                        if (success) {
//                            notifyDataSetChanged();
//                            notifyItemRemoved(position);
//
//                            android.util.Log.v(LOG_TAG, "@@@ Master table deletion success for " + "CullingLossFileRepository");
//                        } else {
//                            android.util.Log.v(LOG_TAG, "@@@ Master table deletion failed for " + "CullingLossFileRepository");
//                        }
//                    }
//                });

            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiatePopupWindow1(position);

            }
//                Context mcontext=context.getApplicationContext();
//                mInflater = LayoutInflater.from(mcontext);
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog);
//               // AlertDialog.Builder mBuilder = new AlertDialog.Builder(context.getApplicationContext());
//                View mView =mInflater.inflate(R.layout.dialog_custom_layout, null);
//                //  Picasso.with(mContext).load(getCollectionInfoById.getResult().getReceiptImg()).error(R.drawable.ic_user).into(photoView);
//                PhotoView photoView = mView.findViewById(R.id.imageView);
//                TextView cancel =mView.findViewById(R.id.cancel);
////                if( imageRepolist.get(position).getFileLocation()!=null) {
////                    Bitmap bitmap2 = BitmapFactory.decodeFile(imageRepolist.get(position).getFileLocation());
////
////                    bitmap2 = ImageUtility.rotatePicture(90, bitmap2);
////Log.e("================>",bitmap2+"");
////                    currentBitmap2 = bitmap2;
////                    photoView.setImageBitmap(bitmap2);
////                }
////                else {
////                    photoView.setImageBitmap(bitmap2);
////                }
//                mBuilder.setView(mView);
//               Dialog dialog = new Dialog(context.getApplicationContext());
//               // final AlertDialog mDialog = new AlertDialog(context.getApplicationContext());
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            }
        });

     //  holder.image.setText(irrigationxref_list.get(position).getIrrigationCode());
//        holder.consignmentcode.setText(irrigationxref_list.get(position).getConsignmentCode());
//        holder.consignmentstatus.setText(irrigationxref_list.get(position).getDesc());
//
//




    }

    private void initiatePopupWindow1(int position) {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.dialog_custom_layout,null);
            pwindo1 = new PopupWindow(layout, 650, 650, true);
            pwindo1.showAtLocation(layout, Gravity.CENTER, 0, 0);
            TextView cancel =layout.findViewById(R.id.cancel);
            PhotoView photoView = layout.findViewById(R.id.imageView);

            if( imageRepolist.get(position).getFileLocation()!=null) {
                Bitmap bitmap2 = BitmapFactory.decodeFile(imageRepolist.get(position).getFileLocation());

                bitmap2 = ImageUtility.rotatePicture(90, bitmap2);
                Log.e("================>",bitmap2+"");
                currentBitmap2 = bitmap2;
                photoView.setImageBitmap(currentBitmap2);
            }
//            else {
//                photoView.setImageBitmap(bitmap2);
//            }
//
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pwindo1.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public interface ImageClickListener{
//        void onImageClicked();
//    }

    @Override
    public int getItemCount() {
        return imageRepolist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
ImageView image,img_delete;
        LinearLayout mainlyt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.image = (ImageView ) itemView.findViewById(R.id.imageView);
            this.img_delete = (ImageView ) itemView.findViewById(R.id.img_delete);


        }
    }
}
