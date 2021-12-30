package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.oilpalm3f.nursery.BuildConfig;
import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.ApplicationThread;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.datasync.helpers.DataSyncHelper;
import com.oilpalm3f.nursery.dbmodels.ConsignmentData;
import com.oilpalm3f.nursery.dbmodels.NurseryDetails;
import com.oilpalm3f.nursery.uihelper.ProgressBar;
import com.oilpalm3f.nursery.utils.ImageUtility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;
import static com.oilpalm3f.nursery.common.CommonUtils.REQUEST_CAM_PERMISSIONS;

public class NurseryVisitLogActivity extends AppCompatActivity {
    public static final String LOG_TAG = NurseryVisitLogActivity.class.getSimpleName();
    LinkedHashMap<String, Pair> nurserydatamap = null;
  LinkedHashMap<String, Pair> typeofLogdatamap = null;
    private Spinner nurserySpinner,Consingmentspinner,logtypespin;
    private DataAccessHandler dataAccessHandler;
    Button submitBtn;
    String nursery_code, Consignment_code;
    List<NurseryDetails> nurseryDetails;
   // LinkedHashMap<String, Pair> consignmentList = null;
   List<ConsignmentData> consignmentList = new ArrayList<>();
    ArrayList<String> listdata = new ArrayList<String>();
    Integer LogTypeId;
    EditText Clientname,mobilenumber,location,latitudeEdit,longitudeEdit, Comments;
    LocationManager locationManager;
    Double latitude, longitude;
    private static final int REQUEST_LOCATION = 1;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int CAMERA_REQUEST = 1888;
    public static  String mCurrentPhotoPath;
    ImageView imageview;
    private  File finalFile;
    private Bitmap currentBitmap = null;
    private LinearLayout consignment_linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursery_visit_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nursery Visit Log");
        setSupportActionBar(toolbar);
        initviews();
        setviews();
    }
    private void initviews() {
        dataAccessHandler = new DataAccessHandler(this);
        nurserySpinner = findViewById(R.id.nurserySpinner);
        submitBtn =  findViewById(R.id.submitBtn);
        logtypespin= findViewById(R.id.logtypespin);
        Consingmentspinner =findViewById(R.id.consignmentcode);
        Clientname= findViewById(R.id.clientname);
        mobilenumber= findViewById(R.id.mobilenumber);
        location = findViewById(R.id.location);
        latitudeEdit = findViewById(R.id.latitude);
        longitudeEdit = findViewById(R.id.longitude);
        Comments = findViewById(R.id.comments);
        imageview = findViewById(R.id.imageview);
        consignment_linear = findViewById(R.id.consignment_linear);


    }

    private void setviews() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
        nurserydatamap = dataAccessHandler.getPairData(Queries.getInstance().getNurseryMasterQuery());


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(nurserydatamap, "Nursery"));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nurserySpinner.setAdapter(spinnerArrayAdapter);

        nurserySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (nurserySpinner.getSelectedItemPosition() != 0) {

                    Log.d("Selected1", nurserySpinner.getSelectedItem().toString());
                    nurseryDetails = dataAccessHandler.getNurseryDetails(Queries.getInstance().getNurseryDetailsQuery(nurserySpinner.getSelectedItem().toString()));
                    nursery_code = nurseryDetails.get(0).getCode();
                    Log.d("Selected1===nurserycode", nursery_code);
                    listdata.clear();
                    consignmentList = dataAccessHandler.getConsignmentcode(Queries.getInstance().getAllConsignment(CommonConstants.USER_ID, nursery_code));
                    Log.d("consignmentList===>", consignmentList.size()+"");
                    if(consignmentList.size() == 0){
                        Toast.makeText(NurseryVisitLogActivity.this, "Zero consignments in this Nursery", Toast.LENGTH_SHORT).show();
                    }
                    for (int ii = 0; ii < consignmentList.size(); ii++) {

                        listdata.add(consignmentList.get(ii).getConsignmentCode());
                        Log.d("consignmentList==>size", listdata.size()+"");
                        //  period_id.add(data.getTypeCdId());
                    }
                } else {


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
       typeofLogdatamap = dataAccessHandler.getPairData(Queries.getInstance().getTypeofvisitLogQuery());
     //   typeofLogdatamap = dataAccessHandler.getGenericData(Queries.getInstance().getTypeofvisitLogQuery());
        ArrayAdapter<String> LogArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(typeofLogdatamap, "Log Type"));
        LogArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        logtypespin.setAdapter(LogArrayAdapter);

        logtypespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (logtypespin.getSelectedItemPosition() != 0) {

                    Log.d("Selected1", logtypespin.getSelectedItem().toString());
                   LogTypeId = dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getlogtypeid(logtypespin.getSelectedItem().toString()));

                    Log.d("LogTypeId====", LogTypeId+"");
//                    nursery_code = nurseryDetails.get(0).getCode();
//                    Log.d("Selected1===nurserycode", nursery_code);
                    if(LogTypeId == 359){
                        consignment_linear.setVisibility(View.GONE);
                    }else{
                        consignment_linear.setVisibility(View.VISIBLE);
                    }


                } else {


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listdata.add("-- Select Consignment --");
        ArrayAdapter consignmentAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, listdata);
        consignmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Consingmentspinner.setAdapter(consignmentAdapter);

        Consingmentspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (Consingmentspinner.getSelectedItemPosition() != 0) {

                    Log.d("Consingmentspinner===>", Consingmentspinner.getSelectedItem().toString());
                    Consignment_code = Consingmentspinner.getSelectedItem().toString();
//                    nurseryDetails = dataAccessHandler.getNurseryDetails(Queries.getInstance().getNurseryDetailsQuery(logtypespin.getSelectedItem().toString()));
//                    nursery_code = nurseryDetails.get(0).getCode();
//                    Log.d("Selected1===nurserycode", nursery_code);


                } else {

                    Consignment_code = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Image onclick listener
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (!CommonUtils.isPermissionAllowed(NurseryVisitLogActivity.this, Manifest.permission.CAMERA))) {
                    Log.v(LOG_TAG, "Location Permissions Not Granted");
                    ActivityCompat.requestPermissions(
                            NurseryVisitLogActivity.this,
                            PERMISSIONS_STORAGE,
                            REQUEST_CAM_PERMISSIONS
                    );
                } else {

                    dispatchTakePictureIntent(CAMERA_REQUEST);
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (goValidate()){

                        LinkedHashMap mapStatus = new LinkedHashMap();
                        mapStatus.put("NurseryCode", nursery_code);
                    mapStatus.put("LogTypeId", LogTypeId);
                    if (Consignment_code != null) {
                        mapStatus.put("CosignmentCode", Consignment_code);
                    }else{
                        mapStatus.put("CosignmentCode", "");
                    }

                    mapStatus.put("ClientName", Clientname.getText().toString());
                    mapStatus.put("MobileNumber", mobilenumber.getText().toString());
                    mapStatus.put("Location", location.getText().toString());
                    mapStatus.put("Latitude", latitude);
                    mapStatus.put("Longitude", longitude);
                    mapStatus.put("Comments", Comments.getText().toString());
                    mapStatus.put("CreatedByUserId", CommonConstants.USER_ID);
                        mapStatus.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                        mapStatus.put("ServerUpdatedStatus", 0);

                    if(mCurrentPhotoPath != null){
                        mapStatus.put("FileName ", "");
                    mapStatus.put("FileLocation", mCurrentPhotoPath);
                        mapStatus.put("FileExtension", ".jpg");}



                        final List<LinkedHashMap> nurseryvisit_log = new ArrayList<>();
                        nurseryvisit_log.add(mapStatus);

                        Log.e("==============>",nurseryvisit_log+"");
                        dataAccessHandler.insertMyDataa("NurseryVisitLog", nurseryvisit_log, new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                if (success) {
                                    if (CommonUtils.isNetworkAvailable(NurseryVisitLogActivity.this)) {


                                        DataSyncHelper.performRefreshTransactionsSync(NurseryVisitLogActivity.this, new ApplicationThread.OnComplete() {
                                            @Override
                                            public void execute(boolean success, Object result, String msg) {
                                                if (success) {
                                                    ApplicationThread.uiPost(LOG_TAG, "transactions sync message", new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(NurseryVisitLogActivity.this, "Successfully data sent to server", Toast.LENGTH_SHORT).show();
//
                                                            finish();
                                                        }
                                                    });
                                                } else {
                                                    ApplicationThread.uiPost(LOG_TAG, "transactions sync failed message", new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            ProgressBar.hideProgressBar();

                                                            finish();

                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }


                                    Log.d(ActivityTask.class.getSimpleName(), "==> NurseryVisitLog    INSERT COMPLETED");
                                }
                            }
                        });


                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please enter at least one value", Toast.LENGTH_SHORT).show();
                    }}
            });}

    private void dispatchTakePictureIntent(int actionCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        switch(actionCode) {
            case CAMERA_REQUEST:
                File f = null;
                mCurrentPhotoPath = null;
                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
//                    FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
//                            BuildConfig.APPLICATION_ID + ".provider", file);
                    Uri photoURI = FileProvider.getUriForFile(this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            f);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        }
        startActivityForResult(takePictureIntent, actionCode);
    }

    //Set Imagepath
    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        Log.e("===========>",mCurrentPhotoPath);

        return f;
    }

    //Create file for image
    private File createImageFile() throws IOException {
        String root = Environment.getExternalStorageDirectory().toString();
        File rootDirectory = new File(root + "/3F_Pictures");
        File pictureDirectory = new File(root + "/3F_Pictures/" + "NurseryPhotos");

        if (!rootDirectory.exists()) {
            rootDirectory.mkdirs();
        }

        if (!pictureDirectory.exists()) {
            pictureDirectory.mkdirs();
        }

        File finalFile = new File(pictureDirectory, Calendar.getInstance().getTimeInMillis() + CommonConstants.JPEG_FILE_SUFFIX);
        return finalFile;
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                NurseryVisitLogActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                NurseryVisitLogActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = Double.valueOf(lat);
                longitude = Double.valueOf(longi);
                latitudeEdit.setText(String.valueOf(latitude));
                longitudeEdit.setText(String.valueOf(longitude));
                Log.d(ActivityTask.class.getSimpleName(), "Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);

            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Handling on Activity Result
    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (resultCode == RESULT_OK) {
                    try {
//                        UiUtils.decodeFile(mCurrentPhotoPath,finalFile);
                        handleBigCameraPhoto();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }
                break;
            }
        }
    }
    private void handleBigCameraPhoto () {
        Log.d("local==image==>449", mCurrentPhotoPath);
        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
//            mCurrentPhotoPath = null;
        }

    }


    //Set image to the imageview
    private void setPic()
    {
        /* There isn't enough memory to open up more than a couple camera photos */
        /* So pre-scale the target bitmap into which the file is decoded */

        Log.e("===========>465",mCurrentPhotoPath);
        /* Get the size of the ImageView */
        int targetW = imageview.getWidth();
        int targetH = imageview.getHeight();

        /* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        ;


        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        /* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        /* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        /* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        bitmap = ImageUtility.rotatePicture(90, bitmap);

        currentBitmap = bitmap;
        imageview.setImageBitmap(bitmap);

    }

    private void galleryAddPic() {
//        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
//        File f = new File(mCurrentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        this.sendBroadcast(mediaScanIntent);
    }

    private boolean goValidate() {
        if (nurserySpinner.getSelectedItemPosition() == 0){

            Toast.makeText(this, "Please Select Nursery", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (logtypespin.getSelectedItemPosition() == 0){

            Toast.makeText(this, "Please Select Log Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(consignment_linear.getVisibility() == View.VISIBLE){
            if (Consingmentspinner.getSelectedItemPosition() == 0){

                Toast.makeText(this, "Please Select Consingment", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if ( Comments.getText().toString().equalsIgnoreCase("") || TextUtils.isEmpty(Comments.getText().toString())){

            Toast.makeText(this, "Please Enter Comments", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}