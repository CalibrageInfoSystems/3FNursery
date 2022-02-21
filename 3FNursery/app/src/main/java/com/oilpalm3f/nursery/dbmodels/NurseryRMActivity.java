package com.oilpalm3f.nursery.dbmodels;

public class NurseryRMActivity {
    private Integer Id;



    private  String ActivityName;
    public NurseryRMActivity(String activityName) {
        ActivityName = activityName;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }
}
