package com.example.temp_monitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("Leppavaara/newest/humidity");
    TextView tvhumidity;
    Button bhistory;
    Intent historyintent;
    ArrayList<String> mylist = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvhumidity = (TextView) findViewById(R.id.tvhumidity);
        bhistory = (Button) findViewById(R.id.bhistory);
        getAllValues();
        historyintent = new Intent(getApplicationContext(),HistoryActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Read from the database
        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                tvhumidity.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        bhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(historyintent);
            }
        });
    }
    private void getAllValues(){
        DatabaseReference mRef = mRootRef.child("Leppavaara/temperature");
        mRef.orderByChild("date").equalTo("01/03/2017").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Weather weather = dataSnapshot.getValue(Weather.class);
                mylist.add(weather.value);
                Log.d(TAG," date " + weather.date + " time " +weather.time+ " value "+weather.value);
                buildIntent();
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
    private void buildIntent(){
        Log.d(TAG, "mylist size"+ mylist.size());
        historyintent.putStringArrayListExtra("mylist", mylist);
    }
}
