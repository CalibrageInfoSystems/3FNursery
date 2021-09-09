package com.oilpalm3f.nursery.ui;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.cloudhelper.ApplicationThread;
import com.oilpalm3f.nursery.cloudhelper.Log;
import com.oilpalm3f.nursery.common.CommonConstants;
import com.oilpalm3f.nursery.common.CommonUtils;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.ActivityTasks;
import com.oilpalm3f.nursery.dbmodels.DisplayData;
import com.oilpalm3f.nursery.dbmodels.ExistingData;
import com.oilpalm3f.nursery.dbmodels.SaplingActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ActivityTask extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    String activityTypeId, consignmentCode, activityName, isMultipleentry, transactionIdFromMultiple;

    private List<ActivityTasks> activityTasklist = new ArrayList<>();
    private DataAccessHandler dataAccessHandler;
    LinkedHashMap<String, Pair> typeofLabourdatamap = null;
    Boolean isSingleentry = false, addactivity = false;

    private List<SaplingActivity> saplingActivitiesList = new ArrayList<>();
    int SaplingActivityCount;
    List<KeyValues> dataValue = new ArrayList<>();
    int random_int = 0;
    int maxnumber;
    TextView textView5;
    String TransactionID;
    int sapactivitysize, sapactivitysizeinc;
    private List<ExistingData> existingData = new ArrayList<>();
    private List<DisplayData> displayData = new ArrayList<>();
    boolean isUpdate = false;
    int activityStatus;
    int isjobDoneId = 0;
    int SCREEN_FROM = 0;

    ActivityTasks showHideActivity;
    CheckBox chkShowHide;
    int yesnoCHeckbox = -10;
    int ButtonId = 100000001;
    String errorMsg = "";
    String Code, dependency_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                activityTypeId = extras.getString("ActivityTypeId");
                activityName = extras.getString("ActivityName");

                SCREEN_FROM = extras.getInt(CommonConstants.SCREEN_CAME_FROM);
                consignmentCode = extras.getString("consignmentcode");
//                dependency_code = extras.getString("DependentActivityCode");
                Code = extras.getString("Code");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // SETUP title For Activity
        textView5 = findViewById(R.id.textView5);
        textView5.setText(activityName);

        dataAccessHandler = new DataAccessHandler(this);
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout2);

        maxnumber = dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getSaplingActivityMaxNumber());
        Log.d("maxnumber", maxnumber + "");

        activityTasklist = dataAccessHandler.getActivityTasksDetails(Queries.getInstance().getActivityTaskDetails(Integer.parseInt(activityTypeId)));
        CheckMantoryItem();
        createDynamicUI(ll);


        if (SCREEN_FROM == CommonConstants.FROM_MUTIPLE_ENTRY_EDITDATA) {
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_MUTIPLE_ENTRY_EDITDATA");
            // SCREEN CAME FROM UPDATE CURRENT SCREEN
            String consignmentcode = extras.getString("consignmentcode");
            String intentTransactionId = extras.getString("transactionId");
            boolean enableEditing = extras.getBoolean("enableEditing");
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> FROM_MUTIPLE_ENTRY_EDITDATA  ###### transaction Id :" + intentTransactionId);
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> FROM_MUTIPLE_ENTRY_EDITDATA  ###### enableEditing :" + enableEditing);
            bindExistingData(intentTransactionId);

//            Button btn = (Button) findViewById(ButtonId);
//            if (enableEditing)
//                btn.setVisibility(View.VISIBLE);
//            else
//                btn.setVisibility(View.GONE);
            // TODO Bind DATA UsingTransactionID

        } else if (SCREEN_FROM == CommonConstants.FROM_MULTIPLE_ADD_NEW_TASK) {
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_MULTIPLE_ADD_NEW_TASK");
            String activityTypeId = extras.getString("ActivityTypeId");
            String consignmentcode = extras.getString("consignmentcode");
            boolean Ismultipleentry = extras.getBoolean("Ismultipleentry");
            // TODO Just Add New Task

        } else if (SCREEN_FROM == CommonConstants.FROM_SINGLE_ENTRY_EDITDATA) {
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_SINGLE_ENTRY_EDITDATA");
            String consignmentcode = extras.getString("consignmentcode");
            String activityTypeId = extras.getString("ActivityTypeId");

            boolean enableEditing = extras.getBoolean("enableEditing");

            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> FROM_MUTIPLE_ENTRY_EDITDATA  ###### enableEditing :" + enableEditing);


//            Button btn = (Button) findViewById(ButtonId);
//            if (enableEditing)
//                btn.setVisibility(View.VISIBLE);
//            else
//                btn.setVisibility(View.GONE);

            // TODO CHECK DATA EXIST OR NOT      IF EXIST BIND DATA

            String transactionId = dataAccessHandler.getSingleValue(Queries.getInstance().getTransactionIdUsingConsimentCode(consignmentcode, activityTypeId));
            if (null != transactionId && !transactionId.isEmpty() && !TextUtils.isEmpty(transactionId)) {
                bindExistingData(transactionId);
            } else {
                Log.d(ActivityTask.class.getSimpleName(), "==> Analysis  ==> New Task Creation Started ");
                String transactionIdNew = "T" + CommonConstants.TAB_ID + consignmentcode + activityTypeId + "-" + (dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getSaplingActivityMaxNumber()) + 1);
                Log.d(ActivityTask.class.getSimpleName(), "==> Analysis   New Transaction ID :" + transactionIdNew);


            }

        }
        if(Integer.parseInt(activityTypeId) == 1 || Integer.parseInt(activityTypeId) == 2 || Integer.parseInt(activityTypeId) == 4){
            Button btn = (Button) findViewById(ButtonId);
            btn.setVisibility(View.GONE);
        }

    }

    private void bindExistingData(String transactionId) {
        displayData = dataAccessHandler.getdisplayDetails(Queries.getInstance().getDisplayData(transactionId));
        Log.d(ActivityTask.class.getSimpleName(), "==> Analysis Count Of DisplayData :" + displayData.size());

        for (int i = 0; i < displayData.size(); i++) {
            if (displayData.get(i).getInputType().equalsIgnoreCase("Check box")) {
                CheckBox chk = (CheckBox) findViewById(displayData.get(i).getFieldId());
                if ( displayData.get(i).getValue().equalsIgnoreCase("true")) {
                    chk.setChecked(true);
                } else
                    chk.setChecked(false);
            } else if (displayData.get(i).getInputType().equalsIgnoreCase("TextBox")) {
                EditText editText = (EditText) findViewById(displayData.get(i).getFieldId());
                if (!TextUtils.isEmpty(displayData.get(i).getValue())) {
                    editText.setText(displayData.get(i).getValue());
                } else
                    editText.setText("");
            } else if (activityTasklist.get(i).getInputType().equalsIgnoreCase("TextBox") || activityTasklist.get(i).getInputType().equalsIgnoreCase("Label") || activityTasklist.get(i).getInputType().equalsIgnoreCase("Display") || activityTasklist.get(i).getInputType().equalsIgnoreCase("Formula")) {
                TextView textView = (TextView) findViewById(displayData.get(i).getFieldId());
                if (!TextUtils.isEmpty(displayData.get(i).getValue())) {
                    textView.setText(displayData.get(i).getValue());
                } else
                    textView.setText("");
            } else if (displayData.get(i).getInputType().equalsIgnoreCase("Dropdown") || displayData.get(i).getInputType().equalsIgnoreCase("dropdown")) {
                String value = displayData.get(i).getValue();
                int position = 0;
                String[] data = CommonUtils.arrayFromPair(typeofLabourdatamap, "Type of Labour");
                for (int j = 0; j < data.length; j++) {
                    if (value.equalsIgnoreCase(data[j])) {
                        position = j;
                    }
                }
                Spinner sp = (Spinner) findViewById(displayData.get(i).getFieldId());
                sp.setSelection(position);
            }
        }
    }

    private boolean goValidate() {
        Log.d("##############################", "YESNO CHECK VALUE :" + yesnoCHeckbox);
//        if (yesnoCHeckbox > 0 || Integer.parseInt(activityTypeId) == 7 || Integer.parseInt(activityTypeId) == 9 ||  Integer.parseInt(activityTypeId) == 10 || Integer.parseInt(activityTypeId) == 12 )
//            return GroupValidate();
//        else
//            return validate();

        return GroupValidate();
    }

    private void addNewSingleEntryActivity(String _consignmentCode, String _activityId, int _statusTypeId, String _transactionId, boolean isFromMultipleEntry) {

        String male_reg = dataAccessHandler.getSingleValue(Queries.getregmalerate(CommonConstants.NurseryCode));
        String femmale_reg = dataAccessHandler.getSingleValue(Queries.getregfemalerate(CommonConstants.NurseryCode));
        String male_contract = dataAccessHandler.getSingleValue(Queries.getcontractmalerate(CommonConstants.NurseryCode));
        String female_contract = dataAccessHandler.getSingleValue(Queries.getcontractfemalerate(CommonConstants.NurseryCode));
        String smallPolyBag = dataAccessHandler.getSingleValue(Queries.getsmallPolyBag(CommonConstants.NurseryCode));
        String bigPolyBag = dataAccessHandler.getSingleValue(Queries.getBigBag(CommonConstants.NurseryCode));

        // DATA Validated next saving data locally
        final List<LinkedHashMap> listKey = new ArrayList<>();
        for (int j = 0; j < dataValue.size(); j++) {

            LinkedHashMap mapXref = new LinkedHashMap();
            mapXref.put("Id", 0);
            mapXref.put("TransactionId", _transactionId);
            mapXref.put("FieldId", dataValue.get(j).id);
            mapXref.put("Value", dataValue.get(j).value);
            mapXref.put("FilePath", "");
            mapXref.put("IsActive", 1);
            mapXref.put("CreatedByUserId", CommonConstants.USER_ID);
            mapXref.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            mapXref.put("UpdatedByUserId", CommonConstants.USER_ID);
            mapXref.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            mapXref.put("ServerUpdatedStatus", 0);
            int id = dataValue.get(j).id;
            if (id == 8 || id == 15 || id == 46 || id == 55 || id == 63 || id == 68 || id == 74 || id == 81 || id == 85 || id == 90 || id == 97 || id == 103 || id == 114 || id == 123 || id == 132 || id == 141 || id == 151 || id == 166 || id == 175 || id == 183 || id == 192 || id == 225 || id == 229 || id == 237 || id == 243 || id == 247 || id == 255 || id == 261 || id == 265 || id == 271 || id == 282 || id == 291 || id == 300 || id == 309 || id == 319 || id == 334 || id == 343 || id == 352 || id == 360 || id == 370 || id == 375 || id == 379 || id == 387 || id == 393 || id == 399 || id == 405 || id == 416 || id == 425 || id == 434 || id == 443 || id == 453 || id == 468 || id == 477 || id == 486 || id == 494 || id == 538 || id == 553 || id == 568 || id == 583 || id == 598 || id == 613 || id == 628 || id == 643 || id == 658 || id == 673 || id == 688 || id == 703 || id == 718 || id == 733 || id == 748 || id == 763 || id == 778 || id == 793 || id == 801 || id == 809 || id == 817 || id == 825 || id == 833 || id == 841 || id == 849 || id == 857 || id == 865 || id == 873 || id == 881 || id == 889 || id == 897 || id == 905 || id == 913 || id == 921 || id == 931 || id == 941 || id == 951 || id == 961 || id == 971 || id == 981 || id == 991 || id == 1001 || id == 1011 || id == 1021 || id == 1031 || id == 1041 || id == 1051 || id == 1061 || id == 1071 || id == 1081 || id == 1090 || id == 1099 || id == 1108 || id == 1117 || id == 1126 || id == 1135 || id == 1144 || id == 1153 || id == 1162 || id == 1171 || id == 1180 || id == 1189 || id == 1198 || id == 1207 || id == 1216 || id == 1225 || id == 1232 || id == 1238 || id == 1244 || id == 1251 || id == 1257 || id == 1263 || id == 1270 || id == 1276 || id == 1282 || id == 1289 || id == 1295 || id == 1301 || id == 1307 || id == 1313 || id == 1319 || id == 1325 || id == 1331 || id == 1337 || id == 1343 || id == 1349 || id == 1355 || id == 1361 || id == 1367 || id == 1373 || id == 1384 || id == 1395 || id == 1404 || id == 1413 || id == 1422 || id == 1431 || id == 1440 || id == 1449 || id == 1459 || id == 1472 || id == 1478 || id == 1484 || id == 1490 || id == 1496 || id == 1502 || id == 1508 || id == 1514 || id == 1520 || id == 1526 || id == 1532 || id == 1538 || id == 1544 || id == 1550 || id == 1556 || id == 1562 || id == 1568 || id == 1574 || id == 1580 || id == 1586 || id == 1592 || id == 1598 || id == 1604 || id == 1619 || id == 1634 || id == 1649 || id == 1664 || id == 1679 || id == 1694 || id == 1709 || id == 1724 || id == 1739 || id == 1754 || id == 1762 || id == 1770 || id == 1778 || id == 1786 || id == 1794 || id == 1802 || id == 1810 || id == 1818 || id == 1826 || id == 1834 || id == 1843 || id == 1852 || id == 1861 || id == 1870 || id == 1879 || id == 1888 || id == 1897 || id == 1906 || id == 1915 || id == 1924 || id == 1934 || id == 1944 || id == 1954 || id == 1964 || id == 1974 || id == 1984 || id == 1994 || id == 2004 || id == 2014 || id == 2024 || id == 2033 || id == 2042 || id == 2051 || id == 2060 || id == 2069 || id == 2078 || id == 2087 || id == 2096 || id == 2105 || id == 2114 || id == 2125 || id == 2136 || id == 2147 || id == 2158 || id == 2169 || id == 2180 || id == 2191 || id == 2202 || id == 2213 || id == 2224 || id == 2233 || id == 2242 || id == 2251 || id == 2260 || id == 2269 || id == 2278 || id == 2287 || id == 2296 || id == 2305 || id == 2314 || id == 2323 || id == 2332 || id == 2341 || id == 2350 || id == 2359 || id == 2368 || id == 2377 || id == 2386 || id == 2395 || id == 2404 || id == 2413 || id == 2422 || id == 2431 || id == 2440 || id == 2449 || id == 2458 || id == 2467 || id == 2476 || id == 2485 || id == 2494 || id == 2504 || id == 2514 || id == 2524 || id == 2534 || id == 2544 || id == 2554 || id == 2564 || id == 2574 || id == 2584 || id == 2594 || id == 2600 || id == 2606 || id == 2612 || id == 2623 || id == 2634 || id == 2645 || id == 2656 || id == 2665 || id == 2674 || id == 2683 || id == 2692 || id == 2701 || id == 2710 || id == 2719 || id == 2728 || id == 2737 || id == 2746 || id == 2755 || id == 2764 || id == 2774 || id == 2784 || id == 2794 || id == 2804 || id == 2819 || id == 2834 || id == 2849 || id == 2864 || id == 2872 || id == 2880 || id == 2888 || id == 2896 || id == 2905 || id == 2914 || id == 2923 || id == 2932 || id == 2942 || id == 2952 || id == 2962 || id == 2972 || id == 2981 || id == 2990 || id == 2999 || id == 3008 || id == 3014 || id == 3020 || id == 3026 || id == 3032 || id == 3038 || id == 3044 || id == 3050 || id == 3056 || id == 3064 || id == 3079) {

                if ((male_reg != null && !male_reg.isEmpty() && !male_reg.equals("null")))
                    mapXref.put("LabourRate", male_reg);
            }
            if (id == 9 || id == 16 || id == 47 || id == 56 || id == 64 || id == 69 || id == 75 || id == 82 || id == 86 || id == 91 || id == 98 || id == 104 || id == 115 || id == 124 || id == 133 || id == 142 || id == 152 || id == 167 || id == 176 || id == 184 || id == 193 || id == 226 || id == 230 || id == 238 || id == 244 || id == 248 || id == 256 || id == 262 || id == 266 || id == 272 || id == 283 || id == 292 || id == 301 || id == 310 || id == 320 || id == 335 || id == 344 || id == 353 || id == 361 || id == 371 || id == 376 || id == 380 || id == 388 || id == 394 || id == 400 || id == 406 || id == 417 || id == 426 || id == 435 || id == 444 || id == 454 || id == 469 || id == 478 || id == 487 || id == 495 || id == 539 || id == 554 || id == 569 || id == 584 || id == 599 || id == 614 || id == 629 || id == 644 || id == 659 || id == 674 || id == 689 || id == 704 || id == 719 || id == 734 || id == 749 || id == 764 || id == 779 || id == 794 || id == 802 || id == 810 || id == 818 || id == 826 || id == 834 || id == 842 || id == 850 || id == 858 || id == 866 || id == 874 || id == 882 || id == 890 || id == 898 || id == 906 || id == 914 || id == 922 || id == 932 || id == 942 || id == 952 || id == 962 || id == 972 || id == 982 || id == 992 || id == 1002 || id == 1012 || id == 1022 || id == 1032 || id == 1042 || id == 1052 || id == 1062 || id == 1072 || id == 1082 || id == 1091 || id == 1100 || id == 1109 || id == 1118 || id == 1127 || id == 1136 || id == 1145 || id == 1154 || id == 1163 || id == 1172 || id == 1181 || id == 1190 || id == 1199 || id == 1208 || id == 1217 || id == 1226 || id == 1233 || id == 1239 || id == 1245 || id == 1252 || id == 1258 || id == 1264 || id == 1271 || id == 1277 || id == 1283 || id == 1290 || id == 1296 || id == 1302 || id == 1308 || id == 1314 || id == 1320 || id == 1326 || id == 1332 || id == 1338 || id == 1344 || id == 1350 || id == 1356 || id == 1362 || id == 1368 || id == 1374 || id == 1385 || id == 1396 || id == 1405 || id == 1414 || id == 1423 || id == 1432 || id == 1441 || id == 1450 || id == 1460 || id == 1473 || id == 1479 || id == 1485 || id == 1491 || id == 1497 || id == 1503 || id == 1509 || id == 1515 || id == 1521 || id == 1527 || id == 1533 || id == 1539 || id == 1545 || id == 1551 || id == 1557 || id == 1563 || id == 1569 || id == 1575 || id == 1581 || id == 1587 || id == 1593 || id == 1599 || id == 1605 || id == 1620 || id == 1635 || id == 1650 || id == 1665 || id == 1680 || id == 1695 || id == 1710 || id == 1725 || id == 1740 || id == 1755 || id == 1763 || id == 1771 || id == 1779 || id == 1787 || id == 1795 || id == 1803 || id == 1811 || id == 1819 || id == 1827 || id == 1835 || id == 1844 || id == 1853 || id == 1862 || id == 1871 || id == 1880 || id == 1889 || id == 1898 || id == 1907 || id == 1916 || id == 1925 || id == 1935 || id == 1945 || id == 1955 || id == 1965 || id == 1975 || id == 1985 || id == 1995 || id == 2005 || id == 2015 || id == 2025 || id == 2034 || id == 2043 || id == 2052 || id == 2061 || id == 2070 || id == 2079 || id == 2088 || id == 2097 || id == 2106 || id == 2115 || id == 2126 || id == 2137 || id == 2148 || id == 2159 || id == 2170 || id == 2181 || id == 2192 || id == 2203 || id == 2214 || id == 2225 || id == 2234 || id == 2243 || id == 2252 || id == 2261 || id == 2270 || id == 2279 || id == 2288 || id == 2297 || id == 2306 || id == 2315 || id == 2324 || id == 2333 || id == 2342 || id == 2351 || id == 2360 || id == 2369 || id == 2378 || id == 2387 || id == 2396 || id == 2405 || id == 2414 || id == 2423 || id == 2432 || id == 2441 || id == 2450 || id == 2459 || id == 2468 || id == 2477 || id == 2486 || id == 2495 || id == 2505 || id == 2515 || id == 2525 || id == 2535 || id == 2545 || id == 2555 || id == 2565 || id == 2575 || id == 2585 || id == 2595 || id == 2601 || id == 2607 || id == 2613 || id == 2624 || id == 2635 || id == 2646 || id == 2657 || id == 2666 || id == 2675 || id == 2684 || id == 2693 || id == 2702 || id == 2711 || id == 2720 || id == 2729 || id == 2738 || id == 2747 || id == 2756 || id == 2765 || id == 2775 || id == 2785 || id == 2795 || id == 2805 || id == 2820 || id == 2835 || id == 2850 || id == 2865 || id == 2873 || id == 2881 || id == 2889 || id == 2897 || id == 2906 || id == 2915 || id == 2924 || id == 2933 || id == 2943 || id == 2953 || id == 2963 || id == 2973 || id == 2982 || id == 2991 || id == 3000 || id == 3009 || id == 3015 || id == 3021 || id == 3027 || id == 3033 || id == 3039 || id == 3045 || id == 3051 || id == 3057 || id == 3065 || id == 3080) {
                if ((femmale_reg != null && !femmale_reg.isEmpty() && !femmale_reg.equals("null")))
                    mapXref.put("LabourRate", femmale_reg);
            }
            if (id == 10 || id == 17 || id == 48 || id == 57 || id == 65 || id == 70 || id == 76 || id == 83 || id == 87 || id == 92 || id == 99 || id == 105 || id == 116 || id == 125 || id == 134 || id == 143 || id == 153 || id == 168 || id == 177 || id == 185 || id == 194 || id == 227 || id == 231 || id == 239 || id == 245 || id == 249 || id == 257 || id == 263 || id == 267 || id == 273 || id == 284 || id == 293 || id == 302 || id == 311 || id == 321 || id == 336 || id == 345 || id == 354 || id == 362 || id == 372 || id == 377 || id == 381 || id == 389 || id == 395 || id == 401 || id == 407 || id == 418 || id == 427 || id == 436 || id == 445 || id == 455 || id == 470 || id == 479 || id == 488 || id == 496 || id == 540 || id == 555 || id == 570 || id == 585 || id == 600 || id == 615 || id == 630 || id == 645 || id == 660 || id == 675 || id == 690 || id == 705 || id == 720 || id == 735 || id == 750 || id == 765 || id == 780 || id == 795 || id == 803 || id == 811 || id == 819 || id == 827 || id == 835 || id == 843 || id == 851 || id == 859 || id == 867 || id == 875 || id == 883 || id == 891 || id == 899 || id == 907 || id == 915 || id == 923 || id == 933 || id == 943 || id == 953 || id == 963 || id == 973 || id == 983 || id == 993 || id == 1003 || id == 1013 || id == 1023 || id == 1033 || id == 1043 || id == 1053 || id == 1063 || id == 1073 || id == 1083 || id == 1092 || id == 1101 || id == 1110 || id == 1119 || id == 1128 || id == 1137 || id == 1146 || id == 1155 || id == 1164 || id == 1173 || id == 1182 || id == 1191 || id == 1200 || id == 1209 || id == 1218 || id == 1227 || id == 1234 || id == 1240 || id == 1246 || id == 1253 || id == 1259 || id == 1265 || id == 1272 || id == 1278 || id == 1284 || id == 1291 || id == 1297 || id == 1303 || id == 1309 || id == 1315 || id == 1321 || id == 1327 || id == 1333 || id == 1339 || id == 1345 || id == 1351 || id == 1357 || id == 1363 || id == 1369 || id == 1375 || id == 1386 || id == 1397 || id == 1406 || id == 1415 || id == 1424 || id == 1433 || id == 1442 || id == 1451 || id == 1461 || id == 1474 || id == 1480 || id == 1486 || id == 1492 || id == 1498 || id == 1504 || id == 1510 || id == 1516 || id == 1522 || id == 1528 || id == 1534 || id == 1540 || id == 1546 || id == 1552 || id == 1558 || id == 1564 || id == 1570 || id == 1576 || id == 1582 || id == 1588 || id == 1594 || id == 1600 || id == 1606 || id == 1621 || id == 1636 || id == 1651 || id == 1666 || id == 1681 || id == 1696 || id == 1711 || id == 1726 || id == 1741 || id == 1756 || id == 1764 || id == 1772 || id == 1780 || id == 1788 || id == 1796 || id == 1804 || id == 1812 || id == 1820 || id == 1828 || id == 1836 || id == 1845 || id == 1854 || id == 1863 || id == 1872 || id == 1881 || id == 1890 || id == 1899 || id == 1908 || id == 1917 || id == 1926 || id == 1936 || id == 1946 || id == 1956 || id == 1966 || id == 1976 || id == 1986 || id == 1996 || id == 2006 || id == 2016 || id == 2026 || id == 2035 || id == 2044 || id == 2053 || id == 2062 || id == 2071 || id == 2080 || id == 2089 || id == 2098 || id == 2107 || id == 2116 || id == 2127 || id == 2138 || id == 2149 || id == 2160 || id == 2171 || id == 2182 || id == 2193 || id == 2204 || id == 2215 || id == 2226 || id == 2235 || id == 2244 || id == 2253 || id == 2262 || id == 2271 || id == 2280 || id == 2289 || id == 2298 || id == 2307 || id == 2316 || id == 2325 || id == 2334 || id == 2343 || id == 2352 || id == 2361 || id == 2370 || id == 2379 || id == 2388 || id == 2397 || id == 2406 || id == 2415 || id == 2424 || id == 2433 || id == 2442 || id == 2451 || id == 2460 || id == 2469 || id == 2478 || id == 2487 || id == 2496 || id == 2506 || id == 2516 || id == 2526 || id == 2536 || id == 2546 || id == 2556 || id == 2566 || id == 2576 || id == 2586 || id == 2596 || id == 2602 || id == 2608 || id == 2614 || id == 2625 || id == 2636 || id == 2647 || id == 2658 || id == 2667 || id == 2676 || id == 2685 || id == 2694 || id == 2703 || id == 2712 || id == 2721 || id == 2730 || id == 2739 || id == 2748 || id == 2757 || id == 2766 || id == 2776 || id == 2786 || id == 2796 || id == 2806 || id == 2821 || id == 2836 || id == 2851 || id == 2866 || id == 2874 || id == 2882 || id == 2890 || id == 2898 || id == 2907 || id == 2916 || id == 2925 || id == 2934 || id == 2944 || id == 2954 || id == 2964 || id == 2974 || id == 2983 || id == 2992 || id == 3001 || id == 3010 || id == 3016 || id == 3022 || id == 3028 || id == 3034 || id == 3040 || id == 3046 || id == 3052 || id == 3058 || id == 3066 || id == 3081) {
                if ((male_contract != null && !male_contract.isEmpty() && !male_contract.equals("null")))
                    mapXref.put("LabourRate", male_contract);
            }
            if (id == 11 || id == 18 || id == 49 || id == 58 || id == 66 || id == 71 || id == 77 || id == 84 || id == 88 || id == 93 || id == 100 || id == 106 || id == 117 || id == 126 || id == 135 || id == 144 || id == 154 || id == 169 || id == 178 || id == 186 || id == 195 || id == 228 || id == 232 || id == 240 || id == 246 || id == 250 || id == 258 || id == 264 || id == 268 || id == 274 || id == 285 || id == 294 || id == 303 || id == 312 || id == 322 || id == 337 || id == 346 || id == 355 || id == 363 || id == 373 || id == 378 || id == 382 || id == 390 || id == 396 || id == 402 || id == 408 || id == 419 || id == 428 || id == 437 || id == 446 || id == 456 || id == 471 || id == 480 || id == 489 || id == 497 || id == 541 || id == 556 || id == 571 || id == 586 || id == 601 || id == 616 || id == 631 || id == 646 || id == 661 || id == 676 || id == 691 || id == 706 || id == 721 || id == 736 || id == 751 || id == 766 || id == 781 || id == 796 || id == 804 || id == 812 || id == 820 || id == 828 || id == 836 || id == 844 || id == 852 || id == 860 || id == 868 || id == 876 || id == 884 || id == 892 || id == 900 || id == 908 || id == 916 || id == 924 || id == 934 || id == 944 || id == 954 || id == 964 || id == 974 || id == 984 || id == 994 || id == 1004 || id == 1014 || id == 1024 || id == 1034 || id == 1044 || id == 1054 || id == 1064 || id == 1074 || id == 1084 || id == 1093 || id == 1102 || id == 1111 || id == 1120 || id == 1129 || id == 1138 || id == 1147 || id == 1156 || id == 1165 || id == 1174 || id == 1183 || id == 1192 || id == 1201 || id == 1210 || id == 1219 || id == 1228 || id == 1235 || id == 1241 || id == 1247 || id == 1254 || id == 1260 || id == 1266 || id == 1273 || id == 1279 || id == 1285 || id == 1292 || id == 1298 || id == 1304 || id == 1310 || id == 1316 || id == 1322 || id == 1328 || id == 1334 || id == 1340 || id == 1346 || id == 1352 || id == 1358 || id == 1364 || id == 1370 || id == 1376 || id == 1387 || id == 1398 || id == 1407 || id == 1416 || id == 1425 || id == 1434 || id == 1443 || id == 1452 || id == 1462 || id == 1475 || id == 1481 || id == 1487 || id == 1493 || id == 1499 || id == 1505 || id == 1511 || id == 1517 || id == 1523 || id == 1529 || id == 1535 || id == 1541 || id == 1547 || id == 1553 || id == 1559 || id == 1565 || id == 1571 || id == 1577 || id == 1583 || id == 1589 || id == 1595 || id == 1601 || id == 1607 || id == 1622 || id == 1637 || id == 1652 || id == 1667 || id == 1682 || id == 1697 || id == 1712 || id == 1727 || id == 1742 || id == 1757 || id == 1765 || id == 1773 || id == 1781 || id == 1789 || id == 1797 || id == 1805 || id == 1813 || id == 1821 || id == 1829 || id == 1837 || id == 1846 || id == 1855 || id == 1864 || id == 1873 || id == 1882 || id == 1891 || id == 1900 || id == 1909 || id == 1918 || id == 1927 || id == 1937 || id == 1947 || id == 1957 || id == 1967 || id == 1977 || id == 1987 || id == 1997 || id == 2007 || id == 2017 || id == 2027 || id == 2036 || id == 2045 || id == 2054 || id == 2063 || id == 2072 || id == 2081 || id == 2090 || id == 2099 || id == 2108 || id == 2117 || id == 2128 || id == 2139 || id == 2150 || id == 2161 || id == 2172 || id == 2183 || id == 2194 || id == 2205 || id == 2216 || id == 2227 || id == 2236 || id == 2245 || id == 2254 || id == 2263 || id == 2272 || id == 2281 || id == 2290 || id == 2299 || id == 2308 || id == 2317 || id == 2326 || id == 2335 || id == 2344 || id == 2353 || id == 2362 || id == 2371 || id == 2380 || id == 2389 || id == 2398 || id == 2407 || id == 2416 || id == 2425 || id == 2434 || id == 2443 || id == 2452 || id == 2461 || id == 2470 || id == 2479 || id == 2488 || id == 2497 || id == 2507 || id == 2517 || id == 2527 || id == 2537 || id == 2547 || id == 2557 || id == 2567 || id == 2577 || id == 2587 || id == 2597 || id == 2603 || id == 2609 || id == 2615 || id == 2626 || id == 2637 || id == 2648 || id == 2659 || id == 2668 || id == 2677 || id == 2686 || id == 2695 || id == 2704 || id == 2713 || id == 2722 || id == 2731 || id == 2740 || id == 2749 || id == 2758 || id == 2767 || id == 2777 || id == 2787 || id == 2797 || id == 2807 || id == 2822 || id == 2837 || id == 2852 || id == 2867 || id == 2875 || id == 2883 || id == 2891 || id == 2899 || id == 2908 || id == 2917 || id == 2926 || id == 2935 || id == 2945 || id == 2955 || id == 2965 || id == 2975 || id == 2984 || id == 2993 || id == 3002 || id == 3011 || id == 3017 || id == 3023 || id == 3029 || id == 3035 || id == 3041 || id == 3047 || id == 3053 || id == 3059 || id == 3067 || id == 3082) {
                if ((male_contract != null && !male_contract.isEmpty() && !male_contract.equals("null")))
                    mapXref.put("LabourRate", female_contract);
            }
            if (id == 32 || id == 33 || id == 34 || id == 35) {
                // Small poly Bag filling
                if ((male_contract != null && !male_contract.isEmpty() && !male_contract.equals("null")))
                    mapXref.put("LabourRate", smallPolyBag);
            }
            if (id == 211 || id == 212 || id == 213 || id == 214) {
                // Small poly Bag filling
                if ((male_contract != null && !male_contract.isEmpty() && !male_contract.equals("null")))
                    mapXref.put("LabourRate", bigPolyBag);
            }

            listKey.add(mapXref);

        }

        dataAccessHandler.insertMyDataa("SaplingActivityXref", listKey, new ApplicationThread.OnComplete<String>() {
            @Override
            public void execute(boolean success, String result, String msg) {
                Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> SaplingActivityXref INSERT COMPLETED");

                if (success) {
                    LinkedHashMap sapling = new LinkedHashMap();
                    sapling.put("TransactionId", _transactionId);
                    sapling.put("ConsignmentCode", _consignmentCode);
                    sapling.put("ActivityId", _activityId);
                    sapling.put("StatusTypeId", 346);  // TODO CHECK DB
                    sapling.put("Comment", "");
                    sapling.put("IsActive", 1);
                    sapling.put("CreatedByUserId", CommonConstants.USER_ID);
                    sapling.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    sapling.put("UpdatedByUserId", CommonConstants.USER_ID);
                    sapling.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    sapling.put("ServerUpdatedStatus", 0);
                    final List<LinkedHashMap> saplingList = new ArrayList<>();

                    saplingList.add(sapling);
                    dataAccessHandler.insertMyDataa("SaplingActivity", saplingList, new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            if (success) {
                                Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> SaplingActivity INSERT COMPLETED");
                                Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> Add new Task Completed");
                                finish();
                                Toast.makeText(ActivityTask.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    // -------------------------
                    if (isFromMultipleEntry) {
                        // Came from Multiple entry then we can update Status only
                        LinkedHashMap status = new LinkedHashMap();

                        status.put("ConsignmentCode", _consignmentCode);
                        status.put("ActivityId", _activityId);
                        status.put("StatusTypeId", _statusTypeId);
                        status.put("CreatedByUserId", CommonConstants.USER_ID);
                        status.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                        status.put("UpdatedByUserId", CommonConstants.USER_ID);
                        status.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                        status.put("JobCompletedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                        status.put("ServerUpdatedStatus", 0);

                        final List<LinkedHashMap> statusList = new ArrayList<>();
                        statusList.add(status);
                        dataAccessHandler.updateData("SaplingActivityStatus", statusList, true, " where ConsignmentCode = " + "'" + _consignmentCode + "' AND ActivityId ='" + _activityId + "'", new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> SaplingActivityStatus INSERT COMPLETED");
                                Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> Update Task Completed");
                                finish();
                                Toast.makeText(ActivityTask.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        LinkedHashMap mapStatus = new LinkedHashMap();
                        mapStatus.put("Id", 0);
                        mapStatus.put("ConsignmentCode", _consignmentCode);
                        mapStatus.put("ActivityId", _activityId);
                        mapStatus.put("StatusTypeId", _statusTypeId);
                        mapStatus.put("CreatedByUserId", CommonConstants.USER_ID);
                        mapStatus.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                        mapStatus.put("UpdatedByUserId", CommonConstants.USER_ID);
                        mapStatus.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                        mapStatus.put("JobCompletedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                        mapStatus.put("ServerUpdatedStatus", 0);

                        final List<LinkedHashMap> statusArray = new ArrayList<>();
                        statusArray.add(mapStatus);

                        dataAccessHandler.insertMyDataa("SaplingActivityStatus", statusArray, new ApplicationThread.OnComplete<String>() {
                            @Override
                            public void execute(boolean success, String result, String msg) {
                                if (success) {
                                    Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> SaplingActivityStatus INSERT COMPLETED");

                                }

                            }
                        });

                    }
                }
            }
        });


    }

    private void createDynamicUI(LinearLayout ll) {


        List<ActivityTasks> groupView = new ArrayList<>();

        for (int i = 0; i < activityTasklist.size(); i++) {
            if (activityTasklist.get(i).getInputType().equalsIgnoreCase("Check box")) {
                if (activityTasklist.get(i).getField().equalsIgnoreCase("Is the activity completed")) {
                    isjobDoneId = activityTasklist.get(i).getId();
                    ll.addView(addCheckbox
                            (activityTasklist.get(i).getField(), activityTasklist.get(i).getId()));
                } else {
                    ll.addView(addCheckbox(activityTasklist.get(i).getField(), activityTasklist.get(i).getId()));
                }
            } else if (activityTasklist.get(i).getInputType().equalsIgnoreCase("TextBox") || activityTasklist.get(i).getInputType().equalsIgnoreCase("Label") || activityTasklist.get(i).getInputType().equalsIgnoreCase("Display") || activityTasklist.get(i).getInputType().equalsIgnoreCase("Formula")) {
                {
                    String UOm = activityTasklist.get(i).getUom().equalsIgnoreCase("null") ? "" : "( " + activityTasklist.get(i).getUom() + ")";
                    String isoptional = activityTasklist.get(i).getIsOptional() == 1 ? "" : " * ";
                    String content = activityTasklist.get(i).getField() +isoptional+ UOm ;
                    ll.addView(addEdittext(content, activityTasklist.get(i).getId(), activityTasklist.get(i).getDataType()));
                }
            } else if (activityTasklist.get(i).getInputType().equalsIgnoreCase("Label") || activityTasklist.get(i).getInputType().equalsIgnoreCase("Display")) {
                ll.addView(addTexView(activityTasklist.get(i).getField(), activityTasklist.get(i).getId()));
            } else if (activityTasklist.get(i).getInputType().equalsIgnoreCase("Dropdown") || activityTasklist.get(i).getInputType().equalsIgnoreCase("dropdown")) {
                ll.addView(addSpinner(activityTasklist.get(i).getId()));
            }

            // GetForeachGruoupItems


        }

        ll.addView(addButton("Submit", ButtonId));
    }

    private boolean validate() {
        dataValue = new ArrayList<>();

        for (int i = 0; i < activityTasklist.size(); i++) {
            int id = activityTasklist.get(i).getId();
            if (activityTasklist.get(i).getInputType().equalsIgnoreCase("Check box")) {

                CheckBox chk = (CheckBox) findViewById(id);
                Log.d("TESTING", "IS CHECKED  " + chk.isChecked() + "");
                dataValue.add(new KeyValues(activityTasklist.get(i).getId(), chk.isChecked() + ""));

            }
            if (findViewById(id).getVisibility() == View.VISIBLE && activityTasklist.get(i).getInputType().equalsIgnoreCase("TextBox")) {

                EditText et = findViewById(id);

                dataValue.add(new KeyValues(activityTasklist.get(i).getId(), et.getText() + ""));

                if (TextUtils.isEmpty(et.getText().toString())) {
                    //TOdo  need to check already exist or not
                    Toast.makeText(this, "Please Enter  " + activityTasklist.get(i).getField(), Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
            if (activityTasklist.get(i).getInputType().equalsIgnoreCase("Dropdown") || activityTasklist.get(i).getInputType().equalsIgnoreCase("dropdown")) {

                Spinner spinnner = findViewById(id);
                int selectedPo = spinnner.getSelectedItemPosition();
                dataValue.add(new KeyValues(activityTasklist.get(i).getId(), spinnner.getSelectedItem().toString()));
                Log.d(ActivityTask.class.getSimpleName(), "DropDownn Selected String :" + spinnner.getSelectedItem().toString());
                if (spinnner.getSelectedItemPosition() == 0) {
                    //TOdo  need to check already exist or not

                    Toast.makeText(this, "Please Select " + activityTasklist.get(i).getField(), Toast.LENGTH_SHORT).show();
                    return false;
                }

            }


        }

        return true;

    }

//    private boolean validateyesNO() {
//        CheckBox chk = findViewById(yesnoCHeckbox);
//        if(chk.isChecked())
//        {
//            if (!GroupValidate()) {
//                return false;
//            }
//        }else
//        {
//            validate()
//        }
//
//        return true;
//
//    }

    private boolean GroupValidate() {
        dataValue = new ArrayList<>();

        for (int i = 0; i < activityTasklist.size(); i++) {
            int id = activityTasklist.get(i).getId();
            if (activityTasklist.get(i).getInputType().equalsIgnoreCase("Check box")) {

                CheckBox chk = (CheckBox) findViewById(id);
                Log.d("TESTING", "IS CHECKED  " + chk.isChecked() + "");
                dataValue.add(new KeyValues(activityTasklist.get(i).getId(), chk.isChecked() + ""));

            }
//            if (findViewById(id).getVisibility() == View.VISIBLE && activityTasklist.get(i).getInputType().equalsIgnoreCase("TextBox")) {
            if (activityTasklist.get(i).getInputType().equalsIgnoreCase("TextBox") || activityTasklist.get(i).getInputType().equalsIgnoreCase("Label") ||
                    activityTasklist.get(i).getInputType().equalsIgnoreCase("Display") || activityTasklist.get(i).getInputType().equalsIgnoreCase("Formula")
            ) {

                EditText et = findViewById(id);

                dataValue.add(new KeyValues(activityTasklist.get(i).getId(), et.getText() + ""));


                Log.d("Groupvalidation ", " Field Id :" + activityTasklist.get(i).getField() + "IS OPTIONAL :"+activityTasklist.get(i).getIsOptional());
                if (findViewById(id).getVisibility() == View.VISIBLE && activityTasklist.get(i).getIsOptional() == 0 && activityTasklist.get(i).getGroupId() == 0 && TextUtils.isEmpty(et.getText().toString())) {
                    //TOdo  need to check already exist or not
                    Toast.makeText(this, "Please Enter  " + activityTasklist.get(i).getField(), Toast.LENGTH_SHORT).show();
                    return false;
                }

            }

            if (activityTasklist.get(i).getInputType().equalsIgnoreCase("Dropdown") || activityTasklist.get(i).getInputType().equalsIgnoreCase("dropdown")) {

                Spinner spinnner = findViewById(id);
                int selectedPo = spinnner.getSelectedItemPosition();
                dataValue.add(new KeyValues(activityTasklist.get(i).getId(), spinnner.getSelectedItem().toString()));
                Log.d(ActivityTask.class.getSimpleName(), "DropDownn Selected String :" + spinnner.getSelectedItem().toString());
                if (spinnner.getSelectedItemPosition() == 0) {
                    //TOdo  need to check already exist or not

                    Toast.makeText(this, "Please Select " + activityTasklist.get(i).getField(), Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
            if(activityTasklist.get(i).getActivityTypeId() == 12){
                try {
                    int int52 = 52, int51 = 51,int53 = 53;

                    int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int51))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int52)));
                    Log.d("TESTING  finalValue", + finalValue + "");
              //      dataValue.add(new KeyValues(int53, finalValue+ ""));
                    if(finalValue < 0){
                        Toast.makeText(this, "Please  Enter Correct Values  " , Toast.LENGTH_SHORT).show();
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if(activityTasklist.get(i).getActivityTypeId() == 13){
                try {
                    int int60 = 60, int61 = 61, int62 = 62;;

                    int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int60))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int61)));
                    Log.d("TESTING  finalValue", + finalValue + "");

                 //   dataValue.add(new KeyValues(int62, finalValue + ""));

                    if(finalValue < 0){
                        Toast.makeText(this, "Please  Enter Correct Values  " , Toast.LENGTH_SHORT).show();
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if(activityTasklist.get(i).getActivityTypeId() == 32){
                try {
                    int int506 = 506, int507 = 507, int508 = 508;

                    int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int506))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int507)));
                    Log.d("TESTING  finalValue", + finalValue + "");

                //    dataValue.add(new KeyValues(int62, finalValue + ""));
                    if(finalValue < 0){
                        Toast.makeText(this, "Please  Enter Correct Values  " , Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    int finalValue2 = CommonUtils.getIntFromEditText(((EditText) findViewById(int506))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int508)));
                    if(finalValue2 < 0){
                        Toast.makeText(this, "Please  Enter Correct Values  " , Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if(activityTasklist.get(i).getActivityTypeId() == 42){
                try {
                    int int510 = 510, int511 = 511,int512 = 512;

                    int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int510))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int511)));
                    Log.d("TESTING  finalValue", + finalValue + "");
                    if(finalValue < 0){
                        Toast.makeText(this, "Please  Enter Correct Values  " , Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    int finalValue2 = CommonUtils.getIntFromEditText(((EditText) findViewById(int510))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int512)));
                    if(finalValue2 < 0){
                        Toast.makeText(this, "Please  Enter Correct Values  " , Toast.LENGTH_SHORT).show();
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if(activityTasklist.get(i).getActivityTypeId() == 63){
                try {
                    int int514 = 514, int515 = 515, int516 = 516;

                    int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int514))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int515)));
                    Log.d("TESTING  finalValue", + finalValue + "");
                    if(finalValue < 0){
                        Toast.makeText(this, "Please  Enter Correct Values  " , Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    int finalValue2 = CommonUtils.getIntFromEditText(((EditText) findViewById(int514))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int516)));
                    if(finalValue2 < 0){
                        Toast.makeText(this, "Please  Enter Correct Values  " , Toast.LENGTH_SHORT).show();
                        return false;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if(activityTasklist.get(i).getActivityTypeId() == 94){
                try {
                    int int518 = 518, int519 = 519,int520 = 520;

                    int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int518))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int519)));
                    Log.d("TESTING  finalValue", + finalValue + "");
                    if(finalValue < 0){
                        Toast.makeText(this, "Please  Enter Correct Values  " , Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    int finalValue2 = CommonUtils.getIntFromEditText(((EditText) findViewById(int518))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int520)));
                    if(finalValue2 < 0){
                        Toast.makeText(this, "Please  Enter Correct Values  " , Toast.LENGTH_SHORT).show();
                        return false;
                    }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
            if(activityTasklist.get(i).getActivityTypeId() == 126){
        try {
            int int522 = 522, int523 = 523;

            int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int522))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int523)));
            Log.d("TESTING  finalValue", + finalValue + "");
            if(finalValue < 0){
                Toast.makeText(this, "Please  Enter Correct Values  " , Toast.LENGTH_SHORT).show();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

        }

        if (yesnoCHeckbox > 0 && Integer.parseInt(activityTypeId) != 9) {
            CheckBox chk = findViewById(yesnoCHeckbox);
            if (chk.isChecked()) {
                boolean isvalid = true;
                List<Integer> groupids = dataAccessHandler.getGroupids(Queries.getGroupIds(activityTypeId));

                for (int i = 0; i < groupids.size(); i++) {
                    List<ActivityTasks> groupedField = dataAccessHandler.getActivityTasksDetails(Queries.getInstance().getActivityTaskDetailsUsingGroupId(Integer.parseInt(activityTypeId), groupids.get(i)));

                    if (!validateGroup(groupedField)) {
                        Toast.makeText(this, "Please Enter Atlest One value for Following \n " + errorMsg, Toast.LENGTH_SHORT).show();
                        isvalid = false;
                    }

                }
                return isvalid;
            }
        } else {
            List<Integer> groupids = dataAccessHandler.getGroupids(Queries.getGroupIds(activityTypeId));
            boolean isvalid = true;
            for (int i = 0; i < groupids.size(); i++) {
                List<ActivityTasks> groupedField = dataAccessHandler.getActivityTasksDetails(Queries.getInstance().getActivityTaskDetailsUsingGroupId(Integer.parseInt(activityTypeId), groupids.get(i)));

                if (!validateGroup(groupedField)) {
                    Toast.makeText(this, "Please Enter Atlest One value for Following \n " + errorMsg, Toast.LENGTH_SHORT).show();
                    isvalid = false;
                }


            }
            return isvalid;
        }

        // write validation
        // get first edit text and second one validate

        return true;
    }






    private void addorupdate() {

    }

    private boolean validateGroup(List<ActivityTasks> groupFields) {
        errorMsg = "";
        for (int i = 0; i < groupFields.size(); i++) {


            EditText editText = (EditText) findViewById(groupFields.get(i).getId());
            errorMsg = errorMsg + "\n" + groupFields.get(i).getField();
            if (editText != null & editText.getText() != null & !StringUtils.isEmpty(editText.getText())) {
                return true;
            }
        }



        return false;
    }

    public Spinner addSpinner(int id) {
        Spinner sp = new Spinner(this);
        typeofLabourdatamap = dataAccessHandler.getPairData(Queries.getInstance().getTypeofLabourQuery());

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(ActivityTask.this, android.R.layout.simple_spinner_item, CommonUtils.arrayFromPair(typeofLabourdatamap, "Type of Labour"));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(spinnerArrayAdapter);

        sp.setId(id);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ActivityTask.this, "Selected PO :" + i, Toast.LENGTH_SHORT).show();

                if (Integer.parseInt(activityTypeId) == 9) {
                    if (i == 1) {

                        // HIDE ITEMS
                        yesnoCHeckbox = 31;

                        for (int f = 21; f < 32; f++) {

                            try {
                                findViewById(f).setVisibility(View.VISIBLE);
                                findViewById(f + 9000).setVisibility(View.VISIBLE);
//                                if(f < 31 && f > 22){
//                                    try {
//                                        ((EditText)findViewById(f)).setText("");
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        for (int f = 31; f < 45; f++) {

                            try {
                                findViewById(f).setVisibility(View.GONE);
                                findViewById(f + 9000).setVisibility(View.GONE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                    } else if (i == 2) {
                        yesnoCHeckbox = 45;
                        for (int f = 21; f < 34; f++) {

                            try {
                                findViewById(f).setVisibility(View.GONE);
                                findViewById(f + 9000).setVisibility(View.GONE);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        for (int f = 32; f < 45; f++) {

                            try {
                                findViewById(f).setVisibility(View.VISIBLE);
                                findViewById(f + 9000).setVisibility(View.VISIBLE);

//                                    if(f < 45 && f > 31){
//                                        try {
//                                            ((EditText)findViewById(f)).setText("");
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                    }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    }


                } else if (Integer.parseInt(activityTypeId) == 91) {
                    if (i == 1) {
                        yesnoCHeckbox = 210;

                        for (int f = 200; f < 211; f++) {

                            try {
                                findViewById(f).setVisibility(View.VISIBLE);
                                findViewById(f + 9000).setVisibility(View.VISIBLE);
//                                if(f < 31 && f > 22){
//                                    try {
//                                        ((EditText)findViewById(f)).setText("");
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        for (int f = 211; f < 225; f++) {

                            try {
                                findViewById(f).setVisibility(View.GONE);
                                findViewById(f + 9000).setVisibility(View.GONE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    } else if (i == 2) {
                        yesnoCHeckbox = 224;


                        for (int f = 200; f < 211; f++) {

                            try {
                                findViewById(f).setVisibility(View.GONE);
                                findViewById(f + 9000).setVisibility(View.GONE);
//                                if(f < 31 && f > 22){
//                                    try {
//                                        ((EditText)findViewById(f)).setText("");
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        for (int f = 211; f < 225; f++) {

                            try {
                                findViewById(f).setVisibility(View.VISIBLE);
                                findViewById(f + 9000).setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return sp;


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public CheckBox addCheckbox(String content, int id) {
        CheckBox cb = new CheckBox(this);
        cb.setText(content);
        cb.setId(id);

        cb.setOnClickListener(this::onClick);
        Log.d(ActivityTask.class.getSimpleName(), "===> Analysis YES NO CHK  ID:  before Assign :" + id + "And Name :" + content);
        if (id == 173 || id == 72 || id == 79 || id == 95 || id == 101 || id == 112 || id == 121 || id == 130 || id == 139 || id == 149 || id == 164 || id == 253 || id == 259 || id == 269 || id == 280 || id == 289 || id == 298 || id == 307 || id == 317 || id == 332 || id == 341 || id == 368 || id == 385 || id == 391 || id == 397 || id == 403 || id == 414 || id == 423 || id == 432 || id == 611 || id == 626 || id == 641 || id == 656 || id == 671 || id == 686 || id == 701 || id == 716 || id == 731 || id == 746 || id == 761 || id == 776 || id == 1079 || id == 1088 || id == 1097 || id == 1106 || id == 1115 || id == 1124 || id == 1133 || id == 1142 || id == 1151 || id == 1160 || id == 1169 || id == 1178 || id == 1178 || id == 1187 || id == 1196 || id == 1205 || id == 1214 || id == 1223 || id == 1230 || id == 1236 || id == 1242 || id == 1249 || id == 1255 || id == 1261 || id == 1268 || id == 1274 || id == 1280 || id == 1287 || id == 1293 || id == 1299 || id == 1305 || id == 1311 || id == 1317 || id == 1323 || id == 1329 || id == 1335 || id == 1341 || id == 1347 || id == 1353 || id == 1359 || id == 1365 || id == 1371 || id == 1382 || id == 1393 || id == 1402 || id == 1411 || id == 1420 || id == 1429 || id == 1438 || id == 1447 || id == 1457 || id == 1470 || id == 1476 || id == 1482 || id == 1488 || id == 1494 || id == 1500 || id == 1506 || id == 1512 || id == 1518 || id == 1524 || id == 1530 || id == 1536 || id == 1542 || id == 1548 || id == 1554 || id == 1560 || id == 1566 || id == 1572 || id == 1578 || id == 1584 || id == 1590 || id == 1596 || id == 1602 || id == 1617 || id == 1632 || id == 1647 || id == 1662 || id == 1677 || id == 1692 || id == 1707 || id == 1722 || id == 1737 || id == 1832 || id == 1841 || id == 1850 || id == 1859 || id == 1868 || id == 1877 || id == 1886 || id == 1895 || id == 1904 || id == 1913 || id == 2022 || id == 2031 || id == 2040 || id == 2049 || id == 2058 || id == 2067 || id == 2076 || id == 2085 || id == 1187 || id == 1196 || id == 1205 || id == 1214 || id == 1223 || id == 1230 || id == 1236 || id == 1242 || id == 1249 || id == 2094 || id == 2103 || id == 2112 || id == 2123 || id == 2145 || id == 2156 || id == 2167 || id == 2178 || id == 2189 || id == 2200 || id == 2211 || id == 2222 || id == 2231 || id == 2240 || id == 2249 || id == 2258 || id == 2267 || id == 2276 || id == 2285 || id == 2294 || id == 2303 || id == 2312 || id == 2321 || id == 2330 || id == 2339 || id == 2348 || id == 2357 || id == 2366 || id == 2375 || id == 2384 || id == 2393 || id == 2402 || id == 2411 || id == 2420 || id == 2429 || id == 2438 || id == 2447 || id == 2456 || id == 2465 || id == 2474 || id == 2483 || id == 2492 || id == 2502 || id == 2512 || id == 2522 || id == 2532 || id == 2542 || id == 2552 || id == 2562 || id == 2572 || id == 2582 || id == 2592 || id == 2598 || id == 2604 || id == 2610 || id == 2621 || id == 2632 || id == 2643 || id == 2654 || id == 2663 || id == 2672 || id == 2681 || id == 2690 || id == 2699 || id == 2708 || id == 2717 || id == 2726 || id == 2735 || id == 2744 || id == 2753 || id == 2762 || id == 2772 || id == 2782 || id == 2792 || id == 2802 || id == 2817 || id == 2832 || id == 2847 || id == 2894 || id == 2903 || id == 2912 || id == 2921 || id == 2970 || id == 2979 || id == 3006 || id == 3012 || id == 3018 || id == 3024 || id == 3030 || id == 3036 || id == 3042 || id == 3048 || id == 3054 || id == 3062 || id == 3077 ||
                id == 1752 || id == 1922 || id == 350 || id == 358 || id == 1760 || id == 1932 || id == 2134 ||
                id == 181 || id == 253 || id == 259 || id == 269 || id == 280 || id == 289 || id == 298 || id == 307 || id == 317 || id == 332 || id == 341 || id == 368 ||
                id == 385 || id == 391 || id == 397 || id == 403 || id == 414 || id == 423 || id == 432 || id == 536 || id == 581 || id == 791 || id == 799 || id == 611 || id == 626 || id == 641 || id == 656 ||
                id == 671 || id == 686 || id == 701 || id == 716 || id == 731 || id == 746 || id == 761 || id == 776 || id == 1079 || id == 1088 || id == 1097 ||
                id == 1106 || id == 1115 || id == 1124 || id == 1133 || id == 1142 || id == 1151 || id == 1160 || id == 1169 || id == 1178 || id == 1178 ||
                id == 1187 || id == 1196 || id == 1205 || id == 1214 || id == 1223 || id == 1230 || id == 1236 || id == 1242 || id == 1249 || id == 1255 ||
                id == 1261 || id == 1268 || id == 1274 || id == 1280 || id == 1287 || id == 1293 || id == 1299 || id == 1305 || id == 1311 || id == 1317 ||
                id == 1323 || id == 1329 || id == 1335 || id == 1341 || id == 1347 || id == 1353 || id == 1359 || id == 1365 || id == 1371 || id == 1382 ||
                id == 1393 || id == 1402 || id == 1411 || id == 1420 || id == 1429 || id == 1438 || id == 1447 || id == 1457 || id == 1470 || id == 1476 || id == 1482 ||
                id == 1488 || id == 1494 || id == 1500 || id == 1506 || id == 1512 || id == 1518 || id == 1524 || id == 1530 || id == 1536 || id == 1542 || id == 1548 ||
                id == 1554 || id == 1560 || id == 1566 || id == 1572 || id == 1578 || id == 1584 || id == 1590 || id == 1596 || id == 1602 || id == 1617 ||
                id == 1632 || id == 1647 || id == 1662 || id == 1677 || id == 1692 || id == 1707 || id == 1722 || id == 1737 || id == 1832 || id == 1841 ||
                id == 1850 || id == 1859 || id == 1868 || id == 1877 || id == 1886 || id == 1895 || id == 1904 || id == 1913 || id == 2022 || id == 2031 ||
                id == 2040 || id == 2049 || id == 2058 || id == 2067 || id == 2076 || id == 2085 || id == 1187 || id == 1196 || id == 1205 || id == 1214 ||
                id == 1223 || id == 1230 || id == 1236 || id == 1242 || id == 1249 || id == 2094 || id == 2103 || id == 2112 || id == 2123 || id == 2145 ||
                id == 2156 || id == 2167 || id == 2178 || id == 2189 || id == 2200 || id == 2211 || id == 2222 || id == 2231 || id == 2240 || id == 2249 ||
                id == 2258 || id == 2267 || id == 2276 || id == 2285 || id == 2294 || id == 2303 || id == 2312 || id == 2321 || id == 2330 || id == 2339 ||
                id == 2348 || id == 2357 || id == 2366 || id == 2375 || id == 2384 || id == 2393 || id == 2402 || id == 2411 || id == 2420 || id == 2429 ||
                id == 2438 || id == 2447 || id == 2456 || id == 2465 || id == 2474 || id == 2483 || id == 2492 || id == 2502 || id == 2512 || id == 2522 ||
                id == 2532 || id == 2542 || id == 2552 || id == 2562 || id == 2572 || id == 2582 || id == 2592 || id == 2598 || id == 2604 || id == 2610 ||
                id == 2621 || id == 2632 || id == 2643 || id == 2654 || id == 2663 || id == 2672 || id == 2681 || id == 2690 || id == 2699 || id == 2708 ||
                id == 2717 || id == 2726 || id == 2735 || id == 2744 || id == 2753 || id == 2762 || id == 2772 || id == 2782 || id == 2792 || id == 2802 ||
                id == 2817 || id == 2832 || id == 2847 || id == 2894 || id == 2903 || id == 2912 || id == 2921 || id == 2970 || id == 2979 || id == 3006 ||
                id == 3012 || id == 3018 || id == 3024 || id == 3030 || id == 3036 || id == 3042 || id == 3048 || id == 3054 || id == 3062 || id == 3077 ||
                id == 1019 || id == 871 || id == 2940 || id == 1962 || id == 1972 || id == 1792 || id == 1800 || id == 1982 || id == 1808 || id == 1992 || id == 1816 || id == 2002 || id == 1824 || id == 2012 || id == 475 || id == 484 || id == 451 || id == 466 || id == 492 || id == 475 || id == 2862 || id == 2930 || id == 2870 || id == 2940 || id == 492 || id == 466 || id == 475 || id == 2862 || id == 2930 || id == 2878 || id == 2950 || id == 2988 || id == 2886 || id == 2997 || id == 2960 ||
                id == 979 || id == 551 || id == 791 || id == 919 || id == 566 || id == 929 || id == 807 || id == 939 || id == 596 || id == 815 || id == 949 || id == 823 || id == 951 || id == 831 || id == 969 || id == 839 || id == 847 || id == 989 || id == 855 || id == 979 || id == 999 || id == 863 || id == 1009 || id == 879 || id == 1029 || id == 887 || id == 1049 || id == 1039 || id == 895 || id == 903 || id == 1059 || id == 911 || id == 1069 || id == 350 || id == 1922 || id == 358 || id == 1942 || id == 1768 || id == 1776 || id == 1952 || id == 1784) {
            yesnoCHeckbox = id;
            cb.setChecked(true);
            Log.d(ActivityTask.class.getSimpleName(), "===> Analysis YES NO CHK  ID:" + yesnoCHeckbox);
        }


        return cb;
    }

    public CheckBox yesNoChekcbox(String content, int id) {
        chkShowHide = new CheckBox(this);
        chkShowHide.setText(content);
        chkShowHide.setId(id);
        chkShowHide.setSelected(true);

        return chkShowHide;
    }

    public CheckBox isJoneDoneChecbox(String content, int id) {
        CheckBox cb = new CheckBox(this);
        cb.setText(content);
        cb.setId(id);
        return cb;
    }


    public TextInputLayout addEdittext(String content, int id, String dataType) {

        TextInputLayout textInputLayout = new TextInputLayout(this);
        textInputLayout.setId(id + 9000);
        EditText et = new EditText(this);
//        et.setHint(content);
        et.setId(id);
        et.setMinLines(1);
        et.setMaxLines(1);

        if (dataType.equalsIgnoreCase("Integer") || dataType.equalsIgnoreCase("Float")) {
            et.setInputType(InputType.TYPE_CLASS_NUMBER);

        }


        et.setOnFocusChangeListener(this::onFocusChange);

        if (id == 60 || id == 506 || id == 510 || id == 514 || id == 518 || id == 522) {
            et.setFocusable(false);
            et.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            et.setClickable(false);
        }


        if (id == 60) {
            try {

                int finalValueold = Integer.parseInt(dataAccessHandler.getSingleValue(Queries.sproutsforSowing(consignmentCode, 54)));
                et.setText(finalValueold + "");

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (id == 506) {
            try {

                int finalValueold = Integer.parseInt(dataAccessHandler.getSingleValue(Queries.sproutsforSowing(consignmentCode, 62)));
                et.setText(finalValueold + "");


            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (id == 510) {
            // DOne
            try {

                int finalValueold = Integer.parseInt(dataAccessHandler.getSingleValue(Queries.sproutsforSowing(consignmentCode, 509)));
                et.setText(finalValueold + "");


            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (id == 514) {
            // DOne
            try {

                int finalValueold = Integer.parseInt(dataAccessHandler.getSingleValue(Queries.sproutsforSowing(consignmentCode, 513)));
                et.setText(finalValueold + "");


            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (id == 518) {
            // DOne
            try {

                int finalValueold = Integer.parseInt(dataAccessHandler.getSingleValue(Queries.sproutsforSowing(consignmentCode, 517)));
                et.setText(finalValueold + "");


            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (id == 522) {
            // DOne
            try {

                int finalValueold = Integer.parseInt(dataAccessHandler.getSingleValue(Queries.sproutsforSowing(consignmentCode, 521)));
                et.setText(finalValueold + "");


            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        textInputLayout.setHint(content);
        textInputLayout.addView(et);


        return textInputLayout;

    }

    public TextView addTexView(String content, int id) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(content);
        return tv;

    }

    public Button addButton(String content, int id) {
        Button btn = new Button(this);
        btn.setText(content);
        btn.setBackgroundColor(getResources().getColor(R.color.green_dark));

        btn.setId(id);
        btn.setOnClickListener(this::onClick);
        return btn;

    }

    private void saveData() {


        Bundle extras = getIntent().getExtras();
        if (SCREEN_FROM == CommonConstants.FROM_MUTIPLE_ENTRY_EDITDATA) {
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_MUTIPLE_ENTRY_EDITDATA");
            // SCREEN CAME FROM UPDATE CURRENT SCREEN
            String intentTransactionId = extras.getString("transactionId");
            String consignmentcode = extras.getString("consignmentcode");
            String ActivityTypeId = extras.getString("ActivityTypeId");
            boolean enableEditing = extras.getBoolean("enableEditing");

            int statusTypeId;
            if (isjobDoneId != 0) {
                CheckBox chk = findViewById(isjobDoneId);
                if (chk.isChecked()) {
                    statusTypeId = 346;
                } else {
                    statusTypeId = 352;
                }
            } else {
                statusTypeId = 346;
            }
            Log.d(ActivityTask.class.getSimpleName(), "==> Analysis => FROM CHECKBOX  STATUS TYPEID : " + statusTypeId);

            updateSingleEntryData(consignmentcode, ActivityTypeId, intentTransactionId, statusTypeId, enableEditing);

        } else if (SCREEN_FROM == CommonConstants.FROM_MULTIPLE_ADD_NEW_TASK) {
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_MULTIPLE_ADD_NEW_TASK");
            String activityTypeId = extras.getString("ActivityTypeId");
            String consignmentcode = extras.getString("consignmentcode");
            boolean Ismultipleentry = extras.getBoolean("Ismultipleentry");
            int statusTypeId;
            if (isjobDoneId != 0) {
                CheckBox chk = findViewById(isjobDoneId);
                if (chk.isChecked()) {
                    statusTypeId = 346;
                } else {
                    statusTypeId = 352;
                }
            } else {
                statusTypeId = 346;
            }
            String transactionIdNew = "T" + CommonConstants.TAB_ID + consignmentcode + activityTypeId + "-" + (dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getSaplingActivityMaxNumber()) + 1);
            Log.d(ActivityTask.class.getSimpleName(), "==> Analysis   New Transaction ID :" + transactionIdNew);
            addNewSingleEntryActivity(consignmentcode, activityTypeId, statusTypeId, transactionIdNew, true);

        } else if (SCREEN_FROM == CommonConstants.FROM_SINGLE_ENTRY_EDITDATA) {
            Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis  ==> SCREEN CAME FROM :FROM_SINGLE_ENTRY_EDITDATA");
            String consignmentcode = extras.getString("consignmentcode");
            String activityTypeId = extras.getString("ActivityTypeId");
            String multipleentry = extras.getString("multipleEntry");

            int statusTypeId;
            if (isjobDoneId != 0) {
                CheckBox chk = findViewById(isjobDoneId);
                if (chk.isChecked()) {
                    statusTypeId = 346;
                } else {
                    statusTypeId = 352;
                }
            } else {
                statusTypeId = 346;
            }
            Log.d(ActivityTask.class.getSimpleName(), "==> Analysis => FROM CHECKBOX  STATUS TYPEID : " + statusTypeId);
            String transactionId = dataAccessHandler.getSingleValue(Queries.getInstance().getTransactionIdUsingConsimentCode(consignmentcode, activityTypeId));
            if (null != transactionId && !transactionId.isEmpty() && !TextUtils.isEmpty(transactionId)) {
                updateSingleEntryData(consignmentcode, activityTypeId, transactionId, statusTypeId, false);
            } else {
                // TODO dont have any Existind data add new activity
                Log.d(ActivityTask.class.getSimpleName(), "==> Analysis  ==> New Task Creation Started ");
                String transactionIdNew = "T" + CommonConstants.TAB_ID + consignmentcode + activityTypeId + "-" + (dataAccessHandler.getOnlyOneIntValueFromDb(Queries.getInstance().getSaplingActivityMaxNumber()) + 1);
                Log.d(ActivityTask.class.getSimpleName(), "==> Analysis   New Transaction ID :" + transactionIdNew);

                addNewSingleEntryActivity(consignmentcode, activityTypeId, statusTypeId, transactionIdNew, false);
            }


        }
//                    setSaplingActivity();
//                    finish();
//                    Toast.makeText(ActivityTask.this, "Task Completed Successfully", Toast.LENGTH_SHORT).show();

    }

    private void CheckMantoryItem() {
        for (int i = 0; i < activityTasklist.size(); i++) {


            if (activityTasklist.get(i).getActivityTypeId() == 26 ||   activityTasklist.get(i).getActivityTypeId() == 93 ||
                    activityTasklist.get(i).getActivityTypeId() == 37 || activityTasklist.get(i).getActivityTypeId() == 39 ||
                    activityTasklist.get(i).getActivityTypeId() == 40 || activityTasklist.get(i).getActivityTypeId() == 16 ||
                    activityTasklist.get(i).getActivityTypeId() == 17 || activityTasklist.get(i).getActivityTypeId() == 18 || activityTasklist.get(i).getActivityTypeId() == 19 ||
                    activityTasklist.get(i).getActivityTypeId() == 124 || activityTasklist.get(i).getActivityTypeId() == 125 || activityTasklist.get(i).getActivityTypeId() == 130 ||
                    activityTasklist.get(i).getActivityTypeId() == 131 || activityTasklist.get(i).getActivityTypeId() == 132 || activityTasklist.get(i).getActivityTypeId() == 133 ||
                    activityTasklist.get(i).getActivityTypeId() == 134 || activityTasklist.get(i).getActivityTypeId() == 139 || activityTasklist.get(i).getActivityTypeId() == 140 ||
                    activityTasklist.get(i).getActivityTypeId() == 141 || activityTasklist.get(i).getActivityTypeId() == 142 || activityTasklist.get(i).getActivityTypeId() == 143 ||
                    activityTasklist.get(i).getActivityTypeId() == 163 || activityTasklist.get(i).getActivityTypeId() == 288 || activityTasklist.get(i).getActivityTypeId() == 289 ||
                    activityTasklist.get(i).getActivityTypeId() == 296 || activityTasklist.get(i).getActivityTypeId() == 21 ||  activityTasklist.get(i).getActivityTypeId() == 33 ||
                    activityTasklist.get(i).getActivityTypeId() == 45 || activityTasklist.get(i).getActivityTypeId() == 49 || activityTasklist.get(i).getActivityTypeId() == 55 ||
                    activityTasklist.get(i).getActivityTypeId() == 59 || activityTasklist.get(i).getActivityTypeId() == 71 || activityTasklist.get(i).getActivityTypeId() == 76 ||
                    activityTasklist.get(i).getActivityTypeId() == 82 || activityTasklist.get(i).getActivityTypeId() == 86 || activityTasklist.get(i).getActivityTypeId() == 100 ||
                    activityTasklist.get(i).getActivityTypeId() == 104 || activityTasklist.get(i).getActivityTypeId() == 109 || activityTasklist.get(i).getActivityTypeId() == 113 ||
                    activityTasklist.get(i).getActivityTypeId() == 117 || activityTasklist.get(i).getActivityTypeId() == 22 ||
                    activityTasklist.get(i).getActivityTypeId() == 34 || activityTasklist.get(i).getActivityTypeId() == 46 || activityTasklist.get(i).getActivityTypeId() == 50 ||
                    activityTasklist.get(i).getActivityTypeId() == 56 || activityTasklist.get(i).getActivityTypeId() == 60 || activityTasklist.get(i).getActivityTypeId() == 72 ||
                    activityTasklist.get(i).getActivityTypeId() == 77 || activityTasklist.get(i).getActivityTypeId() == 83 || activityTasklist.get(i).getActivityTypeId() == 87 || activityTasklist.get(i).getActivityTypeId() == 101 ||
                    activityTasklist.get(i).getActivityTypeId() == 105 || activityTasklist.get(i).getActivityTypeId() == 110 || activityTasklist.get(i).getActivityTypeId() == 114 ||
                    activityTasklist.get(i).getActivityTypeId() == 118 || activityTasklist.get(i).getActivityTypeId() == 23 ||
                    activityTasklist.get(i).getActivityTypeId() == 35 || activityTasklist.get(i).getActivityTypeId() == 47 || activityTasklist.get(i).getActivityTypeId() == 51 ||
                    activityTasklist.get(i).getActivityTypeId() == 57 || activityTasklist.get(i).getActivityTypeId() == 61 || activityTasklist.get(i).getActivityTypeId() == 73 ||
                    activityTasklist.get(i).getActivityTypeId() == 78 || activityTasklist.get(i).getActivityTypeId() == 84 || activityTasklist.get(i).getActivityTypeId() == 88 ||
                    activityTasklist.get(i).getActivityTypeId() == 102 || activityTasklist.get(i).getActivityTypeId() == 106 || activityTasklist.get(i).getActivityTypeId() == 111 ||
                    activityTasklist.get(i).getActivityTypeId() == 115 || activityTasklist.get(i).getActivityTypeId() == 119 ||
                    activityTasklist.get(i).getActivityTypeId() == 31 || activityTasklist.get(i).getActivityTypeId() == 36 || activityTasklist.get(i).getActivityTypeId() == 48 ||
                    activityTasklist.get(i).getActivityTypeId() == 52 || activityTasklist.get(i).getActivityTypeId() == 58 || activityTasklist.get(i).getActivityTypeId() == 62 ||
                    activityTasklist.get(i).getActivityTypeId() == 74 || activityTasklist.get(i).getActivityTypeId() == 79 || activityTasklist.get(i).getActivityTypeId() == 85 || activityTasklist.get(i).getActivityTypeId() == 89 ||
                    activityTasklist.get(i).getActivityTypeId() == 103 || activityTasklist.get(i).getActivityTypeId() == 107 || activityTasklist.get(i).getActivityTypeId() == 112 ||
                    activityTasklist.get(i).getActivityTypeId() == 116 || activityTasklist.get(i).getActivityTypeId() == 120 || activityTasklist.get(i).getActivityTypeId() == 151 ||
                    activityTasklist.get(i).getActivityTypeId() == 166 || activityTasklist.get(i).getActivityTypeId() == 178 || activityTasklist.get(i).getActivityTypeId() == 191 ||
                    activityTasklist.get(i).getActivityTypeId() == 203 || activityTasklist.get(i).getActivityTypeId() == 215 || activityTasklist.get(i).getActivityTypeId() == 228 ||
                    activityTasklist.get(i).getActivityTypeId() == 242 || activityTasklist.get(i).getActivityTypeId() == 255 || activityTasklist.get(i).getActivityTypeId() == 270 ||
                    activityTasklist.get(i).getActivityTypeId() == 152 || activityTasklist.get(i).getActivityTypeId() == 167 || activityTasklist.get(i).getActivityTypeId() == 179 ||
                    activityTasklist.get(i).getActivityTypeId() == 192 || activityTasklist.get(i).getActivityTypeId() == 204 || activityTasklist.get(i).getActivityTypeId() == 216 ||
                    activityTasklist.get(i).getActivityTypeId() == 229 || activityTasklist.get(i).getActivityTypeId() == 243 || activityTasklist.get(i).getActivityTypeId() == 256 ||
                    activityTasklist.get(i).getActivityTypeId() == 271 || activityTasklist.get(i).getActivityTypeId() == 154 || activityTasklist.get(i).getActivityTypeId() == 169 ||
                    activityTasklist.get(i).getActivityTypeId() == 181 || activityTasklist.get(i).getActivityTypeId() == 194 || activityTasklist.get(i).getActivityTypeId() == 206 ||
                    activityTasklist.get(i).getActivityTypeId() == 218 || activityTasklist.get(i).getActivityTypeId() == 231 || activityTasklist.get(i).getActivityTypeId() == 191 ||
                    activityTasklist.get(i).getActivityTypeId() == 203 || activityTasklist.get(i).getActivityTypeId() == 215 || activityTasklist.get(i).getActivityTypeId() == 245 ||
                    activityTasklist.get(i).getActivityTypeId() == 258 || activityTasklist.get(i).getActivityTypeId() == 273 || activityTasklist.get(i).getActivityTypeId() == 155 ||
                    activityTasklist.get(i).getActivityTypeId() == 170 || activityTasklist.get(i).getActivityTypeId() == 182 || activityTasklist.get(i).getActivityTypeId() == 195 ||
                    activityTasklist.get(i).getActivityTypeId() == 207 || activityTasklist.get(i).getActivityTypeId() == 219 || activityTasklist.get(i).getActivityTypeId() == 232 ||
                    activityTasklist.get(i).getActivityTypeId() == 246 || activityTasklist.get(i).getActivityTypeId() == 259 || activityTasklist.get(i).getActivityTypeId() == 274 ||
                    activityTasklist.get(i).getActivityTypeId() == 283 || activityTasklist.get(i).getActivityTypeId() == 297 || activityTasklist.get(i).getActivityTypeId() == 310 || activityTasklist.get(i).getActivityTypeId() == 324 ||
                    activityTasklist.get(i).getActivityTypeId() == 337 || activityTasklist.get(i).getActivityTypeId() == 284 || activityTasklist.get(i).getActivityTypeId() == 298 ||
                    activityTasklist.get(i).getActivityTypeId() == 311 || activityTasklist.get(i).getActivityTypeId() == 325 || activityTasklist.get(i).getActivityTypeId() == 338 ||
                    activityTasklist.get(i).getActivityTypeId() == 285 || activityTasklist.get(i).getActivityTypeId() == 299 || activityTasklist.get(i).getActivityTypeId() == 312 ||
                    activityTasklist.get(i).getActivityTypeId() == 326 || activityTasklist.get(i).getActivityTypeId() == 339 || activityTasklist.get(i).getActivityTypeId() == 286 ||
                    activityTasklist.get(i).getActivityTypeId() == 300 || activityTasklist.get(i).getActivityTypeId() == 313 || activityTasklist.get(i).getActivityTypeId() == 327 ||
                    activityTasklist.get(i).getActivityTypeId() == 340 || activityTasklist.get(i).getActivityTypeId() == 287 || activityTasklist.get(i).getActivityTypeId() == 301 || activityTasklist.get(i).getActivityTypeId() == 314 ||
                    activityTasklist.get(i).getActivityTypeId() == 328 || activityTasklist.get(i).getActivityTypeId() == 341 ||
                    activityTasklist.get(i).getActivityTypeId() == 153 || activityTasklist.get(i).getActivityTypeId() == 168 || activityTasklist.get(i).getActivityTypeId() == 180 || activityTasklist.get(i).getActivityTypeId() == 193 ||
                    activityTasklist.get(i).getActivityTypeId() == 205 || activityTasklist.get(i).getActivityTypeId() == 217 || activityTasklist.get(i).getActivityTypeId() == 230 ||
                    activityTasklist.get(i).getActivityTypeId() == 244 || activityTasklist.get(i).getActivityTypeId() == 257 || activityTasklist.get(i).getActivityTypeId() == 272 ||
                  activityTasklist.get(i).getActivityTypeId() == 53 || activityTasklist.get(i).getActivityTypeId() == 69 || activityTasklist.get(i).getActivityTypeId() == 80 ||
                    activityTasklist.get(i).getActivityTypeId() == 44 || activityTasklist.get(i).getActivityTypeId() == 54 || activityTasklist.get(i).getActivityTypeId() == 70 ||
                    activityTasklist.get(i).getActivityTypeId() == 81 || activityTasklist.get(i).getActivityTypeId() == 39 ||
                    activityTasklist.get(i).getActivityTypeId() == 40  || activityTasklist.get(i).getActivityTypeId() == 64 ||
                    activityTasklist.get(i).getActivityTypeId() == 65 || activityTasklist.get(i).getActivityTypeId() == 66 || activityTasklist.get(i).getActivityTypeId() == 67 ||
                    activityTasklist.get(i).getActivityTypeId() == 68 || activityTasklist.get(i).getActivityTypeId() == 95 || activityTasklist.get(i).getActivityTypeId() == 96 ||
                    activityTasklist.get(i).getActivityTypeId() == 97 || activityTasklist.get(i).getActivityTypeId() == 98 || activityTasklist.get(i).getActivityTypeId() == 99 ||
                    activityTasklist.get(i).getActivityTypeId() == 137 || activityTasklist.get(i).getActivityTypeId() == 149 || activityTasklist.get(i).getActivityTypeId() == 164 ||
                    activityTasklist.get(i).getActivityTypeId() == 176 || activityTasklist.get(i).getActivityTypeId() == 189 || activityTasklist.get(i).getActivityTypeId() == 201 || activityTasklist.get(i).getActivityTypeId() == 220 ||
                    activityTasklist.get(i).getActivityTypeId() == 233 || activityTasklist.get(i).getActivityTypeId() == 247 || activityTasklist.get(i).getActivityTypeId() == 260 || activityTasklist.get(i).getActivityTypeId() == 275 ||
                    activityTasklist.get(i).getActivityTypeId() == 138 || activityTasklist.get(i).getActivityTypeId() == 150 || activityTasklist.get(i).getActivityTypeId() == 165 || activityTasklist.get(i).getActivityTypeId() == 177 || activityTasklist.get(i).getActivityTypeId() == 190 || activityTasklist.get(i).getActivityTypeId() == 202 ||
                    activityTasklist.get(i).getActivityTypeId() == 221 || activityTasklist.get(i).getActivityTypeId() == 234 || activityTasklist.get(i).getActivityTypeId() == 248 ||
                    activityTasklist.get(i).getActivityTypeId() == 261 || activityTasklist.get(i).getActivityTypeId() == 276 ||
                    activityTasklist.get(i).getActivityTypeId() == 214 || activityTasklist.get(i).getActivityTypeId() == 241 || activityTasklist.get(i).getActivityTypeId() == 268 ||
                    activityTasklist.get(i).getActivityTypeId() == 144 || activityTasklist.get(i).getActivityTypeId() == 145 ||
                    activityTasklist.get(i).getActivityTypeId() == 146 || activityTasklist.get(i).getActivityTypeId() == 147 || activityTasklist.get(i).getActivityTypeId() == 148 ||
                    activityTasklist.get(i).getActivityTypeId() == 158 || activityTasklist.get(i).getActivityTypeId() == 159 || activityTasklist.get(i).getActivityTypeId() == 160 ||
                    activityTasklist.get(i).getActivityTypeId() == 161 || activityTasklist.get(i).getActivityTypeId() == 162 || activityTasklist.get(i).getActivityTypeId() == 171 ||
                    activityTasklist.get(i).getActivityTypeId() == 172 || activityTasklist.get(i).getActivityTypeId() == 173 || activityTasklist.get(i).getActivityTypeId() == 174 ||
                    activityTasklist.get(i).getActivityTypeId() == 175 || activityTasklist.get(i).getActivityTypeId() == 184 || activityTasklist.get(i).getActivityTypeId() == 185 ||
                    activityTasklist.get(i).getActivityTypeId() == 186 || activityTasklist.get(i).getActivityTypeId() == 187 || activityTasklist.get(i).getActivityTypeId() == 188 ||
                    activityTasklist.get(i).getActivityTypeId() == 196 || activityTasklist.get(i).getActivityTypeId() == 197 || activityTasklist.get(i).getActivityTypeId() == 198 ||
                    activityTasklist.get(i).getActivityTypeId() == 199 || activityTasklist.get(i).getActivityTypeId() == 200 || activityTasklist.get(i).getActivityTypeId() == 209 || activityTasklist.get(i).getActivityTypeId() == 210 ||
                    activityTasklist.get(i).getActivityTypeId() == 211 || activityTasklist.get(i).getActivityTypeId() == 212 || activityTasklist.get(i).getActivityTypeId() == 213 || activityTasklist.get(i).getActivityTypeId() == 223 || activityTasklist.get(i).getActivityTypeId() == 224 ||
                    activityTasklist.get(i).getActivityTypeId() == 225 || activityTasklist.get(i).getActivityTypeId() == 226 || activityTasklist.get(i).getActivityTypeId() == 227 ||
                    activityTasklist.get(i).getActivityTypeId() == 236 || activityTasklist.get(i).getActivityTypeId() == 237 || activityTasklist.get(i).getActivityTypeId() == 238 || activityTasklist.get(i).getActivityTypeId() == 239 ||
                    activityTasklist.get(i).getActivityTypeId() == 240 || activityTasklist.get(i).getActivityTypeId() == 250 || activityTasklist.get(i).getActivityTypeId() == 251 || activityTasklist.get(i).getActivityTypeId() == 252 || activityTasklist.get(i).getActivityTypeId() == 253 || activityTasklist.get(i).getActivityTypeId() == 254 || activityTasklist.get(i).getActivityTypeId() == 263 ||
                    activityTasklist.get(i).getActivityTypeId() == 264 || activityTasklist.get(i).getActivityTypeId() == 265 || activityTasklist.get(i).getActivityTypeId() == 266 || activityTasklist.get(i).getActivityTypeId() == 302 ||
                    activityTasklist.get(i).getActivityTypeId() == 315 || activityTasklist.get(i).getActivityTypeId() == 329 || activityTasklist.get(i).getActivityTypeId() == 342 ||
                    activityTasklist.get(i).getActivityTypeId() == 316 || activityTasklist.get(i).getActivityTypeId() == 330 || activityTasklist.get(i).getActivityTypeId() == 343 || activityTasklist.get(i).getActivityTypeId() == 323 ||
                    activityTasklist.get(i).getActivityTypeId() == 278 || activityTasklist.get(i).getActivityTypeId() == 279 || activityTasklist.get(i).getActivityTypeId() == 280 ||
                    activityTasklist.get(i).getActivityTypeId() == 281 || activityTasklist.get(i).getActivityTypeId() == 282 || activityTasklist.get(i).getActivityTypeId() == 291 || activityTasklist.get(i).getActivityTypeId() == 292 ||
                    activityTasklist.get(i).getActivityTypeId() == 293 || activityTasklist.get(i).getActivityTypeId() == 294 || activityTasklist.get(i).getActivityTypeId() == 295 ||
                    activityTasklist.get(i).getActivityTypeId() == 305 || activityTasklist.get(i).getActivityTypeId() == 306 || activityTasklist.get(i).getActivityTypeId() == 307 || activityTasklist.get(i).getActivityTypeId() == 308 ||
                    activityTasklist.get(i).getActivityTypeId() == 309 || activityTasklist.get(i).getActivityTypeId() == 318 || activityTasklist.get(i).getActivityTypeId() == 319 || activityTasklist.get(i).getActivityTypeId() == 320 ||
                    activityTasklist.get(i).getActivityTypeId() == 321 || activityTasklist.get(i).getActivityTypeId() == 322 || activityTasklist.get(i).getActivityTypeId() == 332 ||
                    activityTasklist.get(i).getActivityTypeId() == 333 || activityTasklist.get(i).getActivityTypeId() == 334 || activityTasklist.get(i).getActivityTypeId() == 335 || activityTasklist.get(i).getActivityTypeId() == 336) {

                showHideActivity = activityTasklist.get(i);


                // if True We will get id
            }

        }

    }

    private void updateSingleEntryData(String _consignmentcode, String _activityTypeId, String _transactionId, int _statusTypeId, boolean inSertInHistory) {
        displayData = dataAccessHandler.getdisplayDetails(Queries.getInstance().getDisplayData(_transactionId));
        Log.d(ActivityTask.class.getSimpleName(), "==> Analysis Count Of DisplayData :" + displayData.size());
        if (displayData != null && displayData.size() > 0) {


            for (int j = 0; j < dataValue.size(); j++) {

                final List<LinkedHashMap> listKeyUpdate = new ArrayList<>();
                LinkedHashMap updateXref = new LinkedHashMap();
                // map2.put("Id", 0);
                updateXref.put("TransactionId", _transactionId);
                updateXref.put("FieldId", dataValue.get(j).id);
                updateXref.put("Value", dataValue.get(j).value);
                updateXref.put("FilePath", "");
                updateXref.put("IsActive", 1);
//                updateXref.put("CreatedByUserId", CommonConstants.USER_ID);
                updateXref.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                updateXref.put("UpdatedByUserId", CommonConstants.USER_ID);
                updateXref.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                updateXref.put("ServerUpdatedStatus", 0);

                listKeyUpdate.add(updateXref);

                dataAccessHandler.updateData("SaplingActivityXref", listKeyUpdate, true, " where TransactionId = " + "'" + _transactionId + "'" + " AND FieldId = " + dataValue.get(j).getId(), new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        Log.d(ActivityTask.class.getSimpleName(), "==> Analysis   => Update of SaplingXref Done");
                        // Update Sapling Activity status


                    }
                });

            }

            LinkedHashMap activityMap = new LinkedHashMap();
            activityMap.put("TransactionId", _transactionId);
            activityMap.put("ConsignmentCode", _consignmentcode);
            activityMap.put("ActivityId", _activityTypeId);
            activityMap.put("StatusTypeId", 346);  // TODO Check with In DB
//            activityMap.put("Comment", "");
            activityMap.put("IsActive", 1);
//            activityMap.put("CreatedByUserId", CommonConstants.USER_ID);
            activityMap.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            activityMap.put("UpdatedByUserId", CommonConstants.USER_ID);
            activityMap.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
            activityMap.put("ServerUpdatedStatus", 0);
            final List<LinkedHashMap> activityList = new ArrayList<>();
            activityList.add(activityMap);

            dataAccessHandler.updateData("SaplingActivity", activityList, true, " where TransactionId = " + "'" + _transactionId + "'", new ApplicationThread.OnComplete<String>() {
                @Override
                public void execute(boolean success, String result, String msg) {
                    Log.d(ActivityTask.class.getSimpleName(), "==> Analysis   => Update of SaplingActivity");

                    LinkedHashMap status = new LinkedHashMap();

                    status.put("ConsignmentCode", _consignmentcode);
                    status.put("ActivityId", _activityTypeId);
                    status.put("StatusTypeId", _statusTypeId);
                    status.put("CreatedByUserId", CommonConstants.USER_ID);
                    status.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    status.put("UpdatedByUserId", CommonConstants.USER_ID);
                    status.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    status.put("JobCompletedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                    status.put("ServerUpdatedStatus", 0);

                    final List<LinkedHashMap> statusList = new ArrayList<>();
                    statusList.add(status);
                    dataAccessHandler.updateData("SaplingActivityStatus", statusList, true, " where ConsignmentCode = " + "'" + _consignmentcode + "' AND ActivityId ='" + _activityTypeId + "'", new ApplicationThread.OnComplete<String>() {
                        @Override
                        public void execute(boolean success, String result, String msg) {
                            Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> SaplingActivityStatus INSERT COMPLETED");
                            Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> Update Task Completed");
                            finish();
                            Toast.makeText(ActivityTask.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            if (inSertInHistory) {
                LinkedHashMap status = new LinkedHashMap();
                status.put("TransactionId", _transactionId);
                status.put("StatusTypeId", 346);
                status.put("Comments", "");
                status.put("CreatedByUserId", CommonConstants.USER_ID);
                status.put("CreatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                status.put("UpdatedByUserId", CommonConstants.USER_ID);
                status.put("UpdatedDate", CommonUtils.getcurrentDateTime(CommonConstants.DATE_FORMAT_DDMMYYYY_HHMMSS));
                status.put("ServerUpdatedStatus", 0);

                final List<LinkedHashMap> historyList = new ArrayList<>();
                historyList.add(status);
                dataAccessHandler.insertMyDataa("SaplingActivityHistory", historyList, new ApplicationThread.OnComplete<String>() {
                    @Override
                    public void execute(boolean success, String result, String msg) {
                        Log.d(ActivityTask.class.getSimpleName(), "==>  Analysis ==> SaplingActivityXref INSERT COMPLETED");
                    }
                });
            }

        }
    }


    @Override
    public void onClick(View view) {
        int btnid = 1;
        int id = view.getId();
        if (view.getId() == ButtonId) {
            if (goValidate())
                saveData();

        }
        if (id == 173 || id == 72 || id == 79 || id == 95 || id == 101 || id == 112 || id == 121 || id == 130 || id == 139 || id == 149 || id == 164 || id == 536 || id == 253 || id == 259 || id == 269 || id == 280 || id == 289 || id == 298 || id == 307 || id == 317 || id == 332 || id == 341 || id == 368 || id == 385 || id == 391 || id == 397 || id == 403 || id == 414 || id == 423 || id == 432 || id == 611 || id == 626 || id == 641 || id == 656 || id == 671 || id == 686 || id == 701 || id == 716 || id == 731 || id == 746 || id == 761 || id == 776 || id == 1079 || id == 1088 || id == 1097 || id == 1106 || id == 1115 || id == 1124 || id == 1133 || id == 1142 || id == 1151 || id == 1160 || id == 1169 || id == 1178 || id == 1178 || id == 1187 || id == 1196 || id == 1205 || id == 1214 || id == 1223 || id == 1230 || id == 1236 || id == 1242 || id == 1249 || id == 1255 || id == 1261 || id == 1268 || id == 1274 || id == 1280 || id == 1287 || id == 1293 || id == 1299 || id == 1305 || id == 1311 || id == 1317 || id == 1323 || id == 1329 || id == 1335 || id == 1341 || id == 1347 || id == 1353 || id == 1359 || id == 1365 || id == 1371 || id == 1382 || id == 1393 || id == 1402 || id == 1411 || id == 1420 || id == 1429 || id == 1438 || id == 1447 || id == 1457 || id == 1470 || id == 1476 || id == 1482 || id == 1488 || id == 1494 || id == 1500 || id == 1506 || id == 1512 || id == 1518 || id == 1524 || id == 1530 || id == 1536 || id == 1542 || id == 1548 || id == 1554 || id == 1560 || id == 1566 || id == 1572 || id == 1578 || id == 1584 || id == 1590 || id == 1596 || id == 1602 || id == 1617 || id == 1632 || id == 1647 || id == 1662 || id == 1677 || id == 1692 || id == 1707 || id == 1722 || id == 1737 || id == 1832 || id == 1841 || id == 1850 || id == 1859 || id == 1868 || id == 1877 || id == 1886 || id == 1895 || id == 1904 || id == 1913 || id == 2022 || id == 2031 || id == 2040 || id == 2049 || id == 2058 || id == 2067 || id == 2076 || id == 2085 || id == 1187 || id == 1196 || id == 1205 || id == 1214 || id == 1223 || id == 1230 || id == 1236 || id == 1242 || id == 1249 || id == 2094 || id == 2103 || id == 2112 || id == 2123 || id == 2145 || id == 2156 || id == 2167 || id == 2178 || id == 2189 || id == 2200 || id == 2211 || id == 2222 || id == 2231 || id == 2240 || id == 2249 || id == 2258 || id == 2267 || id == 2276 || id == 2285 || id == 2294 || id == 2303 || id == 2312 || id == 2321 || id == 2330 || id == 2339 || id == 2348 || id == 2357 || id == 2366 || id == 2375 || id == 2384 || id == 2393 || id == 2402 || id == 2411 || id == 2420 || id == 2429 || id == 2438 || id == 2447 || id == 2456 || id == 2465 || id == 2474 || id == 2483 || id == 2492 || id == 2502 || id == 2512 || id == 2522 || id == 2532 || id == 2542 || id == 2552 || id == 2562 || id == 2572 || id == 2582 || id == 2592 || id == 2598 || id == 2604 || id == 2610 || id == 2621 || id == 2632 || id == 2643 || id == 2654 || id == 2663 || id == 2672 || id == 2681 || id == 2690 || id == 2699 || id == 2708 || id == 2717 || id == 2726 || id == 2735 || id == 2744 || id == 2753 || id == 2762 || id == 2772 || id == 2782 || id == 2792 || id == 2802 || id == 2817 || id == 2832 || id == 2847 || id == 2894 || id == 2903 || id == 2912 || id == 2921 || id == 2970 || id == 2979 || id == 3006 || id == 3012 || id == 3018 || id == 3024 || id == 3030 || id == 3036 || id == 3042 || id == 3048 || id == 3054 || id == 3062 || id == 3077 || id == 1760 || id == 1932 || id == 2134 ||
                id == 1752 || id == 181 || id == 253 || id == 259 || id == 269 || id == 280 || id == 1019 || id == 871 || id == 2940 || id == 1962 || id == 1972 || id == 1792 || id == 1800 || id == 1982 || id == 1808 || id == 1992 || id == 1816 || id == 2002 || id == 1824 || id == 2012 || id == 475 || id == 484 || id == 451 || id == 466 || id == 492 || id == 475 || id == 2862 || id == 2930 || id == 2870 || id == 2940 || id == 492 || id == 466 || id == 475 || id == 2862 || id == 2930 || id == 2878 || id == 2950 || id == 2988 || id == 2886 || id == 2997 || id == 2960 || id == 289 || id == 298 || id == 307 || id == 317 || id == 332 || id == 341 || id == 368 || id == 385 || id == 391 || id == 397 || id == 403 || id == 414 || id == 423 || id == 432 || id == 611 || id == 626 || id == 641 || id == 656 || id == 671 || id == 686 || id == 701 || id == 716 || id == 731 || id == 746 || id == 761 || id == 776 || id == 581 || id == 791 || id == 799 || id == 1079 || id == 1088 || id == 1097 || id == 1106 || id == 1115 || id == 1124 || id == 1133 || id == 1142 || id == 1151 || id == 1160 || id == 1169 || id == 1178 || id == 1178 || id == 1187 || id == 1196 || id == 1205 || id == 1214 || id == 1223 || id == 1230 || id == 1236 || id == 1242 || id == 1249 || id == 1255 || id == 1261 || id == 1268 || id == 1274 || id == 1280 || id == 1287 || id == 1293 || id == 1299 || id == 1305 || id == 1311 || id == 1317 || id == 1323 || id == 1329 || id == 1335 || id == 1341 || id == 1347 || id == 1353 || id == 1359 || id == 1365 || id == 1371 || id == 1382 || id == 1393 || id == 1402 || id == 1411 || id == 1420 || id == 1429 || id == 1438 || id == 1447 || id == 1457 || id == 1470 || id == 1476 || id == 1482 || id == 1488 || id == 1494 || id == 1500 || id == 1506 || id == 1512 || id == 1518 || id == 1524 || id == 1530 || id == 1536 || id == 1542 || id == 1548 || id == 1554 || id == 1560 || id == 1566 || id == 1572 || id == 1578 || id == 1584 || id == 1590 || id == 1596 || id == 1602 || id == 1617 || id == 1632 || id == 1647 || id == 1662 || id == 1677 || id == 1692 || id == 1707 || id == 1722 || id == 1737 || id == 1832 || id == 1841 || id == 1850 || id == 1859 || id == 1868 || id == 1877 || id == 1886 || id == 1895 || id == 1904 || id == 1913 || id == 2022 || id == 2031 || id == 2040 || id == 2049 || id == 2058 || id == 2067 || id == 2076 || id == 2085 || id == 1187 || id == 1196 || id == 1205 || id == 1214 || id == 1223 || id == 1230 || id == 1236 || id == 1242 || id == 1249 || id == 2094 || id == 2103 || id == 2112 || id == 2123 || id == 2145 || id == 2156 || id == 2167 || id == 2178 || id == 2189 || id == 2200 || id == 2211 || id == 2222 || id == 2231 || id == 2240 || id == 2249 || id == 2258 || id == 2267 || id == 2276 || id == 2285 || id == 2294 || id == 2303 || id == 2312 || id == 2321 || id == 2330 || id == 2339 || id == 2348 || id == 2357 || id == 2366 || id == 2375 || id == 2384 || id == 2393 || id == 2402 || id == 2411 || id == 2420 || id == 2429 || id == 2438 || id == 2447 || id == 2456 || id == 2465 || id == 2474 || id == 2483 || id == 2492 || id == 2502 || id == 2512 || id == 2522 || id == 2532 || id == 2542 || id == 2552 || id == 2562 || id == 2572 || id == 2582 || id == 2592 || id == 2598 || id == 2604 || id == 2610 || id == 2621 || id == 2632 || id == 2643 || id == 2654 || id == 2663 || id == 2672 || id == 2681 || id == 2690 || id == 2699 || id == 2708 || id == 2717 || id == 2726 || id == 2735 || id == 2744 || id == 2753 || id == 2762 || id == 2772 || id == 2782 || id == 2792 || id == 2802 || id == 2817 || id == 2832 || id == 2847 || id == 2894 || id == 2903 || id == 2912 || id == 2921 || id == 2970 || id == 2979 || id == 3006 || id == 3012 || id == 3018 || id == 3024 || id == 3030 || id == 3036 || id == 3042 || id == 3048 || id == 3054 || id == 3062 || id == 3077 || id == 979 || id == 551 || id == 791 || id == 919 || id == 566 || id == 929 || id == 807 || id == 939 || id == 596 || id == 815 || id == 949 || id == 823 || id == 951 || id == 831 || id == 969 || id == 839 || id == 847 || id == 989 || id == 855 || id == 979 || id == 999 || id == 863 || id == 1009 || id == 879 || id == 1029 || id == 887 || id == 1049 || id == 1039 || id == 895 || id == 903 || id == 1059 || id == 911 || id == 1069 || id == 350 || id == 1922 || id == 358 || id == 1942 || id == 1768 || id == 1776 || id == 1952 || id == 1784) {

            if (((CheckBox) view).isChecked()) {
                for (ActivityTasks widget : activityTasklist) {
                    findViewById(widget.getId()).setVisibility(View.VISIBLE);
                    try {
                        findViewById(widget.getId() + 9000).setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                    Toast.makeText(ActivityTask.this, "CHECKED", Toast.LENGTH_SHORT).show();
            } else {
                // Need to disble remainign widgets
                for (ActivityTasks widget : activityTasklist) {
                    String optional = dataAccessHandler.getSingleValueInt(Queries.getIsoptionalField(widget.getId()));
                    Log.d(ActivityTask.class.getSimpleName(), "===> analysis ==> isOptional :" + optional);
                    if (optional != null && !StringUtils.isEmpty(optional)) {
                        findViewById(widget.getId()).setVisibility(View.GONE);
                        findViewById(widget.getId() + 9000).setVisibility(View.GONE);
                    }
                }
//                    Toast.makeText(ActivityTask.this, "UN-CHECKED", Toast.LENGTH_SHORT).show();
            }
        }


    }


    @Override
    public void onFocusChange(View view, boolean b) {
        // SetTextFor formula
        Log.d(ActivityTask.class.getSimpleName(), " ===> Analysis onFocusChange() id : " + view.getId() + "   isView showing :" + b);
        int id = view.getId();

        if (id == 51 || id == 52) {
            try {
                int int52 = 52, int53 = 53, int51 = 51;
                EditText edt53 = findViewById(int53);
                int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int51))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int52)));
//                edt53.setFocusable(false);
                edt53.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (id == 53 || id == 51) {
            try {
                int int51 = 51, int53 = 53, int54 = 54;
                EditText edt54 = findViewById(int54);
                int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int51))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int53)));
                edt54.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (id == 52 || id == 54) {
            try {
                int int52 = 52, int54 = 54, int55 = 55, int61 = 61;
                EditText edt55 = findViewById(int55);
                int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int52))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int54)));
                //    edt55.setText(finalValue + "");
                EditText edt61 = findViewById(int61);
                edt61.setText(finalValue + "");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (id == 60 || id == 61) {
            try {
                int int60 = 60, int61 = 61, int62 = 62;
                EditText edt62 = findViewById(int62);
                int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int60))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int61)));
                edt62.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (id == 508) {

            try {

                int int506 = 506, int508 = 508, int509 = 509;


                EditText edt509 = findViewById(int509);
                int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int506))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int508)));

                edt509.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (id == 512) {

            try {

                int int510 = 510, int512 = 512, int513 = 513;


                EditText edt513 = findViewById(int513);
                int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int510))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int512)));

                edt513.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (id == 516) {

            try {

                int int514 = 514, int516 = 516, int517 = 517;


                EditText edt517 = findViewById(int517);
                int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int514))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int516)));

                edt517.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (id == 520) {

            try {

                int int518 = 518, int520 = 520, int521 = 521;
                EditText edt521 = findViewById(int521);
                int finalValue = CommonUtils.getIntFromEditText(((EditText) findViewById(int518))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int520)));

                edt521.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (id == 523) {

            try {

                int int523 = 523, int524 = 524;


                EditText edt521 = findViewById(int524);
                int finalValue = Integer.parseInt(dataAccessHandler.getSingleValue(Queries.sproutsforSowing(consignmentCode, 521))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int523)));

                edt521.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (id == 526) {
            try {
                int int523 = 523, int527 = 527;
                EditText edt527 = findViewById(int527);
                int finalValue = Integer.parseInt(dataAccessHandler.getSingleValue(Queries.sproutsforSowing(consignmentCode, 513))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int523)));
                edt527.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == 530) {
            try {
                int int530 = 530, int531 = 531;
                EditText edt531 = findViewById(int531);
                int finalValue = Integer.parseInt(dataAccessHandler.getSingleValue(Queries.sproutsforSowing(consignmentCode, 527))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int530)));
                edt531.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (id == 534) {
            try {
                int int534 = 534, int535 = 535;
                EditText edt535 = findViewById(int535);
                int finalValue = Integer.parseInt(dataAccessHandler.getSingleValue(Queries.sproutsforSowing(consignmentCode, 527))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int534)));
                edt535.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == 538) {
            try {
                int int538 = 538, int539 = 539;
                EditText edt539 = findViewById(int539);
                int finalValue = Integer.parseInt(dataAccessHandler.getSingleValue(Queries.sproutsforSowing(consignmentCode, 535))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int538)));
                edt539.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == 541) {
            try {
                int int526 = 526, int541 = 541, int542 = 542;
                EditText edt542 = findViewById(int542);
                int finalValue = Integer.parseInt(dataAccessHandler.getSingleValue(Queries.sproutsforSowing(consignmentCode, 526))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int541)));
                edt542.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == 546) {
            try {
                int int546 = 546, int553 = 553;
                EditText edt553 = findViewById(int553);
                int finalValue = Integer.parseInt(dataAccessHandler.getSingleValue(Queries.sproutsforSowing(consignmentCode, 542))) - CommonUtils.getIntFromEditText(((EditText) findViewById(int546)));
                edt553.setText(finalValue + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class KeyValues {
    int id;
    String value;

    public KeyValues(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}