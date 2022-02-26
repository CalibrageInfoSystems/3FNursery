package com.oilpalm3f.nursery.ui;

import static com.oilpalm3f.nursery.common.CommonUtils.REQUEST_CAM_PERMISSIONS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.nursery.R;

import com.oilpalm3f.nursery.cloudhelper.ApplicationThread;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.ui.irrigation.IrrigationActivity;

import com.oilpalm3f.nursery.common.CommonUtils;

import com.oilpalm3f.nursery.utils.UiUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class RMActivityFields extends AppCompatActivity {
    public static final String LOG_TAG = RMActivityFields.class.getSimpleName();
    Spinner typespinner, uomSpinner;
    LinearLayout labourlyt, otherlyt, nameactivity;

    EditText mandaysmale, mandaysfemale, mandaysmaleoutside, mandaysfemaleoutside,cost;


    EditText expensetype, quantity, date,comment,nameofactivity,othercomments;


    ImageView imageView;
    Button submitBtn, cancelBtn;
    int Flag;
    String transactionId, Activity_Name, Nurseryname;
    TextView activity_name, nurseryname;
    DatePickerDialog picker;
    int labourcost = 10;

    String currentDate,sendcurrentDate;

 //   TextView cost;

    private int GALLERY = 1, CAMERA = 2;

    String activityTypeId, uomId;
    String activityId ;
    String transactionid;
    String Sapcode;

    private DataAccessHandler dataAccessHandler;
    LinkedHashMap<String, Pair> activityTypeDataMap = null;
    LinkedHashMap<String, Pair> uomTypeDataMap = null;



    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static String local_ImagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmfields);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nursery R&M");
        setSupportActionBar(toolbar);
        init();
        setviews();
    }

    private void init() {

        dataAccessHandler = new DataAccessHandler(this);

        nameactivity = findViewById(R.id.nameactivity);
        nurseryname = findViewById(R.id.nurseryname);
        date = findViewById(R.id.rmdate);

        typespinner = findViewById(R.id.typeSpinner);
        uomSpinner = findViewById(R.id.uomspinner);

        labourlyt = findViewById(R.id.labourslyt);
        otherlyt = findViewById(R.id.otherslyt);

        mandaysmale = findViewById(R.id.mandaysmale);
        mandaysfemale = findViewById(R.id.mandaysfemale);
        mandaysmaleoutside = findViewById(R.id.mandaysmaleoutside);
        mandaysfemaleoutside = findViewById(R.id.mandaysfemaleoutside);
        nameofactivity = findViewById(R.id.nameofactivity);
        expensetype = findViewById(R.id.expensetype);
        quantity = findViewById(R.id.quantity);
        comment = findViewById(R.id.comments);
        imageView = findViewById(R.id.rmimageview);
        othercomments = findViewById(R.id.othercomments);

        activity_name = findViewById(R.id.activityname);
        cost = findViewById(R.id.cost);

        submitBtn = findViewById(R.id.rmsubmitBtn);
        cancelBtn = findViewById(R.id.rmcancelBtn);

    }

    private void setviews() {

        labourlyt.setVisibility(View.GONE);
        otherlyt.setVisibility(View.GONE);

        Log.d("Nurserynameeeee", CommonConstants.NurseryName + "");
        Log.d("NurseryCodeeeee", CommonConstants.NurseryCode + "");

        currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        sendcurrentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.i("LOG_RESPONSE date ", currentDate + "========" + sendcurrentDate);
        date.setText(currentDate);
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(RMActivityFields.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                sendcurrentDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                Log.i("LOG_RESPONSE date ", "========" + sendcurrentDate);
                            }
                        }, year, month, day);
                picker.show();
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });


        if (getIntent() != null) {

            Activity_Name = getIntent().getStringExtra("Name");
            Flag = getIntent().getIntExtra("camefrom", 1); // if Flag 2 , edit on rejection
            transactionId = getIntent().getStringExtra("transactionId");
            activityId = getIntent().getStringExtra("ActivityId");
            Log.d(LOG_TAG, "Name==========> :" + Activity_Name +"==="+activityId);
            Log.d(LOG_TAG, "Flag=====" + Flag);
            if (Flag == 2) {
                nurseryname.setText(CommonConstants.NurseryName + "");
                activity_name.setText(Activity_Name + "");
                mandaysmale.setText("5");
                mandaysfemale.setText("6");
                mandaysmaleoutside.setText("4");
                mandaysfemaleoutside.setText("9");

                comment.setText("testing R&m Commets");
                if (Activity_Name.equalsIgnoreCase("Other")) {
                    nameactivity.setVisibility(View.VISIBLE);
                    nameofactivity.setText("Other R&M Activity");
                } else {
                    nameactivity.setVisibility(View.GONE);
                }

            } else if (Flag == 3) {

                othercomments.setText("testing R&m Commets");

                nurseryname.setText(CommonConstants.NurseryName + "");

                activity_name.setText(Activity_Name + "");
                expensetype.setText("test");
                quantity.setText("5");
                submitBtn.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.info);
                cost.setText("500");


                nameofactivity.setEnabled(false);
                date.setEnabled(false);
                typespinner.setEnabled(false);
                uomSpinner.setEnabled(false);
                expensetype.setEnabled(false);
                quantity.setEnabled(false);
                othercomments.setEnabled(false);
                imageView.setEnabled(false);
                if (Activity_Name.equalsIgnoreCase("Other")) {
                    nameactivity.setVisibility(View.VISIBLE);
                    nameofactivity.setText("Test R&M Activity");
                } else {
                    nameactivity.setVisibility(View.GONE);
                }
            } else {
                activity_name.setText(Activity_Name + "");
                nurseryname.setText(CommonConstants.NurseryName + "");
                if (Activity_Name.equalsIgnoreCase("Other")) {
                    nameactivity.setVisibility(View.VISIBLE);
                } else {
                    nameactivity.setVisibility(View.GONE);
                }

            }

        }

//        String[] typeSpinnerArr = getResources().getStringArray(R.array.typespin_values);
//        List<String> typeSpinnerList = Arrays.asList(typeSpinnerArr);
//        ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<>(RMActivityFields.this, android.R.layout.simple_spinner_item, typeSpinnerList);
//        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        typespinner.setAdapter(typeSpinnerAdapter);

        activityTypeDataMap = dataAccessHandler.getPairData(Queries.getInstance().getActivityTypeofRMQuery());
        ArrayAdapter<String> typeArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(activityTypeDataMap, "Type"));
        typeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespinner.setAdapter(typeArrayAdapter);

        typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (typespinner.getSelectedItemPosition() != 0) {

                    int selectedPos = typespinner.getSelectedItemPosition();
                    Log.d("selectedType", typespinner.getSelectedItem().toString());
                    Log.d("selectedTypeId", activityTypeDataMap.keySet().toArray(new String[activityTypeDataMap.size()])[selectedPos - 1]);
                    activityTypeId = activityTypeDataMap.keySet().toArray(new String[activityTypeDataMap.size()])[selectedPos - 1];
                }

                if (Flag == 2) {
                    typespinner.setSelection(1);
                } else if (Flag == 3) {
                    typespinner.setSelection(2);
                }
                if (typespinner.getSelectedItemPosition() == 0) {

                    labourlyt.setVisibility(View.GONE);
                    otherlyt.setVisibility(View.GONE);

                } else if (typespinner.getSelectedItemPosition() == 1) {
                    labourlyt.setVisibility(View.VISIBLE);
                    otherlyt.setVisibility(View.GONE);
                } else {

                    labourlyt.setVisibility(View.GONE);
                    otherlyt.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (Flag == 2) {
                    typespinner.setSelection(1);
                }
            }
        });


//        String[] uomSpinnerArr = getResources().getStringArray(R.array.uom_values);
//        List<String> uomSpinnerList = Arrays.asList(uomSpinnerArr);
//        ArrayAdapter<String> uomSpinnerAdapter = new ArrayAdapter<>(RMActivityFields.this, android.R.layout.simple_spinner_item, uomSpinnerList);
//        uomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        uomSpinner.setAdapter(uomSpinnerAdapter);

        uomTypeDataMap = dataAccessHandler.getPairData(Queries.getInstance().getUOMTypeofRMQuery());
        ArrayAdapter<String> uomArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(uomTypeDataMap, "UOM"));
        uomArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uomSpinner.setAdapter(uomArrayAdapter);

        uomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (uomSpinner.getSelectedItemPosition() != 0) {

                    int selectedPos = uomSpinner.getSelectedItemPosition();
                    Log.d("selecteduom", uomSpinner.getSelectedItem().toString());
                    Log.d("selecteduomId", uomTypeDataMap.keySet().toArray(new String[uomTypeDataMap.size()])[selectedPos - 1]);
                    uomId = uomTypeDataMap.keySet().toArray(new String[uomTypeDataMap.size()])[selectedPos - 1];

                }


                if (Flag == 3) {
                    uomSpinner.setSelection(2);
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (Flag == 3) {
                    uomSpinner.setSelection(2);
                }
            }
        });


        imageView.setOnClickListener(v1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (!CommonUtils.isPermissionAllowed(this, Manifest.permission.CAMERA))) {
                android.util.Log.v(LOG_TAG, "Camera Permissions Not Granted");
                ActivityCompat.requestPermissions(
                        this,
                        PERMISSIONS_STORAGE,
                        REQUEST_CAM_PERMISSIONS
                );
            } else {
                takePhotoFromCamera();
            }
        });


        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validations()) {
                    saveRMTransactionsData();
                    saveRMTransactionsStatus();
                    Toast.makeText(RMActivityFields.this, "Submit Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RMActivityFields.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        Sapcode = dataAccessHandler.getSingleValue(Queries.getSapcode(CommonConstants.NurseryCode));
         transactionid = "TRANRM"+ CommonConstants.TAB_ID + Sapcode + activityId + "-" + (dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getRMActivityMaxNumber(CommonConstants.NurseryCode, activityId)) + 1);


    }

    /* "TransactionId VARCHAR, \n" +
                "SatusTypeId INT , \n" +
                "CreatedByUserId INT,\n" +
                "CreatedDate VARCHAR, \n" +
                "ServerUpdatedStatus INT \n" +*/

    private void saveRMTransactionsStatus() {
        LinkedHashMap mapStatus = new LinkedHashMap();
        mapStatus.put("TransactionId",transactionid);
        mapStatus.put("StatusTypeId",346);
        mapStatus.put("CreatedByUserId",CommonConstants.USER_ID);
        mapStatus.put("CreatedDate",CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        mapStatus.put("ServerUpdatedStatus",0);
    }

    private void saveRMTransactionsData() {

        String male_reg = dataAccessHandler.getSingleValue(Queries.getregmalerate(CommonConstants.NurseryCode));
        String femmale_reg = dataAccessHandler.getSingleValue(Queries.getregfemalerate(CommonConstants.NurseryCode));
        String male_contract = dataAccessHandler.getSingleValue(Queries.getcontractmalerate(CommonConstants.NurseryCode));
        String female_contract = dataAccessHandler.getSingleValue(Queries.getcontractfemalerate(CommonConstants.NurseryCode));

        //String transactionid = "TRANRM"+ CommonConstants.TAB_ID + CommonConstants.NurseryCode + activityId + "-" + (dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getRMActivityMaxNumber(CommonConstants.NurseryCode, activityTypeId)) + 1);
        Log.d("TransactionId", transactionid);


        LinkedHashMap mapStatus = new LinkedHashMap();
        mapStatus.put("TransactionId",transactionid);

        mapStatus.put("NurseryCode",CommonConstants.NurseryCode);
        mapStatus.put("ActivityId",activityId);
        mapStatus.put("ActivityName",Activity_Name);
        mapStatus.put("ActivityTypeId",Integer.parseInt(activityTypeId));
        mapStatus.put("StatusTypeId",346);
        mapStatus.put("TransactionDate",CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY));

        if(mandaysmale.getText().length() == 0){
            mapStatus.put("MaleRegular","");
        }else{
            mapStatus.put("MaleRegular",mandaysmale.getText());
        }

        if(mandaysfemale.getText().length() == 0){
            mapStatus.put("FemaleRegular","");
        }else{
            mapStatus.put("FemaleRegular",mandaysfemale.getText());
        }

        if(mandaysmaleoutside.getText().length() == 0){
            mapStatus.put("MaleOutside","");
        }else{
            mapStatus.put("MaleOutside",mandaysmaleoutside.getText());
        }

        if(mandaysfemaleoutside.getText().length() == 0){
            mapStatus.put("FemaleOutside","");
        }else{
            mapStatus.put("FemaleOutside",mandaysfemaleoutside.getText());
        }
        if ((male_reg != null && !male_reg.isEmpty() && !male_reg.equals("null"))){
        mapStatus.put("MaleRegularCost",Double.parseDouble(male_reg));}
        if ((femmale_reg != null && !femmale_reg.isEmpty() && !femmale_reg.equals("null"))){
        mapStatus.put("FemaleRegularCost",Double.parseDouble(femmale_reg));}
        if ((male_contract != null && !male_contract.isEmpty() && !male_contract.equals("null"))){
        mapStatus.put("MaleOutsideCost",male_contract);}
        if ((female_contract != null && !female_contract.isEmpty() && !female_contract.equals("null"))){
        mapStatus.put("FemaleoutsideCost",female_contract);}


        mapStatus.put("ExpenseType",expensetype.getText());

        if(uomSpinner.getSelectedItemPosition() == 0){
            mapStatus.put("UOMId","");
        }else{
            mapStatus.put("UOMId",Integer.parseInt(uomId));
        }

        if(quantity.getText().equals("")){
            mapStatus.put("Quatity","");
        }else{
            mapStatus.put("Quantity",quantity.getText());
        }

        if (!TextUtils.isEmpty(cost.getText().toString())){
            mapStatus.put("TotalCost",cost.getText());
        }else{
            mapStatus.put("TotalCost"," ");
        }

        if(typespinner.getSelectedItemPosition() == 1) {
            mapStatus.put("Comments",comment.getText());
        }

        if(typespinner.getSelectedItemPosition() == 2) {
            mapStatus.put("Comments",othercomments.getText());
        }


        if(typespinner.getSelectedItemPosition() == 2){
            mapStatus.put("FileName","");
            mapStatus.put("FileLocation",local_ImagePath);
            mapStatus.put("FileExtension",".jpg");
        }else {
            mapStatus.put("FileName","");
            mapStatus.put("FileLocation","");
            mapStatus.put("FileExtension","");
        }

        mapStatus.put("CreatedByUserId",CommonConstants.USER_ID);
        mapStatus.put("CreatedDate",CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        mapStatus.put("UpdatedByUserId",CommonConstants.USER_ID);
        mapStatus.put("UpdatedDate",CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        mapStatus.put("ServerUpdatedStatus",0);

        final List<LinkedHashMap> rmactivityarr = new ArrayList<>();
        rmactivityarr.add(mapStatus);

        dataAccessHandler.insertMyDataa("RMTransactions",
                rmactivityarr, new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {

                        if (success) {
                            Toast.makeText(RMActivityFields.this, "Insert Successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                    });

    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    //Handling on Activity Result
    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

//        final int width = myBitmap.getWidth();
//        final int height = myBitmap.getHeight();
//        Bitmap portraitBitmap = Bitmap.createBitmap(height, width, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(portraitBitmap);
//        c.rotate(90, height/2, width/2);
//        c.drawBitmap(myBitmap, 0,0,null);

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "/3F_Pictures/" + "NurseryPhotos_visit");
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->1" + f.getAbsolutePath());
            local_ImagePath = f.getAbsolutePath();
            Log.d("TAG", "File Saved::--->2" + local_ImagePath);
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    public boolean validations() {

        if (Activity_Name.equalsIgnoreCase("Other")) {

            if (TextUtils.isEmpty(nameofactivity.getText().toString())) {
                UiUtils.showCustomToastMessage("Please Enter Name of the Activity", RMActivityFields.this, 0);
                return false;
            }
        }

        if (typespinner.getSelectedItemPosition() == 0) {
            UiUtils.showCustomToastMessage("Please Select Type", RMActivityFields.this, 0);
            return false;
        }

        if (typespinner.getSelectedItemPosition() == 1) {
            if(labourlyt.getVisibility()== View.VISIBLE){
                if (TextUtils.isEmpty(comment.getText().toString())){
                    UiUtils.showCustomToastMessage("Please Enter Comments", RMActivityFields.this, 0);
                    return false;
                }}

            if (mandaysmale.length() != 0 || mandaysfemale.length() != 0 || mandaysmaleoutside.length() != 0 || mandaysfemaleoutside.length() != 0) {
                //UiUtils.showCustomToastMessage("Please enter atleast one value", RMActivityFields.this, 0);
                return true;
            } else {

                UiUtils.showCustomToastMessage("Please enter atleast one value", RMActivityFields.this, 0);
                return false;
            }
        }

        if (typespinner.getSelectedItemPosition() == 2) {

            if (TextUtils.isEmpty(expensetype.getText().toString())) {
                UiUtils.showCustomToastMessage("Please Enter Expense Type", RMActivityFields.this, 0);
                return false;
            }

            if (TextUtils.isEmpty(cost.getText().toString())){
                UiUtils.showCustomToastMessage("Please Enter Cost", RMActivityFields.this, 0);
                return false;
            }


            if(otherlyt.getVisibility()== View.VISIBLE){
            if (TextUtils.isEmpty(othercomments.getText().toString())){
                UiUtils.showCustomToastMessage("Please Enter Comments", RMActivityFields.this, 0);
                return false;
            }}


            if (TextUtils.isEmpty(quantity.getText().toString())) {
                UiUtils.showCustomToastMessage("Please Enter Quantity", RMActivityFields.this, 0);
                return false;
            }

            if (local_ImagePath == null) {


                UiUtils.showCustomToastMessage("Please Capture Image", RMActivityFields.this, 0);
                return false;
            }

        }
        return true;
    }

}