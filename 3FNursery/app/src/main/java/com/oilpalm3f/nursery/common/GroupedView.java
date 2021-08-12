package com.oilpalm3f.nursery.common;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.oilpalm3f.nursery.dbmodels.ActivityTasks;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class GroupedView {
    List<ActivityTasks> activityTasklist = new ArrayList<>();
    String title;
    Context ctx;
    View itemView;

    public GroupedView(Context context, List<ActivityTasks> activityTasklist, String title, View itemView) {

        this.ctx = context;
        this.title = title;
        this.activityTasklist = activityTasklist;
        this.itemView = itemView;

    }

    public LinearLayout builldUI() {
        LinearLayout parent = new LinearLayout(ctx);

        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.HORIZONTAL);

        parent.addView(addTitle(title));

        for (int i = 0; i < activityTasklist.size(); i++) {
            parent.addView(addEdittext(activityTasklist.get(i).getField(), activityTasklist.get(i).getId(), activityTasklist.get(i).getDataType()));
        }
        return parent;
    }

    public TextView addTitle(String title) {
        TextView tv = new TextView(ctx);
        tv.setText(title);
        return tv;
    }

    public TextInputLayout addEdittext(String content, int id, String dataType) {

        TextInputLayout textInputLayout = new TextInputLayout(ctx);
        textInputLayout.setId(id + 9000);
        EditText et = new EditText(ctx);

//        et.setHint(content);
        et.setId(id);
        et.setMinLines(1);
        et.setMaxLines(1);

        if (dataType.equalsIgnoreCase("Integer") || dataType.equalsIgnoreCase("Float")) {
            et.setInputType(InputType.TYPE_CLASS_NUMBER);

        }
        textInputLayout.setHint(content);
        textInputLayout.addView(et);


        return textInputLayout;

    }

    public boolean validatetlestOneItem() {
        boolean isValid = false;
        for (int i = 0; i < activityTasklist.size(); i++) {
            EditText ext = itemView.findViewById(activityTasklist.get(i).getId());
            if (StringUtils.isEmpty(ext.getText())) {
                isValid = true;
            }

        }
        return isValid;
    }
}
