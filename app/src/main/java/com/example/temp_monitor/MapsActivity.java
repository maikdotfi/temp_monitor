package com.example.temp_monitor;

import android.app.Activity;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Mike on 10.3.2017.
 */

// REMEMBER TO INSTALL GOOGLE PLAY SERVICES FROM SDK MANAGER -> SDK TOOLS TAB, than add the dependency

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    static final LatLng lepuski = new LatLng(60.220805, 24.805210);
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_activity);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(lepuski).title("Leppävaara"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lepuski));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lepuski, 15.5f));
    }
}