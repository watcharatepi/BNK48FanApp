package com.dotnextstudio.com.bnk48fanapp;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yalantis.phoenix.PullToRefreshView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


/**
 * Created by niccapdevila on 3/26/16.
 */
public class CalBaseFragment extends Fragment {

    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    public static final String ARGS_INSTANCE = "com.dotnextstudio.com.bnk48fanapp.argsInstance";
    private ActionBar toolbar;
    final List<String> mutableBookings = new ArrayList<>();
    public  CompactCalendarView compactCalendarView ;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

    private DatabaseReference mDatabase;
    int mInt = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mInt = args.getInt(ARGS_INSTANCE);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


     //   String key =  mDatabase.child("events").push().getKey();
       // CalEvent ev = new CalEvent("Digital Live","1499252400000","","kidcat,jan","Digital Live", "", key);

        //Log.i("dev","getTitle==>"+key);
       //  mDatabase.child("events").child(key).setValue(ev);





    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cal_menu, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);


        final ListView bookingsListView = (ListView) view.findViewById(R.id.bookings_listview);
        final PullToRefreshView mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 1000);
            }
        });


        currentCalender.setTime(new Date());

        long timeInMillis = currentCalender.getTimeInMillis();
        Log.d("dev","timeInMillis==>"+timeInMillis);

       // final ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mutableBookings);

        final ChatUserAdapter adapter = new ChatUserAdapter(getActivity(),0, mutableBookings);

        mDatabase.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                for (DataSnapshot msgSnapshot: snapshot.getChildren()) {

                    CalEvent ev = msgSnapshot.getValue(CalEvent.class);
                    Log.d("dev","===>"+ev.getDate()+""+ev.getTitle()+""+ev.getMember()+""+ev.getUpdated_time());
                    addEventsbyfirebase(ev.getDate(),ev.getTitle(),ev.getMember(),ev.getUpdated_time());
                }



//        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
                //toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                // toolbar.setTitle("Calendar");


                bookingsListView.setAdapter(adapter);
                ViewCompat.setNestedScrollingEnabled(bookingsListView, true);

                compactCalendarView.setUseThreeLetterAbbreviation(true);

                // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
                // Use constants provided by Java Calendar class
                compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);


                //Event ev1 = new Event(Color.GREEN, 1499234048695L, "Some extra data that I want to store.");
                // compactCalendarView.addEvent(ev1);

               // loadEvents();
                //loadEventsForYear(2017);
                //compactCalendarView.invalidate();

                logEventsByMonth(compactCalendarView);
                compactCalendarView.invalidate();
                Log.d("dev","==>"+compactCalendarView);


            }
            @Override
            public void onCancelled(DatabaseError error) {
                //Log.e("Chat", "The read failed: " + error.getText());
            }
        });

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
               // toolbar.setTitle(dateFormatForMonth.format(dateClicked));
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                Log.d("dev", "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                if (bookingsFromMap != null) {
                    Log.d("dev", bookingsFromMap.toString());
                    mutableBookings.clear();
                    for (Event booking : bookingsFromMap) {
                        mutableBookings.add((String) booking.getData());
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
    //                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });





        return view;
    }

    private void loadEvents() {
     //   addEvents(-1, -1);
       // addEvents(Calendar.DECEMBER, -1);
        // addEvents(Calendar.JULY, -1,15);
    }

    private void loadEventsForYear(int year) {
        //addEvents(Calendar.DECEMBER, year);
       // addEvents(Calendar.AUGUST, year);
    }

    private void logEventsByMonth(CompactCalendarView compactCalendarView) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        currentCalender.set(Calendar.MONTH, Calendar.JULY);
        List<String> dates = new ArrayList<>();
        for (Event e : compactCalendarView.getEventsForMonth(new Date())) {
            dates.add(dateFormatForDisplaying.format(e.getTimeInMillis()));
        }
        Log.d("dev", "Events for Aug with simple date formatter: " + dates);
        Log.d("dev", "Events for Aug month using default local and timezone: " + compactCalendarView.getEventsForMonth(currentCalender.getTime()));
    }


    private void addEventsbyfirebase(String date, String title , String member , String id) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DATE, 15);
    
       // Event ev = new Event(Color.argb(255, 169, 68, 65), Long.parseLong(date), title);
       // List<Event> events = null;
    Log.d("dev","==>"+Long.parseLong(date));
        List<Event> events = getEvents(Long.parseLong(date), title);

        Log.d("dev", "Events: " + events);
        compactCalendarView.addEvents(events);

    }

    private void addEvents(int month, int year,int day) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DATE, day);
        Date firstDayOfMonth = currentCalender.getTime();
        for (int i = 0; i < 1; i++) {
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

         //   List<Event> events = getEvents(timeInMillis, day);

           // compactCalendarView.addEvents(events);
        }

    }

    private List<Event> getEvents(long timeInMillis, String day) {

        return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)));

        /*if (day < 2) {
            return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)));
        } else if ( day > 2 && day <= 4) {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)));
        } else {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event 1 at " + new Date(timeInMillis) ),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 3 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 4 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 5 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 6 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 7 at " + new Date(timeInMillis)),

                    new Event(Color.argb(255, 70, 68, 65), timeInMillis, "Event 8 at " + new Date(timeInMillis)));

        }*/


    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public class ChatUserAdapter extends ArrayAdapter<String> {

        private Activity context;
        private List<String> userList;
        private int ilayout;

        public ChatUserAdapter(FragmentActivity context, int layout, List<String> userList) {
            super(context, layout, userList);
            this.context = context;
            this.userList = userList;
            this.ilayout = layout;
        }




        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            public TextView tvEmail;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater layoutInflater = context.getLayoutInflater();
                rowView = layoutInflater.inflate(R.layout.item_cal, null, true);
                viewHolder = new ViewHolder();
                Log.d("dev","==>"+position);
               // viewHolder.tvEmail = (TextView) rowView.findViewById(R.id.tv_email);
               // rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }

          //  UserChat item = userList.get(position);
            //viewHolder.tvEmail.setText(item.getName() + " " + item.getSurname());
            return rowView;
        }
    }


}