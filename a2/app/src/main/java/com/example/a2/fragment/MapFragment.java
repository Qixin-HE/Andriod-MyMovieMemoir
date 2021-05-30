package com.example.a2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.a2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    View Vmap;
    private GoogleMap mMap;
    private LatLng currentlocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        Vmap = inflater.inflate(R.layout.activity_maps, container, false);
        Intent intent = getActivity().getIntent();
        String location = intent.getStringExtra("location"); String[] latlong= location.split(",");
        double latitude = Double.parseDouble(latlong[0]); double longitude = Double.parseDouble(latlong[1]); currentlocation = new LatLng(latitude, longitude);

        return Vmap;
    }




    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(currentlocation).title("Mylocation"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentlocation)); " +
                        /*Zoom levels
                1: World
        5: Landmass/continent
        10: City
        15: Streets
        20: Buildings
                */
        float zoomLevel = (float) 10.0;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentlocation, zoomLevel));
    }


}


