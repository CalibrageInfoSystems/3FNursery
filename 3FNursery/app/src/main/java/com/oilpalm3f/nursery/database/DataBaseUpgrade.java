package com.oilpalm3f.nursery.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.oilpalm3f.nursery.common.CommonConstants;

import static android.content.Context.MODE_PRIVATE;

public class DataBaseUpgrade {

    private static final String LOG_TAG = DataBaseUpgrade.class.getName();

    static void upgradeDataBase(final Context context, final SQLiteDatabase db) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appprefs", MODE_PRIVATE);
        boolean result = true;
        try {
            boolean isFreshInstall = sharedPreferences.getBoolean(CommonConstants.IS_FRESH_INSTALL, true);
            if (isFreshInstall) {
                upgradeDb1(db);
                upgradeDB2(db);
                upgradeDB3(db);

            } else {
                boolean isDbUpgradeFinished = sharedPreferences.getBoolean(String.valueOf(Palm3FoilDatabase.DATA_VERSION), false);
                Log.v(LOG_TAG, "@@@@ database....." + isDbUpgradeFinished);
                if (!isDbUpgradeFinished) {
                    switch (Palm3FoilDatabase.DATA_VERSION) {
                        case 1:
//                            UiUtils.showCustomToastMessage("Updating database 6-->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            upgradeDb1(db);
                            break;
                        case 2:
//                            UiUtils.showCustomToastMessage("Updating database 6-->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            upgradeDB2(db);
                            break;
                        case 3:
//                            UiUtils.showCustomToastMessage("Updating database 6-->" + Palm3FoilDatabase.DATA_VERSION, context, 0);
                            upgradeDB3(db);
                            break;

                    }
                } else {
                    Log.v(LOG_TAG, "@@@@ database is already upgraded " + Palm3FoilDatabase.DATA_VERSION);
                }
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, String.valueOf(e));
            result = false;
        } finally {
            if (result) {
                Log.v(LOG_TAG, "@@@@ database is upgraded " + Palm3FoilDatabase.DATA_VERSION);
            } else {
                Log.e(LOG_TAG, "@@@@ database is upgrade failed or already upgraded");
            }
            sharedPreferences.edit().putBoolean(CommonConstants.IS_FRESH_INSTALL, false).apply();
            sharedPreferences.edit().putBoolean(String.valueOf(Palm3FoilDatabase.DATA_VERSION), true).apply();
        }
    }


    public static void upgradeDb1(final SQLiteDatabase db) {
        Log.d(LOG_TAG, "******* upgradeDataBase " + Palm3FoilDatabase.DATA_VERSION);

       // String alterGeoBoundariesTable1 = "ALTER TABLE GeoBoundaries ADD COLUMN CropMaintenanceCode VARCHAR (60)";

        String  IrrigationLog1 =  "Alter Table NurseryIrrigationLog Add RegularMaleRate FLOAT";
        String  IrrigationLog2 =  "Alter Table NurseryIrrigationLog Add RegularFeMaleRate FLOAT";
        String  IrrigationLog3 =  "Alter Table NurseryIrrigationLog Add ContractMaleRate FLOAT";
        String  IrrigationLog4 =  "Alter Table NurseryIrrigationLog Add ContractFeMaleRate FLOAT";


        String column1 = "Alter Table Sapling Add StatusTypeId int";
        String column2 = "Alter Table Sapling Add ArrivedDate datetime";
        String column3 = "Alter Table Sapling Add ArrivedQuantity int";
        String column4 = "Alter Table Sapling Add SowingDate datetime";
        String column5 = "Alter Table Sapling Add TransplantingDate datetime";
       String column6 = "Alter Table NurseryActivity Add DependentActivityCode VARCHAR(10)";




        String CREATE_LABOUR_RATE = "CREATE TABLE LabourRate(\n" +
                "Id INTEGER   PRIMARY KEY AUTOINCREMENT\n" +
                "                                      NOT NULL,\n" +
                "NurseryCode INT NOT NULL ,\n" +
                "Key VARCHAR NOT NULL ,\n" +
                "Value FLOAT NOT NULL ,\n" +
                "CreatedByUserId int NOT NULL ,\n" +
                "CreatedDate INT datetime NOT NULL\n" +
                ")";




        try {
            db.execSQL(column6);
            db.execSQL(IrrigationLog1);
            db.execSQL(IrrigationLog2);
            db.execSQL(IrrigationLog3);
            db.execSQL(IrrigationLog4);
            db.execSQL(column1);
            db.execSQL(column2);
            db.execSQL(column3);
            db.execSQL(column4);
            db.execSQL(column5);



            db.execSQL(CREATE_LABOUR_RATE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static  void  upgradeDB2(final  SQLiteDatabase db){
        Log.d(LOG_TAG, "******* upgradeDataBase " + Palm3FoilDatabase.DATA_VERSION);

        // String alterGeoBoundariesTable1 = "ALTER TABLE GeoBoundaries ADD COLUMN CropMaintenanceCode VARCHAR (60)";

        String  column1 =  "Alter table Saplingactivitystatus add JobCompletedDate DATETIME";
        try {

            db.execSQL(column1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static  void  upgradeDB3(final  SQLiteDatabase db){
        Log.d(LOG_TAG, "******* upgradeDataBase " + Palm3FoilDatabase.DATA_VERSION);

        String column2= "Alter table Nursery add  CostCenter varchar(50)";
        String  column1 =  "Alter table NurseryActivity add Bucket VARCHAR(50)";

        String alertsTable = "CREATE TABLE Alerts( \n" +
                "Id INTEGER, \n" +
                "Name VARCHAR, \n" +
                "[Desc] VARCHAR, \n" +
                "UserId INT NOT NULL, \n" +
                "HTMLDesc Varchar(2000), \n" +
                "IsRead INT NOT NULL, \n" +
                "PlotCode VARCHAR, \n" +
                "ComplaintCode VARCHAR, \n" +
                "AlertTypeId INT, \n" +
                "CreatedByUserId INT,\n" +
                "CreatedDate VARCHAR,\n" +
                "UpdatedByUserId INT, \n" +
                "UpdatedDate VARCHAR,\n" +
                "ServerUpdatedStatus INT\n" +
                ")";
        try {

            db.execSQL(column1);
            db.execSQL(column2);
            db.execSQL(alertsTable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void checkTheColumnIsThere(String tableName, String columnName, String dataType, final SQLiteDatabase db) {

        boolean isThere = false;
        String query = "PRAGMA table_info(" + tableName + ");";
        try (Cursor cursor = db.rawQuery(query, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));

                    if (name.equals(columnName)) {
                        isThere = true;
                    }

                } while (cursor.moveToNext());


                if (!isThere) {
                    db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + dataType);
                    Log.v(LOG_TAG, "@@@ added the column " + columnName);
                }
            }


        } catch (Exception e) {
            Log.v(LOG_TAG, "@@@ checking the column " + e.getMessage());
        }
    }

}
