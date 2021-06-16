package com.oilpalm3f.nursery.helper;

import android.database.Cursor;
import android.database.CursorWrapper;

public class CustomCursor extends CursorWrapper {

    public CustomCursor(Cursor cursor) { super(cursor); } //simple constructor

    public Integer getInteger(int columnIndex) { // new method to return Integer instead of int
        if (super.isNull(columnIndex)){
            return null;
        }else{
            return super.getInt(columnIndex);
        }
    }
}
