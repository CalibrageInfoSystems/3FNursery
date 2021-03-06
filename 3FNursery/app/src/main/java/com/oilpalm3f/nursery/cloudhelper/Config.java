package com.oilpalm3f.nursery.cloudhelper;

import com.oilpalm3f.nursery.BuildConfig;

public class Config {

    public static final boolean DEVELOPER_MODE = false;

 //  public static String live_url = "http://183.82.111.111/3FOilPlam_Nursery_UAT/API/API"; //UAT test
public static String live_url = "http://182.18.157.215/3FSmartPalm_Nursery/API/api"; //localtest
//public static String live_url = "http://183.82.111.111/3FOilpalm_Nursery_TestLive/API/api"; //Nursery_TestLive

    public static void initialize() {
        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("release")) {
         // live_url = "http://183.82.111.111/3FOilpalm_Nursery_TestLive/API/api"; //Nursery_TestLive
   live_url = "http://182.18.157.215/3FSmartPalm_Nursery/API/api";//localtest
//live_url = "http://183.82.111.111/3FOilPlam_Nursery_UAT/API/API"; //localtest

        } else {
            //live_url = "http://183.82.111.111/3FOilpalm_Nursery_TestLive/API/api"; //Nursery_TestLive
live_url = "http://182.18.157.215/3FSmartPalm_Nursery/API/api";//localtest
//live_url = "http://183.82.111.111/3FOilPlam_Nursery_UAT/API/API"; //localtest

        }
    }

    public static final String masterSyncUrl = "/SyncMasters/SyncNurseryMasters";

    public static final String transactionSyncURL = "/SyncTransactions/SyncNurseryTransactions";
    public static final String locationTrackingURL = "/LocationTracker/SaveOfflineLocations";
    public static final String imageUploadURL = "/SyncTransactions/UploadImage";

    public static final String findcollectioncode = "/SyncTransactions/FindCollectionCode/%s";
    public static final String findconsignmentcode = "/SyncTransactions/FindConsignmentCode/%s";
    public static final String findcollectionplotcode = "/SyncTransactions/FindCollectionPlotXref/%s/%s";

    public static final String updatedbFile = "/TabDatabase/UploadDatabase";

    public static final String getTransCount = "/SyncTransactions/GetNurseryCount";//{Date}/{UserId}
    public static final String getTransData = "/SyncTransactions/%s";//api/TranSync/SyncFarmers/{Date}/{UserId}/{Index}
    public static final String validateTranSync = "/TranSync/ValidateTranSync/%s";
    public static final String image_url = "http://183.82.111.111/3FOilpalm_Nursery_TestLive/3FOilPalmNurseryRepository/";

    // Commented on 12-04-2021 to change the URL to local
 //public static final String image_url = "http://183.82.111.111/3FOilPalm_Nursery/3FNurseryRepository/FileRepository/";
 // public static final String image_url = "http://183.82.111.111/3FOilPlam_Nursery_UAT/NurseryRepository/FileRepository/";

   // http://183.82.111.111/3FOilPalm_Nursery/3FNurseryRepository/FileRepository//2022\03\04\RMTransaction/20220304035712813.jpg
    public static final String GETMONTHLYTARGETSBYUSERIDANDFINANCIALYEAR = "/KRA/GetMonthlyTargetsByUserIdandFinancialYear";
    public static final String GETTARGETSBYUSERIDANDFINANCIALYEAR = "/KRA/GetTargetsByUserIdandFinancialYear";
    public static final String GET_ALERTS = "/SyncTransactions/SyncNurseryAlertDetails";//{UserId}
}
