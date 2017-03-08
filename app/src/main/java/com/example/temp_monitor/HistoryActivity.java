package com.example.temp_monitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by Mike on 2.3.2017.
 */


public class HistoryActivity extends Activity {
    final String TAG = "HistoryActivity";
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("Leppavaara/temperature");
    ArrayList<String> mylist=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i =getIntent();
        mylist = i.getStringArrayListExtra("mylist");
        //Log.d(TAG, "mylist size = "+ mylist.size());

        // should show the date clicked in listfragment
        Log.d(TAG, "date = "+i.getStringExtra("date"));
        setContentView(R.layout.history_activity);

        // create graph object here

        // start firebase query, showGraph(); inside! - remember it's fucked
        showGraph(); // have to change this to real-time db.
        // should just create the chart as empty and use the real-time features to add new datapoints


    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d(TAG, "mylist size= "+ mylist.size());
    }

    private void showGraph(){
        //Log.d(TAG, "nextPart mylist size= "+ mylist.size());
        String date = "08/02/2017"; // should get from onCreate, pass every child value here
        String time = "15:49:58";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(date+" "+time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("MainActivity", "date d to string = "+date.toString());
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d, 1),
                new DataPoint(d, 5),
                new DataPoint(d, 1),
                new DataPoint(d, 5),
                new DataPoint(d, 3)
        });
        graph.addSeries(series);
        graph.getGridLabelRenderer().setHumanRounding(false); //not used with dates
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Log.d("formatLabel", "double value = " +value);
                    String s = new SimpleDateFormat("HH:mm:ss").format(value);
                    Log.d("formatLabel", "string s = "+s);
                    // return super.formatLabel(value, isValueX);  -- this is the original
                    return s;
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " C";
                }
            }
        });

    }
}

