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

import java.lang.reflect.Array;
import java.util.ArrayList;
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
        Log.d(TAG, "mylist size = "+ mylist.size());
        setContentView(R.layout.history_activity);
        nextPart();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d(TAG, "mylist size= "+ mylist.size());
    }

    private void nextPart(){
        //Log.d(TAG, "nextPart mylist size= "+ mylist.size());
    }
}

class Weather {
    public String date;
    public String time;
    public String value;
    public Weather(){
    }
    public Weather(String date, String time, String value){
    }
}