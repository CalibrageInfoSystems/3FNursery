package com.oilpalm3f.nursery.ui;

import static com.oilpalm3f.nursery.common.CommonUtils.REQUEST_CAM_PERMISSIONS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.utils.UiUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RMActivityFields extends AppCompatActivity {

    Spinner typespinner, uomSpinner;
    LinearLayout labourlyt, otherlyt;
    EditText mandaysmale, mandaysfemale, mandaysmaleoutside, mandaysfemaleoutside;
    EditText expensetype, quantity;
    ImageView imageView;
    Button submitBtn;
    int labourcost = 10;
    TextView cost;

    public static final String LOG_TAG = RMActivityFields.class.getSimpleName();

    private int GALLERY = 1, CAMERA = 2;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static  String  local_ImagePath  = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmfields);

        init();
        setviews();
    }

    private void init() {

        typespinner = findViewById(R.id.typeSpinner);
        uomSpinner = findViewById(R.id.uomspinner);

        labourlyt = findViewById(R.id.labourslyt);
        otherlyt = findViewById(R.id.otherslyt);

        mandaysmale = findViewById(R.id.mandaysmale);
        mandaysfemale = findViewById(R.id.mandaysfemale);
        mandaysmaleoutside = findViewById(R.id.mandaysmaleoutside);
        mandaysfemaleoutside = findViewById(R.id.mandaysfemaleoutside);

        expensetype = findViewById(R.id.expensetype);
        quantity = findViewById(R.id.quantity);
        imageView = findViewById(R.id.rmimageview);
        cost = findViewById(R.id.cost);

        submitBtn = findViewById(R.id.rmsubmitBtn);

    }

    private void setviews() {

        labourlyt.setVisibility(View.GONE);
        otherlyt.setVisibility(View.GONE);

        String[] typeSpinnerArr = getResources().getStringArray(R.array.typespin_values);
        List<String> typeSpinnerList = Arrays.asList(typeSpinnerArr);
        ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<>(RMActivityFields.this, android.R.layout.simple_spinner_item, typeSpinnerList);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespinner.setAdapter(typeSpinnerAdapter);

        typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

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

            }
        });


        String[] uomSpinnerArr = getResources().getStringArray(R.array.uom_values);
        List<String> uomSpinnerList = Arrays.asList(uomSpinnerArr);
        ArrayAdapter<String> uomSpinnerAdapter = new ArrayAdapter<>(RMActivityFields.this, android.R.layout.simple_spinner_item, uomSpinnerList);
        uomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uomSpinner.setAdapter(uomSpinnerAdapter);



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

                cost.setText(Integer.parseInt(quantity.getText().toString()) * labourcost + "");
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validations()){

                    Toast.makeText(RMActivityFields.this, "Submit Success", Toast.LENGTH_SHORT).show();
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
    public void onActivityResult ( int requestCode, int resultCode, Intent data){

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

        if (typespinner.getSelectedItemPosition() == 0) {
            UiUtils.showCustomToastMessage("Please Select Type", RMActivityFields.this, 0);
            return false;
        }

        if (typespinner.getSelectedItemPosition() == 1) {

            if (mandaysmale.length() != 0 || mandaysfemale.length() != 0 || mandaysmaleoutside.length() != 0 || mandaysfemaleoutside.length() != 0) {
                //UiUtils.showCustomToastMessage("Please enter atleast one value", RMActivityFields.this, 0);
                return true;
            }else{

                UiUtils.showCustomToastMessage("Please enter atleast one value", RMActivityFields.this, 0);
                return false;
            }
        }

        if (typespinner.getSelectedItemPosition() == 2){

            if (TextUtils.isEmpty(expensetype.getText().toString())){
                UiUtils.showCustomToastMessage("Please Enter Expense Type", RMActivityFields.this, 0);
                return false;
            }

            if (uomSpinner.getSelectedItemPosition() == 0) {
                UiUtils.showCustomToastMessage("Please Select UOM Type", RMActivityFields.this, 0);
                return false;
            }

            if (TextUtils.isEmpty(quantity.getText().toString())){
                UiUtils.showCustomToastMessage("Please Enter Quantity", RMActivityFields.this, 0);
                return false;
            }

            if (local_ImagePath == null){

                UiUtils.showCustomToastMessage("Please Capture Image", RMActivityFields.this, 0);
                return false;
            }

        }
            return true;
    }

}