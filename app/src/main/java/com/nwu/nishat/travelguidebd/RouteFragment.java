package com.nwu.nishat.travelguidebd;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;



public class RouteFragment extends Fragment implements OnMapReadyCallback, DirectionCallback {

    GoogleMap mMap;
    public String placeName;
    public Double placeLat;
    public Double placeLong;
    LatLng destination;
    LatLng origin;


    public RouteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_route, container, false);

        if (getArguments() != null) {
            placeName = getArguments().getString("name");
            placeLat = getArguments().getDouble("lat");
            placeLong = getArguments().getDouble("long");
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.routeMap);

        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        Snackbar.make(getView(), "Direction Requesting...", Snackbar.LENGTH_SHORT).show();

        mMap = googleMap;

        destination = new LatLng(placeLat, placeLong);

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //double longitude = location.getLongitude();
        //double latitude = location.getLatitude();

        origin = new LatLng(22.815505, 89.543277);

        GoogleDirection.withServerKey("AIzaSyCv3t85QlP53C4GC0FO0blZjl2Sa1vkmLA")
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this);

    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        Snackbar.make( getView(), "Success with status : " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            mMap.addMarker(new MarkerOptions().position(origin));
            mMap.addMarker(new MarkerOptions().position(destination));

            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
            mMap.addPolyline(DirectionConverter.createPolyline(getContext(), directionPositionList, 5, Color.RED));
            setCameraWithCoordinationBounds(route);

        } else {
            Snackbar.make(getView(), "Not Ok : "+direction.getStatus(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Snackbar.make(getView(), "Failed : "+t.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120));
    }
}


