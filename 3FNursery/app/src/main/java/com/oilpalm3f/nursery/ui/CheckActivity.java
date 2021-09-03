package com.oilpalm3f.nursery.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.oilpalm3f.nursery.R;
import com.oilpalm3f.nursery.common.DateTimeUtil;
import com.oilpalm3f.nursery.database.DataAccessHandler;
import com.oilpalm3f.nursery.database.Queries;
import com.oilpalm3f.nursery.dbmodels.SaplingActivity;
import com.oilpalm3f.nursery.ui.Adapter.RecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static com.oilpalm3f.nursery.common.DateTimeUtil.stringTodate;

public class CheckActivity extends AppCompatActivity {
    public static String TAG = CheckActivity.class.getSimpleName();
    private CompactCalendarView compactCalendarView;
    RecyclerAdapter recyclerAdapter;
    ListView recyclerView;
    TextView emptyView;
    private TextView textView;
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private ActionBar toolbar;
     ImageButton showPreviousMonthBut,showNextMonthBut;
    final List<String> mutableBookings = new ArrayList<>();
     List<SaplingActivity> saplingActivitiesList = new ArrayList<>();
     DataAccessHandler dataAccessHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        intviews();
        setview();
    }
    private void intviews() {

        dataAccessHandler = new DataAccessHandler(this);
        emptyView = findViewById(R.id.empty_view);
        recyclerView = findViewById(R.id.event_listview);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));


        showPreviousMonthBut = findViewById(R.id.prev_button);
          showNextMonthBut = findViewById(R.id.next_button);
        textView = findViewById(R.id.month_title);

        compactCalendarView = findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setIsRtl(false);
        compactCalendarView.displayOtherMonthDays(false);
//        loadEvents();
//        loadEventsForYear(2017);
        compactCalendarView.invalidate();
        saplingActivitiesList = dataAccessHandler.getSaplingActivityDataa(Queries.getInstance().getupdateddates());
        Log.e("==>listsize",   saplingActivitiesList.size()+"" );

        logEventsByMonth(compactCalendarView, DateTimeUtil.onGetCurrentDate(this).substring(0, 7));
        toolbar = ((AppCompatActivity) this).getSupportActionBar();
        textView.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

    }

    private void loadEvents() {
        addEvents(-1, -1);
        addEvents(Calendar.DECEMBER, -1);
        addEvents(Calendar.AUGUST, -1);
    }
    private void addEvents(int month, int year) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        for (int i = 0; i < 6; i++) {
            currentCalender.setTime(firstDayOfMonth);
            if (month > -1) {
                currentCalender.set(Calendar.MONTH, month);
            }
            if (year > -1) {
                currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
                currentCalender.set(Calendar.YEAR, year);
            }
            currentCalender.add(Calendar.DATE, i);
            setToMidnight(currentCalender);
            long timeInMillis = currentCalender.getTimeInMillis();

            List<Event> events = getEvents(timeInMillis, i);

            compactCalendarView.addEvents(events);
        }
    }

    private List<Event> getEvents(long timeInMillis, int day) {
        if (day < 2) {
            return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)));
        } else if ( day > 2 && day <= 4) {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)));
        } else {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis) ),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 70, 68, 65), timeInMillis, "Event 3 at " + new Date(timeInMillis)));
        }
    }
    private void loadEventsForYear(int year) {
        addEvents(Calendar.DECEMBER, year);
        addEvents(Calendar.AUGUST, year);
    }
    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
    private void setview() {
         ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mutableBookings);
        recyclerView.setAdapter(adapter);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                textView.setText(dateFormatForMonth.format(dateClicked));
                Log.d(TAG, "OnDate Selected :" + DateTimeUtil.DateToString(dateClicked));
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                if (bookingsFromMap != null) {
                    Log.d(TAG, bookingsFromMap.toString());
                    mutableBookings.clear();
                    for (Event booking : bookingsFromMap) {
                        mutableBookings.add((String) booking.getData());
                    }
                    adapter.notifyDataSetChanged();
                }


//                selecteddate = dateClicked;
//                List<RequestCompleteModel> data2 = databaseQueryClass.getRequestsSpecificDate(DateTimeUtil.DateToString(dateClicked));
//
//                if (data2.size() > 0) {
//
//                    recyclerAdapter = new RecyclerAdapter(getActivity(), data2, HomeFragment.this,databaseQueryClass);
//                    recyclerView.setAdapter(recyclerAdapter);
//                    emptyView.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                } else {
//                    emptyView.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
//
//                }


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                textView.setText(dateFormatForMonth.format(firstDayOfNewMonth));
                Log.d(TAG, "On Month Change :" + DateTimeUtil.DateToString(firstDayOfNewMonth));
                logEventsByMonth(compactCalendarView, DateTimeUtil.DateToString(firstDayOfNewMonth).substring(0, 7));
            }
        });


        showPreviousMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.scrollLeft();
            }
        });

        showNextMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.scrollRight();
            }
        });

    }

    private void logEventsByMonth(CompactCalendarView compactCalendarView, String Date) {


        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        //for (SyncRequestHeaderResponse.ListResult data:databaseQueryClass.getCurrentMonthRequests(DateTimeUtil.onGetCurrentDate(getContext()))) {
        //String date=DateTimeUtil.onGetCurrentDate(getContext()).substring(0,7);
        String date = Date;
        Log.d(TAG, "final Month :" + date);

      for(int i = 0; i < saplingActivitiesList.size(); i ++) {
//            //Event ev1 = new Event(Color.GREEN, stringTodate(data.getReqCreatedDate()).getTime(),"mallem");

          saplingActivitiesList = dataAccessHandler.getSaplingActivityDataa(Queries.getInstance().getupdateddates());
          Log.e("==>listsize",   saplingActivitiesList.size()+"" );
          String updateddate = saplingActivitiesList.get(i).getUpdatedDate();

          Log.d(TAG, "Each Event :" + updateddate);
            Event ev1 = new Event(Color.RED, stringTodate(updateddate).getTime(), "Test data");
            try {
                compactCalendarView.removeEvent(ev1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            compactCalendarView.addEvent(ev1);
        }

  }
    @Override
    public void onResume() {
        super.onResume();
        textView.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        // Set to current day on resume to set calendar to latest day
        textView.setText(dateFormatForMonth.format(new Date()));
    }

}