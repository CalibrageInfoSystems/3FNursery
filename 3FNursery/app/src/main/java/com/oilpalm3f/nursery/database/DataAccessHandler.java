package com.oilpalm3f.nursery.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.oilpalm3f.nursery.cloudhelper.ApplicationThread;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.dbmodels.ActivityLog;
import com.oilpalm3f.nursery.dbmodels.ActivityTasks;
import com.oilpalm3f.nursery.dbmodels.Address;
import com.oilpalm3f.nursery.dbmodels.Alerts;
import com.oilpalm3f.nursery.dbmodels.AlertsPlotInfo;
import com.oilpalm3f.nursery.dbmodels.AlertsVisitsInfo;
import com.oilpalm3f.nursery.dbmodels.BasicFarmerDetails;
import com.oilpalm3f.nursery.dbmodels.CheckNurseryAcitivity;
import com.oilpalm3f.nursery.dbmodels.ComplaintRepository;
import com.oilpalm3f.nursery.dbmodels.ComplaintRepositoryRefresh;
import com.oilpalm3f.nursery.dbmodels.ComplaintStatusHistory;
import com.oilpalm3f.nursery.dbmodels.ComplaintTypeXref;
import com.oilpalm3f.nursery.dbmodels.Complaints;
import com.oilpalm3f.nursery.dbmodels.ComplaintsDetails;
import com.oilpalm3f.nursery.dbmodels.ConsignmentData;
import com.oilpalm3f.nursery.dbmodels.ConsignmentDetails;
import com.oilpalm3f.nursery.dbmodels.ConsignmentStatuData;
import com.oilpalm3f.nursery.dbmodels.CookingOil;
import com.oilpalm3f.nursery.dbmodels.CropMaintenanceHistory;
import com.oilpalm3f.nursery.dbmodels.CullinglossFileRepository;
import com.oilpalm3f.nursery.dbmodels.DigitalContract;
import com.oilpalm3f.nursery.dbmodels.Disease;
import com.oilpalm3f.nursery.dbmodels.DisplayData;
import com.oilpalm3f.nursery.dbmodels.ExistingData;
import com.oilpalm3f.nursery.dbmodels.Farmer;
import com.oilpalm3f.nursery.dbmodels.FarmerBank;
import com.oilpalm3f.nursery.dbmodels.FarmerBankRefresh;
import com.oilpalm3f.nursery.dbmodels.FarmerFFBHarvestDetails;
import com.oilpalm3f.nursery.dbmodels.FarmerHistory;
import com.oilpalm3f.nursery.dbmodels.Fertilizer;
import com.oilpalm3f.nursery.dbmodels.FertilizerProvider;
import com.oilpalm3f.nursery.dbmodels.FileRepository;
import com.oilpalm3f.nursery.dbmodels.FileRepositoryRefresh;
import com.oilpalm3f.nursery.dbmodels.FollowUp;
import com.oilpalm3f.nursery.dbmodels.GeoBoundaries;
import com.oilpalm3f.nursery.dbmodels.Harvest;
import com.oilpalm3f.nursery.dbmodels.Healthplantation;
import com.oilpalm3f.nursery.dbmodels.IdentityProof;
import com.oilpalm3f.nursery.dbmodels.IdentityProofFileRepositoryXref;
import com.oilpalm3f.nursery.dbmodels.IdentityProofRefresh;
import com.oilpalm3f.nursery.dbmodels.ImageDetails;
import com.oilpalm3f.nursery.dbmodels.InterCropPlantationXref;
import com.oilpalm3f.nursery.dbmodels.Irrigationhistorymodel;
import com.oilpalm3f.nursery.dbmodels.KrasDataToDisplay;
import com.oilpalm3f.nursery.dbmodels.LandlevellingFields;
import com.oilpalm3f.nursery.dbmodels.LandlordBank;
import com.oilpalm3f.nursery.dbmodels.LandlordIdProof;
import com.oilpalm3f.nursery.dbmodels.LocationTracker;
import com.oilpalm3f.nursery.dbmodels.MarketSurvey;
import com.oilpalm3f.nursery.dbmodels.MissingTressInfo;
import com.oilpalm3f.nursery.dbmodels.MutipleData;
import com.oilpalm3f.nursery.dbmodels.NeighbourPlot;
import com.oilpalm3f.nursery.dbmodels.NurseryAcitivity;
import com.oilpalm3f.nursery.dbmodels.NurseryData;
import com.oilpalm3f.nursery.dbmodels.NurseryDetails;
import com.oilpalm3f.nursery.dbmodels.NurseryIrrigationLog;
import com.oilpalm3f.nursery.dbmodels.NurseryIrrigationLogForDb;
import com.oilpalm3f.nursery.dbmodels.NurseryIrrigationLogXref;
import com.oilpalm3f.nursery.dbmodels.NurseryLabourLog;
import com.oilpalm3f.nursery.dbmodels.NurserySaplingDetails;
import com.oilpalm3f.nursery.dbmodels.NurseryVisitLog;
import com.oilpalm3f.nursery.dbmodels.Nutrient;
import com.oilpalm3f.nursery.dbmodels.Ownershipfilerepository;
import com.oilpalm3f.nursery.dbmodels.Pest;
import com.oilpalm3f.nursery.dbmodels.PestChemicalXref;
import com.oilpalm3f.nursery.dbmodels.Plantation;
import com.oilpalm3f.nursery.dbmodels.PlantationFileRepositoryXref;
import com.oilpalm3f.nursery.dbmodels.Plot;
import com.oilpalm3f.nursery.dbmodels.PlotCurrentCrop;
import com.oilpalm3f.nursery.dbmodels.PlotDetailsObj;
import com.oilpalm3f.nursery.dbmodels.PlotIrrigationTypeXref;
import com.oilpalm3f.nursery.dbmodels.PlotLandlord;
import com.oilpalm3f.nursery.dbmodels.ProspectivePlotsModel;
import com.oilpalm3f.nursery.dbmodels.RecommndFertilizer;
import com.oilpalm3f.nursery.dbmodels.Referrals;
import com.oilpalm3f.nursery.dbmodels.SaplingActivity;
import com.oilpalm3f.nursery.dbmodels.SaplingActivityHistoryModel;
import com.oilpalm3f.nursery.dbmodels.SaplingActivityStatusModel;
import com.oilpalm3f.nursery.dbmodels.SaplingActivityXrefModel;
import com.oilpalm3f.nursery.dbmodels.Saplings;
import com.oilpalm3f.nursery.dbmodels.SoilResource;
import com.oilpalm3f.nursery.dbmodels.Uprootment;
import com.oilpalm3f.nursery.dbmodels.UserDetails;
import com.oilpalm3f.nursery.dbmodels.UserSync;
import com.oilpalm3f.nursery.dbmodels.ViewVisitLog;
import com.oilpalm3f.nursery.dbmodels.Village;
import com.oilpalm3f.nursery.dbmodels.VisitLog;
import com.oilpalm3f.nursery.dbmodels.WaterResource;
import com.oilpalm3f.nursery.dbmodels.Weed;
import com.oilpalm3f.nursery.dbmodels.WhiteFlyAssessment;
import com.oilpalm3f.nursery.dbmodels.YieldAssessment;
import com.oilpalm3f.nursery.helper.CustomCursor;
import com.oilpalm3f.nursery.helper.PrefUtil;
import com.oilpalm3f.nursery.utils.ImageUtility;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class DataAccessHandler<T> {

    private static final String LOG_TAG = DataAccessHandler.class.getName();

    private Context context;
    private SQLiteDatabase mDatabase;
    private String var = "";
    String queryForLookupTable = "select Name from LookUp where id=" + var;
    private int value;

    public DataAccessHandler() {

    }

    SimpleDateFormat simpledatefrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    String currentTime = simpledatefrmt.format(new Date());


    public DataAccessHandler(final Context context) {
        this.context = context;
        try {
            mDatabase = Palm3FoilDatabase.openDataBaseNew();
            DataBaseUpgrade.upgradeDataBase(context, mDatabase);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public DataAccessHandler(final Context context, boolean firstTime) {
        this.context = context;
        try {
            mDatabase = Palm3FoilDatabase.openDataBaseNew();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("WhatistheException", e.toString());
        }
    }

    public String getSingleValue(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return "";
    }

    public Integer getSingleIntValue(String query) {
        Log.v(LOG_TAG, "@@@ query=======int " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getInt(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return null;
    }
    public String getSingleValueInt(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                String isSelect = String.valueOf(mOprQuery.getColumnIndex("IsOptional"));
                return isSelect;
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return null;
    }

    public LinkedHashMap<String, String> getGenericData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        LinkedHashMap<String, String> mGenericData = new LinkedHashMap<>();
        Cursor genericDataQuery = null;
        try {
            genericDataQuery = mDatabase.rawQuery(query, null);
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData.put(genericDataQuery.getString(0), genericDataQuery.getString(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }

    public LinkedHashMap<String, String> getMoreGenericData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        LinkedHashMap<String, String> mGenericData = new LinkedHashMap<>();
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData.put(genericDataQuery.getString(0), genericDataQuery.getString(1) + "-" + genericDataQuery.getString(2) + "-" + genericDataQuery.getString(3));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }


    public LinkedHashMap<String, String> getFarmerDetailsData(String query) {
        LinkedHashMap linkedHashMap = new LinkedHashMap<String, String>();
        Cursor cursor = mDatabase.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String key = cursor.getString(cursor.getColumnIndex("Code"));
                    String value = cursor.getString(cursor.getColumnIndex("FirstName"))
                            + "-" + cursor.getString(cursor.getColumnIndex("ContactNumber"));

                    linkedHashMap.put(key, value);


                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linkedHashMap;

    }


    public long addUserSync(UserSync userSync) {


        ContentValues contentValues = new ContentValues();
        contentValues.put("UserId", userSync.getUserId());
        contentValues.put("App", userSync.getApp());
        contentValues.put("Date", userSync.getDate());
        contentValues.put("MasterSync", userSync.getMasterSync());
        contentValues.put("TransactionSync", userSync.getTransactionSync());
        contentValues.put("ResetData", userSync.getResetData());
        contentValues.put("IsActive", userSync.getIsActive());
        contentValues.put("CreatedByUserId", userSync.getCreatedByUserId());
        contentValues.put("CreatedDate", userSync.getCreatedDate());
        contentValues.put("UpdatedByUserId", userSync.getUpdatedByUserId());
        contentValues.put("UpdatedDate", userSync.getUpdatedDate());
        contentValues.put("ServerUpdatedStatus", userSync.getServerUpdatedStatus());
        return mDatabase.insert("UserSync", null, contentValues);

    }

    public void updateUserSync() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ServerUpdatedStatus", 1);
        mDatabase.update("UserSync", contentValues, "ServerUpdatedStatus='0'", null);
        Log.v("@@@MM", "Updating");
    }

    public void updateMasterSync() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("MasterSync", 1);
        contentValues.put("ServerUpdatedStatus", 0);
        contentValues.put("Date", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        contentValues.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        contentValues.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));

        // mDatabase.update("UserSync",contentValues,"ServerUpdatedStatus='0'",null);
        mDatabase.update("UserSync", contentValues, null, null);
        Log.v("@@@MM", "Updating");
    }

    public void updateTransactionSync() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TransactionSync", 1);
        contentValues.put("ServerUpdatedStatus", 0);
        contentValues.put("Date", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        contentValues.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        contentValues.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));

        // mDatabase.update("UserSync",contentValues,"ServerUpdatedStatus='0'",null);
        mDatabase.update("UserSync", contentValues, null, null);
        Log.v("@@@MM", "Updating");
    }

    public void updateResetDataSync() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ResetData", 1);
        contentValues.put("ServerUpdatedStatus", 0);
        contentValues.put("Date", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        contentValues.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        contentValues.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));

        // mDatabase.update("UserSync",contentValues,"ServerUpdatedStatus='0'",null);
        mDatabase.update("UserSync", contentValues, null, null);
        Log.v("@@@MM", "Updating");
    }


    public String getTwoValues(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        String mGenericData = "";
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData = CommonUtils.twoDForm.format(genericDataQuery.getDouble(0)) + "-" + CommonUtils.twoDForm.format(genericDataQuery.getDouble(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }

    public String getplotAgeandSateId(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        String mGenericData = "";
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData = (genericDataQuery.getInt(0)) + "-" + (genericDataQuery.getInt(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }

    public String getYPHvaluefromBenchMark(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return "";
    }

    public LinkedHashMap<String, Pair> getPairData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        LinkedHashMap<String, Pair> mGenericData = new LinkedHashMap<>();
        Cursor genericDataQuery = null;
        try {
            genericDataQuery = mDatabase.rawQuery(query, null);
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData.put(genericDataQuery.getString(0), Pair.create(genericDataQuery.getString(1), genericDataQuery.getString(2)));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }


    public boolean checkValueExistedInDatabase(final String query) {
        Cursor mOprQuery = mDatabase.rawQuery(query, null);
        Log.e("============>", "checkValueExistedInDatabase" + query+"");
        try {
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return (mOprQuery.getInt(0) > 0);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return false;
    }

    /*    public String  getAreaUnderPalm(String query) {
            Log.v(LOG_TAG, "@@@ query " + query);
            Cursor mOprQuery = null;
            try {
                mOprQuery = mDatabase.rawQuery(query, null);
                if (mOprQuery != null && mOprQuery.moveToFirst()) {
                    return mOprQuery.getString(5);
                }

                return null;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != mOprQuery)
                    mOprQuery.close();

                closeDataBase();
            }
            return "";
        }*/
    public Integer getOnlyOneIntValueFromDb(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getInt(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return null;
    }

    public String getLastVistCodeFromDb(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(mOprQuery.getColumnIndex("Code"));
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return "";
    }

    public String getOnlyOneValueFromDb(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return "";
    }



    public String getOnlyTwoValueFromDb(String query) {
        Log.v(LOG_TAG, "@@@ query " + query);
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0) + "@" + mOprQuery.getString(1);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != mOprQuery)
                mOprQuery.close();

            closeDataBase();
        }
        return "";
    }

    public String getGeneratedFarmerCode(final String query, final String financalYaerDays) {
        String maxNum = getOnlyOneValueFromDb(query);
        String convertedNum = "";
        if (!TextUtils.isEmpty(maxNum)) {
            convertedNum = CommonUtils.serialNumber(Integer.parseInt(maxNum) + 1, 3);
        } else {
            convertedNum = CommonUtils.serialNumber(1, 3);
        }
        StringBuilder farmerCoder = new StringBuilder();

        farmerCoder.append(CommonConstants.stateCode)
                .append(CommonConstants.districtCode)
                .append(CommonConstants.TAB_ID)
                .append(financalYaerDays)
                .append(convertedNum);
        Log.v(LOG_TAG, "@@@ farmer code " + farmerCoder.toString() + " D->" + financalYaerDays + " n->" + convertedNum);
        return farmerCoder.toString();
    }

  /*  public String getGeneratedMarketSurveyCode(final String query) {
        String maxNum = getOnlyOneValueFromDb(query);
//        String convertedNum = "";
//        String maxNum = getOnlyOneValueFromDb(ccQuery);
        int convertedNum = 0;
        if (!TextUtils.isEmpty(maxNum)) {
//            convertedNum = CommonUtils.serialNumber(Integer.parseInt(maxNum) + 1, 6);
            convertedNum = Integer.parseInt(maxNum) + 1;
        } else {
            convertedNum = 1;
        }
        StringBuilder farmerCoder = new StringBuilder();
f
        farmerCoder.append(CommonConstants.MARKET_SURVEY_CODE_PREFIX)
                .append(CommonConstants.FARMER_CODE).append("-").append(String.valueOf(convertedNum));

        Log.v(LOG_TAG, "@@@ MarketSurvey code " + farmerCoder.toString());
        return farmerCoder.toString();
    }*/

    public String getGeneratedPlotId(final String query, final String financalYrDays) {
        String maxNum = getOnlyOneValueFromDb(query);
        String convertedNum = "";
        if (!TextUtils.isEmpty(maxNum)) {
            convertedNum = CommonUtils.serialNumber(Integer.parseInt(maxNum) + 1, 3);
        } else {
            convertedNum = CommonUtils.serialNumber(1, 3);
        }
        StringBuilder farmerCoder = new StringBuilder();
        farmerCoder.append(CommonConstants.stateCodePlot)
                .append(CommonConstants.TAB_ID)
                .append(financalYrDays)
                .append(!convertedNum.isEmpty() ? convertedNum : CommonUtils.serialNumber(1, 3));
        Log.v(LOG_TAG, "@@@ farmer code " + farmerCoder.toString());
        return farmerCoder.toString();
    }

    public String getGenerateHealthPlantImageId(final String query, final String plotCode) {
        String maxNum = getOnlyOneValueFromDb(query);
        String convertedNum = "";
        if (!TextUtils.isEmpty(maxNum)) {
            convertedNum = "001";
        }
        StringBuffer farmerCoder = new StringBuffer();
        farmerCoder.append(plotCode)
                .append(convertedNum);
        Log.v(LOG_TAG, "@@@ farmer code " + farmerCoder.toString());
        return farmerCoder.toString();
    }


    public void updateMasterSyncDate(final boolean isNotFirstTime, String userId) {
        final List<LinkedHashMap> listMap = new ArrayList<>();
        final LinkedHashMap dataMap = new LinkedHashMap();
        dataMap.put(DatabaseKeys.COLUMN_USERID, (null == userId) ? "1" : userId);
        final String finalUserId = userId;
        dataMap.put(DatabaseKeys.COLUMN_UPDATEDON, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY));
        listMap.add(dataMap);
        ApplicationThread.dbPost("MasterVersionTrackingSystem Saving..", "insert", new Runnable() {
            @Override
            public void run() {
                if (isNotFirstTime) {
                    insertData(DatabaseKeys.TABLE_MASTERVERSIONTRACKINGSYSTEM, listMap, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void run() {
                            if (success) {
                                PrefUtil.putBool(context, CommonConstants.IS_MASTER_SYNC_SUCCESS, true);
                                Log.v(LOG_TAG, "@@@ MasterVersionTrackingSystem inserted ");
                            }
                        }
                    });
                } else {
                    String whereCondition = "  where " + DatabaseKeys.COLUMN_USERID + "='" + finalUserId + "'";
                    updateData(DatabaseKeys.TABLE_MASTERVERSIONTRACKINGSYSTEM, listMap, false, whereCondition, null);
                    Log.v(LOG_TAG, "@@@ MasterVersionTrackingSystem updated ");
                }
            }
        });
    }

    /**
     * Inserting data into database
     *
     * @param tableName ---> Table name to insert data
     * @param mapList   ---> map list which contains data
     */
    public synchronized void insertDataOld(String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {
//        if (!ApplicationThread.dbThreadCheck())
//            Log.e(LOG_TAG, "called on non-db thread", new RuntimeException());
        int checkCount = 0;
        boolean errorMessageSent = false;
        try {
            for (int i = 0; i < mapList.size(); i++) {
                checkCount++;
                List<LinkedHashMap.Entry> entryList = new ArrayList<>((mapList.get(i)).entrySet());
                String query = "insert into " + tableName;
                String namestring, valuestring;
                StringBuffer values = new StringBuffer();
                StringBuffer columns = new StringBuffer();
                for (LinkedHashMap.Entry temp : entryList) {
//                    if (temp.getKey().equals("Id"))
//                        continue;
                    columns.append(temp.getKey());
                    columns.append(",");
                    values.append("'");
                    values.append(temp.getValue());
                    values.append("'");
                    values.append(",");
                }
                namestring = "(" + columns.deleteCharAt(columns.length() - 1).toString() + ")";
                valuestring = "(" + values.deleteCharAt(values.length() - 1).toString() + ")";
                query = query + namestring + "values" + valuestring;
                Log.v(getClass().getSimpleName(), "query.." + query);
                Log.v(LOG_TAG, "@@@@ log check " + checkCount + " here " + mapList.size());
                try {
                    mDatabase.execSQL(query);
                } catch (Exception e) {
                    Log.v(LOG_TAG, "@@@ Error while inserting data " + e.getMessage());
                    if (checkCount == mapList.size()) {
                        errorMessageSent = true;
                        if (null != oncomplete)
                            oncomplete.execute(false, "failed to insert data", "");
                    }
                }
                if (checkCount == mapList.size() && !errorMessageSent) {
                    if (null != oncomplete)
                        oncomplete.execute(true, "data inserted successfully", "");
                }
            }
        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            Log.v(LOG_TAG, "@@@@ exception log check " + checkCount + " here " + mapList.size());
            if (checkCount == mapList.size()) {
                if (null != oncomplete)
                    oncomplete.execute(false, "data insertion failed", "" + e.getMessage());
            }
        } finally {
            closeDataBase();
        }
    }

    public synchronized void insertData(boolean fromMaster, String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {
        int checkCount = 0;
        try {
            List<ContentValues> values1 = new ArrayList<>();
            for (int i = 0; i < mapList.size(); i++) {
                checkCount++;
                List<LinkedHashMap.Entry> entryList = new ArrayList<>((mapList.get(i)).entrySet());

                ContentValues contentValues = new ContentValues();
                for (LinkedHashMap.Entry temp : entryList) {
                    String keyToInsert = temp.getKey().toString();
                    if (!fromMaster) {
                        if (keyToInsert.equalsIgnoreCase("Id") && !tableName.equalsIgnoreCase(DatabaseKeys.TABLE_ALERTS) && !tableName.equalsIgnoreCase(DatabaseKeys.TABLE_SAPLING))
                            continue;
                    }
                    if (keyToInsert.equalsIgnoreCase("ServerUpdatedStatus")) {
                        contentValues.put(keyToInsert, "1");
                    } else {
                        contentValues.put(temp.getKey().toString(), temp.getValue().toString());
                    }
                }
                values1.add(contentValues);
            }
            Log.v(LOG_TAG, "@@@@ log check " + checkCount + " here " + values1.size());
            boolean hasError = bulkinserttoTable(values1, tableName);
            if (hasError) {
                Log.v(LOG_TAG, "@@@ Error while inserting data ");
                if (null != oncomplete) {
                    oncomplete.execute(false, "failed to insert data", "");
                }
            } else {
                Log.v(LOG_TAG, "@@@ data inserted successfully for table :" + tableName);
                if (null != oncomplete) {
                    oncomplete.execute(true, "data inserted successfully", "");
                }
            }
        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            Log.v(LOG_TAG, "@@@@ exception log check " + checkCount + " here " + mapList.size());
            if (checkCount == mapList.size()) {
                if (null != oncomplete)
                    oncomplete.execute(false, "data insertion failed", "" + e.getMessage());
            }
        } finally {
            closeDataBase();
        }
    }


    public synchronized void insertMyData(boolean fromMaster, String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {
        int checkCount = 0;
        try {
            List<ContentValues> values1 = new ArrayList<>();
            for (int i = 0; i < mapList.size(); i++) {
                checkCount++;
                List<LinkedHashMap.Entry> entryList = new ArrayList<>((mapList.get(i)).entrySet());

                ContentValues contentValues = new ContentValues();
                for (LinkedHashMap.Entry temp : entryList) {
                    String keyToInsert = temp.getKey().toString();
                    if (!fromMaster) {
                        if (keyToInsert.equalsIgnoreCase("Id") && !tableName.equalsIgnoreCase(DatabaseKeys.TABLE_ALERTS))
                            continue;
                    }
                    if (keyToInsert.equalsIgnoreCase("ServerUpdatedStatus")) {
                        contentValues.put(keyToInsert, "0");
                    } else {
                        contentValues.put(temp.getKey().toString(), temp.getValue().toString());
                    }
                }
                values1.add(contentValues);
            }
            Log.v(LOG_TAG, "@@@@ log check " + checkCount + " here " + values1.size());
            boolean hasError = bulkinserttoTable(values1, tableName);
            if (hasError) {
                Log.v(LOG_TAG, "@@@ Error while inserting data ");
                if (null != oncomplete) {
                    oncomplete.execute(false, "failed to insert data", "");
                }
            } else {
                Log.v(LOG_TAG, "@@@ data inserted successfully for table :" + tableName);
                if (null != oncomplete) {
                    oncomplete.execute(true, "data inserted successfully", "");
                }
            }
        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            Log.v(LOG_TAG, "@@@@ exception log check " + checkCount + " here " + mapList.size());
            if (checkCount == mapList.size()) {
                if (null != oncomplete)
                    oncomplete.execute(false, "data insertion failed", "" + e.getMessage());
            }
        } finally {
            closeDataBase();
        }
    }

    public synchronized void insertData(String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {
        insertData(false, tableName, mapList, oncomplete);
    }

    public synchronized void insertMyDataa(String tableName, List<LinkedHashMap> mapList, final ApplicationThread.OnComplete<String> oncomplete) {
        insertMyData(false, tableName, mapList, oncomplete);
    }

    /**
     * Updating database records
     *
     * @param tableName      ---> Table name to update
     * @param list           ---> List which contains data values
     * @param isClaues       ---> Checking where condition availability
     * @param whereCondition ---> condition
     */
    public synchronized void updateData(String tableName, List<LinkedHashMap> list, Boolean isClaues, String whereCondition, final ApplicationThread.OnComplete<String> oncomplete) {
        boolean isUpdateSuccess = false;
        int checkCount = 0;
        try {
            for (int i = 0; i < list.size(); i++) {
                checkCount++;
                List<Map.Entry> entryList = new ArrayList<Map.Entry>((list.get(i)).entrySet());
                String query = "update " + tableName + " set ";
                String namestring = "";
                Log.v(LOG_TAG, "@@@ query for namestring 1" + query);
                System.out.println("\n==> Size of Entry list: " + entryList.size());
                StringBuffer columns = new StringBuffer();
                for (Map.Entry temp : entryList) {
                    columns.append(temp.getKey());
                    columns.append("='");
                    columns.append(temp.getValue());
                    columns.append("',");
                }
                Log.v(LOG_TAG, "@@@ query for namestring " + namestring);
                namestring = columns.deleteCharAt(columns.length() - 1).toString();
                query = query + namestring + "" + whereCondition;
                mDatabase.execSQL(query);
                isUpdateSuccess = true;
                Log.v(LOG_TAG, "@@@ query for Plantation " + query);
            }
        } catch (Exception e) {
            checkCount++;
            e.printStackTrace();
            isUpdateSuccess = false;
        } finally {
            closeDataBase();
            if (checkCount == list.size()) {
                if (isUpdateSuccess) {
                    Log.v(LOG_TAG, "@@@ data updated successfully for " + tableName);
                    oncomplete.execute(true, null, "data updated successfully for " + tableName);
                } else {
                    oncomplete.execute(false, null, "data updation failed for " + tableName);
                }
            }
        }
    }

    /**
     * Deleting records from database table
     *
     * @param tableName  ---> Table name
     * @param columnName ---> Column name to deleting
     * @param value      ---> Value for where condition
     * @param isWhere    ---> Checking where condition is required or not
     */
    public synchronized void deleteRow(String tableName, String columnName, String value, boolean isWhere, final ApplicationThread.OnComplete<String> onComplete) {
        boolean isDataDeleted = true;
//        if (!ApplicationThread.dbThreadCheck())
//            Log.e(LOG_TAG, "called on non-db thread", new RuntimeException());

        try {
//            mDatabase = palm3FoilDatabase.getWritableDatabase();
            String query = "delete from " + tableName;
            if (isWhere) {
                query = query + " where " + columnName + " = '" + value + "'";
            }
            mDatabase.execSQL(query);
        } catch (Exception e) {
            isDataDeleted = false;
            Log.e(LOG_TAG, "@@@ master data deletion failed for " + tableName + " error is " + e.getMessage());
            onComplete.execute(false, null, "master data deletion failed for " + tableName + " error is " + e.getMessage());
        } finally {
            closeDataBase();

            if (isDataDeleted) {
                Log.v(LOG_TAG, "@@@ master data deleted successfully for " + tableName);
                onComplete.execute(true, null, "master data deleted successfully for " + tableName);
            }

        }
    }

    public ArrayList<String> getListOfCodes(final String query) {
        ArrayList<String> plotCodes = new ArrayList<>();
        Cursor paCursor = null;
        try {
            paCursor = mDatabase.rawQuery(query, null);
            if (paCursor.moveToFirst()) {
                do {
                    plotCodes.add(paCursor.getString(0).trim());
                } while (paCursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != paCursor)
                paCursor.close();

            closeDataBase();
        }
        return plotCodes;
    }

    public String getCountValue(String query) {

        Log.v(LOG_TAG, "@@@ getCountValue for " + query);
//        mDatabase = palm3FoilDatabase.getWritableDatabase();
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mOprQuery.close();
            closeDataBase();
        }
        return "";
    }

    public String getdeleteDuplicateValue(String query) {
//        mDatabase = palm3FoilDatabase.getWritableDatabase();
        Cursor mOprQuery = null;
        try {
            mOprQuery = mDatabase.rawQuery(query, null);
            if (mOprQuery != null && mOprQuery.moveToFirst()) {
                return mOprQuery.getString(0);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mOprQuery.close();
            closeDataBase();
        }
        return "";
    }

    public synchronized void insertImageData(String code, String farmercode, String imagepath, String serverUpdatedStatus) {
        try {
//            mDatabase = palm3FoilDatabase.getWritableDatabase();
            ContentValues update_values = new ContentValues();
            update_values.put(DatabaseKeys.COLUMN_CODE, code);
            update_values.put(DatabaseKeys.COLUMN_FARMERCODE, farmercode);
            update_values.put(DatabaseKeys.COLUMN_MODULEID, 100);
//            update_values.put(DatabaseKeys.COLUMN_PLOTCODE, "");
            update_values.put(DatabaseKeys.COLUMN_PHOTO, imagepath);
            update_values.put(DatabaseKeys.COLUMN_CREATEDDATE, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            update_values.put(DatabaseKeys.COLUMN_CREATEDBYUSERID, CommonConstants.USER_ID);
            update_values.put(DatabaseKeys.COLUMN_CREATEDDATE, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            update_values.put(DatabaseKeys.COLUMN_UPDATEDBYUSERID, CommonConstants.USER_ID);
            update_values.put(DatabaseKeys.COLUMN_UPDATEDDATE, CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            update_values.put(DatabaseKeys.COLUMN_SERVERUPDATEDSTATUS, serverUpdatedStatus);
            mDatabase.insert(DatabaseKeys.TABLE_PICTURE_REPORTING, null, update_values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDataBase();
        }
    }

    public void closeDataBase() {
//        if (mDatabase != null)
//            mDatabase.close();
    }

    public void executeRawQuery(String query) {
        try {
            if (mDatabase != null) {
                mDatabase.execSQL(query);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public List<Pair> getOnlyPairData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        List<Pair> mGenericData = new ArrayList<>();
//        mDatabase = palm3FoilDatabase.getWritableDatabase();
        Cursor genericDataQuery = null;
        try {
            genericDataQuery = mDatabase.rawQuery(query, null);
            if (genericDataQuery.moveToFirst()) {
                do {
                    mGenericData.add(Pair.create(genericDataQuery.getString(0), genericDataQuery.getString(1)));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }


    public List<NurserySaplingDetails> getNurserySaplingDetails(final String query) {
        List<NurserySaplingDetails> nurserySaplingDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    NurserySaplingDetails saplingDetails = new NurserySaplingDetails();
                    saplingDetails.setNurseryId(cursor.getInt(cursor.getColumnIndex("NurseryId")));
                    saplingDetails.setCropVarietyId(cursor.getInt(cursor.getColumnIndex("CropVarietyId")));
                    saplingDetails.setSaplingSourceId(cursor.getInt(cursor.getColumnIndex("SaplingSourceId")));
                    saplingDetails.setSaplingVendorId(cursor.getInt(cursor.getColumnIndex("SaplingVendorId")));
                    saplingDetails.setNoOfSaplingsDispatched(cursor.getInt(cursor.getColumnIndex("NoOfSaplingsDispatched")));

                    nurserySaplingDetails.add(saplingDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return nurserySaplingDetails;
    }

    public List<Village> getVillageDetails(final String query) {
        List<Village> nurserySaplingDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    Village saplingDetails = new Village();
                    saplingDetails.setName(cursor.getString(cursor.getColumnIndex("Name")));


                    nurserySaplingDetails.add(saplingDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return nurserySaplingDetails;
    }


    public void getFarmerDetailsForSearch(String key, int offset, int limit, final ApplicationThread.OnComplete onComplete) {
        List<BasicFarmerDetails> farmerDetails = new ArrayList<>();
        Cursor cursor = null;
        String query = null;
        if (CommonUtils.isFromConversion()) {
            query = Queries.getInstance().getFilterBasedFarmers(83, key, offset, limit);
        } else if (CommonUtils.isFromCropMaintenance()) {
            query = Queries.getInstance().getFilterBasedFarmersCrop(key, offset, limit);
        } else if (CommonUtils.isViewProspectiveFarmers()) {
            query = Queries.getInstance().getFilterBasedProspectiveFarmers(81, key, offset, limit);
        } else if (CommonUtils.isFromFollowUp()) {
            query = Queries.getInstance().getFilterBasedFarmersFollowUp(key, offset, limit);
        } else if (CommonUtils.isComplaint()) {
            query = Queries.getInstance().getFilterBasedFarmersCrop(key, offset, limit);
        } else if (CommonUtils.isPlotSplitFarmerPlots()) {
            query = Queries.getInstance().getFilterBasedFarmersCropRetake(key, offset, limit);
        } else if (CommonUtils.isVisitRequests()) {
            query = Queries.getInstance().getVisitRequestFarmers(key, offset, limit);
            Log.v("@@@query", "2");
        } else {
            query = Queries.getInstance().getFarmersDataForWithOffsetLimit(key, offset, limit);
            Log.v("@@@query", "1");
        }

        Log.v(LOG_TAG, "Query for getting farmers " + query);

        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    BasicFarmerDetails farmlanddetails = new BasicFarmerDetails();
                    farmlanddetails.setFarmerCode(cursor.getString(cursor.getColumnIndex("Code")));
                    farmlanddetails.setFarmerFirstName(cursor.getString(cursor.getColumnIndex("FirstName")));
                    farmlanddetails.setFarmerMiddleName(cursor.getString(cursor.getColumnIndex("MiddleName")));
                    farmlanddetails.setFarmerLastName(cursor.getString(cursor.getColumnIndex("LastName")));
                    farmlanddetails.setFarmerStateName(cursor.getString(cursor.getColumnIndex("StateName")));
                    farmlanddetails.setPrimaryContactNum(cursor.getString(cursor.getColumnIndex("ContactNumber")));
                    farmlanddetails.setFarmerVillageName(cursor.getString(cursor.getColumnIndex("Name")));
                    farmlanddetails.setPhotoLocation(cursor.getString(cursor.getColumnIndex("FileLocation")));
                    farmlanddetails.setPhotoName(cursor.getString(cursor.getColumnIndex("FileName")));
                    farmlanddetails.setFileExtension(cursor.getString(cursor.getColumnIndex("FileExtension")));
                    farmlanddetails.setFarmerFatherName(cursor.getString(cursor.getColumnIndex("GuardianName")));
                    farmerDetails.add(farmlanddetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "getting failed fromLocalDb" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDataBase();

            onComplete.execute(true, farmerDetails, "getting data");
        }
    }

    public void getFarmerDetailsForSearchYield(String key, int offset, int limit, final ApplicationThread.OnComplete onComplete) {
        List<BasicFarmerDetails> farmerDetails = new ArrayList<>();
        Cursor cursor = null;
        String query = null;
        query = Queries.getInstance().getFarmersDataForWithOffsetLimit(key, offset, limit);
        Log.v("@@@query", "1");


        Log.v(LOG_TAG, "Query for getting farmers " + query);

        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    BasicFarmerDetails farmlanddetails = new BasicFarmerDetails();
                    farmlanddetails.setFarmerCode(cursor.getString(cursor.getColumnIndex("Code")));
                    farmlanddetails.setFarmerFirstName(cursor.getString(cursor.getColumnIndex("FirstName")));
                    farmlanddetails.setFarmerMiddleName(cursor.getString(cursor.getColumnIndex("MiddleName")));
                    farmlanddetails.setFarmerLastName(cursor.getString(cursor.getColumnIndex("LastName")));
                    farmlanddetails.setFarmerStateName(cursor.getString(cursor.getColumnIndex("StateName")));
                    farmlanddetails.setPrimaryContactNum(cursor.getString(cursor.getColumnIndex("ContactNumber")));
                    farmlanddetails.setFarmerVillageName(cursor.getString(cursor.getColumnIndex("Name")));
                    farmlanddetails.setPhotoLocation(cursor.getString(cursor.getColumnIndex("FileLocation")));
                    farmlanddetails.setPhotoName(cursor.getString(cursor.getColumnIndex("FileName")));
                    farmlanddetails.setFileExtension(cursor.getString(cursor.getColumnIndex("FileExtension")));
                    farmlanddetails.setFarmerFatherName(cursor.getString(cursor.getColumnIndex("GuardianName")));
                    farmerDetails.add(farmlanddetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "getting failed fromLocalDb" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDataBase();

            onComplete.execute(true, farmerDetails, "getting data");
        }
    }

    public List<FarmerFFBHarvestDetails> getFFBHarvestRefresh(String query) {
        List<FarmerFFBHarvestDetails> listFarmerActivities = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    try {
                        FarmerFFBHarvestDetails rec = new FarmerFFBHarvestDetails();
                        rec.setFFBHarvestId(cursor.getInt(0));
                        rec.setFarmerCode(cursor.getString(1));
                        rec.setPlotCode(cursor.getString(2));
                        rec.setCollectionCentreId(cursor.getInt(3));
                        rec.setModeOfTransport(cursor.getString(4));
                        rec.setHarvestingMethod(cursor.getString(5));
                        rec.setWagesPerDay(cursor.getDouble(6));
                        rec.setContractRsPerMonth(cursor.getDouble(7));
                        rec.setContractRsPerAnum(cursor.getDouble(8));
                        rec.setTypeOfHarvesting(cursor.getString(9));
                        rec.setContractorPitch(cursor.getInt(10));
                        rec.setFarmerConsent(cursor.getInt(11));
                        rec.setComments(cursor.getString(12));
                        rec.setCreatedBy(cursor.getString(13));
                        rec.setCreatedDate(cursor.getString(14));
                        rec.setUpdatedBy(cursor.getString(15));
                        rec.setUpdatedDate(cursor.getString(16));
                        rec.setServerUpdatedStatus(cursor.getInt(17));
                        listFarmerActivities.add(rec);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();

        }
        return listFarmerActivities;
    }

    public List<String> getSingleListData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        List<String> mGenericData = new ArrayList<>();
        Cursor genericDataQuery = null;
        try {
            genericDataQuery = mDatabase.rawQuery(query, null);
            if (genericDataQuery.moveToFirst()) {
                do {
                    String plotCode = genericDataQuery.getString(0);
                    mGenericData.add(plotCode);
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();
            closeDataBase();
        }
        return mGenericData;
    }

    public List<String> getZombiData(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        List<String> mGenericData = new ArrayList<>();
//        mDatabase = palm3FoilDatabase.getWritableDatabase();
        Cursor genericDataQuery = null;
        try {
            genericDataQuery = mDatabase.rawQuery(query, null);
            if (genericDataQuery.moveToFirst()) {
                do {
                    String plotCode = genericDataQuery.getString(0);
                    if (!TextUtils.isEmpty(plotCode)) {
                        mGenericData.add("'" + plotCode + "'");
                    }
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return mGenericData;
    }


    public T getUserDetails(final String query, int dataReturnType) {
        UserDetails userDetails = null;
        Cursor cursor = null;
        List userDataList = new ArrayList();
        Log.v(LOG_TAG, "@@@ user details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    userDetails = new UserDetails();
                    userDetails.setUserId(cursor.getString(0));
                    userDetails.setUserName(cursor.getString(1));
                    userDetails.setPassword(cursor.getString(2));
                    userDetails.setRoleId(cursor.getInt(3));
                    userDetails.setManagerId(cursor.getInt(4));
                    userDetails.setId(cursor.getString(5));
                    userDetails.setFirstName(cursor.getString(6));
                    userDetails.setTabName(cursor.getString(7));
                    userDetails.setUserCode(cursor.getString(8));
//                    userDetails.setTabletId(cursor.getInt(5));
//                    userDetails.setUserVillageId(cursor.getString(6));
                    if (dataReturnType == 1) {
                        userDataList.add(userDetails);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((dataReturnType == 0) ? userDetails : userDataList);
    }


    public List<PlotDetailsObj> getPlotDetails(String farmerCode, int plotStatus, boolean withoutGps) {
        List<PlotDetailsObj> plotDetailslistObj = new ArrayList<>();
        Cursor cursor = null;
        String query = null;
        if (CommonUtils.isFromCropMaintenance() || CommonUtils.isComplaint()) {
            query = Queries.getInstance().getPlotDetailsForCC(farmerCode.trim(), plotStatus, 89, true);
        } else if (CommonUtils.isFromFollowUp()) {
            query = Queries.getInstance().getPlotDetailsForCC(farmerCode.trim(), plotStatus);
        } else if (CommonUtils.isPlotSplitFarmerPlots()) {
            query = Queries.getInstance().getPlotDetailsForCC(farmerCode.trim(), plotStatus);
        } else if (CommonUtils.isFromConversion()) {
            query = Queries.getInstance().getPlotDetailsForConversion(farmerCode.trim(), plotStatus);
        }
//        if (withoutGps && !CommonUtils.isFromCropMaintenance()) {
//            query = Queries.getInstance().getPlotDetailsForNonGeo(farmerCode.trim(), plotStatus);
//        } else if (CommonUtils.isFromCropMaintenance()) {
//            query = Queries.getInstance().getPlotDetailsForCC(farmerCode.trim(), plotStatus, 89, true);
//        } else {
//            query = Queries.getInstance().getPlotDetailsForCC(farmerCode.trim(), plotStatus);
//        }
        Log.v(LOG_TAG, "Query for getting plots related to farmer " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {
                    PlotDetailsObj plotDetailsObj = new PlotDetailsObj();
                    plotDetailsObj.setPlotID(cursor.getString(0));
                    plotDetailsObj.setTotalPalm(cursor.getString(1));
                    plotDetailsObj.setPlotArea(cursor.getString(2));
                    plotDetailsObj.setGpsPlotArea(cursor.getString(3));
                    plotDetailsObj.setSurveyNumber(cursor.getString(4));
                    plotDetailsObj.setPlotLandMark(cursor.getString(5));
                    plotDetailsObj.setVillageCode(cursor.getString(6));
                    plotDetailsObj.setVillageName(cursor.getString(7));
                    plotDetailsObj.setVillageId(cursor.getString(8));
                    plotDetailsObj.setMandalCode(cursor.getString(9));
                    plotDetailsObj.setFarmerMandalName(cursor.getString(10));
                    plotDetailsObj.setMandalId(cursor.getString(11));
                    plotDetailsObj.setDistrictCode(cursor.getString(12));
                    plotDetailsObj.setFarmerDistrictName(cursor.getString(13));
                    plotDetailsObj.setDistrictId(cursor.getString(14));
                    plotDetailsObj.setStateCode(cursor.getString(15));
                    plotDetailsObj.setFarmerStateName(cursor.getString(16));
                    plotDetailsObj.setStateId(cursor.getString(17));
                    plotDetailsObj.setDateofPlanting(cursor.getString(18));
                    plotDetailslistObj.add(plotDetailsObj);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return plotDetailslistObj;
    }


    public T getAlertsDetails(final String query, int dataReturnType, boolean fromRefresh) {
        Cursor cursor = null;
        Alerts alertDetails = null;
        List alertsDataList = new ArrayList();
        Log.v(LOG_TAG, "@@@ alertDetails  query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    alertDetails = new Alerts();
                    alertDetails.setId(cursor.getInt(0));
                    alertDetails.setName(cursor.getString(1));
                    alertDetails.setDesc(cursor.getString(2));
                    alertDetails.setUserId(cursor.getInt(3));
                    alertDetails.setHTMLDesc(cursor.getString(4));
                    alertDetails.setIsRead(cursor.getInt(5));
                    alertDetails.setPlotCode(cursor.getString(6));
                    alertDetails.setComplaintCode(cursor.getString(7));
                    alertDetails.setAlertTypeId(cursor.getInt(8));
                    alertDetails.setCreatedByUserId(cursor.getInt(9));
                    alertDetails.setCreatedDate(cursor.getString(10));
                    alertDetails.setUpdatedByUserId(cursor.getInt(11));
                    alertDetails.setUpdatedDate(cursor.getString(12));
                    alertDetails.setServerUpdatedStatus(cursor.getInt(13));
                    if (!fromRefresh) {
                        alertDetails.setHTMLDesc(cursor.getString(14));
                    }
                    if (dataReturnType == 1) {
                        alertsDataList.add(alertDetails);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting  alertDetails " + e.getMessage());
        }
        return (T) ((dataReturnType == 0) ? alertDetails : alertsDataList);
    }

    public boolean bulkinserttoTable(List<ContentValues> cv, final String tableName) {
        boolean isError = false;
        mDatabase.beginTransaction();
        try {
            for (int i = 0; i < cv.size(); i++) {
                ContentValues stockResponse = cv.get(i);

                // Added BY MAHESH - CIS   06082021 SAPLING TABLE ID NOT INCREMENT VALUE
                long id = mDatabase.insert(tableName, null, stockResponse);
                if (id < 0) {
                    isError = true;
                }


            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return isError;
    }

    public boolean bulkUpdateToTable(List<ContentValues> cv, final String tableName) {
        boolean isError = false;
        mDatabase.beginTransaction();
        try {
            for (int i = 0; i < cv.size(); i++) {
                ContentValues stockResponse = cv.get(i);
                long id = mDatabase.replaceOrThrow(tableName, null, stockResponse);
                if (id < 0) {
                    isError = true;
                }
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return isError;
    }

    public <T> ArrayList<T> SelectAll() {
        Class<T> foo = (Class<T>) Farmer.class;
        Cursor cursor = mDatabase.rawQuery("select * from Farmer", null);
        ArrayList<T> list = new ArrayList<>();

        Field[] fields = foo.getFields();
        try {
            Constructor<T> constructor = foo.getConstructor(foo);
            list.add(constructor.newInstance());
        } catch (Exception ex) {
            return list;
        }
        return null;
    }

    public T getSelectedFarmerAddress(final String query, final int type) {
        List<Address> farmerAddrList = new ArrayList<>();
        Address mAddress = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mAddress = new Address();
                    mAddress.setCode(cursor.getString(1));
                    mAddress.setAddressline1(cursor.getString(2));
                    mAddress.setAddressline2(cursor.getString(3));
                    mAddress.setAddressline3(cursor.getString(4));
                    mAddress.setLandmark(cursor.getString(5));
                    mAddress.setVillageid(cursor.getInt(6));
                    mAddress.setMandalid(cursor.getInt(7));
                    mAddress.setDistictid(cursor.getInt(8));
                    mAddress.setStateid(cursor.getInt(9));
                    mAddress.setCountryid(cursor.getInt(10));
                    mAddress.setPincode(cursor.getInt(11));
                    mAddress.setIsactive(cursor.getInt(12));
                    mAddress.setCreatedbyuserid(cursor.getInt(13));
                    mAddress.setCreateddate(cursor.getString(14));
                    mAddress.setUpdatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
                    mAddress.setUpdateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    mAddress.setServerupdatedstatus(Integer.parseInt(CommonConstants.ServerUpdatedStatus));
                    if (type == 1) {
                        farmerAddrList.add(mAddress);
                        mAddress = null;
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mAddress : farmerAddrList);
    }


    public T getSelectedNurseryData(final String query, final int type) {
        List<Address> farmerAddrList = new ArrayList<>();
        Address mAddress = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mAddress = new Address();
                    mAddress.setCode(cursor.getString(1));
                    mAddress.setAddressline1(cursor.getString(2));
                    mAddress.setAddressline2(cursor.getString(3));
                    mAddress.setAddressline3(cursor.getString(4));
                    mAddress.setLandmark(cursor.getString(5));
                    mAddress.setVillageid(cursor.getInt(6));
                    mAddress.setMandalid(cursor.getInt(7));
                    mAddress.setDistictid(cursor.getInt(8));
                    mAddress.setStateid(cursor.getInt(9));
                    mAddress.setCountryid(cursor.getInt(10));
                    mAddress.setPincode(cursor.getInt(11));
                    mAddress.setIsactive(cursor.getInt(12));
                    mAddress.setCreatedbyuserid(cursor.getInt(13));
                    mAddress.setCreateddate(cursor.getString(14));
                    mAddress.setUpdatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
                    mAddress.setUpdateddate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    mAddress.setServerupdatedstatus(Integer.parseInt(CommonConstants.ServerUpdatedStatus));
                    if (type == 1) {
                        farmerAddrList.add(mAddress);
                        mAddress = null;
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mAddress : farmerAddrList);
    }


    public T getSelectedFarmerData(final String query, int type) {

        List<Farmer> farmerRefresh = new ArrayList<>();
        Cursor cursor = null;
        Farmer mFarmer = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFarmer = new Farmer();
                    mFarmer.setCode(cursor.getString(1));
                    mFarmer.setCountryid(cursor.getInt(2));
                    mFarmer.setRegionid(null);
                    mFarmer.setStateid(cursor.getInt(4));
                    mFarmer.setDistictid(cursor.getInt(5));
                    mFarmer.setMandalid(cursor.getInt(6));
                    mFarmer.setVillageid(cursor.getInt(7));
                    mFarmer.setSourceofcontacttypeid((cursor.getInt(8) == 0) ? null : cursor.getInt(8));
                    mFarmer.setTitletypeid((cursor.getInt(9) == 0) ? null : cursor.getInt(9));
                    mFarmer.setFirstname(cursor.getString(10));
                    mFarmer.setMiddlename(cursor.getString(11));
                    mFarmer.setLastname(cursor.getString(12));
                    mFarmer.setGuardianname(cursor.getString(13));
                    mFarmer.setMothername(cursor.getString(14));
                    mFarmer.setGendertypeid(cursor.getInt(15));
                    mFarmer.setContactnumber(cursor.getString(16));
                    mFarmer.setMobilenumber(cursor.getString(17));
                    mFarmer.setDOB(cursor.getString(18));
                    mFarmer.setAge(cursor.getInt(19));
                    mFarmer.setEmail(cursor.getString(20));
                    mFarmer.setCategorytypeid(cursor.getInt(21));
                    mFarmer.setAnnualincometypeid((cursor.getInt(22) == 0) ? null : cursor.getInt(22));
                    mFarmer.setAddresscode(cursor.getString(23));
                    mFarmer.setEducationtypeid((cursor.getInt(24) == 0) ? null : cursor.getInt(24));
                    mFarmer.setIsactive(cursor.getInt(25));
                    mFarmer.setCreatedbyuserid(cursor.getInt(26));
                    mFarmer.setCreateddate(cursor.getString(27));
                    mFarmer.setUpdatedDate(cursor.getString(28));
                    mFarmer.setInActivatedByUserId(null);
                    mFarmer.setInactivatedDate(null);
                    mFarmer.setInactivatedReasonTypeId(null);
                    mFarmer.setUpdatedbyuserid(Integer.parseInt(CommonConstants.USER_ID));
                    mFarmer.setServerupdatedstatus(0);
                    mFarmer.setUpdatedDate(CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));


                    if (type == 1) {
                        farmerRefresh.add(mFarmer);

                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFarmer : farmerRefresh);
    }


    public int getSelectedRetakeGeoTag(final String query) {

        Plot mPlot = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ Plot IsRetakeGeoTag details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                 /*   mPlot = new Plot();
                    mPlot.setIsRetakeGeoTagRequired(cursor.getInt(0));*/

                    value = cursor.getInt(0);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return value;
    }

    public T getSelectedPlotData(final String query, final int type) {
        List<Plot> plotList = new ArrayList<>();
        Plot mPlot = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ address details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlot = new Plot();
                    mPlot.setCode(cursor.getString(1));
                    mPlot.setFarmercode(cursor.getString(2));
                    mPlot.setIsBoundryFencing((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mPlot.setTotalplotarea(cursor.getDouble(4));
                    mPlot.setTotalpalmarea(cursor.getDouble(5));
                    mPlot.setGpsplotarea(cursor.getDouble(6));
                    mPlot.setCropincometypeid((cursor.getInt(7) == 0) ? null : cursor.getInt(7));
                    mPlot.setAddesscode(cursor.getString(8));
                    mPlot.setSurveynumber(cursor.getString(9));
                    mPlot.setAdangalnumber(cursor.getString(10));
                    mPlot.setLeftoutarea(cursor.getDouble(11));
                    mPlot.setLeftoutareacropid((cursor.getInt(12) == 0) ? null : cursor.getInt(12));
                    mPlot.setPlotownershiptypeid((cursor.getInt(13) == 0) ? null : cursor.getInt(13));
                    if (cursor.getInt(14) == 0) {
                        mPlot.setIsplothandledbycaretaker(null);
                    } else {
                        Integer careTakerType = cursor.getInt(14);
                        if (careTakerType != null && careTakerType == 2) {
                            careTakerType = 0;
                        }
                        mPlot.setIsplothandledbycaretaker(careTakerType);
                    }
                    mPlot.setCaretakername(cursor.getString(15));
                    mPlot.setCaretakercontactnumber(cursor.getString(16));
                    mPlot.setIsActive(cursor.getInt(17));
                    mPlot.setCreatedbyuserid(cursor.getInt(18));
                    mPlot.setCreateddate(cursor.getString(19));
                    mPlot.setUpdatedbyuserid(cursor.getInt(20));
                    mPlot.setUpdateddate(cursor.getString(21));
                    mPlot.setServerupdatedstatus(cursor.getInt(22));
                    mPlot.setComments(cursor.getString(23));
                    mPlot.setIsPLotSubsidySubmission((cursor.getInt(24) == 0) ? null : cursor.getInt(24));
                    mPlot.setIsPLotHavingIdCard((cursor.getInt(25) == 0) ? null : cursor.getInt(25));
                    mPlot.setIsGeoBoundariesVerification((cursor.getInt(26) == 0) ? null : cursor.getInt(26));
                    mPlot.setSuitablePalmOilArea(cursor.getDouble(27));
                    mPlot.setDateofPlanting(cursor.getString(28));
                    mPlot.setSwapingReasonId((cursor.getInt(29) == 0) ? null : cursor.getInt(29));
                    mPlot.setOriginCode((cursor.getString(30)));
                    mPlot.setReasonTypeId((cursor.getInt(31)));
                    mPlot.setInactivatedDate((cursor.getString(32)));
                    mPlot.setInactivatedByUserId((cursor.getInt(33)));
                    mPlot.setInActiveReasonTypeId((cursor.getInt(34)));
                    mPlot.setPlansToPlanInFuture((cursor.getInt(35)));
                    mPlot.setIsRetakeGeoTagRequired((cursor.getInt(36)));
                    mPlot.setTotalAreaUnderHorticulture(cursor.getFloat(37));
                    mPlot.setLandTypeId((cursor.getInt(38) == 0) ? null : cursor.getInt(38));
                    mPlot.setSaplingsplanted((cursor.getInt(39) == 0) ? null : cursor.getInt(39));

                    if (type == 1) {
                        plotList.add(mPlot);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPlot : plotList);
    }

    public T getSelectedPlotCurrentCropData(final String query, final int type) {
        PlotCurrentCrop mPlotCurrentCrop = null;
        List<PlotCurrentCrop> plotCurrentCropList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ PlotCurrentCrop query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {
                    mPlotCurrentCrop = new PlotCurrentCrop();
                    mPlotCurrentCrop.setPlotcode(cursor.getString(1));
                    mPlotCurrentCrop.setCropid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mPlotCurrentCrop.setCurrentcroparea(cursor.getDouble(3));
                    mPlotCurrentCrop.setIsactive(cursor.getInt(4));
                    mPlotCurrentCrop.setCreatedbyuserid(cursor.getInt(5));
                    mPlotCurrentCrop.setCreateddate(cursor.getString(6));
                    mPlotCurrentCrop.setUpdatedbyuserid(cursor.getInt(7));
                    mPlotCurrentCrop.setUpdateddate(cursor.getString(8));
                    mPlotCurrentCrop.setServerupdatedstatus(cursor.getInt(9));
                    if (type == 1) {
                        plotCurrentCropList.add(mPlotCurrentCrop);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPlotCurrentCrop : plotCurrentCropList);
    }

    public T getSelectedNeighbourPlotData(final String query, final int type) {
        NeighbourPlot mNeighbourPlot = null;
        List<NeighbourPlot> neighbourPlotList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ NeighbourPlot query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mNeighbourPlot = new NeighbourPlot();
                    mNeighbourPlot.setPlotCode(cursor.getString(1));
                    mNeighbourPlot.setName(cursor.getString(2));
                    mNeighbourPlot.setCropid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mNeighbourPlot.setIsactive(cursor.getInt(4));
                    mNeighbourPlot.setCreatedbyuserid(cursor.getInt(5));
                    mNeighbourPlot.setCreateddate(cursor.getString(6));
                    mNeighbourPlot.setUpdatedbyuserid(cursor.getInt(7));
                    mNeighbourPlot.setUpdateddate(cursor.getString(8));
                    mNeighbourPlot.setServerupdatedstatus(cursor.getInt(9));
                    if (type == 1) {
                        neighbourPlotList.add(mNeighbourPlot);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mNeighbourPlot : neighbourPlotList);
    }

    public T getSelectedIdProofsData(final String query, final int type) {
        IdentityProof mIdentityProof = null;
        List<IdentityProof> mIdentityProofList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ IdentityProof query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mIdentityProof = new IdentityProof();
                    mIdentityProof.setFarmercode(cursor.getString(1));
                    mIdentityProof.setIdprooftypeid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mIdentityProof.setIdproofnumber(cursor.getString(3));
                    mIdentityProof.setIsActive(cursor.getInt(4));
                    mIdentityProof.setCreatedbyuserid(cursor.getInt(5));
                    mIdentityProof.setCreatedDate(cursor.getString(6));
                    mIdentityProof.setUpdatedbyuserid(cursor.getInt(7));
                    mIdentityProof.setUpdatedDate(cursor.getString(8));
                    mIdentityProof.setServerUpdatedStatus(cursor.getInt(9));
                    if (type == 1) {
                        mIdentityProofList.add(mIdentityProof);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mIdentityProof : mIdentityProofList);
    }

    public T getIdProofsData(final String query, final int type) {
        IdentityProofRefresh mIdentityProof = null;
        List<IdentityProofRefresh> mIdentityProofList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ IdentityProof query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mIdentityProof = new IdentityProofRefresh();
                    mIdentityProof.setFarmercode(cursor.getString(1));
                    mIdentityProof.setIdprooftypeid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mIdentityProof.setIdproofnumber(cursor.getString(3));
                    mIdentityProof.setIsActive(cursor.getInt(4));
                    mIdentityProof.setCreatedbyuserid(cursor.getInt(5));
                    mIdentityProof.setCreatedDate(cursor.getString(6));
                    mIdentityProof.setUpdatedbyuserid(cursor.getInt(7));
                    mIdentityProof.setUpdatedDate(cursor.getString(8));
                    mIdentityProof.setServerUpdatedStatus(cursor.getInt(9));
                    mIdentityProof.setFileName(cursor.getString(10));
                    mIdentityProof.setFileLocation(cursor.getString(11));
                    mIdentityProof.setFileExtension(cursor.getString(12));
                    File imgFile = new File(mIdentityProof.getFileLocation());
                    if (imgFile.exists()) {
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(imgFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bm = BitmapFactory.decodeStream(fis);
                        bm = ImageUtility.rotatePicture(90, bm);
                        String base64string = ImageUtility.convertBitmapToString(bm);
                        mIdentityProof.setByteImage(base64string);
                    } else {
                        mIdentityProof.setByteImage("");
                    }

                    if (type == 1) {
                        mIdentityProofList.add(mIdentityProof);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mIdentityProof : mIdentityProofList);
    }


    public T getFarmerHistoryData(final String query) {
        FarmerHistory mFarmerHistory = null;
        List<FarmerHistory> mFarmerHistoryList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmerHistory details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFarmerHistory = new FarmerHistory();
                    mFarmerHistory.setFarmercode(cursor.getString(1));
                    mFarmerHistory.setPlotcode(cursor.getString(2));
                    mFarmerHistory.setStatustypeid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mFarmerHistory.setIsactive(cursor.getInt(4));
                    mFarmerHistory.setCreatedbyuserid(cursor.getInt(5));
                    mFarmerHistory.setCreateddate(cursor.getString(6));
                    mFarmerHistory.setUpdatedbyuserid(cursor.getInt(7));
                    mFarmerHistory.setUpdateddate(cursor.getString(8));
                    mFarmerHistory.setServerUpdatedStatus(cursor.getInt(9));

                    mFarmerHistoryList.add(mFarmerHistory);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting Farmer History details " + e.getMessage());
        }

        return (T) (mFarmerHistoryList);
    }

    public T getSelectedFarmerBankData(final String query, final int type) {
        FarmerBank mFarmerBank = null;
        List<FarmerBank> mFarmerBankList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFarmerBank = new FarmerBank();
                    mFarmerBank.setFarmercode(cursor.getString(1));
                    mFarmerBank.setBankid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mFarmerBank.setAccountholdername(cursor.getString(3));
                    mFarmerBank.setAccountnumber(cursor.getString(4));
                    mFarmerBank.setFilename(cursor.getString(5));
                    mFarmerBank.setFilelocation(cursor.getString(6));
                    mFarmerBank.setFileextension(cursor.getString(7));
                    mFarmerBank.setIsActive(cursor.getInt(8));
                    mFarmerBank.setCreatedbyuserid(cursor.getInt(9));
                    mFarmerBank.setCreatedDate(cursor.getString(10));
                    mFarmerBank.setUpdatedbyuserid(cursor.getInt(11));
                    mFarmerBank.setUpdatedDate(cursor.getString(12));
                    mFarmerBank.setServerUpdatedStatus(cursor.getInt(13));
                    // mFarmerBank.setBranchId((cursor.getInt(14)== 0) ? null :cursor.getInt(14));
                    if (type == 1) {
                        mFarmerBankList.add(mFarmerBank);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFarmerBank : mFarmerBankList);
    }

    public T getFarmerBankData(final String query, final int type) {
        FarmerBankRefresh mFarmerBank = null;
        List<FarmerBankRefresh> mFarmerBankList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFarmerBank = new FarmerBankRefresh();
                    mFarmerBank.setFarmercode(cursor.getString(1));
                    mFarmerBank.setBankid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mFarmerBank.setAccountholdername(cursor.getString(3));
                    mFarmerBank.setAccountnumber(cursor.getString(4));
                    mFarmerBank.setFilename(cursor.getString(5));
                    mFarmerBank.setFilelocation(cursor.getString(6));
                    mFarmerBank.setFileextension(cursor.getString(7));
                    mFarmerBank.setIsActive(cursor.getInt(8));
                    mFarmerBank.setCreatedbyuserid(cursor.getInt(9));
                    mFarmerBank.setCreatedDate(cursor.getString(10));
                    mFarmerBank.setUpdatedbyuserid(cursor.getInt(11));
                    mFarmerBank.setUpdatedDate(cursor.getString(12));
                    mFarmerBank.setServerUpdatedStatus(cursor.getInt(13));
                    // mFarmerBank.setBranchId((cursor.getInt(14)== 0) ? null :cursor.getInt(14));
                    File imgFile = new File(mFarmerBank.getFilelocation());
                    if (imgFile.exists()) {
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(imgFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bm = BitmapFactory.decodeStream(fis);
                        bm = ImageUtility.rotatePicture(90, bm);
                        String base64string = ImageUtility.convertBitmapToString(bm);
                        mFarmerBank.setByteImage(base64string);
                    } else {
                        mFarmerBank.setByteImage("");
                    }
                    if (type == 1) {
                        mFarmerBankList.add(mFarmerBank);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFarmerBank : mFarmerBankList);
    }

    public T getWaterResourceData(final String query, final int type) {
        WaterResource mWaterResource = null;
        List<WaterResource> mWaterResourceList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mWaterResource = new WaterResource();
                    mWaterResource.setPlotcode(cursor.getString(1));
                    mWaterResource.setSourceofwaterid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mWaterResource.setBorewellnumber((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mWaterResource.setWaterdischargecapacity(cursor.getDouble(4));
                    mWaterResource.setCanalwater(cursor.getDouble(5));
                    mWaterResource.setIsactive(cursor.getInt(6));
                    mWaterResource.setCreatedbyuserid(cursor.getInt(7));
                    mWaterResource.setCreateddate(cursor.getString(8));
                    mWaterResource.setUpdatedbyuserid(cursor.getInt(9));
                    mWaterResource.setUpdateddate(cursor.getString(10));
                    mWaterResource.setServerupdatedstatus(cursor.getInt(11));
                    if (type == 1) {
                        mWaterResourceList.add(mWaterResource);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mWaterResource : mWaterResourceList);
    }

    public T getSoilResourceData(final String query, final int type) {
        SoilResource mSoilResource = null;
        List<SoilResource> mSoilResourceeList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mSoilResource = new SoilResource();
                    mSoilResource.setPlotcode(cursor.getString(1));
                    mSoilResource.setSoiltypeid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mSoilResource.setIspoweravailable((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mSoilResource.setAvailablepowerhours(cursor.getDouble(4));
                    mSoilResource.setPrioritizationtypeid((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mSoilResource.setComments(cursor.getString(6));
                    mSoilResource.setCreatedbyuserid(cursor.getInt(7));
                    mSoilResource.setCreateddate(cursor.getString(8));
                    mSoilResource.setUpdatedbyuserid(cursor.getInt(9));
                    mSoilResource.setUpdateddate(cursor.getString(10));
                    mSoilResource.setServerupdatedstatus(cursor.getInt(11));
                    mSoilResource.setIrrigatedArea(cursor.getFloat(12));
                    mSoilResource.setSoilNatureId((cursor.getInt(13) == 0) ? null : cursor.getInt(13));

                    if (type == 1) {
                        mSoilResourceeList.add(mSoilResource);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mSoilResource : mSoilResourceeList);
    }

    public T getPlotIrrigationXRefData(final String query, final int type) {
        PlotIrrigationTypeXref mPlotIrrigationTypeXref = null;
        List<PlotIrrigationTypeXref> mPlotIrrigationTypeXrefList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlotIrrigationTypeXref = new PlotIrrigationTypeXref();
                    mPlotIrrigationTypeXref.setPlotcode(cursor.getString(1));
                    mPlotIrrigationTypeXref.setName(cursor.getString(2));
                    mPlotIrrigationTypeXref.setIrrigationtypeid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mPlotIrrigationTypeXref.setIsactive(cursor.getInt(4));
                    mPlotIrrigationTypeXref.setCreatedbyuserid(cursor.getInt(5));
                    mPlotIrrigationTypeXref.setCreateddate(cursor.getString(6));
                    mPlotIrrigationTypeXref.setUpdatedbyuserid(cursor.getInt(7));
                    mPlotIrrigationTypeXref.setUpdateddate(cursor.getString(8));
                    mPlotIrrigationTypeXref.setServerupdatedstatus(cursor.getInt(9));
                    mPlotIrrigationTypeXref.setRecmIrrgId(cursor.getInt(cursor.getColumnIndex("RecmIrrgId")));
                    if (type == 1) {
                        mPlotIrrigationTypeXrefList.add(mPlotIrrigationTypeXref);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPlotIrrigationTypeXref : mPlotIrrigationTypeXrefList);
    }

    public T getPlantationDataset(final String query, final int type) {
        Plantation mPlantation = null;
        List<Plantation> mPlantationList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlantation = new Plantation();
                    mPlantation.setPlotcode(cursor.getString(1));
                    mPlantation.setNurserycode((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mPlantation.setSaplingsourceid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mPlantation.setSaplingvendorid((cursor.getInt(4) == 0) ? null : cursor.getInt(4));
                    mPlantation.setCropVarietyId((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mPlantation.setAllotedarea(cursor.getDouble(6));
                    mPlantation.setTreescount(cursor.getInt(7));
                    mPlantation.setCreatedbyuserid(cursor.getInt(8));
                    mPlantation.setCreateddate(cursor.getString(9));
                    mPlantation.setUpdatedbyuserid(cursor.getInt(10));
                    mPlantation.setUpdateddate(cursor.getString(11));
                    mPlantation.setIsActive(cursor.getInt(12));
                    mPlantation.setServerUpdatedStatus(cursor.getInt(13));
                    mPlantation.setReasonTypeId(cursor.getInt(14));
                    mPlantation.setGFReceiptNumber(cursor.getString(15));
                    if (type == 1) {
                        mPlantationList.add(mPlantation);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }

        return (T) ((type == 0) ? mPlantation : mPlantationList);
    }


    public List<NurseryDetails> getNurseryDetails(final String query) {
        List<NurseryDetails> nurserySaplingDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    NurseryDetails nurseryDetails = new NurseryDetails();
                    nurseryDetails.setCode(cursor.getString(cursor.getColumnIndex("Code")));
                    nurseryDetails.setName(cursor.getString(cursor.getColumnIndex("Name")));
                    nurseryDetails.setPinCode(cursor.getInt(cursor.getColumnIndex("PinCode")));

                    nurserySaplingDetails.add(nurseryDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return nurserySaplingDetails;
    }


    public List<NurseryData> getNurseryData(final String query) {    // Get Nursery Details
        List<NurseryData> nurseryData = new ArrayList<>();
        Log.d(LOG_TAG, "=== > Analysis ==> getNurseryData:" + query);
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    NurseryData nurseryDataDetails = new NurseryData();
                    nurseryDataDetails.setCode(cursor.getString(cursor.getColumnIndex("Code")));
                    nurseryDataDetails.setName(cursor.getString(cursor.getColumnIndex("name")));
                    nurseryDataDetails.setStatename(cursor.getString(cursor.getColumnIndex("Statename")));
                    nurseryDataDetails.setDistrictname(cursor.getString(cursor.getColumnIndex("DistrictName")));
                    nurseryDataDetails.setMandalname(cursor.getString(cursor.getColumnIndex("MandalName")));
                    nurseryDataDetails.setVillagename(cursor.getString(cursor.getColumnIndex("Villagename")));
                    nurseryDataDetails.setPinCode(cursor.getInt(cursor.getColumnIndex("PinCode")));


                    nurseryData.add(nurseryDataDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return nurseryData;
    }

    public List<ConsignmentData> getConsignmentData(final String query) {
        Log.d(LOG_TAG, "==> analysis GetConsinmentData :" + query);
        List<ConsignmentData> consignmentData = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    ConsignmentData consignmentdetails = new ConsignmentData();
                    consignmentdetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    consignmentdetails.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ConsignmentCode")));
                    consignmentdetails.setOriginname(cursor.getString(cursor.getColumnIndex("Originname")));
                    consignmentdetails.setVendorname(cursor.getString(cursor.getColumnIndex("Vendorname")));
                    consignmentdetails.setVarietyname(cursor.getString(cursor.getColumnIndex("Varietyname")));
                    consignmentdetails.setEstimatedQuantity(cursor.getInt(cursor.getColumnIndex("EstimatedQuantity")));
                    consignmentdetails.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    consignmentdetails.setArrivedDate(cursor.getString(cursor.getColumnIndex("ArrivedDate")));
                    consignmentdetails.setArrivedQuantity(cursor.getInt(cursor.getColumnIndex("ArrivedQuantity")));
                    consignmentdetails.setStatus(cursor.getString(cursor.getColumnIndex("Status")));


                    consignmentData.add(consignmentdetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return consignmentData;
    }

    public List<SaplingActivity> getSaplingActivityData(final String query) {
        List<SaplingActivity> sapactivitydata = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    SaplingActivity saplingsactivityDetails = new SaplingActivity();
                 //   saplingsactivityDetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    saplingsactivityDetails.setTransactionId(cursor.getString(cursor.getColumnIndex("TransactionId")));
                    saplingsactivityDetails.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ConsignmentCode")));
                    saplingsactivityDetails.setActivityId(cursor.getInt(cursor.getColumnIndex("ActivityId")));
                    saplingsactivityDetails.setStatusTypeId(cursor.getInt(cursor.getColumnIndex("StatusTypeId")));
                    saplingsactivityDetails.setComment(cursor.getString(cursor.getColumnIndex("Comment")));
                    saplingsactivityDetails.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    saplingsactivityDetails.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    saplingsactivityDetails.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    saplingsactivityDetails.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    saplingsactivityDetails.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    saplingsactivityDetails.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));


                    sapactivitydata.add(saplingsactivityDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return sapactivitydata;
    }


    public List<ConsignmentStatuData> getConsignmentStatus(final String query) {
        Log.d(DataAccessHandler.class.getSimpleName(),"@@Query :"+query);
        List<ConsignmentStatuData> consignmentStatusData = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    ConsignmentStatuData consignmentstatusdetails = new ConsignmentStatuData();
                    consignmentstatusdetails.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ConsignmentCode")));
                    consignmentstatusdetails.setOriginname(cursor.getString(cursor.getColumnIndex("Originname")));
                    consignmentstatusdetails.setSowingDate(cursor.getString(cursor.getColumnIndex("SowingDate")));
                    consignmentstatusdetails.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    consignmentstatusdetails.setStatusType(cursor.getString(cursor.getColumnIndex("StatusType")));
                    consignmentstatusdetails.setVarietyname(cursor.getString(cursor.getColumnIndex("Varietyname")));


                    consignmentStatusData.add(consignmentstatusdetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return consignmentStatusData;
    }

    public List<ConsignmentDetails> getConsignmentDetails(final String query) {
        List<ConsignmentDetails> consignmentDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    ConsignmentDetails consDetails = new ConsignmentDetails();
                    consDetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    consDetails.setNurseryCode(cursor.getString(cursor.getColumnIndex("NurseryCode")));
                    consDetails.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ConsignmentCode")));

                    consignmentDetails.add(consDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return consignmentDetails;
    }


    public String getCropVariety(final String query, final int type) {
        ArrayList<String> arrCropVariety = new ArrayList<>();
        String resultCropVariety = "";
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    arrCropVariety.add(cursor.getString(0));


                } while (cursor.moveToNext());
            }
            Set<String> hs = new HashSet<>(arrCropVariety);
            arrCropVariety.clear();
            arrCropVariety.addAll(hs);

            for (int i = 0; i < arrCropVariety.size(); i++) {

                cursor = mDatabase.rawQuery("select Name from LookUp where id=" + arrCropVariety.get(i), null);
                if (cursor != null && cursor.moveToFirst()) {
                    do {

                        resultCropVariety = resultCropVariety + cursor.getString(0) + "\n";


                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        // resultCropVariety.replace("@",",");
        return resultCropVariety;
    }

    public T getFollowupData(final String query, final int type) {
        FollowUp mFollowUp = null;
        List<FollowUp> mFollowUpList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ FollowUp query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFollowUp = new FollowUp();
                    mFollowUp.setPlotcode(cursor.getString(1));
                    mFollowUp.setIsfarmerreadytoconvert(cursor.getInt(2));
                    mFollowUp.setIssuedetails(cursor.getString(3));
                    mFollowUp.setComments(cursor.getString(4));
                    mFollowUp.setPotentialscore((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mFollowUp.setHarvestingmonth(cursor.getString(6));
                    mFollowUp.setCreatedbyuserid(cursor.getInt(7));
                    mFollowUp.setCreateddate(cursor.getString(8));
                    mFollowUp.setUpdatedbyuserid(cursor.getInt(9));
                    mFollowUp.setUpdateddate(cursor.getString(10));
                    mFollowUp.setServerupdatedstatus(cursor.getInt(11));
                    mFollowUp.setExpectedMonthofSowing(cursor.getString(12));
                    if (type == 1) {
                        mFollowUpList.add(mFollowUp);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFollowUp : mFollowUpList);
    }

    public List<NurseryAcitivity> getNurseryActivityDetails(final String query) {
        Log.d(DataAccessHandler.class.getSimpleName(), "====> Analysis ==> GET ACTIVITIES :" + query);
        List<NurseryAcitivity> nurseryActivityDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {




                    NurseryAcitivity nurseryActivityyDetails = new NurseryAcitivity();
                    nurseryActivityyDetails.setActivityId(cursor.getInt(cursor.getColumnIndex("ActivityId")));
                    nurseryActivityyDetails.setActivityTypeId(cursor.getInt(cursor.getColumnIndex("ActivityId")));
                    nurseryActivityyDetails.setIsMultipleEntries(cursor.getString(cursor.getColumnIndex("IsMultipleEntries")));
                    nurseryActivityyDetails.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ActivityCode")));
                    nurseryActivityyDetails.setActivityName(cursor.getString(cursor.getColumnIndex("ActivityName")));
                    nurseryActivityyDetails.setStatusTypeId(cursor.getInt(cursor.getColumnIndex("StatusTypeId")));
                    nurseryActivityyDetails.setActivityStatus(cursor.getString(cursor.getColumnIndex("ActivityStatus")));
                    nurseryActivityyDetails.setActivityDoneDate(cursor.getString(cursor.getColumnIndex("ActivityDoneDate")));
                    nurseryActivityyDetails.setTargetDate(cursor.getString(cursor.getColumnIndex("TargetDate")));
                    nurseryActivityyDetails.setDependentActivityCode(cursor.getString(cursor.getColumnIndex("DependentActivityCode")));
                    nurseryActivityyDetails.setColorIndicator(cursor.getInt(cursor.getColumnIndex("ColorIndicator")));

                    nurseryActivityDetails.add(nurseryActivityyDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return nurseryActivityDetails;
    }


    public List<MutipleData> getMultipleDataDetails(final String query) {
        Log.d(DataAccessHandler.class.getSimpleName(), "==> analysis Query :" + query);
        List<MutipleData> mutipleDataListDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    MutipleData multipleDataDetails = new MutipleData();
                    multipleDataDetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    multipleDataDetails.setTransactionId(cursor.getString(cursor.getColumnIndex("TransactionId")));
                    multipleDataDetails.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ConsignmentCode")));
                    multipleDataDetails.setActivityId(cursor.getInt(cursor.getColumnIndex("ActivityId")));
                    multipleDataDetails.setStatusTypeId(cursor.getInt(cursor.getColumnIndex("StatusTypeId")));
                    multipleDataDetails.setComment(cursor.getString(cursor.getColumnIndex("Comment")));
                    multipleDataDetails.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    multipleDataDetails.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    multipleDataDetails.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    multipleDataDetails.setDesc(cursor.getString(cursor.getColumnIndex("Desc")));

                    mutipleDataListDetails.add(multipleDataDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return mutipleDataListDetails;
    }

    public List<LandlevellingFields> getlandlevelligfeildDetails(final String query) {
        List<LandlevellingFields> landlevellingDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    LandlevellingFields fieldDetails = new LandlevellingFields();
                    fieldDetails.setValue(cursor.getInt(cursor.getColumnIndex("Value")));
                    fieldDetails.setField(cursor.getString(cursor.getColumnIndex("Field")));
                    landlevellingDetails.add(fieldDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return landlevellingDetails;
    }

    public List<ExistingData> getexistingDetails(final String query) {
        List<ExistingData> existingData = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    ExistingData existingDetails = new ExistingData();
                    existingDetails.setValue(cursor.getString(cursor.getColumnIndex("Value")));
                    existingDetails.setField(cursor.getString(cursor.getColumnIndex("Field")));
                    existingDetails.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    existingData.add(existingDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return existingData;
    }

    public List<DisplayData> getdisplayDetails(final String query) {
        List<DisplayData> displayData = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    DisplayData displayDetails = new DisplayData();
                    displayDetails.setFieldId(cursor.getInt(cursor.getColumnIndex("FieldId")));
                    displayDetails.setInputType(cursor.getString(cursor.getColumnIndex("InputType")));
                    displayDetails.setValue(cursor.getString(cursor.getColumnIndex("Value")));
                    displayDetails.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    displayDetails.setTransactionId(cursor.getString(cursor.getColumnIndex("TransactionId")));
                    displayData.add(displayDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return displayData;
    }

    public List<Integer> getGroupids(final String query) {
        List<Integer> groupIds = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    Integer groupId = cursor.getInt(cursor.getColumnIndex("GroupId"));

                    groupIds.add(groupId);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return groupIds;
    }

    public List<ActivityTasks> getActivityTasksDetails(final String query) {
        List<ActivityTasks> activityTaskDetails = new ArrayList<>();
        Log.d(DataAccessHandler.class.getSimpleName(), "===> getActivityTasksDetails Query :" + query);
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    ActivityTasks taskDetails = new ActivityTasks();
                    taskDetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    taskDetails.setActivityTypeId(cursor.getInt(cursor.getColumnIndex("ActivityTypeId")));
                    taskDetails.setDependency(cursor.getString(cursor.getColumnIndex("Dependency")));
                    taskDetails.setIsOptional(cursor.getInt(cursor.getColumnIndex("IsOptional")));
                    taskDetails.setBucket(cursor.getString(cursor.getColumnIndex("Bucket")));
                    taskDetails.setField(cursor.getString(cursor.getColumnIndex("Field")));
                    taskDetails.setItemCode(cursor.getString(cursor.getColumnIndex("ItemCode")));
                    taskDetails.setItemCodeName(cursor.getString(cursor.getColumnIndex("ItemCodeName")));
                    taskDetails.setGLCode(cursor.getString(cursor.getColumnIndex("GLCode")));
                    taskDetails.setGLName(cursor.getString(cursor.getColumnIndex("GLName")));
                    taskDetails.setInputType(cursor.getString(cursor.getColumnIndex("InputType")));
                    taskDetails.setUom(cursor.getString(cursor.getColumnIndex("UOM")));
                    taskDetails.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    taskDetails.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    taskDetails.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    taskDetails.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    taskDetails.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    taskDetails.setDataType(cursor.getString(cursor.getColumnIndex("DataType")));
                    taskDetails.setGroupId(cursor.getInt(cursor.getColumnIndex("GroupId")));

                    activityTaskDetails.add(taskDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return activityTaskDetails;
    }

    public List<Saplings> getSaplingDetails(final String query, final int type) {
        List<Saplings> saplingDataDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    Saplings saplingsDetails = new Saplings();
                    saplingsDetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    saplingsDetails.setNurseryCode(cursor.getString(cursor.getColumnIndex("NurseryCode")));
                    saplingsDetails.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ConsignmentCode")));
                    saplingsDetails.setOriginId(cursor.getInt(cursor.getColumnIndex("OriginId")));
                    saplingsDetails.setVendorId(cursor.getInt(cursor.getColumnIndex("VendorId")));
                    saplingsDetails.setVarietyId(cursor.getInt(cursor.getColumnIndex("VarietyId")));
                    saplingsDetails.setPurchaseDate(cursor.getString(cursor.getColumnIndex("PurchaseDate")));
                    saplingsDetails.setEstimatedDate(cursor.getString(cursor.getColumnIndex("EstimatedDate")));
                    saplingsDetails.setEstimatedQuantity(cursor.getInt(cursor.getColumnIndex("EstimatedQuantity")));
                    saplingsDetails.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    saplingsDetails.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    saplingsDetails.setCreatedDate(CommonUtils.getPropeCreateDate(cursor.getString(cursor.getColumnIndex("CreatedDate"))));
                    saplingsDetails.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    saplingsDetails.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    saplingsDetails.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    saplingsDetails.setStatusTypeId(cursor.getInt(cursor.getColumnIndex("StatusTypeId")));

                    saplingsDetails.setArrivedDate(cursor.getString(cursor.getColumnIndex("ArrivedDate")));
                    saplingsDetails.setArrivedQuantity(cursor.getInt(cursor.getColumnIndex("ArrivedQuantity")));
                    saplingsDetails.setSowingDate(cursor.getString(cursor.getColumnIndex("SowingDate")));
                    saplingsDetails.setTransplantingDate(cursor.getString(cursor.getColumnIndex("TransplantingDate")));
                    saplingsDetails.setSAPCode(cursor.getString(cursor.getColumnIndex("SAPCode")));
                    saplingsDetails.setCurrentClosingStock(cursor.getInt(cursor.getColumnIndex("CurrentClosingStock")));

                    saplingDataDetails.add(saplingsDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return saplingDataDetails;
    }


    public List<SaplingActivity> getSaplingActivityDetails(final String query, final int type) {
        List<SaplingActivity> saplingActivityDataDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    SaplingActivity saplingsactivityDetails = new SaplingActivity();
                  //  saplingsactivityDetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    saplingsactivityDetails.setTransactionId(cursor.getString(cursor.getColumnIndex("TransactionId")));
                    saplingsactivityDetails.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ConsignmentCode")));
                    saplingsactivityDetails.setActivityId(cursor.getInt(cursor.getColumnIndex("ActivityId")));
                    saplingsactivityDetails.setStatusTypeId(cursor.getInt(cursor.getColumnIndex("StatusTypeId")));
                    saplingsactivityDetails.setComment(cursor.getString(cursor.getColumnIndex("Comment")));
                    saplingsactivityDetails.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    saplingsactivityDetails.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    saplingsactivityDetails.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    saplingsactivityDetails.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    saplingsactivityDetails.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    saplingsactivityDetails.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));

                    saplingActivityDataDetails.add(saplingsactivityDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return saplingActivityDataDetails;
    }
    public List<SaplingActivityXrefModel> getSaplingActivityXrefDetails(final String query, final int type) {
        List<SaplingActivityXrefModel> saplingActivityXrefDataDetails = new ArrayList<>();



        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ GradingRepo details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    SaplingActivityXrefModel saplingsactivityxrefDetails = new SaplingActivityXrefModel();

                    String filelocation = cursor.getString(cursor.getColumnIndex("FilePath"));
                    if (filelocation != null) {
                        try {
                            saplingsactivityxrefDetails.setImageString(CommonUtils.encodeFileToBase64Binary(new File(filelocation)));
                            saplingsactivityxrefDetails.setFileExtension(".jpg");
                        } catch (Exception exc) {

                        }
                    }

//
//                    String imagestr="";
//                    String filelocation = cursor.getString(cursor.getColumnIndex("FileLocation"));
//                    Log.d(DataAccessHandler.class.getSimpleName(), "===> Analsis getSaplingActivityXrefDetails() FilePath :"+filelocation);
//
//                    // Log.v(LOG_TAG, "@@@ image base 64 ==>" + filelocation+"");
//
////                    if (!filelocation.isEmpty()) {
////                        File imagefile = new File(filelocation);
////                        FileInputStream fis = null;
////                        try {
////                            fis = new FileInputStream(imagefile);
////                        } catch (FileNotFoundException e) {
////                            e.printStackTrace();
////                        }
////
////                        Bitmap bm = BitmapFactory.decodeStream(fis);
////                        bm = ImageUtility.rotatePicture(90, bm);
////                        String base64string = ImageUtility.convertBitmapToString(bm);
////                        saplingsactivityxrefDetails.setImageString(base64string);
////                        //saplingsactivityxrefDetails.setFilePath(base64string);
////                    }
//                    if (!filelocation.isEmpty()) {
//                        try {
//
////                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
////                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.addimage);
////                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
////                            byte[] imageBytes = baos.toByteArray();
////                            String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//
//                            imagestr = CommonUtils.encodeFileToBase64Binary(new File(filelocation));
//
//
//                            //saplingsactivityxrefDetails.setImageString(imageString);
//                            //Log.v(LOG_TAG, "@@@ image base 64 ==>" + CommonUtils.encodeFileToBase64Binary(new File(filelocation)));
//
//                        } catch (Exception exc) {
//                            Log.d(DataAccessHandler.class.getSimpleName(), "===> Analsis Error getSaplingActivityXrefDetails() "+exc.getLocalizedMessage());
//                        }
//                    }
                    // saplingsactivityxrefDetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                 //   saplingsactivityxrefDetails.setImageString(imagestr);
                    saplingsactivityxrefDetails.setTransactionId(cursor.getString(cursor.getColumnIndex("TransactionId")));
                    saplingsactivityxrefDetails.setFieldId(cursor.getInt(cursor.getColumnIndex("FieldId")));
                    saplingsactivityxrefDetails.setValue(cursor.getString(cursor.getColumnIndex("Value")));
                    saplingsactivityxrefDetails.setFilePath(cursor.getString(cursor.getColumnIndex("FilePath")));
                    saplingsactivityxrefDetails.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    saplingsactivityxrefDetails.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    saplingsactivityxrefDetails.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    saplingsactivityxrefDetails.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    saplingsactivityxrefDetails.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    saplingsactivityxrefDetails.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    saplingsactivityxrefDetails.setLabourRate(cursor.getDouble(cursor.getColumnIndex("LabourRate")));

                   // saplingsactivityxrefDetails.setFileName(cursor.getString(cursor.getColumnIndex("FileName")));
                 //   saplingsactivityxrefDetails.setFileLocation(cursor.getString(cursor.getColumnIndex("FileLocation")));

                    saplingActivityXrefDataDetails.add(saplingsactivityxrefDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return saplingActivityXrefDataDetails;
    }
//    public List<SaplingActivityXrefModel> getSaplingActivityXrefDetails(final String query, final int type) {
//        List<SaplingActivityXrefModel> saplingActivityXrefDataDetails = new ArrayList<>();
//
//
//
//        Cursor cursor = null;
//        Log.v(LOG_TAG, "@@@ GradingRepo details query " + query);
//        try {
//            cursor = mDatabase.rawQuery(query, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    SaplingActivityXrefModel saplingsactivityxrefDetails = new SaplingActivityXrefModel();
//
//
//                    String filelocation = cursor.getString(cursor.getColumnIndex("FilePath"));
//                    Log.v(LOG_TAG, "@@@ image base 64 ==>" + filelocation+"");
//
//                    if (!filelocation.isEmpty()) {
//                        File imagefile = new File(filelocation);
//                        FileInputStream fis = null;
//                        try {
//                            fis = new FileInputStream(imagefile);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//
//                        Bitmap bm = BitmapFactory.decodeStream(fis);
//                        bm = ImageUtility.rotatePicture(90, bm);
//                        String base64string = ImageUtility.convertBitmapToString(bm);
//                        saplingsactivityxrefDetails.setFilePath(base64string);
//                        //saplingsactivityxrefDetails.setFilePath(base64string);
//                    }
////                    if (filelocation != null) {
////                        try {
////
////                         //   saplingsactivityxrefDetails.setFilePath(CommonUtils.encodeFileToBase64Binary(new File(filelocation)));
////                            saplingsactivityxrefDetails.setFilePath("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qOQx4CAiIiIiIiKikseAg4iIiIiIiIhKHgMOIiIiIiIiIip5DDiIiIiIiIiIqOQx4CAiIiIiIiKikseAg4iIiIiIiIhKHgMOIiIiIiIiIip5DDiIiIiIiIiIqORZgNjoMRARERERERERrQpncBARERERERFRyWPAQUREREREREQlz+IKFQzQI9gAACAASURBVCIiIiIiIiIqdZzBQUREREREREQljwEHEREREREREZU8BhxEREREREREVPIYcBARERERERFRyWPAQUREREREREQljwEHEREREREREZU8BhxEREREREREVPIYcBARERERERFRyWPAQUREREREREQljwEHEREREREREZU8BhxEREREREREVPIYcBARERERERFRyWPAQUREREREREQlz4LY6CEQEREREREREa0OZ3AQERERERERUcljwEFEREREREREJY8BBxERERERERGVPAYcRERERERERFTyGHAQERERERERUcljwEFEREREREREJY8BBxERERERERGVPAYcRERERERERFTyGHAQERERERERUcljwEFEREREREREJY8BBxERERERERGVPAYcRERERERERFTyGHAQERERERERUcljwEFEREREREREJc+C2OghEBERERERERGtjrXRA9jUhAAME2aqAmYyBSgF6WWhvRy0DKHDENBqnmsNwDBguDEY8STMRBlUPgeVTUPmMtF1ap5riYiIiIiIiGhZGHDMRwhACAjLgl3TAKexFToMEAwPIBzuh8p7gM5Bh4VDCmGaEJYNM1kOu6EFTl0LwrEh+P1d0GEQ/dEa0Hqd3xgRERERERHR1sOAYz7GZECRSMGua4K7bS+0n4cwbSAMEaZHIcMACIMC1xoQjgsjloBVWQunsQ2xbfsQDHRDBz5UZgLKy0IryYCDiIiIiIiIaA1YYBGO2SZnblgVNbDrmmDXNsFt3QWnIZrBYbhxmBXV8LsuIK/aEea9GdcaEJYFw43BbmiF09AatVHTCKu2ETAMaCkhbAd+byeCwV5o35t/LERERERERES0JJzB8X5CQBgmzPIqOM07ENu+F3ZNA6zKGmilYCRSMCtroP08gqH+2dcaBoTtwIglYdc1I7brAJyG1uiaRArCMACtIUwTKpdFODoI7W/M2yQiIiIiIiLaShhwzCCcGMxkCkayHE5jG5yGFti1jTATZRCW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CAZ7IMdHoAIfWhYIKLQGtITKZaGDABACvu0CAMKxIQRDvQgnRqB9H1Bqnd8YERERERER0dbEgGM+k4VH4eej2RheFghDyMw4VN6Lwo2FAgqloMMAMjMOv68TMjsB5WUh0+PQfn6yMCkDDiIiIiIiIqK1wIBjPpMzMbQfFQQNB3uXeX1UeFRl01DZNIL+zuKMk4iIiIiIiIhgbPQAiIiIiIiIiIhWiwEHEREREREREZU8BhxEREREREREVPIYcBARERERERFRyWPAQUREREREREQlz4LY6CEQEREREREREa0OZ3AQERERERERUcljwEFEREREREREJY8BBxERERERERGVPAYcRERERERERFTyGHAQERERERERUcljwEFEREREREREJY8BBxERERERERGVPAYcRERERERERFTyGHAQERERERERUcljwEFEREREREREJY8BBxERERERERGVPAYcRERERERERFTyGHAQERERERERUcljwEFEREREREREJc+C2OghEBERERERERGtDmdwEBEREREREVHJY8BBRERERERERCWPAQcRERERERERlTwGHERERERERERU8iywyigRERERERERlTjO4CAiIiIiIiKikseAg4iIiIiIiIhKHgMOIiIiIiIiIip5DDiIiIiIiIiIqOQx4CAiIiIiIiKikseAg4iIiIiIiIhKHgOO/5+9+46T47zvPP99KnSehEFOBEAikGAASYA5iCIpMZiUSEUrWdqTvV45rc/e3dtbW3sOa/vO9tmyvV6dLctrW5IVaFrWWokSSYlBEnOOAElEEmkwuWNVPfdHDXqmMQEDYAbdNfN56zXU1PRT1b+HBHqqv/0EAAAAAACQeAQcAAAAAAAg8TyZZpcAAAAAAABwahjBAQAAAAAAEo+AAwAAAAAAJB4BBwAAAAAASDwCDgAAAAAAkHgEHAAAAAAAIPEIOAAAAAAAQOIRcAAAAAAAgMQj4AAAAAAAAInnNbsAAMDsc7J55TdfIn/parltXfLau+S2d8lr65JJpZtdHgDMCFutKBjsVTjQq2CgV+Fgr2r7d2v4hUcVlYabXR4AYJaZ1//T+2yziwAAzDyva5Hymy9R7pxtyqzZJDkM2gMwT0WRyjtfUvHFxzX8wqMKeg81uyIAwCwg4ACAOSa1eKW6bv6wcmdf3OxSAKAlFV96Qr3f/ZKq+/c0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OAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMjrqCE2AAAWNUlEQVQTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8gQMAAADIEzgAAACAPIEDAAAAyBM4AAAAgDyBAwAAAMgTOAAAAIA8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");
////                            Log.v(LOG_TAG, "@@@ image base 64 ==>" + CommonUtils.encodeFileToBase64Binary(new File(filelocation)));
////
////                        } catch (Exception exc) {
////
////                        }
////                    }
//                   // saplingsactivityxrefDetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
//                    saplingsactivityxrefDetails.setTransactionId(cursor.getString(cursor.getColumnIndex("TransactionId")));
//                    saplingsactivityxrefDetails.setFieldId(cursor.getInt(cursor.getColumnIndex("FieldId")));
//                    saplingsactivityxrefDetails.setValue(cursor.getString(cursor.getColumnIndex("Value")));
//               //     saplingsactivityxrefDetails.setFilePath(cursor.getString(cursor.getColumnIndex("FilePath")));
//                    saplingsactivityxrefDetails.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
//                    saplingsactivityxrefDetails.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
//                    saplingsactivityxrefDetails.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
//                    saplingsactivityxrefDetails.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
//                    saplingsactivityxrefDetails.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
//                    saplingsactivityxrefDetails.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
//                    saplingsactivityxrefDetails.setLabourRate(cursor.getDouble(cursor.getColumnIndex("LabourRate")));
//
//                    saplingActivityXrefDataDetails.add(saplingsactivityxrefDetails);
//                } while (cursor.moveToNext());
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return saplingActivityXrefDataDetails;
//    }

    public List<SaplingActivityHistoryModel> getSaplingActivityHistoryDetails(final String query, final int type) {
        List<SaplingActivityHistoryModel> saplingActivityHistoryDataDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    SaplingActivityHistoryModel saplingsactivityhistoryDetails = new SaplingActivityHistoryModel();
                    saplingsactivityhistoryDetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    saplingsactivityhistoryDetails.setTransactionId(cursor.getString(cursor.getColumnIndex("TransactionId")));
                    saplingsactivityhistoryDetails.setStatusTypeId(cursor.getInt(cursor.getColumnIndex("StatusTypeId")));
                    saplingsactivityhistoryDetails.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
                    saplingsactivityhistoryDetails.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    saplingsactivityhistoryDetails.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    saplingsactivityhistoryDetails.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));

                    saplingActivityHistoryDataDetails.add(saplingsactivityhistoryDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return saplingActivityHistoryDataDetails;
    }
    public List<NurseryIrrigationLogForDb> getIrrigationDetails(final String query, final int type) {
        List<NurseryIrrigationLogForDb> NurseryIrrigationLogList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    NurseryIrrigationLogForDb nurseryIrrigationLog = new NurseryIrrigationLogForDb();
                    nurseryIrrigationLog.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    nurseryIrrigationLog.setLogDate(cursor.getString(cursor.getColumnIndex("LogDate")));
                    nurseryIrrigationLog.setIrrigationCode(cursor.getString(cursor.getColumnIndex("IrrigationCode")));
                    nurseryIrrigationLog.setRegularMale(cursor.getDouble(cursor.getColumnIndex("RegularMale")));
                    nurseryIrrigationLog.setRegularFemale(cursor.getDouble(cursor.getColumnIndex("RegularFemale")));
                    nurseryIrrigationLog.setContractMale(cursor.getDouble(cursor.getColumnIndex("ContractMale")));
                    nurseryIrrigationLog.setContractFemale(cursor.getDouble(cursor.getColumnIndex("ContractFemale")));
                    nurseryIrrigationLog.setStatusTypeId(cursor.getInt(cursor.getColumnIndex("StatusTypeId")));
                    nurseryIrrigationLog.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
                    nurseryIrrigationLog.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive") ));
                    nurseryIrrigationLog.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    nurseryIrrigationLog.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    nurseryIrrigationLog.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    nurseryIrrigationLog.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    nurseryIrrigationLog.setRegularMaleRate(cursor.getDouble(cursor.getColumnIndex("RegularMaleRate")));
                    nurseryIrrigationLog.setRegularFeMaleRate(cursor.getDouble(cursor.getColumnIndex("RegularFeMaleRate")));
                    nurseryIrrigationLog.setContractMaleRate(cursor.getDouble(cursor.getColumnIndex("ContractMaleRate")));
                    nurseryIrrigationLog.setContractFeMaleRate(cursor.getDouble(cursor.getColumnIndex("ContractFeMaleRate")));

                    NurseryIrrigationLogList.add(nurseryIrrigationLog);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return NurseryIrrigationLogList;
    }
    public List<NurseryIrrigationLogXref> getIrrigationDetailsXref(final String query, final int type) {
        List<NurseryIrrigationLogXref> NurseryIrrigationLogList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    NurseryIrrigationLogXref nurseryIrrigationLogXref = new NurseryIrrigationLogXref();
                    nurseryIrrigationLogXref.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    nurseryIrrigationLogXref.setIrrigationCode(cursor.getString(cursor.getColumnIndex("IrrigationCode")));
                    nurseryIrrigationLogXref.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ConsignmentCode")));
                    nurseryIrrigationLogXref.setActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    nurseryIrrigationLogXref.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    nurseryIrrigationLogXref.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    nurseryIrrigationLogXref.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    nurseryIrrigationLogXref.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));

                    NurseryIrrigationLogList.add(nurseryIrrigationLogXref);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return NurseryIrrigationLogList;
    }

    public List<SaplingActivityStatusModel> getSaplingActivityStatusDetails(final String query, final int type) {
        List<SaplingActivityStatusModel> saplingActivitystatusDataDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    SaplingActivityStatusModel saplingsactivitystatusDetails = new SaplingActivityStatusModel();
                  //  saplingsactivitystatusDetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    saplingsactivitystatusDetails.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ConsignmentCode")));
                    saplingsactivitystatusDetails.setActivityId(cursor.getInt(cursor.getColumnIndex("ActivityId")));
                    saplingsactivitystatusDetails.setStatusTypeId(cursor.getInt(cursor.getColumnIndex("StatusTypeId")));
                    saplingsactivitystatusDetails.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    saplingsactivitystatusDetails.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    saplingsactivitystatusDetails.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    saplingsactivitystatusDetails.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    saplingsactivitystatusDetails.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));

                    saplingsactivitystatusDetails.setJobCompletedDate(cursor.getString(cursor.getColumnIndex("JobCompletedDate")));

                    saplingActivitystatusDataDetails.add(saplingsactivitystatusDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return saplingActivitystatusDataDetails;
    }


    public T getMarketSurveyData(final String query, final int type) {
        MarketSurvey mMarketSurvey = null;
        List<MarketSurvey> mMarketSurveyList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mMarketSurvey = new MarketSurvey();
                    mMarketSurvey.setVillageId(cursor.getInt(1));
                    mMarketSurvey.setSurveyDate(cursor.getString(2));
                    mMarketSurvey.setFarmerCode(cursor.getString(3));
                    mMarketSurvey.setFarmerName(cursor.getString(4));
                    mMarketSurvey.setFamilyMembersCount(cursor.getInt(5));
                    mMarketSurvey.setReason(cursor.getString(6));
                    mMarketSurvey.setIsFarmerWillingtoUse((cursor.getInt(7) == 0) ? null : cursor.getInt(7));
                    mMarketSurvey.setEstimatedAmounttoPay(cursor.getDouble(8));
                    mMarketSurvey.setIsFarmerUseSmartPhone((cursor.getInt(9) == 0) ? null : cursor.getInt(9));
                    mMarketSurvey.setIsCattleExist((cursor.getInt(10) == 0) ? null : cursor.getInt(10));
                    mMarketSurvey.setCattleTypeId((cursor.getInt(11) == 0) ? null : cursor.getInt(11));
                    mMarketSurvey.setCattlesCount(cursor.getInt(12));
                    mMarketSurvey.setIsFarmerHavingOwnVehicle((cursor.getInt(13) == 0) ? null : cursor.getInt(13));
                    mMarketSurvey.setVehicleTypeId((cursor.getInt(14) == 0) ? null : cursor.getInt(14));
                    mMarketSurvey.setIsActive(cursor.getInt(15));
                    mMarketSurvey.setCreatedByUserId(cursor.getInt(16));
                    mMarketSurvey.setCreatedDate(cursor.getString(17));
                    mMarketSurvey.setUpdatedByUserId(cursor.getInt(18));
                    mMarketSurvey.setUpdatedDate(cursor.getString(19));
                    mMarketSurvey.setServerUpdatedStatus(cursor.getInt(20));
                    mMarketSurvey.setCode(cursor.getString(21));
                    mMarketSurvey.setFarmerMobileNumber(cursor.getString(22));
                    if (type == 1) {
                        mMarketSurveyList.add(mMarketSurvey);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mMarketSurvey : mMarketSurveyList);
    }

    public T getReferralsData(final String query, final int type) {
        Referrals mReferrals = null;
        List<Referrals> mReferralsList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mReferrals = new Referrals();
                    mReferrals.setMandalname(cursor.getString(1));
                    mReferrals.setVillageName(cursor.getString(2));
                    mReferrals.setFarmername(cursor.getString(3));
                    mReferrals.setContactnumber(cursor.getString(4));
                    mReferrals.setCreatedbyuserid(cursor.getInt(5));
                    mReferrals.setCreateddate(cursor.getString(6));
                    mReferrals.setUpdatedbyuserid(cursor.getInt(7));
                    mReferrals.setUpdatedDate(cursor.getString(8));
                    mReferrals.setServerUpdatedStatus(cursor.getInt(9));
                    if (type == 1) {
                        mReferralsList.add(mReferrals);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mReferrals : mReferralsList);
    }

//    public T getVisitLogData(final String query) {
//        Cursor cursor = null;
//        VisitLog visitLog = null;
//        List<VisitLog> visitLogList = new ArrayList<>();
//        Log.v(LOG_TAG, "@@@ VisitLog data " + query);
//        try {
//            cursor = mDatabase.rawQuery(query, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    visitLog = new VisitLog();
//                    visitLog.setClientName(cursor.getString(1));
//                    visitLog.setMobileNumber(cursor.getString(2));
//                    visitLog.setLocation(cursor.getString(3));
//                    visitLog.setDetails(cursor.getString(4));
//                    visitLog.setLatitude(cursor.getFloat(5));
//                    visitLog.setLongitude(cursor.getFloat(6));
//                    visitLog.setCreatedByUserId(cursor.getInt(7));
//                    visitLog.setCreatedDate(cursor.getString(8));
//                    visitLog.setServerUpdatedStatus(cursor.getInt(9));
//                    visitLogList.add(visitLog);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception se) {
//            Log.e(LOG_TAG, "@@@ getting VisitLog details " + se.getMessage());
//            se.printStackTrace();
//        }
//        return (T) (visitLogList);
//    }

    public T getUserSyncData(final String query) {
        Cursor cursor = null;
        UserSync userSync = null;
        List<UserSync> userSyncList = new ArrayList<>();
        Log.v(LOG_TAG, "@@@ UserSync data " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    userSync = new UserSync();
                    userSync.setUserId(cursor.getInt(cursor.getColumnIndex("UserId")));
                    userSync.setApp(cursor.getString(cursor.getColumnIndex("App")));
                    userSync.setDate(cursor.getString(cursor.getColumnIndex("Date")));
                    userSync.setMasterSync(cursor.getInt(cursor.getColumnIndex("MasterSync")));
                    userSync.setTransactionSync(cursor.getInt(cursor.getColumnIndex("TransactionSync")));
                    userSync.setResetData(cursor.getInt(cursor.getColumnIndex("ResetData")));
                    userSync.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    userSync.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    userSync.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    userSync.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    userSync.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    userSync.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    userSyncList.add(userSync);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@  UserSyncdetails " + e.getMessage());
            e.printStackTrace();
        }
        return (T) (userSyncList);

    }


    public T getFileRepositoryData(final String query, final int type) {
        List<FileRepositoryRefresh> mFileRepositoryList = new ArrayList<>();
        FileRepositoryRefresh mFileRepository = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ FileRepository query " + query);
        try {

            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFileRepository = new FileRepositoryRefresh();
                    mFileRepository.setFarmerCode(cursor.getString(1));
                    int moduleTypeId = cursor.getInt(3);
                    mFileRepository.setModuleTypeId(moduleTypeId);
                    if (moduleTypeId == 193) {
                        mFileRepository.setPlotCode(null);
                        mFileRepository.setCropMaintenanceCode(null);
                    } else if (moduleTypeId == 303) {
                        mFileRepository.setPlotCode(cursor.getString(2));
                        mFileRepository.setCropMaintenanceCode(null);
                    } else {
                        mFileRepository.setPlotCode(cursor.getString(2));

                        mFileRepository.setCropMaintenanceCode(cursor.getString(13));
                    }
                    mFileRepository.setFileName(cursor.getString(4));
                    mFileRepository.setFileLocation(cursor.getString(5));
                    mFileRepository.setFileExtension(cursor.getString(6));
                    mFileRepository.setIsActive(cursor.getInt(7));
                    mFileRepository.setCreatedByUserId(cursor.getInt(8));
                    mFileRepository.setCreatedDate(cursor.getString(9));
                    mFileRepository.setUpdatedByUserId(cursor.getInt(10));
                    mFileRepository.setUpdatedDate(cursor.getString(11));
                    mFileRepository.setServerUpdatedStatus(cursor.getInt(12));
                    // mFileRepository.setCropMaintenanceCode(cursor.getString(13));
                    File imgFile = new File(mFileRepository.getFileLocation());
                    if (imgFile.exists()) {
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(imgFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bm = BitmapFactory.decodeStream(fis);
                        bm = ImageUtility.rotatePicture(90, bm);
                        String base64string = ImageUtility.convertBitmapToString(bm);
                        mFileRepository.setByteImage(base64string);
                    } else {
                        mFileRepository.setByteImage("");
                    }
                    if (type == 1) {
                        mFileRepositoryList.add(mFileRepository);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFileRepository : mFileRepositoryList);
    }

    //Falog_Tracking...
    public T getGpsTrackingData(final String query, final int type) {
        LocationTracker mGpsBoundaries = null;
        List<LocationTracker> mGpsBoundariesList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ GeoBoundaries query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mGpsBoundaries = new LocationTracker();

                    mGpsBoundaries.setUserId(cursor.getInt(1));
                    mGpsBoundaries.setLatitude(cursor.getDouble(2));
                    mGpsBoundaries.setLongitude(cursor.getDouble(3));
                    mGpsBoundaries.setAddress(cursor.getString(4));
                    mGpsBoundaries.setLogDate(cursor.getString(5));
                    //mGpsBoundaries.setServerUpdatedStatus(cursor.getInt(6));
                    if (type == 1) {
                        mGpsBoundariesList.add(mGpsBoundaries);
                    }

                    Log.v(LOG_TAG, "Lat@Log" + String.valueOf(mGpsBoundariesList));


                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mGpsBoundaries : mGpsBoundariesList);

    }

    public T getGeoTagData(final String query, final int type) {
        GeoBoundaries mGeoBoundaries = null;
        List<GeoBoundaries> mGeoBoundariesList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ GeoBoundaries query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mGeoBoundaries = new GeoBoundaries();
                    mGeoBoundaries.setPlotcode(cursor.getString(1));
                    mGeoBoundaries.setLatitude(cursor.getDouble(2));
                    mGeoBoundaries.setLongitude(cursor.getDouble(3));
                    mGeoBoundaries.setGeocategorytypeid((cursor.getInt(4) == 0) ? null : cursor.getInt(4));
                    mGeoBoundaries.setCreatedbyuserid(cursor.getInt(5));
                    mGeoBoundaries.setCreateddate(cursor.getString(6));
                    mGeoBoundaries.setUpdatedbyuserid(cursor.getInt(7));
                    mGeoBoundaries.setUpdateddate(cursor.getString(8));
                    mGeoBoundaries.setServerupdatedstatus(cursor.getInt(9));
                    String cropMainCode = cursor.getString(10);
                    mGeoBoundaries.setCropMaintenanceCode((TextUtils.isEmpty(cropMainCode) || cropMainCode.equalsIgnoreCase("null") ? null : cropMainCode));
                    if (type == 1) {
                        mGeoBoundariesList.add(mGeoBoundaries);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mGeoBoundaries : mGeoBoundariesList);
    }

    public FileRepository getSelectedFileRepository(String query) {
        FileRepository savedPictureData = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ FileRepository details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    savedPictureData = new FileRepository();
                    savedPictureData.setFarmercode(cursor.getString(1));
                    savedPictureData.setPlotcode(cursor.getString(2));
                    savedPictureData.setModuletypeid(cursor.getInt(3));
                    savedPictureData.setFilename(cursor.getString(4));
                    savedPictureData.setPicturelocation(cursor.getString(5));
                    savedPictureData.setFileextension(cursor.getString(6));
                    savedPictureData.setIsActive((cursor.getInt(7)));
                    savedPictureData.setCreatedbyuserid(cursor.getInt(8));
                    savedPictureData.setCreatedDate(cursor.getString(9));
                    savedPictureData.setUpdatedbyuserid(cursor.getInt(10));
                    savedPictureData.setUpdatedDate(cursor.getString(11));
                    savedPictureData.setServerUpdatedStatus(cursor.getInt(12));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting getSelectedFileRepository " + e.getMessage());
        }
        return savedPictureData;
    }


    public LinkedHashMap<String, String> getLookUpData11(String query) {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        Cursor cursor = mDatabase.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    linkedHashMap.put(cursor.getString(cursor.getColumnIndex("Code")),
                            cursor.getString(cursor.getColumnIndex("Name")));


                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linkedHashMap;

    }


    public ArrayList<Village> mgetVillageList(String query) {
        Village village = null;
        Cursor cursor = null;
        ArrayList<Village> villageArrayList = new ArrayList<>();
        android.util.Log.v(LOG_TAG, "village query" + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {
                    village = new Village();

                    village.setName(cursor.getString(cursor.getColumnIndex("Name")));

                    villageArrayList.add(village);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return villageArrayList;
    }


    public T getPlantationData(final String query, final int type) {
        Plantation mPlantation = null;
        List<Plantation> mPlantationList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ Plantation query " + query);
        try {

            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlantation = new Plantation();
                    mPlantation.setPlotcode(cursor.getString(1));
                    mPlantation.setNurserycode((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mPlantation.setSaplingsourceid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mPlantation.setSaplingvendorid((cursor.getInt(4) == 0) ? null : cursor.getInt(4));
                    mPlantation.setCropVarietyId((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mPlantation.setAllotedarea(cursor.getDouble(6));
                    mPlantation.setTreescount(cursor.getInt(7));
                    mPlantation.setCreatedbyuserid(cursor.getInt(8));
                    mPlantation.setCreateddate(cursor.getString(9));
                    mPlantation.setUpdatedbyuserid(cursor.getInt(10));
                    mPlantation.setUpdateddate(cursor.getString(11));
                    mPlantation.setIsActive(cursor.getInt(12));
                    mPlantation.setServerUpdatedStatus(cursor.getInt(13));
                    mPlantation.setReasonTypeId((cursor.getInt(14) == 0) ? null : cursor.getInt(14));
                    mPlantation.setGFReceiptNumber(cursor.getString(15));

                    if (type == 1) {
                        mPlantationList.add(mPlantation);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPlantation : mPlantationList);
    }

    public T getPlotLandLordData(final String query, final int type) {
        PlotLandlord mPlotLandlord = null;
        List<PlotLandlord> mPlotLandlordList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {

            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlotLandlord = new PlotLandlord();
                    mPlotLandlord.setPlotcode(cursor.getString(1));
                    mPlotLandlord.setLandlordname(cursor.getString(2));
                    mPlotLandlord.setLandlordcontactnumber(cursor.getString(3));
                    mPlotLandlord.setLeasestartdate(cursor.getString(4));
                    mPlotLandlord.setLeaseenddate(cursor.getString(5));
                    mPlotLandlord.setIsactive(cursor.getInt(6));
                    mPlotLandlord.setCreatedbyuserid(cursor.getInt(7));
                    mPlotLandlord.setCreateddate(cursor.getString(8));
                    mPlotLandlord.setUpdatedbyuserid(cursor.getInt(9));
                    mPlotLandlord.setUpdateddate(cursor.getString(10));
                    mPlotLandlord.setServerupdatedstatus(cursor.getInt(11));
                    if (type == 1) {
                        mPlotLandlordList.add(mPlotLandlord);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPlotLandlord : mPlotLandlordList);
    }


    public T getCropMaintanceHistoryData(final String query, final int type) {
        CropMaintenanceHistory cropMaintenanceHistory = null;
        List<CropMaintenanceHistory> maintenanceHistoryList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    cropMaintenanceHistory = new CropMaintenanceHistory();
                    cropMaintenanceHistory.setCode(cursor.getString(1));
                    cropMaintenanceHistory.setPlotCode(cursor.getString(2));
                    cropMaintenanceHistory.setIsActive(cursor.getInt(3));
                    cropMaintenanceHistory.setCreatedByUserId(cursor.getInt(4));
                    cropMaintenanceHistory.setCreatedDate(cursor.getString(5));
                    cropMaintenanceHistory.setUpdatedByUserId(cursor.getInt(6));
                    cropMaintenanceHistory.setUpdatedDate(cursor.getString(7));
                    cropMaintenanceHistory.setServerUpdatedStatus(cursor.getInt(8));
                    cropMaintenanceHistory.setName(cursor.getString(9));
                    if (type == 1) {
                        maintenanceHistoryList.add(cropMaintenanceHistory);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? cropMaintenanceHistory : maintenanceHistoryList);
    }

    public T getLandLordBankData(final String query, final int type) {
        LandlordBank mLandlordBank = null;
        List<LandlordBank> mLandlordBankList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mLandlordBank = new LandlordBank();
                    mLandlordBank.setPlotcode(cursor.getString(1));
                    mLandlordBank.setAccountholdername(cursor.getString(2));
                    mLandlordBank.setAccountnumber(cursor.getString(3));
                    mLandlordBank.setBankid(cursor.getInt(4));
                    mLandlordBank.setFilename(cursor.getString(5));
                    mLandlordBank.setFilelocation(cursor.getString(6));
                    mLandlordBank.setFileextension(cursor.getString(7));
                    mLandlordBank.setIsActive(cursor.getInt(8));
                    mLandlordBank.setCreatedbyuserid(cursor.getInt(9));
                    mLandlordBank.setCreatedDate(cursor.getString(10));
                    mLandlordBank.setUpdatedbyuserid(cursor.getInt(11));
                    mLandlordBank.setUpdatedDate(cursor.getString(12));
                    mLandlordBank.setServerUpdatedStatus(cursor.getInt(13));
                    if (type == 1) {
                        mLandlordBankList.add(mLandlordBank);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mLandlordBank : mLandlordBankList);
    }

    public T getLandLordIDProofsData(final String query, final int type) {
        LandlordIdProof mLandlordIdProof = null;
        List<LandlordIdProof> mLandlordIdProofList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mLandlordIdProof = new LandlordIdProof();
                    mLandlordIdProof.setPlotCode(cursor.getString(1));
                    mLandlordIdProof.setIDProofTypeId((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mLandlordIdProof.setIdProofNumber(cursor.getString(3));
                    mLandlordIdProof.setIsActive(cursor.getInt(4));
                    mLandlordIdProof.setCreatedByUserId(cursor.getInt(5));
                    mLandlordIdProof.setCreatedDate(cursor.getString(6));
                    mLandlordIdProof.setUpdatedByUserId(cursor.getInt(7));
                    mLandlordIdProof.setUpdatedDate(cursor.getString(8));
                    mLandlordIdProof.setServerUpdatedStatus(cursor.getInt(9));
                    if (type == 1) {
                        mLandlordIdProofList.add(mLandlordIdProof);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mLandlordIdProof : mLandlordIdProofList);
    }

    public List<ImageDetails> getImageDetails() {
        List<ImageDetails> imageDetailsList = new ArrayList<>();
        Cursor cursor = null;
        try {
            // mDatabase = palm3FoilDatabase.getReadableDatabase();
            cursor = mDatabase.rawQuery(Queries.getInstance().getImageDetails(), null);
            if (cursor.moveToFirst()) {
                do {
                    try {
                        if (cursor.getString(3) != null) {
                            ImageDetails rec = new ImageDetails();
                            rec.setFarmerCode(cursor.getString(0));
                            rec.setPlotCode(cursor.getString(1));
                            if (cursor.getInt(2) == 193) {
                                rec.setTableName(DatabaseKeys.TABLE_FARMER);
                            } else {
                                rec.setTableName("");
                            }
                            if (cursor.getString(3) != null && cursor.getString(3).length() > 0) {
                                String filepath = cursor.getString(3);
                                File imagefile = new File(filepath);
                                FileInputStream fis = null;
                                try {
                                    fis = new FileInputStream(imagefile);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                Bitmap bm = BitmapFactory.decodeStream(fis);
                                bm = ImageUtility.rotatePicture(90, bm);
                                String base64string = ImageUtility.convertBitmapToString(bm);
                                rec.setImageString(base64string);
                                imageDetailsList.add(rec);

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();

        }
        return imageDetailsList;
    }

    public T getCookingOilData(final String query, final int type) {
        CookingOil mCookingOil = null;
        List<CookingOil> mCookingOilList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mCookingOil = new CookingOil();
                    mCookingOil.setMarketSurveyCode(cursor.getString(1));
                    mCookingOil.setCookingoiltypeid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mCookingOil.setBrandname(cursor.getString(3));
                    mCookingOil.setMonthlyquantity(cursor.getDouble(4));
                    mCookingOil.setTotalpaidamount(cursor.getDouble(5));
                    mCookingOil.setCreatedbyuserid(cursor.getInt(6));
                    mCookingOil.setCreateddate(cursor.getString(7));
                    mCookingOil.setUpdatedbyuserid(cursor.getInt(8));
                    mCookingOil.setUpdateddate(cursor.getString(9));
                    mCookingOil.setServerUpdatedStatus(cursor.getInt(10));
                    if (type == 1) {
                        mCookingOilList.add(mCookingOil);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mCookingOil : mCookingOilList);
    }

    public T getDiseaseData(final String query, final int type) {
        Disease mDisease = null;
        List<Disease> mDiseaseList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mDisease = new Disease();
                    mDisease.setPlotCode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mDisease.setIsdiseasenoticedinpreviousvisit((cursor.getInt(cursor.getColumnIndex("IsDiseaseNoticedinPreviousVisit")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsDiseaseNoticedinPreviousVisit")));
                    mDisease.setIsproblemrectified((cursor.getInt(cursor.getColumnIndex("IsProblemRectified")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsProblemRectified")));
                    mDisease.setProblemrectifiedcomments(cursor.getString(cursor.getColumnIndex("ProblemRectifiedComments")));
                    mDisease.setDiseaseid((cursor.getInt(cursor.getColumnIndex("DiseaseId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("DiseaseId")));
                    mDisease.setChemicalid((cursor.getInt(cursor.getColumnIndex("ChemicalId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("ChemicalId")));
                    mDisease.setIsresultseen((cursor.getInt(cursor.getColumnIndex("IsResultSeen")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsResultSeen")));
                    mDisease.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
                    mDisease.setIsactive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mDisease.setCreatedbyuserid(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mDisease.setCreateddate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mDisease.setUpdatedbyuserid(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mDisease.setUpdateddate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mDisease.setServerupdatedstatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mDisease.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    mDisease.setRecommendFertilizerProviderId((cursor.getInt(cursor.getColumnIndex("RecommendedChemicalId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("RecommendedChemicalId")));
                    mDisease.setRecommendDosage(cursor.getDouble(cursor.getColumnIndex("Dosage")));
                    mDisease.setRecommendUOMId((cursor.getInt(cursor.getColumnIndex("UOMId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("UOMId")));
                    mDisease.setPercTreesId(cursor.getInt(cursor.getColumnIndex("PercTreesId")));
                    mDisease.setIsControlMeasure(cursor.getInt((cursor.getColumnIndex("IsControlMeasure"))));
                    if (type == 1) {
                        mDiseaseList.add(mDisease);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mDisease : mDiseaseList);
    }

    public T getFertilizerData(final String query, final int type) {
        Fertilizer mFertilizer = null;
        List<Fertilizer> mFertilizerList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFertilizer = new Fertilizer();
                    mFertilizer.setPlotcode(cursor.getString(1));
                    mFertilizer.setFertilizersourcetypeid((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mFertilizer.setFertilizerid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mFertilizer.setFertilizerproviderid((cursor.getInt(4) == 0) ? null : cursor.getInt(4));
                    mFertilizer.setUomid((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mFertilizer.setDosage(cursor.getDouble(6));
                    mFertilizer.setLastapplieddate(cursor.getString(7));
                    mFertilizer.setApplyfertilizerfrequencytypeid((cursor.getInt(8) == 0) ? null : cursor.getInt(8));
                    mFertilizer.setRatescale((cursor.getInt(9) == 0) ? null : cursor.getInt(9));
                    mFertilizer.setComments(cursor.getString(10));
                    mFertilizer.setIsactive(cursor.getInt(11));
                    mFertilizer.setCreatedbyuserid(cursor.getInt(12));
                    mFertilizer.setCreateddate(cursor.getString(13));
                    mFertilizer.setUpdatedbyuserid(cursor.getInt(14));
                    mFertilizer.setUpdateddate(cursor.getString(15));
                    mFertilizer.setServerupdatedstatus(cursor.getInt(16));
                    mFertilizer.setCropMaintenanceCode(cursor.getString(17));
                    mFertilizer.setSourceName(cursor.getString(18));
                    mFertilizer.setIsFertilizerApplied(cursor.getInt(19));
                    mFertilizer.setApplicationYear(cursor.getInt(20));
                    mFertilizer.setApplicationMonth(cursor.getString(21));
                    mFertilizer.setQuarter(cursor.getInt(22));
                    mFertilizer.setApplicationType(cursor.getString(23));
                    mFertilizer.setBioFertilizerId(cursor.getInt(24));
                    //mFertilizer.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    //mFertilizer.setSourceName(cursor.getString(cursor.getColumnIndex("SourceName")));
                    if (type == 1) {
                        mFertilizerList.add(mFertilizer);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFertilizer : mFertilizerList);
    }


    public T getFertilizerPrevQtrdtls(final String query, final int type) {
        Fertilizer mFertilizer = null;
        List<Fertilizer> mFertilizerList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFertilizer = new Fertilizer();

                    mFertilizer.setSourceName(cursor.getString(0));
                    mFertilizer.setApplicationYear(cursor.getInt(1));
                    mFertilizer.setApplicationMonth(cursor.getString(2));
                    mFertilizer.setApplicationType(cursor.getString(3));

                    mFertilizer.setComments(cursor.getString(4));
                    mFertilizer.setFertilizersourcetypeid(cursor.getInt(5));

                    //mFertilizer.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    //mFertilizer.setSourceName(cursor.getString(cursor.getColumnIndex("SourceName")));
                    if (type == 1) {
                        mFertilizerList.add(mFertilizer);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFertilizer : mFertilizerList);
    }

    /* public T getRecommndFertilizerData(final String query, final int type) {
         RecommndFertilizer mFertilizer = null;
         List<RecommndFertilizer> mFertilizerList = new ArrayList<>();
         Cursor cursor = null;
         Log.v(LOG_TAG, "@@@ farmer details query " + query);
         try {
             cursor = mDatabase.rawQuery(query, null);
             if (cursor != null && cursor.moveToFirst()) {
                 do {
                     mFertilizer = new RecommndFertilizer();
                     mFertilizer.setPlotcode(cursor.getString(1));
                     mFertilizer.setCropMaintenanceCode((""*//*+cursor.getInt(2) == 0) ? null : cursor.getInt(2)*//*));
                    mFertilizer.setRecommendFertilizerProviderId((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mFertilizer.setRecommendDosage((cursor.getInt(4) == 0) ? null : cursor.getInt(4));
                    mFertilizer.setRecommendUOMId((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                   *//* mFertilizer.setDosage(cursor.getDouble(6));
                    mFertilizer.setLastapplieddate(cursor.getString(7));
                    mFertilizer.setApplyfertilizerfrequencytypeid((cursor.getInt(8) == 0) ? null : cursor.getInt(8));
                    mFertilizer.setRatescale((cursor.getInt(9) == 0) ? null : cursor.getInt(9));*//*
                    mFertilizer.setComments(cursor.getString(6));
                    mFertilizer.setIsactive(cursor.getInt(7));
                    mFertilizer.setCreatedbyuserid(cursor.getInt(8));
                    mFertilizer.setCreateddate(cursor.getString(9));
                    mFertilizer.setUpdatedbyuserid(cursor.getInt(10));
                    mFertilizer.setUpdateddate(cursor.getString(11));
                    mFertilizer.setServerupdatedstatus(cursor.getInt(12));
                    mFertilizer.setCropMaintenanceCode(*//*cursor.getString(13)*//*"");
                    if (type == 1) {
                        mFertilizerList.add(mFertilizer);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFertilizer : mFertilizerList);
    }*/
    public T getFertilizerProviderData(final String query, final int type) {
        FertilizerProvider mFertilizerProvider = null;
        List<FertilizerProvider> mFertilizerProviderList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ FertilizerProvider query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mFertilizerProvider = new FertilizerProvider();
                    mFertilizerProvider.setName(cursor.getString(1));
                    mFertilizerProvider.setFertilizerTypeId((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mFertilizerProvider.setIsActive(cursor.getInt(3));
                    mFertilizerProvider.setCreatedByUserId(cursor.getInt(4));
                    mFertilizerProvider.setCreatedDate(cursor.getString(5));
                    mFertilizerProvider.setUpdatedByUserId(cursor.getInt(6));
                    mFertilizerProvider.setUpdatedDate(cursor.getString(7));
                    mFertilizerProvider.setServerUpdatedStatus(cursor.getInt(8));
                    if (type == 1) {
                        mFertilizerProviderList.add(mFertilizerProvider);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mFertilizerProvider : mFertilizerProviderList);
    }

    public T getHarvestData(final String query, final int type) {
        Harvest mHarvest = null;
        List<Harvest> mHarvestList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ Harvest query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mHarvest = new Harvest();
                    mHarvest.setPlotCode(cursor.getString(1));
                    mHarvest.setPlotyield(cursor.getDouble(2));
                    mHarvest.setYieldperhactor(cursor.getDouble(3));
                    mHarvest.setCollectioncenterid((cursor.getInt(4) == 0) ? null : cursor.getInt(4));
                    mHarvest.setTransportmodetypeid((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mHarvest.setVehicletypeid((cursor.getInt(6) == 0) ? null : cursor.getInt(6));
                    mHarvest.setTransportpaidamount(cursor.getInt(7));
                    mHarvest.setHarvestingmethodtypeid((cursor.getInt(8) == 0) ? null : cursor.getInt(8));
                    mHarvest.setWagesperday(cursor.getDouble(9));
                    mHarvest.setHarvestingtypeid((cursor.getInt(10) == 0) ? null : cursor.getInt(10));
                    mHarvest.setComments(cursor.getString(11));
                    mHarvest.setIsActive(cursor.getInt(12));
                    mHarvest.setCreatedbyuserid(cursor.getInt(13));
                    mHarvest.setCreateddate(cursor.getString(14));
                    mHarvest.setUpdatedbyuserid(cursor.getInt(15));
                    mHarvest.setUpdateddate(cursor.getString(16));
                    mHarvest.setServerUpdatedStatus(cursor.getInt(17));
                    mHarvest.setCropMaintenanceCode(cursor.getString(18));
                    mHarvest.setWagesUnitTypeId((cursor.getInt(19) == 0) ? null : cursor.getInt(19));
                    mHarvest.setContractAmount(cursor.getDouble(20));
                    if (type == 1) {
                        mHarvestList.add(mHarvest);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mHarvest : mHarvestList);
    }

    public T getHealthplantationData(final String query, final int type) {
        Healthplantation mHealthplantation = null;
        List<Healthplantation> mHealthplantationList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ Healthplantation query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    mHealthplantation = new Healthplantation();
                    mHealthplantation.setPlotCode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mHealthplantation.setPlantationstatetypeid((cursor.getInt(cursor.getColumnIndex("PlantationStateTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("PlantationStateTypeId")));
                    mHealthplantation.setTreesappearancetypeid((cursor.getInt(cursor.getColumnIndex("TreesAppearanceTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("TreesAppearanceTypeId")));
                    mHealthplantation.setTreegirthtypeid((cursor.getInt(cursor.getColumnIndex("TreeGirthTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("TreeGirthTypeId")));
                    mHealthplantation.setTreeheighttypeid((cursor.getInt(cursor.getColumnIndex("TreeHeightTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("TreeHeightTypeId")));
                    mHealthplantation.setFruitcolortypeid((cursor.getInt(cursor.getColumnIndex("FruitColorTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("FruitColorTypeId")));
                    mHealthplantation.setFruitsizetypeid((cursor.getInt(cursor.getColumnIndex("FruitSizeTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("FruitSizeTypeId")));
                    mHealthplantation.setFruithyegienetypeid((cursor.getInt(cursor.getColumnIndex("FruitHyegieneTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("FruitHyegieneTypeId")));
                    mHealthplantation.setPlantationtypeid((cursor.getInt(cursor.getColumnIndex("PlantationTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("PlantationTypeId")));
                    mHealthplantation.setIsactive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mHealthplantation.setCreatedbyuserid(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mHealthplantation.setCreateddate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mHealthplantation.setUpdatedbyuserid(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mHealthplantation.setUpdateddate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mHealthplantation.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mHealthplantation.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    mHealthplantation.setSpearleafId(cursor.getInt(cursor.getColumnIndex("SpearleafId")));
                    mHealthplantation.setSpearLeafRating(cursor.getString(cursor.getColumnIndex("SpearLeafRating")));
                    mHealthplantation.setDiseasesRating(cursor.getString(cursor.getColumnIndex("DiseasesRating")));
                    mHealthplantation.setPestRating(cursor.getString(cursor.getColumnIndex("PestRating")));
                    mHealthplantation.setWeevilsRating(cursor.getString(cursor.getColumnIndex("WeevilsRating")));
                    mHealthplantation.setInflorescenceRating(cursor.getString(cursor.getColumnIndex("InflorescenceRating")));
                    mHealthplantation.setBasinHealthRating(cursor.getString(cursor.getColumnIndex("BasinHealthRating")));
                    mHealthplantation.setNutDefRating(cursor.getString(cursor.getColumnIndex("NutDefRating")));
                    mHealthplantation.setNoOfFlorescene(cursor.getInt(cursor.getColumnIndex("NoOfFlorescene")));
                    mHealthplantation.setNoOfBuches(cursor.getInt(cursor.getColumnIndex("NoOfBuches")));
                    mHealthplantation.setBunchWeight(cursor.getInt(cursor.getColumnIndex("BunchWeight")));
                    if (type == 1) {
                        mHealthplantationList.add(mHealthplantation);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mHealthplantation : mHealthplantationList);
    }

    public T getInterCropPlantationXrefData(final String query, final int type) {
        InterCropPlantationXref mInterCropPlantationXref = null;
        List<InterCropPlantationXref> mInterCropPlantationXrefList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    mInterCropPlantationXref = new InterCropPlantationXref();
                    mInterCropPlantationXref.setPlotCode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mInterCropPlantationXref.setCropId((cursor.getInt(cursor.getColumnIndex("CropId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("CropId")));
                    mInterCropPlantationXref.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mInterCropPlantationXref.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mInterCropPlantationXref.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mInterCropPlantationXref.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mInterCropPlantationXref.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mInterCropPlantationXref.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    mInterCropPlantationXref.setRecmCropId(cursor.getInt(cursor.getColumnIndex("RecmCropId")));
                    if (cursor.getString(8).equalsIgnoreCase("")) {
                        mInterCropPlantationXref.setCropMaintenanceCode(null);
                    } else {
                        mInterCropPlantationXref.setCropMaintenanceCode(cursor.getString(8));
                    }
                    if (type == 1) {
                        mInterCropPlantationXrefList.add(mInterCropPlantationXref);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mInterCropPlantationXref : mInterCropPlantationXrefList);
    }

    public T getRecomFertlizerData(final String query, final int type) {
        RecommndFertilizer mRecomFertilizer = null;
        List<RecommndFertilizer> mNutrientList = new ArrayList<>();
        Cursor customCursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            customCursor = mDatabase.rawQuery(query, null);
            CustomCursor cursor = new CustomCursor(customCursor);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mRecomFertilizer = new RecommndFertilizer();
                    mRecomFertilizer.setPlotcode(cursor.getString(1));
                    mRecomFertilizer.setCropMaintenanceCode(cursor.getString(2));
                    mRecomFertilizer.setRecommendFertilizerProviderId((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mRecomFertilizer.setRecommendDosage((cursor.getDouble(4) == 0) ? null : cursor.getDouble(4));
                    mRecomFertilizer.setRecommendUOMId((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mRecomFertilizer.setComments(cursor.getString(6));
                    mRecomFertilizer.setIsactive(cursor.getInt(7));
                    mRecomFertilizer.setCreatedbyuserid(cursor.getInt(8));
                    mRecomFertilizer.setCreateddate(cursor.getString(9));
                    mRecomFertilizer.setUpdatedbyuserid(cursor.getInt(10));
                    mRecomFertilizer.setUpdateddate(cursor.getString(11));
                    mRecomFertilizer.setServerupdatedstatus(cursor.getInt(12));
                    //mNutrient.setCropMaintenanceCode(cursor.getString(16));
                    if (type == 1) {
                        mNutrientList.add(mRecomFertilizer);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting mRecomFertilizer details " + e.getMessage());
        }
        return (T) ((type == 0) ? mRecomFertilizer : mNutrientList);
    }

    public T getNutrientData(final String query, final int type) {
        Nutrient mNutrient = null;
        List<Nutrient> mNutrientList = new ArrayList<>();
        Cursor customCursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            customCursor = mDatabase.rawQuery(query, null);
            CustomCursor cursor = new CustomCursor(customCursor);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mNutrient = new Nutrient();
                    mNutrient.setPlotcode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mNutrient.setIsPreviousNutrientDeficiency((cursor.getInt(cursor.getColumnIndex("IsPreviousNutrientDeficiency")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsPreviousNutrientDeficiency")));
                    mNutrient.setIsproblemrectified((cursor.getInt(cursor.getColumnIndex("IsProblemRectified")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsProblemRectified")));
                    mNutrient.setIscurrentnutrientdeficiency((cursor.getInt(cursor.getColumnIndex("IsCurrentNutrientDeficiency")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsCurrentNutrientDeficiency")));
                    mNutrient.setNutrientid((cursor.getInt(cursor.getColumnIndex("NutrientId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("NutrientId")));
                    mNutrient.setChemicalid((cursor.getInt(cursor.getColumnIndex("ChemicalId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("ChemicalId")));
                    mNutrient.setApplynutrientfrequencytypeid((cursor.getInt(cursor.getColumnIndex("ApplyNutrientFrequencyTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("ApplyNutrientFrequencyTypeId")));
                    mNutrient.setIsresultseen((cursor.getInt(cursor.getColumnIndex("IsResultSeen")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsResultSeen")));
                    mNutrient.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
                    mNutrient.setIsactive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mNutrient.setCreatedbyuserid(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mNutrient.setCreateddate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mNutrient.setUpdatedbyuserid(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mNutrient.setUpdateddate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mNutrient.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mNutrient.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    mNutrient.setRecommendFertilizerProviderId((cursor.getInt(cursor.getColumnIndex("RecommendedFertilizerId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("RecommendedFertilizerId")));
                    mNutrient.setRecommendDosage(cursor.getDouble(cursor.getColumnIndex("Dosage")));
                    mNutrient.setRecommendUOMId((cursor.getInt(cursor.getColumnIndex("UOMId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("UOMId")));
                    mNutrient.setPercTreesId(cursor.getInt(cursor.getColumnIndex("PercTreesId")));

                    if (type == 1) {
                        mNutrientList.add(mNutrient);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mNutrient : mNutrientList);
    }

    public T getOwnershipfilerepositoryData(final String query, final int type) {
        Ownershipfilerepository mOwnershipfilerepository = null;
        List<Ownershipfilerepository> mOwnershipfilerepositoryList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mOwnershipfilerepository = new Ownershipfilerepository();
                    mOwnershipfilerepository.setFarmerCode(cursor.getString(1));
                    mOwnershipfilerepository.setPlotcode(cursor.getString(2));
                    mOwnershipfilerepository.setModuletypeid((cursor.getInt(3) == 0) ? null : cursor.getInt(3));
                    mOwnershipfilerepository.setFilename(cursor.getString(4));
                    mOwnershipfilerepository.setFilelocation(cursor.getString(5));
                    mOwnershipfilerepository.setFileextension(cursor.getString(6));
                    mOwnershipfilerepository.setComments(cursor.getString(7));
                    mOwnershipfilerepository.setIsactive(cursor.getInt(8));
                    mOwnershipfilerepository.setCreatedbyuserid(cursor.getInt(9));
                    mOwnershipfilerepository.setCreateddate(cursor.getString(10));
                    mOwnershipfilerepository.setUpdatedbyuserid(cursor.getInt(11));
                    mOwnershipfilerepository.setUpdateddate(cursor.getString(12));
                    mOwnershipfilerepository.setServerupdatedstatus(cursor.getInt(13));
                    if (type == 1) {
                        mOwnershipfilerepositoryList.add(mOwnershipfilerepository);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mOwnershipfilerepository : mOwnershipfilerepositoryList);
    }

    public T getPestData(final String query, final int type) {
        Pest mPest = null;
        List<Pest> mPestList = new ArrayList<>();
        Cursor cursor = null;

        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    mPest = new Pest();
                    mPest.setPlotCode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mPest.setPestid((cursor.getInt(cursor.getColumnIndex("PestId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("PestId")));
                    mPest.setIsresultsseen((cursor.getInt(cursor.getColumnIndex("IsResultsSeen")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsResultsSeen")));
                    mPest.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
                    mPest.setIsactive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mPest.setCreatedbyuserid(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mPest.setCreateddate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mPest.setUpdatedbyuserid(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mPest.setUpdateddate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mPest.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mPest.setCode(cursor.getString(cursor.getColumnIndex("Code")));
                    mPest.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    mPest.setRecommendFertilizerProviderId((cursor.getInt(cursor.getColumnIndex("RecommendedChemicalId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("RecommendedChemicalId")));
                    mPest.setRecommendDosage(cursor.getDouble(cursor.getColumnIndex("Dosage")));
                    mPest.setRecommendUOMId((cursor.getInt(cursor.getColumnIndex("UOMId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("UOMId")));
                    mPest.setPercTreesId(cursor.getInt(cursor.getColumnIndex("PercTreesId")));
                    mPest.setIsControlMeasure(cursor.getInt(cursor.getColumnIndex("IsControlMeasure")));
                    if (type == 1) {
                        mPestList.add(mPest);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting pest details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPest : mPestList);
    }

    public T getUprootmentData(final String query, final int type) {
        Uprootment mUprootment = null;
        List<Uprootment> mUprootmentList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mUprootment = new Uprootment();
                    mUprootment.setPlotcode(cursor.getString(1));
                    mUprootment.setSeedsplanted(cursor.getInt(2));
                    mUprootment.setPlamscount(cursor.getInt(3));
                    mUprootment.setIstreesmissing(cursor.getInt(4));
                    mUprootment.setMissingtreescount(cursor.getInt(5));
                    mUprootment.setReasontypeid((cursor.getInt(6) == 0) ? null : cursor.getInt(6));
                    mUprootment.setComments(cursor.getString(7));
                    mUprootment.setIsactive(cursor.getInt(8));
                    mUprootment.setCreatedbyuserid(cursor.getInt(9));
                    mUprootment.setCreateddate(cursor.getString(10));
                    mUprootment.setUpdatedbyuserid(cursor.getInt(11));
                    mUprootment.setUpdateddate(cursor.getString(12));
                    mUprootment.setServerupdatedstatus(cursor.getInt(13));
                    mUprootment.setCropMaintenanceCode(cursor.getString(14));
                    mUprootment.setExpectedPlamsCount(cursor.getInt(15));
                    if (type == 1) {
                        mUprootmentList.add(mUprootment);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mUprootment : mUprootmentList);
    }

    public T getWeedData(final String query, final int type) {
        Weed mWeed = null;
        List<Weed> mWeedList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mWeed = new Weed();
                    mWeed.setPlotCode(cursor.getString(cursor.getColumnIndex("PlotCode")));
                    mWeed.setIsweedproperlydone((cursor.getInt(cursor.getColumnIndex("IsWeedProperlyDone")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsWeedProperlyDone")));
                    mWeed.setMethodtypeid((cursor.getInt(cursor.getColumnIndex("MethodTypeId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("MethodTypeId")));
                    mWeed.setChemicalid((cursor.getInt(cursor.getColumnIndex("ChemicalId")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("ChemicalId")));
                    mWeed.setApplicationfrequency((cursor.getInt(cursor.getColumnIndex("ApplicationFrequency")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("ApplicationFrequency")));
                    mWeed.setIsprunning((cursor.getInt(cursor.getColumnIndex("IsPrunning")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsPrunning")));
                    mWeed.setPrunningfrequency((cursor.getInt(cursor.getColumnIndex("PrunningFrequency")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("PrunningFrequency")));
                    mWeed.setIsmulchingseen((cursor.getInt(cursor.getColumnIndex("IsMulchingSeen")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsMulchingSeen")));
                    mWeed.setIsweavilsseen((cursor.getInt(cursor.getColumnIndex("IsWeavilsSeen")) == 0) ? null : cursor.getInt(cursor.getColumnIndex("IsWeavilsSeen")));
                    mWeed.setIsactive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mWeed.setCreatedbyuserid(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mWeed.setCreateddate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mWeed.setUpdatedbyuserid(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mWeed.setUpdateddate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mWeed.setServerupdatedstatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    mWeed.setCropMaintenanceCode(cursor.getString(cursor.getColumnIndex("CropMaintenanceCode")));
                    mWeed.setBasinHealthId(cursor.getInt(cursor.getColumnIndex("BasinHealthId")));
                    mWeed.setPruningId(cursor.getInt(cursor.getColumnIndex("PruningId")));
                    mWeed.setWeedId(cursor.getInt(cursor.getColumnIndex("WeedId")));
                    mWeed.setWeevilsId(cursor.getInt(cursor.getColumnIndex("WeevilsId")));
                    mWeed.setInflorescenceId(cursor.getInt(cursor.getColumnIndex("InflorescenceId")));


                    if (type == 1) {
                        mWeedList.add(mWeed);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mWeed : mWeedList);
    }


    public T getYieldData(final String query, final int type) {
        YieldAssessment mYield = null;
        List<YieldAssessment> mYieldList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mYield = new YieldAssessment();
                    mYield.setCropMaintenaceCode(cursor.getString(cursor.getColumnIndex("CropMaintenaceCode")));
                    mYield.setQuestion(cursor.getString(cursor.getColumnIndex("Question")));
                    mYield.setAnswer(cursor.getString(cursor.getColumnIndex("Answer")));
                    mYield.setValue(cursor.getString(cursor.getColumnIndex("Value")));
                    mYield.setYear(cursor.getInt(cursor.getColumnIndex("Year")));
                    mYield.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mYield.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mYield.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mYield.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mYield.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mYield.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));

                    if (type == 1) {
                        mYieldList.add(mYield);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mYield : mYieldList);
    }


    public T getWhiteData(final String query, final int type) {
        WhiteFlyAssessment mWhite = null;
        List<WhiteFlyAssessment> mWhiteList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mWhite = new WhiteFlyAssessment();
                    mWhite.setCropMaintenaceCode(cursor.getString(cursor.getColumnIndex("CropMaintenaceCode")));
                    mWhite.setQuestion(cursor.getString(cursor.getColumnIndex("Question")));
                    mWhite.setAnswer(cursor.getString(cursor.getColumnIndex("Answer")));
                    mWhite.setValue(cursor.getString(cursor.getColumnIndex("Value")));
                    mWhite.setYear(cursor.getInt(cursor.getColumnIndex("Year")));
                    mWhite.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    mWhite.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    mWhite.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    mWhite.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    mWhite.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    mWhite.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));


                    if (type == 1) {
                        mWhiteList.add(mWhite);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mWhite : mWhiteList);
    }


    public T getIdentityProofFileRepositoryXrefData(final String query, final int type) {
        IdentityProofFileRepositoryXref mIdentityProofFileRepositoryXref = null;
        List<IdentityProofFileRepositoryXref> mIdentityProofFileRepositoryXrefList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mIdentityProofFileRepositoryXref = new IdentityProofFileRepositoryXref();
                    mIdentityProofFileRepositoryXref.setIdentityProofId((cursor.getInt(0) == 0) ? null : cursor.getInt(0));
                    mIdentityProofFileRepositoryXref.setFileRepositoryId((cursor.getInt(1) == 0) ? null : cursor.getInt(1));
                    mIdentityProofFileRepositoryXref.setServerUpdatedStatus(cursor.getInt(2));
                    if (type == 1) {
                        mIdentityProofFileRepositoryXrefList.add(mIdentityProofFileRepositoryXref);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mIdentityProofFileRepositoryXref : mIdentityProofFileRepositoryXrefList);
    }

    public T getPestChemicalXrefData(final String query, final int type) {
        PestChemicalXref mPestChemicalXref = null;
        List<PestChemicalXref> mPestChemicalXrefList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPestChemicalXref = new PestChemicalXref();
                    mPestChemicalXref.setPestCode(cursor.getString(1));
                    mPestChemicalXref.setChemicalId((cursor.getInt(2) == 0) ? null : cursor.getInt(2));
                    mPestChemicalXref.setCreatedByUserId(cursor.getInt(3));
                    mPestChemicalXref.setCreatedDate(cursor.getString(4));
                    mPestChemicalXref.setUpdatedByUserId(cursor.getInt(5));
                    mPestChemicalXref.setUpdatedDate(cursor.getString(6));
                    mPestChemicalXref.setServerUpdatedStatus(cursor.getInt(7));
                    mPestChemicalXref.setCropMaintenanceCode(cursor.getString(8));
                    if (type == 1) {
                        mPestChemicalXrefList.add(mPestChemicalXref);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPestChemicalXref : mPestChemicalXrefList);
    }

    public T getPlantationFileRepositoryXrefData(final String query, final int type) {
        PlantationFileRepositoryXref mPlantationFileRepositoryXref = null;
        List<PlantationFileRepositoryXref> mPlantationFileRepositoryXrefList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mPlantationFileRepositoryXref = new PlantationFileRepositoryXref();
                    mPlantationFileRepositoryXref.setPlantationId((cursor.getInt(0) == 0) ? null : cursor.getInt(0));
                    mPlantationFileRepositoryXref.setPlantationId((cursor.getInt(1) == 0) ? null : cursor.getInt(1));
                    mPlantationFileRepositoryXref.setServerUpdatedStatus(cursor.getInt(2));
                    if (type == 1) {
                        mPlantationFileRepositoryXrefList.add(mPlantationFileRepositoryXref);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mPlantationFileRepositoryXref : mPlantationFileRepositoryXrefList);
    }

    public T getDigitalContractData(final String query, final int type) {
        DigitalContract mDigitalContract = null;
        List<DigitalContract> mDigitalContractList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ farmer details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    mDigitalContract = new DigitalContract();
                    mDigitalContract.setName(cursor.getString(1));
                    mDigitalContract.setFILENAME(cursor.getString(2));
                    mDigitalContract.setFileLocation(cursor.getString(3));
                    mDigitalContract.setFileExtension(cursor.getString(4));
                    mDigitalContract.setStateId((cursor.getInt(5) == 0) ? null : cursor.getInt(5));
                    mDigitalContract.setIsActive(cursor.getInt(6));
                    mDigitalContract.setCreatedByUserId(cursor.getInt(7));
                    mDigitalContract.setCreatedDate(cursor.getString(8));
                    mDigitalContract.setUpdatedByUserId(cursor.getInt(9));
                    mDigitalContract.setUpdatedDate(cursor.getString(10));
                    mDigitalContract.setServerUpdatedStatus(cursor.getInt(11));
                    if (type == 1) {
                        mDigitalContractList.add(mDigitalContract);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting user details " + e.getMessage());
        }
        return (T) ((type == 0) ? mDigitalContract : mDigitalContractList);
    }

    public void upDataPlotStatus(String plotCode) {
        if (checkValueExistedInDatabase(Queries.getInstance().isPlotExisted(DatabaseKeys.TABLE_FARMERHISTORY, plotCode))) {
            ContentValues update_values = new ContentValues();
            update_values.put("IsActive", "0");
            update_values.put("ServerUpdatedStatus", "0");
            update_values.put("UpdatedByUserId", CommonConstants.USER_ID);
            update_values.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            String where = " PlotCode = '" + plotCode + "'" + " and IsActive = '1'";
            mDatabase.update("FarmerHistory", update_values, where, null);
        }
    }

    public void upDataPlotCureentCropStatus(String plotCode) {
        if (checkValueExistedInDatabase(Queries.getInstance().isPlotExisted(DatabaseKeys.TABLE_PLOTCURRENTCROP, plotCode))) {
            ContentValues update_values = new ContentValues();
            update_values.put("IsActive", "0");
            update_values.put("ServerUpdatedStatus", "0");
            update_values.put("UpdatedByUserId", CommonConstants.USER_ID);
            update_values.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            String where = " PlotCode = '" + plotCode + "'" + " and IsActive = '1'";
            mDatabase.update("PlotCurrentCrop", update_values, where, null);
        }
    }

    public void upNotificationStatus() {
        ContentValues update_values = new ContentValues();
        update_values.put("ServerUpdatedStatus", "0");
        update_values.put("isRead", 1);
        update_values.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        String where = " isRead ='" + 0 + "'";
        mDatabase.update("Alerts", update_values, where, null);
    }

    public void updateComplaintStatus(String complaintCode) {
        ContentValues update_values = new ContentValues();
        update_values.put("IsActive", "0");
        update_values.put("ServerUpdatedStatus", "0");
        update_values.put("UpdatedByUserId", CommonConstants.USER_ID);
        update_values.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
        String where = " ComplaintCode ='" + complaintCode + "'" + " and IsActive = '1'";
        mDatabase.update("ComplaintStatusHistory", update_values, where, null);
    }

    public void updateComplaintFilePath(String complaintCode, String filePath) {
        ContentValues update_values = new ContentValues();
        update_values.put("FileLocation", filePath);
        String where = "ComplaintCode ='" + complaintCode + "'" + " and FileExtension='.mp3'";
        mDatabase.update("ComplaintRepository", update_values, where, null);
    }

    public List<ProspectivePlotsModel> getProspectivePlotDetails(String farmerCode, int plotStatus) {
        List<ProspectivePlotsModel> plotsModels = new ArrayList<>();
        Cursor cursor = null;
        String query = Queries.getInstance().getPlotDetails(farmerCode.trim(), plotStatus);
        Log.v(LOG_TAG, "Query for getting plots related to farmer " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ProspectivePlotsModel plotsModel = new ProspectivePlotsModel();
                    plotsModel.setPlotID(cursor.getString(0));
                    plotsModel.setPlotArea(cursor.getDouble(1));
                    plotsModel.setPlotVillageName(cursor.getString(2));
                    plotsModel.setMandalName(cursor.getString(3));
                    plotsModel.setPlotIncome(cursor.getString(4));
                    plotsModel.setPotentialScore(cursor.getInt(5));
                    plotsModel.setPlotCrops(cursor.getString(6));
                    plotsModel.setLastVisitedDate(cursor.getString(7));
                    plotsModels.add(plotsModel);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return plotsModels;
    }

    public List<ActivityLog> getActivityLogData() {
        List<ActivityLog> activityLogs = new ArrayList<>();
        Cursor cursor = null;
        String query = Queries.getInstance().queryActivityLog();
        Log.v(LOG_TAG, "Query for getting plots related to farmer " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ActivityLog activityLog = new ActivityLog();
                    activityLog.setFarmerCode(cursor.getString(1));
                    activityLog.setPlotCode(cursor.getString(2));
                    activityLog.setCollectionCode(null);
                    activityLog.setComplaintCode(null);
                    activityLog.setActivityTypeId(cursor.getInt(5));
                    activityLog.setCreatedByUserId(cursor.getInt(6));
                    activityLog.setCreatedDate(cursor.getString(7));
                    activityLog.setServerUpdatedStatus(cursor.getInt(8));
                    activityLog.setConsignmentCode(null);
                    activityLogs.add(activityLog);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return activityLogs;
    }


    public String getFalogLatLongs(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        String latlongData = "";
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.getCount() > 0 && genericDataQuery.moveToFirst()) {
                do {
                    latlongData = (genericDataQuery.getDouble(0) + "-" + genericDataQuery.getDouble(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return latlongData;
    }

    public String getLatLongs(final String query) {
        Log.v(LOG_TAG, "@@@ Generic Query " + query);
        String latlongData = "";
        Cursor genericDataQuery = mDatabase.rawQuery(query, null);
        try {
            if (genericDataQuery.getCount() > 0 && genericDataQuery.moveToFirst()) {
                do {
                    latlongData = (genericDataQuery.getDouble(0) + "-" + genericDataQuery.getDouble(1));
                } while (genericDataQuery.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (null != genericDataQuery)
                genericDataQuery.close();

            closeDataBase();
        }
        return latlongData;
    }

    public List<ComplaintsDetails> getComplaintsDataByPlot(String plotCode, String farmerCode) {
        List<ComplaintsDetails> complaintsDetailsesArrayList = new ArrayList<>();
        Cursor cursor = null;
        String qurey = Queries.getInstance().getComplaintsDataByPlot(plotCode, farmerCode);
        Log.v(LOG_TAG, "Query for getting complaints of plot " + qurey);
        try {
            cursor = mDatabase.rawQuery(qurey, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ComplaintsDetails complaintsDetails = new ComplaintsDetails();
                    complaintsDetails.setComplaintId(cursor.getString(0));
                    complaintsDetails.setComplaintTypeId(cursor.getString(1));
                    complaintsDetails.setAssigntoUserId(cursor.getString(2));
                    complaintsDetails.setStatusTypeId(cursor.getString(3));
                    complaintsDetails.setCriticalityByTypeId(cursor.getString(4));
                    complaintsDetails.setComplaintRaisedon(cursor.getString(5));
                    complaintsDetails.setPlotId(cursor.getString(6));
                    complaintsDetails.setfarmerFirstName(cursor.getString(7));
                    complaintsDetails.setFarmerLastName(cursor.getString(8));
                    complaintsDetails.setVillage(cursor.getString(9));
                    complaintsDetailsesArrayList.add(complaintsDetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return complaintsDetailsesArrayList;
    }

    public T getComplaints(final String query, int type) {
        List<Complaints> complaintsList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for getting complaints " + query);
        Complaints complaints = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    complaints = new Complaints();
                    complaints.setCode(cursor.getString(1));
                    complaints.setPlotCode(cursor.getString(2));
                    complaints.setCriticalityByTypeId(null);
                    complaints.setIsActive(cursor.getInt(4));
                    complaints.setCreatedByUserId(cursor.getInt(5));
                    complaints.setCreatedDate(cursor.getString(6));
                    complaints.setUpdatedByUserId(cursor.getInt(7));
                    complaints.setUpdatedDate(cursor.getString(8));
                    complaints.setServerUpdatedStatus(cursor.getInt(9));
                    if (type == 1) {
                        complaintsList.add(complaints);
                    }
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? complaints : complaintsList);
    }


    public T getComplaintRepository(final String query, int type) {
        List<ComplaintRepository> complaintRepositories = new ArrayList<>();
        Cursor cursor = null;
        ComplaintRepository complaintRepository = null;
        Log.v(LOG_TAG, "Query for getting complaints " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    complaintRepository = new ComplaintRepository();
                    complaintRepository.setComplaintCode(cursor.getString(1));
                    complaintRepository.setModuleTypeId(cursor.getInt(2));
                    complaintRepository.setFileName(cursor.getString(3));
                    complaintRepository.setFileLocation(cursor.getString(4));
                    complaintRepository.setFileExtension(cursor.getString(5));
                    complaintRepository.setIsVideoRecording(cursor.getInt(6));
                    complaintRepository.setIsResult(cursor.getInt(7));
                    complaintRepository.setIsActive(cursor.getInt(8));
                    complaintRepository.setCreatedByUserId(cursor.getInt(9));
                    complaintRepository.setCreatedDate(cursor.getString(10));
                    complaintRepository.setUpdatedByUserId(cursor.getInt(11));
                    complaintRepository.setUpdatedDate(cursor.getString(12));
                    complaintRepository.setServerUpdatedStatus(cursor.getInt(13));
                    if (type == 1) {
                        complaintRepositories.add(complaintRepository);
                    }
                } while (cursor.moveToNext());

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? complaintRepository : complaintRepositories);
    }

    public T getComplaintRefreshRepository(final String query, int type) {
        List<ComplaintRepositoryRefresh> complaintRepositories = new ArrayList<>();
        Cursor cursor = null;
        ComplaintRepositoryRefresh complaintRepository = null;
        Log.v(LOG_TAG, "Query for getting complaints " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    complaintRepository = new ComplaintRepositoryRefresh();
                    complaintRepository.setcomplaintCode(cursor.getString(1));
                    complaintRepository.setModuleTypeId(cursor.getInt(2));
                    complaintRepository.setFileName(cursor.getString(3));
                    complaintRepository.setFileLocation(cursor.getString(4));
                    complaintRepository.setFileExtension(cursor.getString(5));
                    complaintRepository.setIsVideoRecording(cursor.getInt(6));
                    complaintRepository.setIsResult(cursor.getInt(7));
                    complaintRepository.setIsActive(cursor.getInt(8));
                    complaintRepository.setCreatedByUserId(cursor.getInt(9));
                    complaintRepository.setCreatedDate(cursor.getString(10));
                    complaintRepository.setUpdatedByUserId(cursor.getInt(11));
                    complaintRepository.setUpdatedDate(cursor.getString(12));
                    complaintRepository.setServerUpdatedStatus(cursor.getInt(13));
                    if (complaintRepository.isIsVideoRecording() == 0) {
                        File imgFile = new File(complaintRepository.getFileLocation());
                        if (imgFile.exists()) {
                            FileInputStream fis = null;
                            try {
                                fis = new FileInputStream(imgFile);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Bitmap bm = BitmapFactory.decodeStream(fis);
                            bm = ImageUtility.rotatePicture(90, bm);
                            String base64string = ImageUtility.convertBitmapToString(bm);
                            complaintRepository.setByteImage(base64string);
                        } else {
                            complaintRepository.setByteImage("");
                        }
                    } else {

                        complaintRepository.setByteImage(doFileUpload(complaintRepository.getFileLocation()));
                    }
                    if (type == 1) {
                        complaintRepositories.add(complaintRepository);
                    }
                } while (cursor.moveToNext());

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? complaintRepository : complaintRepositories);
    }


    private String doFileUpload(String selectedPath) {
        byte[] videoBytes;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            File AudioFile = new File(selectedPath);
            if (AudioFile.exists()) {
                FileInputStream fis = new FileInputStream(AudioFile);

                byte[] buf = new byte[1024];
                int n;
                while (-1 != (n = fis.read(buf)))
                    baos.write(buf, 0, n);

                videoBytes = baos.toByteArray();


                String video_str = android.util.Base64.encodeToString(videoBytes, 0);
                System.out.println("video array" + video_str);
                return video_str;
            } else {
                return "";
            }

        } catch (Exception e) {
            return "";
            // TODO: handle exception
        }
    }

    public T getComplaintStatusHistory(String query, int type) {
        List<ComplaintStatusHistory> complaintStatusHistorys = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for getComplaintStatusHistory " + query);
        ComplaintStatusHistory complaintStatusHistory = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {
                    complaintStatusHistory = new ComplaintStatusHistory();
                    complaintStatusHistory.setComplaintCode(cursor.getString(1));
                    complaintStatusHistory.setStatusTypeId(cursor.getString(2));
                    complaintStatusHistory.setAssigntoUserId(cursor.getString(3));
                    complaintStatusHistory.setComments(cursor.getString(4));
                    complaintStatusHistory.setIsActive(cursor.getInt(5));
                    complaintStatusHistory.setCreatedByUserId(cursor.getInt(6));
                    complaintStatusHistory.setCreatedDate(cursor.getString(7));
                    complaintStatusHistory.setUpdatedByUserId(cursor.getInt(8));
                    complaintStatusHistory.setUpdatedDate(cursor.getString(9));
                    complaintStatusHistory.setServerUpdatedStatus(cursor.getInt(10));

                    if (type == 1) {
                        complaintStatusHistorys.add(complaintStatusHistory);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? complaintStatusHistory : complaintStatusHistorys);
    }

    public T getComplaintTypeXref(String query, int type) {
        List<ComplaintTypeXref> complaintTypeXref = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for getComplaintTypeXref " + query);
        ComplaintTypeXref complaintTypeXref1 = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    complaintTypeXref1 = new ComplaintTypeXref();
                    complaintTypeXref1.setComplaintCode(cursor.getString(1));
                    complaintTypeXref1.setComplaintTypeId(cursor.getInt(2));
                    complaintTypeXref1.setCreatedByUserId(cursor.getInt(3));
                    complaintTypeXref1.setCreatedDate(cursor.getString(4));
                    complaintTypeXref1.setUpdatedByUserId(cursor.getInt(5));
                    complaintTypeXref1.setUpdatedDate(cursor.getString(6));
                    complaintTypeXref1.setServerUpdatedStatus(cursor.getInt(7));
                    complaintTypeXref.add(complaintTypeXref1);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? complaintTypeXref1 : complaintTypeXref);

    }

    public void getComplaintsByUser(String query, final ApplicationThread.OnComplete onComplete) {
        List<ComplaintsDetails> complaintsDetailsesArrayList = new ArrayList<>();
        Cursor cursor = null;
        // String qurey = Queries.getInstance().getComplaintsDataByPlot(plotCode, farmerCode);
        Log.v(LOG_TAG, "Query for getting complaints of plot " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ComplaintsDetails complaintsDetails = new ComplaintsDetails();
                    complaintsDetails.setComplaintId(cursor.getString(0));
                    complaintsDetails.setComplaintTypeId(cursor.getString(1));
                    complaintsDetails.setAssigntoUserId(cursor.getString(2));
                    complaintsDetails.setStatusTypeId(cursor.getString(3));
                    complaintsDetails.setCriticalityByTypeId(null);
                    complaintsDetails.setComplaintRaisedon(cursor.getString(5));
                    complaintsDetails.setPlotId(cursor.getString(6));
                    complaintsDetails.setfarmerFirstName(cursor.getString(7));
                    complaintsDetails.setFarmerLastName(cursor.getString(8));
                    complaintsDetails.setVillage(cursor.getString(9));
                    complaintsDetails.setComplaintTypeName(cursor.getString(10));
                    complaintsDetails.setComplaintStatusTypeName(cursor.getString(11));
                    complaintsDetails.setComplaintCreatedBy(cursor.getString(12));
                    complaintsDetailsesArrayList.add(complaintsDetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            onComplete.execute(true, complaintsDetailsesArrayList, "getting data");
        }
    }

    public LinkedHashMap<String, List<KrasDataToDisplay>> getKrasDataToDisplay(String query) {
        Log.v(LOG_TAG, "@@@@ kras query " + query);
        Cursor cursor = null;
        LinkedHashMap<String, List<KrasDataToDisplay>> krasMap = new LinkedHashMap<>();
        try {
            cursor = mDatabase.rawQuery(query, null);
            List<KrasDataToDisplay> krasDataToDisplays = null;
            String oldKraCode = "", newKraCode;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    newKraCode = cursor.getString(1);
                    if (TextUtils.isEmpty(oldKraCode) || !newKraCode.equalsIgnoreCase(oldKraCode)) {
                        Log.v(LOG_TAG, "@@@ kra code changed " + newKraCode);
                        oldKraCode = newKraCode;
                        krasDataToDisplays = new ArrayList<>();
                    }
                    KrasDataToDisplay krasDataToDisplay = new KrasDataToDisplay();
                    krasDataToDisplay.setUserKraId(cursor.getInt(0));
                    krasDataToDisplay.setkRACode(oldKraCode);
                    krasDataToDisplay.setkRAName(cursor.getString(2));
                    krasDataToDisplay.setuOM(cursor.getString(3));
                    krasDataToDisplay.setAnnualTarget(cursor.getDouble(4));
                    krasDataToDisplay.setAnnualAchievedTarget(cursor.getDouble(5));
                    krasDataToDisplay.setUserId(cursor.getInt(6));
                    krasDataToDisplay.setMonthNumber(cursor.getInt(7));
                    krasDataToDisplay.setMonthlyTarget(cursor.getDouble(8));
                    krasDataToDisplay.setMonthlyAchievedTarget(cursor.getDouble(9));
                    krasDataToDisplays.add(krasDataToDisplay);
                    krasMap.put(oldKraCode, krasDataToDisplays);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return krasMap;
    }

    //Alerts

    public T getAlertsPlotInfo(String query, int type) {
        List<AlertsPlotInfo> alertsPlotInfoList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for alertsPlotInfo " + query);
        AlertsPlotInfo alertsPlotInfoObj = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    alertsPlotInfoObj = new AlertsPlotInfo();
                    alertsPlotInfoObj.setPlotCode(cursor.getString(0));
                    alertsPlotInfoObj.setFarmerCode(cursor.getString(1));
                    alertsPlotInfoObj.setFirstName(cursor.getString(2));
                    alertsPlotInfoObj.setMiddleName(cursor.getString(3));
                    alertsPlotInfoObj.setLastName(cursor.getString(4));
                    alertsPlotInfoObj.setContactNumber(cursor.getString(5));
                    alertsPlotInfoObj.setMandalName(cursor.getString(6));
                    alertsPlotInfoObj.setVillageName(cursor.getString(7));
                    alertsPlotInfoObj.setTotalPlotArea(cursor.getString(8));
                    alertsPlotInfoObj.setPotentialScore(cursor.getString(9));
                    alertsPlotInfoObj.setCropName(cursor.getString(10));
                    alertsPlotInfoObj.setLastVistDate(cursor.getString(11));
                    alertsPlotInfoObj.setHarvestDate(cursor.getString(12));
                    alertsPlotInfoObj.setPrioritization(cursor.getString(13));
                    alertsPlotInfoObj.setUserName(cursor.getString(14));
                    alertsPlotInfoList.add(alertsPlotInfoObj);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? alertsPlotInfoObj : alertsPlotInfoList);

    }

    public T getAlertsVisitsInfo(String query, int type) {
        List<AlertsVisitsInfo> alertsPlotInfoList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for alertsPlotInfo " + query);
        AlertsVisitsInfo alertsPlotInfoObj = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    alertsPlotInfoObj = new AlertsVisitsInfo();
                    alertsPlotInfoObj.setPlotCode(cursor.getString(0));
                    alertsPlotInfoObj.setFarmerCode(cursor.getString(1));
                    alertsPlotInfoObj.setFirstName(cursor.getString(2));
                    alertsPlotInfoObj.setMiddleName(cursor.getString(3));
                    alertsPlotInfoObj.setLastName(cursor.getString(4));
                    alertsPlotInfoObj.setContactNumber(cursor.getString(5));
                    alertsPlotInfoObj.setMandalName(cursor.getString(6));
                    alertsPlotInfoObj.setVillageName(cursor.getString(7));
                    alertsPlotInfoObj.setTotalPlotArea(cursor.getString(8));
                    alertsPlotInfoObj.setDateofplanting(cursor.getString(9));
                    alertsPlotInfoObj.setPlotvisiteddate(cursor.getString(10));
                    alertsPlotInfoList.add(alertsPlotInfoObj);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return (T) ((type == 0) ? alertsPlotInfoObj : alertsPlotInfoList);

    }

    public T getAletsMissingTreesInfo(String query, int type) {
        List<MissingTressInfo> alertsMissingTreesInfoList = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for alertsPlotInfo " + query);
        MissingTressInfo alertsMissingTreesInfoObj = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    alertsMissingTreesInfoObj = new MissingTressInfo();
                    alertsMissingTreesInfoObj.setPlotCode(cursor.getString(0));
                    alertsMissingTreesInfoObj.setFarmerCode(cursor.getString(1));
                    alertsMissingTreesInfoObj.setFirstName(cursor.getString(2));
                    alertsMissingTreesInfoObj.setMiddleName(cursor.getString(3));
                    alertsMissingTreesInfoObj.setLastName(cursor.getString(4));
                    alertsMissingTreesInfoObj.setMandalName(cursor.getString(5));
                    alertsMissingTreesInfoObj.setVillageName(cursor.getString(6));
                    alertsMissingTreesInfoObj.setSaplingsplanted(cursor.getString(7));
                    alertsMissingTreesInfoObj.setCurrentTrees(cursor.getString(8));
                    alertsMissingTreesInfoObj.setMissingTrees(cursor.getString(9));
                    alertsMissingTreesInfoObj.setPercent(cursor.getString(10));
                    alertsMissingTreesInfoList.add(alertsMissingTreesInfoObj);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return (T) ((type == 0) ? alertsMissingTreesInfoObj : alertsMissingTreesInfoList);
    }


    public List<NurseryIrrigationLog> getirigationlogs(final String query) {
        Log.d(DataAccessHandler.class.getSimpleName(), "====> Analysis ==> GET Irrigation :" + query);
        List<NurseryIrrigationLog> irrigationlogDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    NurseryIrrigationLog nurseryIrrigationLog  = new NurseryIrrigationLog();
                    nurseryIrrigationLog.setIrrigationCode(cursor.getString(cursor.getColumnIndex("IrrigationCode")));
                    nurseryIrrigationLog.setLogDate(cursor.getString(cursor.getColumnIndex("LogDate")));
                    nurseryIrrigationLog.setRegularMale(cursor.getDouble(cursor.getColumnIndex("RegularMale")));
                    nurseryIrrigationLog.setRegularFemale(cursor.getDouble(cursor.getColumnIndex("RegularFemale")));
                    nurseryIrrigationLog.setContractMale(cursor.getDouble(cursor.getColumnIndex("ContractMale")));
                    nurseryIrrigationLog.setContractFemale(cursor.getDouble(cursor.getColumnIndex("ContractFemale")));
                    nurseryIrrigationLog.setStatusTypeId(cursor.getInt(cursor.getColumnIndex("StatusTypeId")));
                    nurseryIrrigationLog.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
                    nurseryIrrigationLog.setDesc(cursor.getString(cursor.getColumnIndex("Desc")));
                    nurseryIrrigationLog.setRegularMaleRate(cursor.getDouble(cursor.getColumnIndex("RegularMaleRate")));
                    nurseryIrrigationLog.setContractFeMaleRate(cursor.getDouble(cursor.getColumnIndex("RegularFeMaleRate")));
                    nurseryIrrigationLog.setContractMaleRate(cursor.getDouble(cursor.getColumnIndex("ContractMaleRate")));
                    nurseryIrrigationLog.setContractFeMaleRate(cursor.getDouble(cursor.getColumnIndex("ContractFeMaleRate")));
//                    nurseryIrrigationLog.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
//                    nurseryIrrigationLog.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
//                    nurseryIrrigationLog.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
//                    nurseryIrrigationLog.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
//                    nurseryIrrigationLog.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));

                    irrigationlogDetails.add(nurseryIrrigationLog);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return irrigationlogDetails;
    }


    public List<NurseryIrrigationLogXref> getirigationlogxref(final String query) {
        Log.d(DataAccessHandler.class.getSimpleName(), "====> Analysis ==> GET Irrigation :" + query);
        List<NurseryIrrigationLogXref> irrigationlogxrefDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    NurseryIrrigationLogXref nurseryIrrigationLogxref  = new NurseryIrrigationLogXref();
                    nurseryIrrigationLogxref.setIrrigationCode(cursor.getString(cursor.getColumnIndex("IrrigationCode")));
                    nurseryIrrigationLogxref.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ConsignmentCode")));
                    nurseryIrrigationLogxref.setDesc(cursor.getString(cursor.getColumnIndex("Desc")));
//                    nurseryIrrigationLog.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
//

                    irrigationlogxrefDetails.add(nurseryIrrigationLogxref);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return irrigationlogxrefDetails;
    }

    public ArrayList<Farmer> getFarmerList(final String query) {
        ArrayList<Farmer> farmerDetails = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "Query for getting farmers " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Farmer farmlanddetails = new Farmer();


                    farmlanddetails.setCode(cursor.getString(cursor.getColumnIndex("Code")));
                    farmlanddetails.setFirstname(cursor.getString(cursor.getColumnIndex("FirstName")));
                    farmlanddetails.setMiddlename(cursor.getString(cursor.getColumnIndex("MiddleName")));
                    farmlanddetails.setLastname(cursor.getString(cursor.getColumnIndex("LastName")));
                    farmerDetails.add(farmlanddetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(LOG_TAG, "getting failed fromLocalDb" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDataBase();


        }
        return farmerDetails;
    }

    public List<SaplingActivity> getSaplingActivityDataa(final String query) {
        List<SaplingActivity> sapactivitydata = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    SaplingActivity saplingsactivityDetails = new SaplingActivity();
//
                    saplingsactivityDetails.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));



                    sapactivitydata.add(saplingsactivityDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return sapactivitydata;
    }
    public List<CheckNurseryAcitivity> getNurseryCheckActivityDetails(final String query) {
        Log.d(DataAccessHandler.class.getSimpleName(), "====> Analysis ==> GET ACTIVITIES :" + query);
        List<CheckNurseryAcitivity> nurseryActivityDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {



                    CheckNurseryAcitivity nurseryActivityyDetails = new CheckNurseryAcitivity();
                    nurseryActivityyDetails.setId(cursor.getInt(cursor.getColumnIndex("ActivityId")));
                    nurseryActivityyDetails.setActivityTypeId(cursor.getInt(cursor.getColumnIndex("ActivityTypeId")));
                    nurseryActivityyDetails.setIsMultipleEntries(cursor.getString(cursor.getColumnIndex("IsMultipleEntries")));
                    nurseryActivityyDetails.setActicityType(cursor.getString(cursor.getColumnIndex("ActicityType")));
                    nurseryActivityyDetails.setCode(cursor.getString(cursor.getColumnIndex("ActivityCode")));
                    nurseryActivityyDetails.setName(cursor.getString(cursor.getColumnIndex("ActivityName")));
                    nurseryActivityyDetails.setStatusTypeId(cursor.getInt(cursor.getColumnIndex("StatusTypeId")));
                    nurseryActivityyDetails.setDesc(cursor.getString(cursor.getColumnIndex("ActivityStatus")));
                    nurseryActivityyDetails.setActivityDoneDate(cursor.getString(cursor.getColumnIndex("ActivityDoneDate")));
                    nurseryActivityyDetails.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ConsignmentCode")));
                    nurseryActivityyDetails.setTargetDate(cursor.getString(cursor.getColumnIndex("TargetDate")));
                    nurseryActivityDetails.add(nurseryActivityyDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return nurseryActivityDetails;
    }

    public List<SaplingActivity> getSaplingActivityDatadetails(final String query) {
        List<SaplingActivity> sapactivitydata = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    SaplingActivity saplingsactivityDetails = new SaplingActivity();
                    saplingsactivityDetails.setTransactionId(cursor.getString(cursor.getColumnIndex("TransactionId")));
                    saplingsactivityDetails.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ConsignmentCode")));
                    saplingsactivityDetails.setStatusTypeId(cursor.getInt(cursor.getColumnIndex("StatusTypeId")));



                    sapactivitydata.add(saplingsactivityDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return sapactivitydata;
    }



    public List<CullinglossFileRepository>getCullinglossRepoDetails(final String query) {
        List<CullinglossFileRepository> Cullinglossrepolist = new ArrayList<>();
        CullinglossFileRepository cullinglossrepository = null;
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ GradingRepo details query " + query);
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    cullinglossrepository = new CullinglossFileRepository();

                    String filelocation = cursor.getString(cursor.getColumnIndex("FileLocation"));
                    if (filelocation != null) {
                        try {
                            cullinglossrepository.setImageString(CommonUtils.encodeFileToBase64Binary(new File(filelocation)));
                        } catch (Exception exc) {

                        }
                    }
                    cullinglossrepository.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    cullinglossrepository.setTransactionId(cursor.getString(cursor.getColumnIndex("TransactionId")));
                    cullinglossrepository.setFileName(cursor.getString(cursor.getColumnIndex("FileName")));
                    cullinglossrepository.setFileLocation(cursor.getString(cursor.getColumnIndex("FileLocation")));
                    cullinglossrepository.setFileExtension(cursor.getString(cursor.getColumnIndex("FileExtension")));
                    cullinglossrepository.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    cullinglossrepository.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    cullinglossrepository.setServerUpdatedStatus(0);
                    Cullinglossrepolist.add(cullinglossrepository);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "@@@ getting GradingRepo details " + e.getMessage());
        }
return Cullinglossrepolist;
    }
    public List<Irrigationhistorymodel> getIrrigationHistoryDetails(final String query, final int type) {
        List<Irrigationhistorymodel> IrrigationHistoryDataDetails = new ArrayList<>();
        Cursor cursor = null;
        Log.v(LOG_TAG, "@@@ Irrigationhistory details query " + query);

        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    Irrigationhistorymodel IrrigationhistoryDetails = new Irrigationhistorymodel();
                    IrrigationhistoryDetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    IrrigationhistoryDetails.setIrrigationCode(cursor.getString(cursor.getColumnIndex("IrrigationCode")));
                    IrrigationhistoryDetails.setStatusTypeId(cursor.getInt(cursor.getColumnIndex("StatusTypeId")));
                    IrrigationhistoryDetails.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
                    IrrigationhistoryDetails.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    IrrigationhistoryDetails.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    IrrigationhistoryDetails.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    IrrigationhistoryDetails.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));

                    IrrigationHistoryDataDetails.add(IrrigationhistoryDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return IrrigationHistoryDataDetails;
    }


    public String getGenerateActivityid(final String maxNum) {
       // String maxNum = getOnlyOneValueFromDb(query);
        String convertedNum = "";
        if (!TextUtils.isEmpty(maxNum)) {
            convertedNum = CommonUtils.serialNumber(Integer.parseInt(maxNum) , 3);
        } else {
            convertedNum = CommonUtils.serialNumber(1, 3);
        }
     //   StringBuilder farmerCoder = new StringBuilder();
        String finalNumber = StringUtils.leftPad(convertedNum,3,"0");

        Log.v(LOG_TAG, "@@@ finalNumber code " + finalNumber);
        return finalNumber;
    }



    public List<NurseryLabourLog> getnurserylabourlogs(final String query) {
        Log.d(DataAccessHandler.class.getSimpleName(), "====> Analysis ==> GET Nursery :" + query);
        List<NurseryLabourLog> Nurserylog = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    NurseryLabourLog nurserylabourLog  = new NurseryLabourLog();

                    nurserylabourLog.setLogDate(cursor.getString(cursor.getColumnIndex("LogDate")));
                    nurserylabourLog.setRegularMale(cursor.getDouble(cursor.getColumnIndex("RegularMale")));
                    nurserylabourLog.setRegularFemale(cursor.getDouble(cursor.getColumnIndex("RegularFemale")));
                    nurserylabourLog.setContractMale(cursor.getDouble(cursor.getColumnIndex("ContractMale")));
                    nurserylabourLog.setContractFemale(cursor.getDouble(cursor.getColumnIndex("ContractFemale")));
                    nurserylabourLog.setIsActive(cursor.getInt(cursor.getColumnIndex("IsActive")));
                    nurserylabourLog.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                    nurserylabourLog.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                    nurserylabourLog.setUpdatedByUserId(cursor.getInt(cursor.getColumnIndex("UpdatedByUserId")));
                    nurserylabourLog.setUpdatedDate(cursor.getString(cursor.getColumnIndex("UpdatedDate")));
                    nurserylabourLog.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));
                    nurserylabourLog.setNurseryCode(cursor.getString(cursor.getColumnIndex("NurseryCode")));

                    Nurserylog.add(nurserylabourLog);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return Nurserylog;
    }
    public List<ConsignmentData> getConsignmentcode(final String query) {
        Log.d(LOG_TAG, "==> analysis GetConsinmentData :" + query);
        List<ConsignmentData> consignmentData = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    ConsignmentData consignmentdetails = new ConsignmentData();
                    consignmentdetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    consignmentdetails.setConsignmentCode(cursor.getString(cursor.getColumnIndex("ConsignmentCode")));



                    consignmentData.add(consignmentdetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return consignmentData;
    }


    public List<NurseryVisitLog> getNurseryVisitLog(final String query) {
        Log.v(LOG_TAG, "@@@ Nurseryvisit details query " + query);
        List<NurseryVisitLog> nurserySaplingDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    NurseryVisitLog nurseryVisitLogDetails = new NurseryVisitLog();
                    String filelocation = cursor.getString(cursor.getColumnIndex("FileLocation"));
                    if (filelocation != null) {
                        try {
                            nurseryVisitLogDetails.setImageString(CommonUtils.encodeFileToBase64Binary(new File(filelocation)));
                        } catch (Exception exc) {

                        }
                    }

                        nurseryVisitLogDetails.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                        nurseryVisitLogDetails.setNurseryCode(cursor.getString(cursor.getColumnIndex("NurseryCode")));
                        nurseryVisitLogDetails.setLogTypeId(cursor.getInt(cursor.getColumnIndex("LogTypeId")));
                        nurseryVisitLogDetails.setCosignmentCode(cursor.getString(cursor.getColumnIndex("CosignmentCode")));
                        nurseryVisitLogDetails.setClientName(cursor.getString(cursor.getColumnIndex("ClientName")));
                        nurseryVisitLogDetails.setLogDate(cursor.getString(cursor.getColumnIndex("LogDate")));

                        nurseryVisitLogDetails.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
                        nurseryVisitLogDetails.setFileName(cursor.getString(cursor.getColumnIndex("FileName")));
                       // nurseryVisitLogDetails.setFileLocation(cursor.getString(cursor.getColumnIndex("FileLocation")));
                        nurseryVisitLogDetails.setFileExtension(cursor.getString(cursor.getColumnIndex("FileExtension")));
                        nurseryVisitLogDetails.setCreatedByUserId(cursor.getInt(cursor.getColumnIndex("CreatedByUserId")));
                        nurseryVisitLogDetails.setCreatedDate(cursor.getString(cursor.getColumnIndex("CreatedDate")));
                        nurseryVisitLogDetails.setServerUpdatedStatus(cursor.getInt(cursor.getColumnIndex("ServerUpdatedStatus")));

                    nurserySaplingDetails.add(nurseryVisitLogDetails);
                    } while (cursor.moveToNext());

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                if (cursor != null) {
                    cursor.close();
                }
            }
            return nurserySaplingDetails;
        }


    public List<ViewVisitLog> getviewNurseryVisitLog(final String query) {
        Log.v(LOG_TAG, "@@@ Nurseryvisit details query " + query);
        List<ViewVisitLog> nurserySaplingDetails = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    ViewVisitLog nurseryVisitLogDetails = new ViewVisitLog();
                    nurseryVisitLogDetails.setNurseryname(cursor.getString(cursor.getColumnIndex("name")));
                    nurseryVisitLogDetails.setNurseryCode(cursor.getString(cursor.getColumnIndex("NurseryCode")));
                    nurseryVisitLogDetails.setLogTypeId(cursor.getInt(cursor.getColumnIndex("LogTypeId")));
                    nurseryVisitLogDetails.setCosignmentCode(cursor.getString(cursor.getColumnIndex("CosignmentCode")));
                    nurseryVisitLogDetails.setClientName(cursor.getString(cursor.getColumnIndex("ClientName")));
                    nurseryVisitLogDetails.setLogDate(cursor.getString(cursor.getColumnIndex("LogDate")));
                    nurseryVisitLogDetails.setComments(cursor.getString(cursor.getColumnIndex("Comments")));
                    nurseryVisitLogDetails.setFileLocation(cursor.getString(cursor.getColumnIndex("FileLocation")));
                    nurseryVisitLogDetails.setLogtype(cursor.getString(cursor.getColumnIndex("Desc")));

                    nurserySaplingDetails.add(nurseryVisitLogDetails);
                } while (cursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return nurserySaplingDetails;
    }
    }




