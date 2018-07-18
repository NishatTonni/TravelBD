package com.nwu.nishat.travelguidebd;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapsFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap mMap;
    public String placeName;
    public Double placeLat;
    public Double placeLong;


    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        if(getArguments() != null){
            placeName = getArguments().getString("name");
            placeLat = getArguments().getDouble("lat");
            placeLong = getArguments().getDouble("long");
        }

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng placeGeo = new LatLng( placeLat, placeLong);

        MarkerOptions options = new MarkerOptions();

        options.position(placeGeo).title(placeName);

        mMap.addMarker(options);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeGeo,15));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 4 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 4000, null);
    }
}
