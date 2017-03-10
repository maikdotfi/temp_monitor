package com.example.temp_monitor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import com.jjoe64.graphview.Viewport;
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
    LineGraphSeries<DataPoint> series;
    String date;
    GraphView graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i =getIntent();
        mylist = i.getStringArrayListExtra("mylist");
        //Log.d(TAG, "mylist size = "+ mylist.size());

        // should show the date clicked in listfragment
        Log.d(TAG, "date = "+i.getStringExtra("date"));
        date = i.getStringExtra("date");
        setContentView(R.layout.history_activity);
        // graph view object created
        graph = (GraphView) findViewById(R.id.graph);
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
                    // show C for y values
                    Log.d("formatLabel", "double "+ Double.toString(value));
                    return super.formatLabel(value, isValueX) + " C°";
                }
            }
        });

        series = new LineGraphSeries<DataPoint>();

        // formating stuff for the graph

        // start firebase query, append datapoints to chart
        getXYvalues(date);
        //showGraph(); // have to change this to real-time db.
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d(TAG, "mylist size= "+ mylist.size());
    }

    private void graphFormating(GraphView graph, Date d, double d1){
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(d1-10);
        graph.getViewport().setMaxY(d1+15);
        Log.d("graphFormating", "date = "+ Double.toString(d.getTime()));
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(d.getTime());
        graph.getViewport().setMaxX(d.getTime()+35000);
        graph.getViewport().scrollToEnd();

        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling
        graph.getViewport().setDrawBorder(true);
        graph.getViewport().setBackgroundColor(Color.argb(200, 238, 152, 250));
        graph.setTitle("Temperature history "+date);

    }

    private void getXYvalues(String date){
        DatabaseReference mRef = mRootRef.child("Leppavaara/temperature");
        mRef.orderByChild("date").equalTo(date).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Weather weather = dataSnapshot.getValue(Weather.class);
                if (weather.value == null){
                    weather.value = "0";
                    Log.d(TAG, "weather.value null");
                }
                // have to convert values to double for the chart
                Log.d(TAG," date " + weather.date + " time " +weather.time+ " value "+weather.value.toString());
                // getting the date value
                String date = weather.date; // should get from onCreate, pass every child value here
                String time = weather.time;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date d = null;
                try {
                    d = sdf.parse(date+" "+time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "date d to string = "+d.toString());
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                // convert value to double
                double d1 = Double.parseDouble(weather.value);
                Log.d(TAG, "double d1 from weather value = "+ Double.toString(d1));
                // append to chart here
                series.appendData(new DataPoint(d, d1), true, 10);
                graph.addSeries(series);
                graphFormating(graph, d, d1);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

